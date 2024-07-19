package net.merchantpug.bovinesandbuttercups.registry;

import com.mojang.serialization.MapCodec;
import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.api.CowTypeType;
import net.merchantpug.bovinesandbuttercups.api.cowtype.modifier.TextureModifierFactory;
import net.minecraft.core.Registry;

public class BovinesRegistries {
    public static final Registry<CowTypeType<?>> COW_TYPE_TYPE = BovinesAndButtercups.getHelper().createRegistry(BovinesRegistryKeys.COW_TYPE_TYPE);
    public static final Registry<MapCodec<? extends TextureModifierFactory<?>>> TEXTURE_MODIFIER = BovinesAndButtercups.getHelper().createRegistry(BovinesRegistryKeys.TEXTURE_MODIFIER);

    public static void init() {
    }

}
