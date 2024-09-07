package house.greenhouse.bovinesandbuttercups.content.data.flowercrown;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record FlowerCrownMaterial(ItemStack ingredient,
                                  ItemTextures itemTextures,
                                  EquippedTextures equippedTextures,
                                  Component description) {
    public static final Codec<FlowerCrownMaterial> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("ingredient").forGetter(FlowerCrownMaterial::ingredient),
            ItemTextures.CODEC.fieldOf("item_textures").forGetter(FlowerCrownMaterial::itemTextures),
            EquippedTextures.CODEC.fieldOf("equipped_textures").forGetter(FlowerCrownMaterial::equippedTextures),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(FlowerCrownMaterial::description)
    ).apply(inst, FlowerCrownMaterial::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FlowerCrownMaterial> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, FlowerCrownMaterial::ingredient,
            ItemTextures.STREAM_CODEC, FlowerCrownMaterial::itemTextures,
            EquippedTextures.STREAM_CODEC, FlowerCrownMaterial::equippedTextures,
            ComponentSerialization.STREAM_CODEC, FlowerCrownMaterial::description,
            FlowerCrownMaterial::new
    );
    public static final Codec<Holder<FlowerCrownMaterial>> CODEC = RegistryFileCodec.create(BovinesRegistryKeys.FLOWER_CROWN_MATERIAL, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FlowerCrownMaterial>> STREAM_CODEC = ByteBufCodecs.holder(BovinesRegistryKeys.FLOWER_CROWN_MATERIAL, FlowerCrownMaterial.DIRECT_STREAM_CODEC);

    public record ItemTextures(ResourceLocation topLeft, ResourceLocation top, ResourceLocation topRight,
                               ResourceLocation centerLeft, ResourceLocation centerRight,
                               ResourceLocation bottomLeft, ResourceLocation bottom, ResourceLocation bottomRight) {
        public static final Codec<ItemTextures> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.fieldOf("top_left").forGetter(ItemTextures::topLeft),
                ResourceLocation.CODEC.fieldOf("top").forGetter(ItemTextures::top),
                ResourceLocation.CODEC.fieldOf("top_right").forGetter(ItemTextures::topRight),
                ResourceLocation.CODEC.fieldOf("center_left").forGetter(ItemTextures::centerLeft),
                ResourceLocation.CODEC.fieldOf("center_right").forGetter(ItemTextures::centerRight),
                ResourceLocation.CODEC.fieldOf("bottom_left").forGetter(ItemTextures::bottomLeft),
                ResourceLocation.CODEC.fieldOf("bottom").forGetter(ItemTextures::bottom),
                ResourceLocation.CODEC.fieldOf("bottom_right").forGetter(ItemTextures::bottomRight)
        ).apply(inst, ItemTextures::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, ItemTextures> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public ItemTextures decode(RegistryFriendlyByteBuf buffer) {
                return new ItemTextures(
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation()
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, ItemTextures value) {
                buffer.writeResourceLocation(value.topLeft());
                buffer.writeResourceLocation(value.top());
                buffer.writeResourceLocation(value.topRight());
                buffer.writeResourceLocation(value.centerLeft());
                buffer.writeResourceLocation(value.centerRight());
                buffer.writeResourceLocation(value.bottomLeft());
                buffer.writeResourceLocation(value.bottom());
                buffer.writeResourceLocation(value.bottomRight());
            }
        };
    }

    public record EquippedTextures(ResourceLocation topLeft, ResourceLocation top, ResourceLocation topRight,
                                   ResourceLocation centerLeft, ResourceLocation centerRight,
                                   ResourceLocation bottomLeft, ResourceLocation bottom, ResourceLocation bottomRight) {
        public static final Codec<EquippedTextures> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.fieldOf("top_left").forGetter(EquippedTextures::topLeft),
                ResourceLocation.CODEC.fieldOf("top").forGetter(EquippedTextures::top),
                ResourceLocation.CODEC.fieldOf("top_right").forGetter(EquippedTextures::topRight),
                ResourceLocation.CODEC.fieldOf("center_left").forGetter(EquippedTextures::centerLeft),
                ResourceLocation.CODEC.fieldOf("center_right").forGetter(EquippedTextures::centerRight),
                ResourceLocation.CODEC.fieldOf("bottom_left").forGetter(EquippedTextures::bottomLeft),
                ResourceLocation.CODEC.fieldOf("bottom").forGetter(EquippedTextures::bottom),
                ResourceLocation.CODEC.fieldOf("bottom_right").forGetter(EquippedTextures::bottomRight)
        ).apply(inst, EquippedTextures::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, EquippedTextures> STREAM_CODEC = new StreamCodec<>() {
            @Override
            public EquippedTextures decode(RegistryFriendlyByteBuf buffer) {
                return new EquippedTextures(
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation(),
                        buffer.readResourceLocation()
                );
            }

            @Override
            public void encode(RegistryFriendlyByteBuf buffer, EquippedTextures value) {
                buffer.writeResourceLocation(value.topLeft());
                buffer.writeResourceLocation(value.top());
                buffer.writeResourceLocation(value.topRight());
                buffer.writeResourceLocation(value.centerLeft());
                buffer.writeResourceLocation(value.centerRight());
                buffer.writeResourceLocation(value.bottomLeft());
                buffer.writeResourceLocation(value.bottom());
                buffer.writeResourceLocation(value.bottomRight());
            }
        };

        public ResourceLocation get(int i) {
            return List.of(centerLeft, topLeft, top, topRight, centerRight, bottomRight, bottom, bottomLeft).get(i);
        }
    }
}
