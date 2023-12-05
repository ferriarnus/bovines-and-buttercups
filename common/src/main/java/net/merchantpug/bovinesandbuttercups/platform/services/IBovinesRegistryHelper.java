package net.merchantpug.bovinesandbuttercups.platform.services;

import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.platform.ServiceUtil;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public interface IBovinesRegistryHelper {
    IBovinesRegistryHelper INSTANCE = ServiceUtil.load(IBovinesRegistryHelper.class);

    <T extends Mob> SpawnEggItem createSpawnEggItem(Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor, Item.Properties properties);

    NectarBowlItem createNectarBowlItem(Item.Properties properties);

    MobEffect createLockdownEffect();

    CustomFlowerItem createCustomFlowerItem();

    CustomMushroomItem createCustomMushroomItem();

    CustomHugeMushroomItem createCustomHugeMushroomItem();
}
