package house.greenhouse.bovinesandbuttercups.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import house.greenhouse.bovinesandbuttercups.BovinesAndButtercups;
import net.minecraft.resources.ResourceLocation;

public class AdvancementUtil {
    public static JsonElement addMoobloomToBredAllAnimals(JsonObject original) {
        original.getAsJsonArray("requirements").add(createRequirement(BovinesAndButtercups.asResource("moobloom").toString()));
        original.getAsJsonObject("criteria").add("bovinesandbuttercups:moobloom", createBredAnimalsTrigger(BovinesAndButtercups.asResource("moobloom")));
        return original;
    }

    public static JsonElement addRichHoneyBlockToHoneyBlockSlide(JsonObject original) {
        original.getAsJsonArray("requirements").get(0).getAsJsonArray().add("bovinesandbuttercups:rich_honey_block_slide");
        original.getAsJsonObject("criteria").add("bovinesandbuttercups:rich_honey_block_slide", createSlideDownBlockTrigger(BovinesAndButtercups.asResource("rich_honey_block")));
        return original;
    }

    private static JsonElement createRequirement(String requirement) {
        JsonArray array = new JsonArray();
        array.add(requirement);
        return array;
    }

    private static JsonElement createSlideDownBlockTrigger(ResourceLocation blockId) {
        JsonObject trigger = new JsonObject();
        trigger.addProperty("trigger", "minecraft:slide_down_block");

        JsonObject conditions = new JsonObject();
        conditions.addProperty("block", blockId.toString());

        trigger.add("conditions", conditions);

        return trigger;
    }

    private static JsonElement createBredAnimalsTrigger(ResourceLocation entityId) {
        JsonObject trigger = new JsonObject();
        trigger.addProperty("trigger", "minecraft:bred_animals");

        JsonObject conditions = new JsonObject();
        JsonArray child = new JsonArray();
        JsonObject isMoobloomCondition = new JsonObject();
        isMoobloomCondition.addProperty("condition", "minecraft:entity_properties");
        isMoobloomCondition.addProperty("entity", "this");
        JsonObject predicate = new JsonObject();
        predicate.addProperty("type", entityId.toString());
        isMoobloomCondition.add("predicate", predicate);
        child.add(isMoobloomCondition);
        conditions.add("child", child);
        trigger.add("conditions", conditions);

        return trigger;
    }

}
