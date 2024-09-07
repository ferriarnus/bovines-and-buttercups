package house.greenhouse.bovinesandbuttercups.content.item;

import house.greenhouse.bovinesandbuttercups.content.component.NectarEffects;
import house.greenhouse.bovinesandbuttercups.registry.BovinesDataComponents;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class NectarBowlItem extends Item {

    public NectarBowlItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        Player player = livingEntity instanceof Player ? (Player) livingEntity : null;
        if (player instanceof ServerPlayer serverPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        if (livingEntity instanceof Player && !player.getAbilities().instabuild)
            itemStack.shrink(1);

        NectarEffects effects = itemStack.getOrDefault(BovinesDataComponents.NECTAR_EFFECTS, NectarEffects.EMPTY);
        effects.applyEffectInstance(livingEntity);
        if (player != null) {
            player.awardStat(Stats.ITEM_USED.get(this));
            if (!player.getAbilities().instabuild) {
                itemStack.shrink(1);
            }
        }
        if (itemStack.isEmpty()) {
            return new ItemStack(Items.BOWL);
        }
        return itemStack;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity living) {
        return 32;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        return ItemUtils.startUsingInstantly(level, player, interactionHand);
    }
}