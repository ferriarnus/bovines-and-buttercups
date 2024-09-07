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

public record FlowerCrownPetal(ItemStack ingredient,
                               ItemTextures itemTextures,
                               EquippedTextures equippedTextures,
                               Component description) {
    public static final Codec<FlowerCrownPetal> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ItemStack.STRICT_SINGLE_ITEM_CODEC.fieldOf("ingredient").forGetter(FlowerCrownPetal::ingredient),
            ItemTextures.CODEC.fieldOf("item_textures").forGetter(FlowerCrownPetal::itemTextures),
            EquippedTextures.CODEC.fieldOf("equipped_textures").forGetter(FlowerCrownPetal::equippedTextures),
            ComponentSerialization.CODEC.fieldOf("description").forGetter(FlowerCrownPetal::description)
    ).apply(inst, FlowerCrownPetal::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, FlowerCrownPetal> DIRECT_STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, FlowerCrownPetal::ingredient,
            ItemTextures.STREAM_CODEC, FlowerCrownPetal::itemTextures,
            EquippedTextures.STREAM_CODEC, FlowerCrownPetal::equippedTextures,
            ComponentSerialization.STREAM_CODEC, FlowerCrownPetal::description,
            FlowerCrownPetal::new
    );
    public static final Codec<Holder<FlowerCrownPetal>> CODEC = RegistryFileCodec.create(BovinesRegistryKeys.FLOWER_CROWN_PETAL, DIRECT_CODEC);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<FlowerCrownPetal>> STREAM_CODEC = ByteBufCodecs.holder(BovinesRegistryKeys.FLOWER_CROWN_PETAL, FlowerCrownPetal.DIRECT_STREAM_CODEC);

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
