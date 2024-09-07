package house.greenhouse.bovinesandbuttercups.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.CowType;
import house.greenhouse.bovinesandbuttercups.content.data.configuration.MoobloomConfiguration;
import house.greenhouse.bovinesandbuttercups.content.data.nectar.Nectar;
import house.greenhouse.bovinesandbuttercups.content.data.nectar.NectarEffects;
import house.greenhouse.bovinesandbuttercups.registry.BovinesRegistryKeys;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.Optional;
import java.util.function.Consumer;

public record ItemNectar(Holder<Nectar> nectar, Optional<Holder<CowType<?>>> cowType) implements TooltipProvider {
    public static final ItemNectar EMPTY = new ItemNectar(Holder.direct(new Nectar(BovinesAndButtercups.asResource("bovinesandbuttercups/item/buttercup_nectar_bowl"), NectarEffects.EMPTY)), Optional.empty());
    public static final Codec<ItemNectar> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Nectar.CODEC.fieldOf("nectar").forGetter(ItemNectar::nectar),
            CowType.CODEC.optionalFieldOf("cow_type").forGetter(ItemNectar::cowType)
    ).apply(inst, ItemNectar::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemNectar> STREAM_CODEC = StreamCodec.composite(
            Nectar.STREAM_CODEC, ItemNectar::nectar,
            ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(BovinesRegistryKeys.COW_TYPE)), ItemNectar::cowType,
            ItemNectar::new
    );

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> component, TooltipFlag flag) {
        if (cowType.isPresent() && cowType.get().isBound() && cowType.get().value().configuration() instanceof MoobloomConfiguration configuration) {
            if (configuration.flower().blockState().isPresent()) {
                component.accept(Component.translatable(configuration.flower().blockState().get().getBlock().getDescriptionId()).withStyle(ChatFormatting.BLUE));
            } else if (configuration.flower().customType().isPresent()) {
                ResourceLocation location = configuration.flower().customType().get().unwrapKey().orElseThrow().location();
                component.accept(Component.translatable("block." + location.getNamespace() + "." + location.getPath()).withStyle(ChatFormatting.BLUE));
            } else {
                component.accept(Component.translatable("cow_type." + cowType.get().unwrapKey().orElseThrow().location().getNamespace() + "." + cowType.get().unwrapKey().orElseThrow().location().getPath() + ".name").withStyle(ChatFormatting.BLUE));
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ItemNectar otherMoobloomType))
            return false;
        return otherMoobloomType.cowType.equals(cowType);
    }

    @Override
    public int hashCode() {
        return cowType.hashCode();
    }
}
