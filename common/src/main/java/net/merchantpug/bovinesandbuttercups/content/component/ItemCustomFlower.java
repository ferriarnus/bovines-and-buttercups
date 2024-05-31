package net.merchantpug.bovinesandbuttercups.content.component;

import com.mojang.serialization.Codec;
import net.merchantpug.bovinesandbuttercups.api.block.CustomFlowerType;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ItemCustomFlower(Holder<CustomFlowerType> holder) {
    public static final Codec<ItemCustomFlower> CODEC =
            CustomFlowerType.CODEC.xmap(ItemCustomFlower::new, ItemCustomFlower::holder);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemCustomFlower> STREAM_CODEC = ByteBufCodecs.holderRegistry(BovinesRegistryKeys.CUSTOM_FLOWER_TYPE).map(ItemCustomFlower::new, ItemCustomFlower::holder);
}
