package net.merchantpug.bovinesandbuttercups.content.item;

import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesComponentHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SuspiciousEffectHolder;

import java.util.List;
import java.util.Optional;

public class CustomFlowerItem extends BlockItem {
    public CustomFlowerItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag compound = new CompoundTag();
        compound.putString("Type", "bovinesandbuttercups:missing");
        stack.getOrCreateTag().put("BlockEntityTag", compound);
        return stack;
    }

    public static ResourceLocation getFlowerTypeKeyFromTag(ItemStack stack, RegistryAccess registryAccess) {
        return registryAccess.registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().getKey(getFlowerTypeFromTag(stack, registryAccess).orElse(CustomFlowerType.MISSING));
    }

    public static Optional<CustomFlowerType> getFlowerTypeFromTag(ItemStack stack, RegistryAccess registryAccess) {
        if (stack.getTag() != null) {
            CompoundTag compound = stack.getTag().getCompound("BlockEntityTag");
            if (compound.contains("Type")) {
                CustomFlowerType flowerType = registryAccess.registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().get(ResourceLocation.tryParse(compound.getString("Type")));
                if (flowerType != null && flowerType != CustomFlowerType.MISSING) {
                    return Optional.of(flowerType);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public Component getName(ItemStack stack) {
        CompoundTag compound = stack.getOrCreateTag().getCompound("BlockEntityTag");
        if (compound.contains("Type")) {
            ResourceLocation resource = ResourceLocation.tryParse(compound.getString("Type"));
            if (resource != null && IBovinesComponentHelper.INSTANCE.getAttachedRegistryAccess(stack).registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().containsKey(resource)) {
                return getOrCreateNameTranslationKey(resource);
            }
        }
        return super.getName(stack);
    }

    private static Component getOrCreateNameTranslationKey(ResourceLocation location) {
        return Component.translatable("block." + location.getNamespace() + "." + location.getPath());
    }

    public static List<SuspiciousEffectHolder.EffectEntry> getSuspiciousStewEffect(ItemStack customFlower, RegistryAccess registryAccess) {
        if (customFlower.getTag() != null) {
            CompoundTag compound = customFlower.getTag().getCompound("BlockEntityTag");
            if (compound.contains("Type")) {
                CustomFlowerType flowerType = registryAccess.registry(BovinesResourceKeys.CUSTOM_FLOWER_TYPE).orElseThrow().get(ResourceLocation.tryParse(compound.getString("Type")));
                if (!flowerType.stewEffectInstances().isEmpty()) {
                    return flowerType.stewEffectInstances();
                }
            }
        }
        return List.of();
    }

}
