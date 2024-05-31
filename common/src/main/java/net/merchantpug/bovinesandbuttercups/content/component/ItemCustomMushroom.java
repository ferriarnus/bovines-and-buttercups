package net.merchantpug.bovinesandbuttercups.content.component;

import com.mojang.serialization.Codec;
import net.merchantpug.bovinesandbuttercups.api.block.CustomMushroomType;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record ItemCustomMushroom(Holder<CustomMushroomType> holder) {
    public static final Codec<ItemCustomMushroom> CODEC =
            CustomMushroomType.CODEC.xmap(ItemCustomMushroom::new, ItemCustomMushroom::holder);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemCustomMushroom> STREAM_CODEC = ByteBufCodecs.holderRegistry(BovinesRegistryKeys.CUSTOM_MUSHROOM_TYPE).map(ItemCustomMushroom::new, ItemCustomMushroom::holder);
}
