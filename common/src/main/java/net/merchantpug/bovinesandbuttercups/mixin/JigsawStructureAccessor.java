package net.merchantpug.bovinesandbuttercups.mixin;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Optional;

@Mixin(JigsawStructure.class)
public interface JigsawStructureAccessor {
    @Accessor("startPool")
    Holder<StructureTemplatePool> bovinesandbuttercups$getStartPool();

    @Accessor("startJigsawName")
    Optional<ResourceLocation> bovinesandbuttercups$getStartJigsawName();

    @Accessor("maxDepth")
    int bovinesandbuttercups$getMaxDepth();

    @Accessor("startHeight")
    HeightProvider bovinesandbuttercups$getStartHeight();

    @Accessor("useExpansionHack")
    boolean bovinesandbuttercups$shouldUseExpansionHack();

    @Accessor("projectStartToHeightmap")
    Optional<Heightmap.Types> bovinesandbuttercups$getProjectStartToHeightmap();

    @Accessor("maxDistanceFromCenter")
    int bovinesandbuttercups$getMaxDistanceFromCenter();

    @Accessor("poolAliases")
    List<PoolAliasBinding> bovinesandbuttercups$getPoolAliases();

    @Accessor("dimensionPadding")
    DimensionPadding bovinesandbuttercups$getDimensionPadding();

    @Accessor("liquidSettings")
    LiquidSettings bovinesandbuttercups$getLiquidSettings();
}
