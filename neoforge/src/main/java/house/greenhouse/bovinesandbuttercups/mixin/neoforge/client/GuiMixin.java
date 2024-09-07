package house.greenhouse.bovinesandbuttercups.mixin.neoforge.client;

import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.api.attachment.LockdownAttachment;
import house.greenhouse.bovinesandbuttercups.content.effect.LockdownEffect;
import house.greenhouse.bovinesandbuttercups.registry.BovinesAttachments;
import house.greenhouse.bovinesandbuttercups.registry.BovinesEffects;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffectInstance;
import net.neoforged.neoforge.client.extensions.common.IClientMobEffectExtensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Mixin(Gui.class)
public class GuiMixin {
    @Shadow @Final
    private Minecraft minecraft;

    @Inject(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lnet/minecraft/resources/ResourceLocation;IIII)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILHARD)
    private void bovinesandbuttercups$overlayLockdownBorder(GuiGraphics graphics, DeltaTracker deltaTracker, CallbackInfo ci, Collection collection, int j1, int k1, MobEffectTextureManager mobeffecttexturemanager, List list, Iterator var8, MobEffectInstance mobeffectinstance, IClientMobEffectExtensions renderer, Holder holder, int i, int j, float f) {
        if (minecraft.player == null || !minecraft.player.hasEffect(BovinesEffects.LOCKDOWN)) return;

        Optional<LockdownAttachment> attachment = minecraft.player.getExistingData(BovinesAttachments.LOCKDOWN);

        if (attachment.isPresent()) {
            if (!(holder.value() instanceof LockdownEffect) && attachment.get().effects().entrySet().stream().anyMatch(instance -> instance.getKey() == holder)) {
                graphics.blitSprite(BovinesAndButtercups.asResource("hud/lockdown_frame"), i, j, 24, 24);
            }
        }
    }
}