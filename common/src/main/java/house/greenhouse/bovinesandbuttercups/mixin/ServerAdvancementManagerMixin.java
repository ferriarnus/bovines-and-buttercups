package house.greenhouse.bovinesandbuttercups.mixin;

import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import house.greenhouse.bovinesandbuttercups.util.AdvancementUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerAdvancementManager.class)
public class ServerAdvancementManagerMixin {
    @ModifyVariable(method = { "lambda$apply$0", "method_20723"}, at = @At(value = "HEAD"), argsOnly = true)
    private JsonElement bovinesandbuttercups$applyAdvancement(JsonElement element, @Local(argsOnly = true) ResourceLocation id) {
        if (element.isJsonObject() && element.getAsJsonObject().getAsJsonPrimitive("bovinesandbuttercups:pack_source").getAsString().equals("vanilla")) {
            if (id.equals(ResourceLocation.withDefaultNamespace("husbandry/bred_all_animals")))
                return AdvancementUtil.addMoobloomToBredAllAnimals(element.getAsJsonObject());
            if (id.equals(ResourceLocation.withDefaultNamespace("adventure/honey_block_slide")))
                return AdvancementUtil.addRichHoneyBlockToHoneyBlockSlide(element.getAsJsonObject());
        }
        return element;
    }
}
