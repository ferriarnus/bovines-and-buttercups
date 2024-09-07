package net.merchantpug.bovinesandbuttercups.registry;

import net.merchantpug.bovinesandbuttercups.BovinesAndButtercups;
import net.merchantpug.bovinesandbuttercups.content.recipe.FlowerCrownRecipe;
import net.merchantpug.bovinesandbuttercups.registry.internal.RegistrationCallback;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class BovinesRecipeSerializers {
    public static final RecipeSerializer<FlowerCrownRecipe> FLOWER_CROWN = new SimpleCraftingRecipeSerializer<>(FlowerCrownRecipe::new);

    public static void registerAll(RegistrationCallback<RecipeSerializer<?>> callback) {
        callback.register(BuiltInRegistries.RECIPE_SERIALIZER, BovinesAndButtercups.asResource("crafting_special_flowercrown"), FLOWER_CROWN);
    }
}
