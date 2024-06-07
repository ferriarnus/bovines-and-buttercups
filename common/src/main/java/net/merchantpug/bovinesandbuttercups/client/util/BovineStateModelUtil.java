package net.merchantpug.bovinesandbuttercups.client.util;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovinesBlockstateTypeRegistry;
import net.merchantpug.bovinesandbuttercups.client.bovinestate.BovineStatesAssociationRegistry;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BlockModelDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BovineStateModelUtil {
    private static final BlockModelDefinition.Context CONTEXT = new BlockModelDefinition.Context();
    private static final Map<ModelResourceLocation, ResourceLocation> MODEL_TO_TYPE_MAP = new HashMap<>();
    private static final Map<ModelResourceLocation, JsonElement> LOADED_JSON = new HashMap<>();

    public static List<ModelResourceLocation> getModels(ResourceManager manager) {
        BovineStatesAssociationRegistry.clear();
        List<ModelResourceLocation> models = new ArrayList<>();
        Map<ResourceLocation, Resource> blocks = manager.listResources("blockstates/bovinesandbuttercups", fileName -> fileName.getPath().endsWith(".json"));

        for (Map.Entry<ResourceLocation, Resource> resourceEntry : blocks.entrySet()) {
            StringBuilder newIdBuilder = new StringBuilder(resourceEntry.getKey().getPath());
            newIdBuilder.replace(0, 12, "");
            newIdBuilder.replace(newIdBuilder.length() - 5, newIdBuilder.length(), "");
            String newId = newIdBuilder.toString();
            ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(resourceEntry.getKey().getNamespace(), newId);

            try {
                Reader reader = resourceEntry.getValue().openAsReader();
                JsonElement json = JsonParser.parseReader(reader);
                reader.close();
                if (json instanceof JsonObject jsonObject) {
                    if (!jsonObject.has("type")) {
                        BovinesAndButtercups.LOG.error("Could not find 'type' field inside bovinestate json: {}.", resourceLocation);
                        continue;
                    }
                    ResourceLocation typeLocation = ResourceLocation.tryParse(jsonObject.get("type").getAsString());
                    StateDefinition<Block, BlockState> tempStateDefinition = null;
                    if (!Objects.equals(typeLocation, BovinesAndButtercups.asResource("item"))) {
                        try {
                            tempStateDefinition = BovinesBlockstateTypeRegistry.get(typeLocation);
                        } catch (NullPointerException e) {
                            BovinesAndButtercups.LOG.warn("Could not find 'type' field value in bovinestate type registry. (Skipping). {}", e.getMessage());
                            continue;
                        }
                    }

                    if (tempStateDefinition == null) {
                        if (jsonObject.has("inventory")) {
                            ResourceLocation resourceLocation1 = ResourceLocation.tryParse(jsonObject.get("inventory").getAsString());
                            if (resourceLocation1 == null) {
                                BovinesAndButtercups.LOG.warn("Could not create valid resource location from string '{}'.", jsonObject.get("inventory").getAsString());
                            } else {
                                BovineStatesAssociationRegistry.registerItem(resourceLocation, null, true, resourceLocation1);
                                ModelResourceLocation inventoryModelLocation = new ModelResourceLocation(resourceLocation1, "inventory");
                                models.add(inventoryModelLocation);
                                MODEL_TO_TYPE_MAP.put(inventoryModelLocation, typeLocation);
                            }
                        }
                        continue;
                    }

                    StateDefinition<Block, BlockState> stateDefinition = tempStateDefinition;
                    ImmutableList<BlockState> possibleStates = stateDefinition.getPossibleStates();
                    CONTEXT.setDefinition(stateDefinition);

                    if (jsonObject.has("linked_block_type")) {
                        ResourceLocation linkedType = ResourceLocation.tryParse(jsonObject.get("linked_block_type").getAsString());

                        if (linkedType == null) {
                            BovinesAndButtercups.LOG.info("Could not parse linked_block_type from key: {}.", jsonObject.get("linked_block_type").getAsString());
                            continue;
                        }

                        BovineStatesAssociationRegistry.registerBlock(linkedType, tempStateDefinition, resourceLocation);

                        if (jsonObject.has("inventory")) {
                            ResourceLocation resourceLocation1 = ResourceLocation.tryParse(jsonObject.get("inventory").getAsString());
                            if (resourceLocation1 == null) {
                                BovinesAndButtercups.LOG.warn("Could not create valid resource location from string '{}'.", jsonObject.get("inventory").getAsString());
                            } else {
                                BovineStatesAssociationRegistry.registerItem(linkedType, stateDefinition, false, resourceLocation1);
                                ModelResourceLocation inventoryModelLocation = new ModelResourceLocation(resourceLocation1, "inventory");
                                models.add(inventoryModelLocation);
                                MODEL_TO_TYPE_MAP.put(inventoryModelLocation, typeLocation);
                            }
                        }
                    }

                    for (BlockState state : possibleStates) {
                        ModelResourceLocation stateResource = new ModelResourceLocation(resourceLocation, BlockModelShaper.statePropertiesToString(state.getValues()));
                        models.add(stateResource);
                        MODEL_TO_TYPE_MAP.put(stateResource, typeLocation);
                        LOADED_JSON.put(stateResource, json);
                    }
                }
            } catch (Exception ignored) {

            }
        }
        return models;
    }

    public static UnbakedModel getVariantModel(ModelResourceLocation modelId) {
        if (modelId.id().getPath().startsWith("bovinesandbuttercups/") && modelId instanceof ModelResourceLocation modelLocation && !modelLocation.getVariant().equals("inventory")) {
            ResourceLocation typeKey = MODEL_TO_TYPE_MAP.get(modelId);
            MODEL_TO_TYPE_MAP.remove(modelId);
            StateDefinition<Block, BlockState> stateDefinition = BovinesBlockstateTypeRegistry.get(typeKey);
            if (stateDefinition == null)
                return null;

            CONTEXT.setDefinition(stateDefinition);

            JsonElement json = LOADED_JSON.get(modelId);
            LOADED_JSON.remove(modelId);

            BlockModelDefinition definition = BlockModelDefinition.fromJsonElement(CONTEXT, json);

            if (definition.isMultiPart())
                return definition.getMultiPart();

            if (definition.hasVariant(modelLocation.getVariant()))
                return definition.getVariant(modelLocation.getVariant());
            else
                return definition.getVariant("");
        }
        return null;
    }

}
