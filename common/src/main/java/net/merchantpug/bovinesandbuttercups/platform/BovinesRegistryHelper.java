package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public interface BovinesRegistryHelper {
    NectarBowlItem createNectarBowlItem(Item.Properties properties);

    MobEffect createLockdownEffect();

    CustomFlowerItem createCustomFlowerItem();

    CustomMushroomItem createCustomMushroomItem();

    CustomHugeMushroomItem createCustomHugeMushroomItem();
}
