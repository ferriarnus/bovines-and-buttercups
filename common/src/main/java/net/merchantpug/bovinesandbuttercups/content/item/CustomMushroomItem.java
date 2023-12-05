package net.merchantpug.bovinesandbuttercups.content.item;

import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.platform.services.IBovinesComponentHelper;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Optional;

public class CustomMushroomItem extends BlockItem {
    public CustomMushroomItem(Block block, Properties properties) {
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

    public static ResourceLocation getMushroomTypeKeyFromTag(ItemStack stack, RegistryAccess registryAccess) {
        return registryAccess.registry(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE).orElseThrow().getKey(getMushroomTypeFromTag(stack, registryAccess).orElse(CustomMushroomType.MISSING));
    }

    public static Optional<CustomMushroomType> getMushroomTypeFromTag(ItemStack stack, RegistryAccess registryAccess) {
        if (stack.getTag() != null) {
            CompoundTag compound = stack.getTag().getCompound("BlockEntityTag");
            if (compound.contains("Type")) {
                CustomMushroomType mushroomType = registryAccess.registry(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE).orElseThrow().get(ResourceLocation.tryParse(compound.getString("Type")));
                if (mushroomType != null && mushroomType != CustomMushroomType.MISSING) {
                    return Optional.of(mushroomType);
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
            if (resource != null && IBovinesComponentHelper.INSTANCE.getAttachedRegistryAccess(stack).registry(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE).orElseThrow().containsKey(resource)) {
                return getOrCreateNameTranslationKey(resource);
            }
        }
        return super.getName(stack);
    }

    private static Component getOrCreateNameTranslationKey(ResourceLocation location) {
        return Component.translatable("block." + location.getNamespace() + "." + location.getPath());
    }
}
