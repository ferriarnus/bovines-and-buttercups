package net.merchantpug.bovinesandbuttercups.api.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.merchantpug.bovinesandbuttercups.api.codec.BovinesCodecs;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record CustomFlowerType(List<SuspiciousEffectHolder.EffectEntry> stewEffectInstances, Optional<ItemStack> dyeCraftResult) {

    public static final Codec<CustomFlowerType> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ExtraCodecs.catchDecoderException(SuspiciousEffectHolder.EffectEntry.LIST_CODEC).optionalFieldOf("stew_effect", List.of()).forGetter(CustomFlowerType::stewEffectInstances),
            ItemStack.CODEC.optionalFieldOf("dye_craft_result").forGetter(CustomFlowerType::dyeCraftResult)
    ).apply(builder, CustomFlowerType::new));

    public static final Codec<Holder<CustomFlowerType>> CODEC = RegistryFileCodec.create(BovinesResourceKeys.CUSTOM_FLOWER_TYPE, DIRECT_CODEC);
    public static final CustomFlowerType MISSING = new CustomFlowerType(List.of(), Optional.empty());

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof CustomFlowerType other))
            return false;

        return other.stewEffectInstances.equals(this.stewEffectInstances) && other.dyeCraftResult.equals(this.dyeCraftResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.stewEffectInstances, this.dyeCraftResult);
    }

    @ApiStatus.Internal
    public static RegistrySetBuilder.RegistryBootstrap<CustomFlowerType> bootstrap() {
        return bootstapContext -> bootstapContext.register(ResourceKey.create(BovinesResourceKeys.CUSTOM_FLOWER_TYPE, BovinesAndButtercups.asResource("missing")), CustomFlowerType.MISSING);
    }
}