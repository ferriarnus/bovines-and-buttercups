package net.merchantpug.bovinesandbuttercups.client.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovinesBlockstateTypeRegistry;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

// TODO: Remove as soon as data driven blocks are introduced.
public class BovineStateModelUtil {
    private static final BlockModelDefinition.Context CONTEXT = new BlockModelDefinition.Context();
    private static final Map<ResourceLocation, ResourceLocation> MODEL_TO_TYPE_MAP = new HashMap<>();
    private static final Map<ResourceLocation, JsonElement> LOADED_JSON = new HashMap<>();

    public static CompletableFuture<List<ResourceLocation>> getModels(ResourceManager manager, Executor executor) {
        BovineStatesAssociationRegistry.clear();
        return CompletableFuture.supplyAsync(() -> manager.listResources("bovinesandbuttercups", fileName -> fileName.getPath().endsWith(".json")), executor).thenCompose(blocks -> {
            ArrayList<CompletableFuture<List<ResourceLocation>>> models = new ArrayList<>();

            for (Map.Entry<ResourceLocation, Resource> resourceEntry : blocks.entrySet()) {
                models.add(CompletableFuture.supplyAsync(() -> {
                    ResourceLocation resourceLocation = resourceEntry.getKey().withPath(s -> s.substring(0, s.length() - 5));

                    try {
                        Reader reader = resourceEntry.getValue().openAsReader();
                        JsonElement json = JsonParser.parseReader(reader);
                        reader.close();
                        if (json instanceof JsonObject jsonObject) {
                            if (!jsonObject.has("type")) {
                                BovinesAndButtercups.LOG.error("Could not find 'type' field inside bovinestate json: {}.", resourceLocation);
                                return List.of();
                            }
                            ResourceLocation typeLocation = ResourceLocation.parse(jsonObject.get("type").getAsString());
                            StateDefinition<Block, BlockState> tempStateDefinition = null;
                            if (!Objects.equals(typeLocation, BovinesAndButtercups.asResource("item"))) {
                                tempStateDefinition = BovinesBlockstateTypeRegistry.get(typeLocation);
                                if (tempStateDefinition == null) {
                                    BovinesAndButtercups.LOG.error("Could not find 'type' field value '{}' in bovinestate type registry.", typeLocation);
                                    return List.of();
                                }
                            }

                            if (tempStateDefinition == null) {
                                if (jsonObject.has("inventory")) {
                                    ResourceLocation resourceLocation1 = ResourceLocation.tryParse(jsonObject.get("inventory").getAsString());
                                    if (resourceLocation1 == null) {
                                        BovinesAndButtercups.LOG.error("Could not create valid resource location from string '{}'.", jsonObject.get("inventory").getAsString());
                                        return List.of();
                                    } else {
                                        ResourceLocation inventoryModelLocation = resourceLocation1.withPath(s -> "bovinesandbuttercups/item/" + s + "/inventory");
                                        BovineStatesAssociationRegistry.registerItem(resourceLocation, null, true, inventoryModelLocation);
                                        MODEL_TO_TYPE_MAP.put(inventoryModelLocation, typeLocation);
                                        return List.of(inventoryModelLocation);
                                    }
                                }
                                BovinesAndButtercups.LOG.error("Could not find 'inventory' field in 'bovinesandbuttercups:item' bovinestate file '{}'", resourceEntry.getKey());
                                return List.of();
                            }

                            List<ResourceLocation> modelList = new ArrayList<>();
                            StateDefinition<Block, BlockState> stateDefinition = tempStateDefinition;
                            ImmutableList<BlockState> possibleStates = stateDefinition.getPossibleStates();
                            CONTEXT.setDefinition(stateDefinition);

                            if (jsonObject.has("linked_block_type")) {
                                ResourceLocation linkedType = ResourceLocation.tryParse(jsonObject.get("linked_block_type").getAsString());

                                if (linkedType == null) {
                                    BovinesAndButtercups.LOG.error("Could not parse linked_block_type from key: {}.", jsonObject.get("linked_block_type").getAsString());
                                }

                                BovineStatesAssociationRegistry.registerBlock(linkedType, tempStateDefinition, resourceLocation);

                                if (jsonObject.has("inventory")) {
                                    ResourceLocation resourceLocation1 = ResourceLocation.tryParse(jsonObject.get("inventory").getAsString());
                                    if (resourceLocation1 == null) {
                                        BovinesAndButtercups.LOG.error("Could not create valid resource location from string '{}'.", jsonObject.get("inventory").getAsString());
                                    } else {
                                        ResourceLocation inventoryModelLocation = resourceLocation1.withPath(s -> "bovinesandbuttercups/item/" + s + "/inventory");
                                        BovineStatesAssociationRegistry.registerItem(linkedType, stateDefinition, false, inventoryModelLocation);
                                        MODEL_TO_TYPE_MAP.put(inventoryModelLocation, typeLocation);
                                        modelList.add(inventoryModelLocation);
                                    }
                                }
                            } else {
                                BovineStatesAssociationRegistry.registerBlock(resourceLocation, tempStateDefinition, resourceLocation);
                            }


                            for (BlockState state : possibleStates) {
                                ResourceLocation stateResource = resourceLocation.withPath(s ->
                                        s + "/" + acceptedStateProperties(BlockModelShaper.statePropertiesToString(state.getValues()))
                                );
                                MODEL_TO_TYPE_MAP.put(stateResource, typeLocation);
                                LOADED_JSON.put(stateResource, json);
                                modelList.add(stateResource);
                            }

                            return modelList;
                        }
                    } catch (Exception ex) {
                        BovinesAndButtercups.LOG.error("Exception in bovinestate type registry: ", ex);
                        return List.of();
                    }
                    BovinesAndButtercups.LOG.error("Unexpected error in bovinestate type registry: {}.", resourceEntry.getKey());
                    return List.of();
                }, executor));
            }
            return Util.sequenceFailFast(models).thenApply(m -> m.stream().flatMap(Collection::stream).filter(Objects::nonNull).toList());
        });
    }

