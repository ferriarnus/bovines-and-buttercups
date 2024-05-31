package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.content.effect.neoforge.LockdownEffectNeo;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.CustomFlowerItemNeoForge;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.CustomHugeMushroomItemNeoForge;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.CustomMushroomItemNeoForge;
import net.merchantpug.bovinesandbuttercups.content.item.neoforge.NectarBowlItemNeoForge;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public class NeoBovinesRegistryHelper implements BovinesRegistryHelper {
    @Override
    public NectarBowlItem createNectarBowlItem(Item.Properties properties) {
        return new NectarBowlItemNeoForge(properties);
    }

    @Override
    public MobEffect createLockdownEffect() {
        return new LockdownEffectNeo();
    }

    @Override
    public CustomFlowerItem createCustomFlowerItem() {
        return new CustomFlowerItemNeoForge(BovinesBlocks.CUSTOM_FLOWER, new Item.Properties());
    }

    @Override
    public CustomMushroomItem createCustomMushroomItem() {
        return new CustomMushroomItemNeoForge(BovinesBlocks.CUSTOM_MUSHROOM, new Item.Properties());
    }

    @Override
    public CustomHugeMushroomItem createCustomHugeMushroomItem() {
        return new CustomHugeMushroomItemNeoForge(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK, new Item.Properties());
    }
}
