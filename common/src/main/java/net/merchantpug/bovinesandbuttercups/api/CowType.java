package net.merchantpug.bovinesandbuttercups.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.merchantpug.bovinesandbuttercups.platform.Services;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class CowType<CTC extends CowTypeConfiguration> {
    public static final Codec<CowType<?>> CODEC = Services.PLATFORM.getCowTypeCodec();
    private static final Map<String, Codec<?>> NAME_TO_CODEC = new HashMap<>();
    private final Codec<ConfiguredCowType<CTC, CowType<CTC>>> configuredCodec;

    private final ResourceLocation defaultConfiguredId;

    private final Supplier<ConfiguredCowType<CTC, CowType<CTC>>> defaultConfigured;

    /**
     * The constructor for the CowType class.
     *
     * @param name                 The name used for indexing the cow type in subfolders.
     *                             I'd recommend prefixing this with your modid `(example/cow_type)`
     *                             if you're making an addon or integration.
     * @param codec                The codec for the ConfiguredCowType relating to this cow type.
     * @param defaultConfiguredId  The ResourceLocation/Identifier used for the default value of this
     *                             cow type. Presumedly defaults to this for when a cow type isn't found.
     * @param defaultConfigured    A supplier for the default ConfiguredCowType.
     *                             Presumedly defaults to this for when a cow type isn't found.
     */
    public CowType(String name, MapCodec<CTC> codec, ResourceLocation defaultConfiguredId, Supplier<ConfiguredCowType<CTC, CowType<CTC>>> defaultConfigured) {
        this.configuredCodec = RecordCodecBuilder.create(instance ->
                instance.group(
                        codec.forGetter(ConfiguredCowType::configuration)
                ).apply(instance, (ctc) -> new ConfiguredCowType<>(this, ctc)));
        this.defaultConfiguredId = defaultConfiguredId;
        this.defaultConfigured = defaultConfigured;
        NAME_TO_CODEC.put(name.toLowerCase(Locale.ROOT), this.configuredCodec);
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

    public static Optional<Codec<ConfiguredCowType<?, ?>>> getFromName(String name) {
        if (!NAME_TO_CODEC.containsKey(name)) {
            return Optional.empty();
        }
        return Optional.of((Codec<ConfiguredCowType<?, ?>>)NAME_TO_CODEC.get(name));
    }

}