    public static UnbakedModel getUnbakedModel(ResourceLocation modelId, Function<ResourceLocation, UnbakedModel> itemFunction) {
        if (modelId == null)
            return null;

        if (MODEL_TO_TYPE_MAP.containsKey(modelId)) {
            ResourceLocation typeKey = MODEL_TO_TYPE_MAP.get(modelId);
            MODEL_TO_TYPE_MAP.remove(modelId);
            if (modelId.getPath().endsWith("/inventory")) {
                ResourceLocation itemModelId = modelId.withPath(s -> {
                    String noBovinesHeader = s.substring(21);
                    return noBovinesHeader.substring(0, noBovinesHeader.length() - 10);
                });
                return itemFunction.apply(itemModelId);
            }

            StateDefinition<Block, BlockState> stateDefinition = BovinesBlockstateTypeRegistry.get(typeKey);
            if (stateDefinition == null)
                return null;

            CONTEXT.setDefinition(stateDefinition);

            JsonElement json = LOADED_JSON.get(modelId);
            LOADED_JSON.remove(modelId);

            BlockModelDefinition definition = BlockModelDefinition.fromJsonElement(CONTEXT, json);

            if (definition.isMultiPart())
                return definition.getMultiPart();

            String variant = getVariant(modelId);
            if (definition.getVariants().containsKey(variant))
                return definition.getVariants().get(variant);
            else
                return definition.getVariants().get("");
        }
        return null;
    }

    public static String acceptedStateProperties(String stateProperties) {
        return stateProperties.replaceAll("=", ".").replaceAll(",", "-");
    }

    private static String getVariant(ResourceLocation modelId) {
        String path = modelId.getPath();
        if (path.lastIndexOf("/") == path.length() - 1)
            return "";
        return path.substring(path.lastIndexOf("/") + 1).replaceAll("\\.", "=").replaceAll("-", ",");
    }

}
