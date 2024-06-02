package net.merchantpug.bovinesandbuttercups.util;

import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomFlower;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomMushroom;
import net.merchantpug.bovinesandbuttercups.content.component.ItemMoobloomType;
import net.merchantpug.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class CreativeTabHelper {
    public static List<ItemStack> getCustomFlowersForCreativeTab(HolderLookup.Provider lookup) {
        return lookup.lookupOrThrow(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE).listElements().filter(flowerType -> flowerType.isBound() && !flowerType.value().equals(CustomFlowerType.MISSING)).map(flowerType -> {
            ItemStack stack = new ItemStack(BovinesItems.CUSTOM_FLOWER);
            stack.set(BovinesDataComponents.CUSTOM_FLOWER, new ItemCustomFlower(flowerType));
            return stack;
        }).toList();
    }

    public static List<ItemStack> getCustomMushroomsForCreativeTab(HolderLookup.Provider lookup) {
        return lookup.lookupOrThrow(BovinesRegistryKeys.CUSTOM_MUSHROOM_TYPE).listElements().filter(mushroomType -> mushroomType.isBound() && !mushroomType.value().equals(CustomMushroomType.MISSING)).map(mushroomType -> {
            ItemStack stack = new ItemStack(BovinesItems.CUSTOM_FLOWER);
            stack.set(BovinesDataComponents.CUSTOM_MUSHROOM, new ItemCustomMushroom(mushroomType));
            return stack;
        }).toList();
    }

    public static List<ItemStack> getCustomMushroomBlocksForCreativeTab(HolderLookup.Provider lookup) {
        return lookup.lookupOrThrow(BovinesRegistryKeys.CUSTOM_MUSHROOM_TYPE).listElements().filter(mushroomType -> mushroomType.isBound() && !mushroomType.value().equals(CustomMushroomType.MISSING)).map(mushroomType -> {
            ItemStack stack = new ItemStack(BovinesItems.CUSTOM_FLOWER);
            stack.set(BovinesDataComponents.CUSTOM_MUSHROOM, new ItemCustomMushroom(mushroomType));
            return stack;
        }).toList();
    }

    public static List<ItemStack> getNectarBowlsForCreativeTab(HolderLookup.Provider lookup) {
        return lookup.lookupOrThrow(BovinesRegistryKeys.COW_TYPE).listElements().filter(cowType -> cowType.isBound() && cowType.value().configuration() instanceof MoobloomConfiguration && !cowType.key().location().equals(BovinesCowTypes.MoobloomKeys.MISSING_MOOBLOOM.location())).map(cowType -> {
            ItemStack stack = new ItemStack(BovinesItems.NECTAR_BOWL);
            stack.set(BovinesDataComponents.MOOBLOOM_TYPE, new ItemMoobloomType(cowType));
            stack.set(BovinesDataComponents.NECTAR_EFFECTS, ((MoobloomConfiguration) cowType.value().configuration()).nectarEffects());
            return stack;
        }).toList();
    }
}
