package house.greenhouse.bovinesandbuttercups.util;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BovinesFoods {
    public static final FoodProperties RICH_HONEY_BOTTLE = new FoodProperties.Builder()
            .nutrition(6)
            .saturationModifier(0.3F)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0), 1.0F)
            .alwaysEdible()
            .build();
}