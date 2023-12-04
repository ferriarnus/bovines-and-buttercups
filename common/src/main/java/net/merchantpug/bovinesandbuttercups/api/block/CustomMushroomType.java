package net.merchantpug.bovinesandbuttercups.api.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.BovinesResourceKeys;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.HolderSetCodec;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

public record CustomMushroomType(HolderSet<Structure> hugeMushroomStructureList) {

    public static final Codec<CustomMushroomType> DIRECT_CODEC = RecordCodecBuilder.create(builder -> builder.group(
            HolderSetCodec.create(Registries.STRUCTURE, Structure.CODEC, true).optionalFieldOf("huge_structures", HolderSet.direct()).forGetter(CustomMushroomType::hugeMushroomStructureList)
    ).apply(builder, CustomMushroomType::new));

    public static final Codec<Holder<CustomMushroomType>> CODEC = RegistryFileCodec.create(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE, DIRECT_CODEC);
    public static final CustomMushroomType MISSING = new CustomMushroomType(HolderSet.direct());

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof CustomMushroomType other))
            return false;

        return other.hugeMushroomStructureList.equals(this.hugeMushroomStructureList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.hugeMushroomStructureList);
    }

    @ApiStatus.Internal
    public static RegistrySetBuilder.RegistryBootstrap<CustomMushroomType> bootstrap() {
        return bootstapContext -> bootstapContext.register(ResourceKey.create(BovinesResourceKeys.CUSTOM_MUSHROOM_TYPE, BovinesAndButtercups.asResource("missing")), CustomMushroomType.MISSING);
    }
}