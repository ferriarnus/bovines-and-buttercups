package house.greenhouse.bovinesandbuttercups.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Map;
import java.util.Objects;

@Mixin(SimpleJsonResourceReloadListener.class)
public class SimpleJsonResourceReloadListenerMixin {
    @ModifyVariable(method = "scanDirectory", at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private static JsonElement bovinesandbuttercups$addAdvancementSourceToJson(JsonElement original, @Local(argsOnly = true) String name, @Local Map.Entry<ResourceLocation, Resource> entry) {
        if (original.isJsonObject() && Objects.equals(name, Registries.elementsDirPath(Registries.ADVANCEMENT))) {
            original.getAsJsonObject().addProperty("bovinesandbuttercups:pack_source", entry.getValue().source().packId());
        }
        return original;
    }
}
