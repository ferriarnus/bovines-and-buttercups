package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.CustomFlowerItemForge;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.CustomHugeMushroomItemForge;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.CustomMushroomItemForge;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.NectarBowlItemForge;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesRegistryHelper;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlockEntityTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;

import java.util.function.Supplier;

public class NeoBovinesRegistryHelper implements IBovinesRegistryHelper {
    @Override
    public <T extends Mob> SpawnEggItem createSpawnEggItem(Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor, Item.Properties properties) {
        return new DeferredSpawnEggItem(entityType, backgroundColor, highlightColor, properties);
    }

    @Override
    public NectarBowlItem createNectarBowlItem(Item.Properties properties) {
        return new NectarBowlItemForge(properties);
    }

    @Override
    public MobEffect createLockdownEffect() {
        return null;
    }

    @Override
    public CustomFlowerItem createCustomFlowerItem() {
        return new CustomFlowerItemForge(BovinesBlocks.CUSTOM_FLOWER.value(), new Item.Properties());
    }

    @Override
    public CustomMushroomItem createCustomMushroomItem() {
        return new CustomMushroomItemForge(BovinesBlocks.CUSTOM_MUSHROOM.value(), new Item.Properties());
    }

    @Override
    public CustomHugeMushroomItem createCustomHugeMushroomItem() {
        return new CustomHugeMushroomItemForge(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK.value(), new Item.Properties());
    }
}
