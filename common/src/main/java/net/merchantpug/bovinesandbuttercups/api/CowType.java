package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.registry.BovinesRegistries;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class CowType<CTC extends CowTypeConfiguration> {
    public static final Codec<CowType<?>> CODEC = BovinesRegistries.COW_TYPE_REGISTRY.byNameCodec();
    private final Codec<ConfiguredCowType<CTC, CowType<CTC>>> configuredCodec;

    private final ResourceLocation defaultConfiguredId;

    private final Supplier<ConfiguredCowType<CTC, CowType<CTC>>> defaultConfigured;

    /**
     * The constructor for the CowType class.
     *
     * @param codec                The codec for the ConfiguredCowType relating to this cow type.
     * @param defaultConfiguredId  The ResourceLocation/Identifier used for the default value of this
     *                             cow type. Presumedly defaults to this for when a cow type isn't found.
     * @param defaultConfigured    A supplier for the default ConfiguredCowType.
     *                             Presumedly defaults to this for when a cow type isn't found.
     */
    public CowType(MapCodec<CTC> codec, ResourceLocation defaultConfiguredId, Supplier<ConfiguredCowType<CTC, CowType<CTC>>> defaultConfigured) {
        this.configuredCodec = RecordCodecBuilder.create(instance ->
                instance.group(
                        codec.forGetter(ConfiguredCowType::configuration)
                ).apply(instance, (ctc) -> new ConfiguredCowType<>(this, ctc)));
        this.defaultConfiguredId = defaultConfiguredId;
        this.defaultConfigured = defaultConfigured;
    }

    public Codec<ConfiguredCowType<CTC, CowType<CTC>>> codec() {
        return this.configuredCodec;
    }

    public ResourceLocation defaultConfiguredId() {
        return this.defaultConfiguredId;
    }

    public Supplier<ConfiguredCowType<CTC ,CowType<CTC>>> defaultConfigured() {
        return this.defaultConfigured;
    }

}
