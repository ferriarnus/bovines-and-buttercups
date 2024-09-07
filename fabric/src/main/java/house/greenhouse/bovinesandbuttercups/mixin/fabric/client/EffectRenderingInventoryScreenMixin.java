package house.greenhouse.bovinesandbuttercups.mixin.fabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import house.greenhouse.bovinesandbuttercups.content.effect.LockdownEffect;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(EffectRenderingInventoryScreen.class)
public abstract class EffectRenderingInventoryScreenMixin<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

    public EffectRenderingInventoryScreenMixin(T handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Inject(method = "renderIcons", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(IIIIILnet/minecraft/client/renderer/texture/TextureAtlasSprite;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void bovinesandbuttercups$drawOverridenEffectSprite(GuiGraphics guiGraphics, int x, int y, Iterable<MobEffectInstance> iterable, boolean large, CallbackInfo ci, MobEffectTextureManager mobEffectTextureManager, int k, Iterator var8, MobEffectInstance mobEffectInstance, Holder<MobEffect> holder, TextureAtlasSprite textureAtlasSprite) {
        if (!holder.isBound() || !(holder.value() instanceof LockdownEffect)) return;

        List<Map.Entry<Holder<MobEffect>, Integer>> statusEffectList = BovinesAndButtercups.getHelper().getLockdownAttachment(minecraft.player).effects().entrySet().stream().toList();

        if (statusEffectList.isEmpty()) return;
        int lockdownEffectIndex = minecraft.player.tickCount / (160 / statusEffectList.size()) % statusEffectList.size();

        Holder<MobEffect> mobEffect1 = statusEffectList.get(lockdownEffectIndex).getKey();

        TextureAtlasSprite additionalSprite = mobEffectTextureManager.get(mobEffect1);
        RenderSystem.setShaderTexture(0, additionalSprite.atlasLocation());
        guiGraphics.blit(x + (large ? 6 : 7), k + 7, 0, 18, 18, additionalSprite);
    }

}
