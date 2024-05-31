package net.merchantpug.bovinesandbuttercups.api;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public class BovinesTags {
    public static class BiomeTags {
        public static final TagKey<Biome> HAS_MOOBLOOM_FLOWER_FOREST = TagKey.create(Registries.BIOME, BovinesAndButtercups.asResource("has_moobloom/flower_forest"));
        public static final TagKey<Biome> PREVENT_COW_SPAWNS = TagKey.create(Registries.BIOME, BovinesAndButtercups.asResource("prevent_cow_spawns"));
    }

    public static class BlockTags {
        public static final TagKey<Block> MOOBLOOM_FLOWERS = TagKey.create(Registries.BLOCK, BovinesAndButtercups.asResource("moobloom_flowers"));
        public static final TagKey<Block> SNOWDROP_PLACEABLE = TagKey.create(Registries.BLOCK, BovinesAndButtercups.asResource("snowdrop_placeable"));
    }

    public static class ItemTags {
        public static final TagKey<Item> MOOBLOOM_FLOWERS = TagKey.create(Registries.ITEM, BovinesAndButtercups.asResource("moobloom_flowers"));
    }
}
