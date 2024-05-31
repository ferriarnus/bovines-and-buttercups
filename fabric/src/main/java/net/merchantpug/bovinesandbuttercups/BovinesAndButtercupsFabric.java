package net.merchantpug.bovinesandbuttercups;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.merchantpug.bovinesandbuttercups.client.util.CowTextureReloadListenerFabric;
import net.merchantpug.bovinesandbuttercups.content.entity.Moobloom;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncLockdownEffectsClientboundPacket;
import net.merchantpug.bovinesandbuttercups.platform.BovinesPlatformHelperFabric;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypeTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEffects;
import net.merchantpug.bovinesandbuttercups.registry.BovinesEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesFabricDynamicRegistries;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesLootItemConditionTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesParticleTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.merchantpug.bovinesandbuttercups.registry.BovinesSoundEvents;
import net.merchantpug.bovinesandbuttercups.registry.BovinesStructureTypes;
import net.merchantpug.bovinesandbuttercups.util.CreativeTabHelper;
import net.minecraft.core.Registry;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.stream.Stream;

public class BovinesAndButtercupsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        BovinesAndButtercups.init(new BovinesPlatformHelperFabric());

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new CowTextureReloadListenerFabric());

        registerContents();
        registerNetwork();
        registerCreativeTabEntries();

        BovinesFabricDynamicRegistries.init();

        FabricDefaultAttributeRegistry.register(BovinesEntityTypes.MOOBLOOM, Moobloom.createAttributes());
    }

    public static void registerNetwork() {
        PayloadTypeRegistry.playS2C().register(SyncLockdownEffectsClientboundPacket.TYPE, SyncLockdownEffectsClientboundPacket.STREAM_CODEC);
    }

    public static void registerContents() {
        BovinesBlockEntityTypes.registerAll(Registry::register);
        BovinesBlocks.registerAll(Registry::register);
        BovinesCowTypeTypes.registerAll(Registry::register);
        BovinesDataComponents.registerAll(Registry::register);
        BovinesEffects.registerAll(Registry::registerForHolder);
        BovinesEntityTypes.registerAll(Registry::register);
        BovinesLootItemConditionTypes.registerAll(Registry::register);
        BovinesItems.registerAll(Registry::register);
        BovinesParticleTypes.registerAll(Registry::register);
        BovinesSoundEvents.registerAll(Registry::register);
        BovinesStructureTypes.registerAll(Registry::register);


    }

    public static void registerCreativeTabEntries() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.LILY_OF_THE_VALLEY, Stream.of(
                    BovinesItems.FREESIA,
                    BovinesItems.BIRD_OF_PARADISE,
                    BovinesItems.BUTTERCUP,
                    BovinesItems.LIMELIGHT,
                    BovinesItems.CHARGELILY,
                    BovinesItems.TROPICAL_BLUE,
                    BovinesItems.HYACINTH,
                    BovinesItems.PINK_DAISY,
                    BovinesItems.SNOWDROP).map(ItemStack::new).toList());
            entries.addAfter(BovinesItems.SNOWDROP, CreativeTabHelper.getCustomFlowersForCreativeTab(entries.getContext().holders()));
            entries.addAfter(Items.RED_MUSHROOM, CreativeTabHelper.getCustomMushroomsForCreativeTab(entries.getContext().holders()));
            entries.addAfter(Items.RED_MUSHROOM_BLOCK, CreativeTabHelper.getCustomMushroomBlocksForCreativeTab(entries.getContext().holders()));
        });
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries ->
                entries.addAfter(Items.MILK_BUCKET, CreativeTabHelper.getNectarBowlsForCreativeTab(entries.getContext().holders())));
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.accept(BovinesItems.MOOBLOOM_SPAWN_EGG);
        });
    }
}
