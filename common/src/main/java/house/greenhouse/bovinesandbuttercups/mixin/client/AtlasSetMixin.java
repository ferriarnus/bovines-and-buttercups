package house.greenhouse.bovinesandbuttercups.mixin.client;

import com.google.common.collect.ImmutableMap;
import house.greenhouse.bovinesandbuttercups.client.util.BovinesAtlases;
import net.minecraft.client.resources.model.AtlasSet;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;

@Mixin(AtlasSet.class)
public class AtlasSetMixin {
    @ModifyVariable(method = "<init>", at = @At("HEAD"), argsOnly = true)
    private static Map<ResourceLocation, ResourceLocation> bovinesandbuttercups$registerPetalsAtlas(Map<ResourceLocation, ResourceLocation> atlasMap) {
        var map = new HashMap<>(atlasMap);
        map.put(BovinesAtlases.PETALS_SHEET, BovinesAtlases.PETALS);
        return ImmutableMap.copyOf(map);
    }
}
