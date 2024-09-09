package house.greenhouse.bovinesandbuttercups.content.worldgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class OffsetPoolElement extends SinglePoolElement {
    public static final ResourceLocation ID = BovinesAndButtercups.asResource("offset_pool_element");
    public static final MapCodec<OffsetPoolElement> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            templateCodec(),
            processorsCodec(),
            projectionCodec(),
            overrideLiquidSettingsCodec(),
            Vec3i.CODEC.fieldOf("offset").forGetter(OffsetPoolElement::getOffset)
    ).apply(inst, OffsetPoolElement::new));

    private final Vec3i offset;

    protected OffsetPoolElement(Either<ResourceLocation, StructureTemplate> template, Holder<StructureProcessorList> processors, StructureTemplatePool.Projection projection, Optional<LiquidSettings> overrideLiquidSettings, Vec3i offset) {
        super(template, processors, projection, overrideLiquidSettings);
        this.offset = offset;
    }

    @Override
    public boolean place(StructureTemplateManager structureTemplateManager, WorldGenLevel level, StructureManager structureManager, ChunkGenerator generator, BlockPos offset, BlockPos pos, Rotation rotation, BoundingBox box, RandomSource random, LiquidSettings liquidSettings, boolean keepJigsaws) {
        return super.place(structureTemplateManager, level, structureManager, generator, offset, pos.offset(this.offset), rotation, box, random, liquidSettings, keepJigsaws);
    }

    public Vec3i getOffset() {
        return offset;
    }
}
