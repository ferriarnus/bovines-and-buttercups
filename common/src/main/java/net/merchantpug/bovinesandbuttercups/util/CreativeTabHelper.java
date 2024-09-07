package net.merchantpug.bovinesandbuttercups.util;

import net.merchantpug.bovinesandbuttercups.api.BovinesTags;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.content.component.FlowerCrown;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomFlower;
import net.merchantpug.bovinesandbuttercups.content.component.ItemCustomMushroom;
import net.merchantpug.bovinesandbuttercups.content.component.ItemMoobloomType;
import net.merchantpug.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.content.data.flowercrown.FlowerCrownPetal;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesDataComponents;
import net.merchantpug.bovinesandbuttercups.registry.BovinesFlowerCrownPetals;
import net.merchantpug.bovinesandbuttercups.registry.BovinesItems;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    public static List<ItemStack> getFlowerCrownsForCreativeTab(HolderLookup.Provider lookup) {
        HolderLookup.RegistryLookup<FlowerCrownPetal> registry = lookup.lookupOrThrow(BovinesRegistryKeys.FLOWER_CROWN_PETAL);
        HolderSet<FlowerCrownPetal> creativeModeTabOrder = registry.getOrThrow(BovinesTags.FlowerCrownPetalTags.CREATIVE_MENU_ORDER);
        List<ItemStack> stacks = registry.listElements().filter(Holder.Reference::isBound).sorted(Comparator.comparingInt(value -> {
            int i = creativeModeTabOrder.stream().toList().indexOf(value);
            if (i == -1)
                return Integer.MAX_VALUE;
            return i;
        })).map(petal -> {
            ItemStack stack = new ItemStack(BovinesItems.FLOWER_CROWN);
            stack.set(BovinesDataComponents.FLOWER_CROWN, new FlowerCrown(petal, petal, petal, petal, petal, petal, petal, petal));
            return stack;
        }).collect(Collectors.toCollection(ArrayList::new));

        ItemStack rainbowCrown = new ItemStack(BovinesItems.FLOWER_CROWN);
        rainbowCrown.set(BovinesDataComponents.FLOWER_CROWN, new FlowerCrown(registry.getOrThrow(BovinesFlowerCrownPetals.BIRD_OF_PARADISE), registry.getOrThrow(BovinesFlowerCrownPetals.BUTTERCUP), registry.getOrThrow(BovinesFlowerCrownPetals.LIMELIGHT), registry.getOrThrow(BovinesFlowerCrownPetals.FREESIA), registry.getOrThrow(BovinesFlowerCrownPetals.CHARGELILY), registry.getOrThrow(BovinesFlowerCrownPetals.PINK_DAISY), registry.getOrThrow(BovinesFlowerCrownPetals.HYACINTH), registry.getOrThrow(BovinesFlowerCrownPetals.TROPICAL_BLUE)));
        stacks.add(rainbowCrown);

        return stacks;
    }
}
