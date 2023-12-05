
package net.merchantpug.bovinesandbuttercups.platform;

import com.google.auto.service.AutoService;
import net.merchantpug.bovinesandbuttercups.content.block.CustomFlowerBlock;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesRegistryHelper;
import net.merchantpug.bovinesandbuttercups.registry.BovinesBlocks;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

@AutoService(IBovinesRegistryHelper.class)
public class FabricBovinesRegistryHelper implements IBovinesRegistryHelper {
    @Override
    public <T extends Mob> SpawnEggItem createSpawnEggItem(Supplier<EntityType<T>> entityType, int backgroundColor, int highlightColor, Item.Properties properties) {
        return new SpawnEggItem(entityType.get(), backgroundColor, highlightColor, properties);
    }

    @Override
    public NectarBowlItem createNectarBowlItem(Item.Properties properties) {
        return new NectarBowlItem(new Item.Properties().stacksTo(1));
    }

    @Override
    public MobEffect createLockdownEffect() {
        return null;
    }

    @Override
    public CustomFlowerItem createCustomFlowerItem() {
        return new CustomFlowerItem(BovinesBlocks.CUSTOM_FLOWER.value(), new Item.Properties());
    }

    @Override
    public CustomMushroomItem createCustomMushroomItem() {
        return new CustomMushroomItem(BovinesBlocks.CUSTOM_MUSHROOM.value(), new Item.Properties());
    }

    @Override
    public CustomHugeMushroomItem createCustomHugeMushroomItem() {
        return new CustomHugeMushroomItem(BovinesBlocks.CUSTOM_MUSHROOM_BLOCK.value(), new Item.Properties());
    }

}
