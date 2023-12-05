package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.item.CustomFlowerItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomHugeMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.CustomMushroomItem;
import net.merchantpug.bovinesandbuttercups.content.item.NectarBowlItem;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesRegistryHelper;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class BovinesItems {
    private static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, BovinesAndButtercups.MOD_ID);

    public static final Holder<NectarBowlItem> NECTAR_BOWL = register("nectar_bowl", () -> IBovinesRegistryHelper.INSTANCE.createNectarBowlItem(new Item.Properties().stacksTo(1)));
    public static final Holder<Item> MOOBLOOM_SPAWN_EGG = register("moobloom_spawn_egg", () -> IBovinesRegistryHelper.INSTANCE.createSpawnEggItem(BovinesEntityTypes.MOOBLOOM::value, 0xfad200, 0x437f34, new Item.Properties()));

    public static final Holder<BlockItem> BUTTERCUP = register("buttercup", () -> new BlockItem(BovinesBlocks.BUTTERCUP.value(), new Item.Properties()));
    public static final Holder<BlockItem> PINK_DAISY = register("pink_daisy", () -> new BlockItem(BovinesBlocks.PINK_DAISY.value(), new Item.Properties()));
    public static final Holder<BlockItem> LIMELIGHT = register("limelight", () -> new BlockItem(BovinesBlocks.LIMELIGHT.value(), new Item.Properties()));
    public static final Holder<BlockItem> BIRD_OF_PARADISE = register("bird_of_paradise", () -> new BlockItem(BovinesBlocks.BIRD_OF_PARADISE.value(), new Item.Properties()));
    public static final Holder<BlockItem> CHARGELILY = register("chargelily", () -> new BlockItem(BovinesBlocks.CHARGELILY.value(), new Item.Properties()));
    public static final Holder<BlockItem> HYACINTH = register("hyacinth", () -> new BlockItem(BovinesBlocks.HYACINTH.value(), new Item.Properties()));
    public static final Holder<BlockItem> SNOWDROP = register("snowdrop", () -> new BlockItem(BovinesBlocks.SNOWDROP.value(), new Item.Properties()));
    public static final Holder<BlockItem> TROPICAL_BLUE = register("tropical_blue", () -> new BlockItem(BovinesBlocks.TROPICAL_BLUE.value(), new Item.Properties()));
    public static final Holder<BlockItem> FREESIA = register("freesia", () -> new BlockItem(BovinesBlocks.FREESIA.value(), new Item.Properties()));

    public static final Holder<CustomFlowerItem> CUSTOM_FLOWER = register("custom_flower", IBovinesRegistryHelper.INSTANCE::createCustomFlowerItem);
    public static final Holder<CustomMushroomItem> CUSTOM_MUSHROOM = register("custom_mushroom", IBovinesRegistryHelper.INSTANCE::createCustomMushroomItem);
    public static final Holder<CustomHugeMushroomItem> CUSTOM_MUSHROOM_BLOCK = register("custom_mushroom_block", IBovinesRegistryHelper.INSTANCE::createCustomHugeMushroomItem);

    public static void register() {

    }

    private static <T extends Item> Holder<T> register(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }
}
