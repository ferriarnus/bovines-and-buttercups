
package net.merchantpug.bovinesandbuttercups.platform;

import net.merchantpug.bovinesandbuttercups.content.component.NectarEffects;
import net.merchantpug.bovinesandbuttercups.content.effect.LockdownEffect;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;

public class BovinesRegistryHelperFabric implements BovinesRegistryHelper {

    @Override
    public NectarBowlItem createNectarBowlItem(Item.Properties properties) {
        return new NectarBowlItem(new Item.Properties().stacksTo(1)
                .component(BovinesDataComponents.NECTAR_EFFECTS, NectarEffects.EMPTY));
    }

    @Override
    public MobEffect createLockdownEffect() {
        return new LockdownEffect();
    }

    @Override
    public CustomFlowerItem createCustomFlowerItem() {
        return new CustomFlowerItem(BovinesBlocks.CUSTOM_FLOWER, new Item.Properties());
    }

    @Override
    public CustomMushroomItem createCustomMushroomItem() {
        return new CustomMushroomItem(BovinesBlocks.CUSTOM_MUSHROOM, new Item.Properties());
    }

    @Override
    public CustomHugeMushroomItem createCustomHugeMushroomItem() {
        return new CustomHugeMushroomItem(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK, new Item.Properties());
    }

}
