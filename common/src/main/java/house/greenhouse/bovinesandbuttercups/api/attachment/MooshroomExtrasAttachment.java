package house.greenhouse.bovinesandbuttercups.api.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;

import java.util.stream.Stream;

public record MooshroomExtrasAttachment(boolean shearable) {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("mooshroom_extras");
    public static final Codec<MooshroomExtrasAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
                    Codec.BOOL.fieldOf("shearable").forGetter(MooshroomExtrasAttachment::getShearable)
            ).apply(inst, MooshroomExtrasAttachment::new)
            );
    public MooshroomExtrasAttachment() {
        this(true);
    }
    public MooshroomExtrasAttachment(boolean shearable) {
        this.shearable=shearable;
    }
    public boolean getShearable() {
        return shearable;
    }
}
