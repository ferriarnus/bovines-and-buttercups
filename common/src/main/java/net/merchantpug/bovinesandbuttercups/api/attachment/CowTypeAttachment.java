package net.merchantpug.bovinesandbuttercups.api.attachment;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowType;
import net.merchantpug.bovinesandbuttercups.api.CowTypeConfiguration;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.merchantpug.bovinesandbuttercups.network.clientbound.SyncCowTypeClientboundPacket;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;

public record CowTypeAttachment(Holder<CowType<?>> cowType, Optional<Holder<CowType<?>>> previousCowType) {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("cow_type");
    public static final Codec<CowTypeAttachment> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
            CowType.CODEC.fieldOf("current").forGetter(CowTypeAttachment::cowType),
            CowType.CODEC.optionalFieldOf("previous").forGetter(CowTypeAttachment::previousCowType)
    ).apply(inst, CowTypeAttachment::new));
    public static final Codec<CowTypeAttachment> CODEC = Codec.either(CowType.CODEC, DIRECT_CODEC).flatComapMap(either -> either.map(current -> new CowTypeAttachment(current, Optional.empty()), Function.identity()), attachment -> {
        if (attachment.previousCowType().isEmpty())
            return DataResult.success(Either.left(attachment.cowType()));
        return DataResult.success(Either.right(attachment));
    });

    @Nullable
    public static <C extends CowTypeConfiguration, T extends CowTypeType<C>> CowType<C> getCowTypeFromEntity(LivingEntity living, T cowType) {
        Holder<CowType<C>> type = getCowTypeHolderFromEntity(living, cowType);
        if (type != null && type.isBound())
            return type.value();
        return null;
    }

    @Nullable
    public static <C extends CowTypeConfiguration, T extends CowTypeType<C>> Holder<CowType<C>> getCowTypeHolderFromEntity(LivingEntity living, T cowType) {
        Holder<CowType<?>> type = BovinesAndButtercups.getHelper().getCowTypeAttachment(living).cowType();
        if (type.isBound() && type.value().type() == cowType) {
            return (Holder)type;
        }
        return null;
    }

    @Nullable
    public static <C extends CowTypeConfiguration, T extends CowTypeType<C>> CowType<C> getPreviousCowTypeFromEntity(LivingEntity living, T cowType) {
        Holder<CowType<C>> type = getPreviousCowTypeHolderFromEntity(living, cowType);
        if (type != null && type.isBound())
            return type.value();
        return null;
    }

    @Nullable
    public static <C extends CowTypeConfiguration, T extends CowTypeType<C>> Holder<CowType<C>> getPreviousCowTypeHolderFromEntity(LivingEntity living, T cowType) {
        Optional<Holder<CowType<?>>> type = BovinesAndButtercups.getHelper().getCowTypeAttachment(living).previousCowType();
        if (type.isPresent() && type.get().isBound() && type.get().value().type() == cowType) {
            return (Holder)type.get();
        }
        return null;
    }

    public static <C extends CowTypeConfiguration> void setCowType(LivingEntity entity, Holder<CowType<C>> cowType) {
        if (cowType.isBound() && cowType.value().type().isApplicable(entity))
            BovinesAndButtercups.getHelper().setCowTypeAttachment(entity, new CowTypeAttachment((Holder)cowType, Optional.empty()));
    }

    public static <C extends CowTypeConfiguration> void setCowType(LivingEntity entity, Holder<CowType<C>> cowType, Holder<CowType<C>> previousCowType) {
        if (cowType.isBound() && cowType.value().type().isApplicable(entity))
            BovinesAndButtercups.getHelper().setCowTypeAttachment(entity, new CowTypeAttachment((Holder)cowType, Optional.of((Holder)previousCowType)));
    }

    public static void sync(LivingEntity entity) {
        if (entity.level().isClientSide())
            return;
        BovinesAndButtercups.getHelper().sendTrackingClientboundPacket(new SyncCowTypeClientboundPacket(entity.getId(), BovinesAndButtercups.getHelper().getCowTypeAttachment(entity)), entity);
    }
}
