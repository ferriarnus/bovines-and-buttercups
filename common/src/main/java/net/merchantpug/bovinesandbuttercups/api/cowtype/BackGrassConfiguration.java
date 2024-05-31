package net.merchantpug.bovinesandbuttercups.api.cowtype;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public record BackGrassConfiguration(ResourceLocation textureLocation, boolean grassTinted) {
    public static Codec<BackGrassConfiguration> codec(ResourceLocation defaultLocation, boolean defaultTinted) {
        return RecordCodecBuilder.create(inst -> inst.group(
                ResourceLocation.CODEC.optionalFieldOf("texture_location", defaultLocation).forGetter(BackGrassConfiguration::textureLocation),
                Codec.BOOL.optionalFieldOf("grass_tinted", defaultTinted).forGetter(BackGrassConfiguration::grassTinted)
        ).apply(inst, BackGrassConfiguration::new));
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == this)
            return true;

        if (!(obj instanceof BackGrassConfiguration other))
            return false;

        return other.textureLocation.equals(this.textureLocation) && other.grassTinted == this.grassTinted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.textureLocation, this.grassTinted);
    }
}
