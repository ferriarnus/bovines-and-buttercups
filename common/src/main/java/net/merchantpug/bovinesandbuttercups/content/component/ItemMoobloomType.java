package net.merchantpug.bovinesandbuttercups.content.component;

import com.mojang.serialization.Codec;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.content.configuration.MoobloomConfiguration;
import net.merchantpug.bovinesandbuttercups.registry.BovinesCowTypeTypes;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistryKeys;
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

import java.util.function.Consumer;

public record ItemMoobloomType(Holder<CowType<MoobloomConfiguration>> cowType) implements TooltipProvider {
    public static final Codec<ItemMoobloomType> CODEC = CowType.filteredCodec(BovinesCowTypeTypes.MOOBLOOM_TYPE)
            .xmap(ItemMoobloomType::new, ItemMoobloomType::cowType);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemMoobloomType> STREAM_CODEC = ByteBufCodecs.holderRegistry(BovinesRegistryKeys.COW_TYPE).map(cowType -> {
        if (cowType.isBound() && cowType.value().type() == BovinesCowTypeTypes.MOOBLOOM_TYPE) {
            new ItemMoobloomType((Holder) cowType);
        }
        throw new IllegalArgumentException("Cow Type was not a Moobloom.");
    }, itemMoobloomType -> (Holder) itemMoobloomType.cowType());

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> component, TooltipFlag flag) {
        if (cowType.value().configuration().flower().blockState().isPresent()) {
            component.accept(Component.translatable(cowType.value().configuration().flower().blockState().get().getBlock().getDescriptionId()).withStyle(ChatFormatting.BLUE));
        } else if (cowType.value().configuration().flower().customType().isPresent()) {
            ResourceLocation location = cowType.value().configuration().flower().customType().get().unwrapKey().orElseThrow().location();
            component.accept(Component.translatable("block." + location.getNamespace() + "." + location.getPath()).withStyle(ChatFormatting.BLUE));
        } else {
            component.accept(Component.translatable("configured_cow_type." + cowType.unwrapKey().orElseThrow().location().getNamespace() + "." + cowType.unwrapKey().orElseThrow().location().getPath() + ".name").withStyle(ChatFormatting.BLUE));
        }
    }
}
