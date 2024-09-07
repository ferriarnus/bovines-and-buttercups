package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.content.data.flowercrown.FlowerCrownPetal;
import net.merchantpug.bovinesandbuttercups.registry.internal.HolderRegistrationCallback;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.function.Consumer;

@EventBusSubscriber(modid = BovinesAndButtercups.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class BovinesRegistryEvents {
    @SubscribeEvent
    public static void registerContent(RegisterEvent event) {
        register(event, BovinesAttachments::registerAll);
        register(event, BovinesBiomeModifierSerializers::registerAll);
        register(event, BovinesBlockEntityTypes::registerAll);
        register(event, BovinesBlocks::registerAll);
        register(event, BovinesCowTypeTypes::registerAll);
        register(event, BovinesCriteriaTriggers::registerAll);
        register(event, BovinesDataComponents::registerAll);
        register(event, BovinesEntityTypes::registerAll);
        register(event, BovinesLootItemConditionTypes::registerAll);
        register(event, BovinesParticleTypes::registerAll);
        register(event, BovinesSoundEvents::registerAll);
        register(event, BovinesStructureTypes::registerAll);
        register(event, BovinesTextureModificationFactories::registerAll);

        if (event.getRegistryKey() == Registries.ARMOR_MATERIAL) {
            registerHolders(BovinesArmorMaterials::registerAll);
            BovinesItems.registerAll(Registry::register);
        }

        if (event.getRegistryKey() == Registries.SOUND_EVENT)
            registerHolders(BovinesSoundEvents::registerHolders);

        if (event.getRegistryKey() == Registries.MOB_EFFECT)
            registerHolders(BovinesEffects::registerAll);
    }

    private static <T> void register(RegisterEvent event, Consumer<RegistrationCallback<T>> consumer) {
        consumer.accept((registry, id, value) ->
                event.register(registry.key(), id, () -> value));
    }

    private static <T> void registerHolders(Consumer<HolderRegistrationCallback<T>> consumer) {
        consumer.accept((registry, id, value) -> {
            Registry.register(registry, id, value);
            return DeferredHolder.create(registry.key(), id);
        });
    }

    @SubscribeEvent
    public static void createNewRegistries(NewRegistryEvent event) {
        event.register(BovinesRegistries.COW_TYPE_TYPE);
        event.register(BovinesRegistries.TEXTURE_MODIFIER);
    }

    @SubscribeEvent
    public static void createNewDataPackRegistry(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(BovinesRegistryKeys.COW_TYPE, CowType.DIRECT_CODEC, CowType.DIRECT_CODEC);
        event.dataPackRegistry(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE, CustomFlowerType.DIRECT_CODEC, CustomFlowerType.DIRECT_CODEC);
        event.dataPackRegistry(BovinesRegistryKeys.CUSTOM_MUSHROOM_TYPE, CustomMushroomType.DIRECT_CODEC, CustomMushroomType.DIRECT_CODEC);
        event.dataPackRegistry(BovinesRegistryKeys.FLOWER_CROWN_PETAL, FlowerCrownPetal.DIRECT_CODEC, FlowerCrownPetal.DIRECT_CODEC);
    }
}
