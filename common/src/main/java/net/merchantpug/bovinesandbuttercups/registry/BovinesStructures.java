package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.structure.RanchStructure;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class BovinesStructures {
    public static final ResourceKey<Structure> BIRD_OF_PARADISE_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/bird_of_paradise"));
    public static final ResourceKey<Structure> BUTTERCUP_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/buttercup"));
    public static final ResourceKey<Structure> FREESIA_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/freesia"));
    public static final ResourceKey<Structure> HYACINTH_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/hyacinth"));
    public static final ResourceKey<Structure> LIMELIGHT_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/limelight"));
    public static final ResourceKey<Structure> PINK_DAISY_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/pink_daisy"));
    public static final ResourceKey<Structure> SNOWDROP_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/snowdrop"));
    public static final ResourceKey<Structure> TROPICAL_BLUE_RANCH = ResourceKey.create(Registries.STRUCTURE, BovinesAndButtercups.asResource("ranch/tropical_blue"));
}
