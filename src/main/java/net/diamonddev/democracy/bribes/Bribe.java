package net.diamonddev.democracy.bribes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.diamonddev.democracy.mixin.StatTypeAccessor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public record Bribe(Ingredient material, float multiplier, Stat<?> statisticBoost) {

    public static int getVoteMultiplier(ItemStack stack, ServerPlayer player) {
        List<Bribe> applying = BribeLoader.bribes.stream()
                .filter(bribe -> bribe.material.test(stack))
                .toList();
        if (applying.isEmpty()) {
            return -1;
        }
        float result = 1.0f;
        for (Bribe bribe : applying) {
            result *= bribe.multiplier;
            if (bribe.statisticBoost != null) {
                int value = player.getStats().getValue(bribe.statisticBoost);
                if (value >= 1) {
                    result *= Math.log(value) / 20.0f;
                }
            }
        }
        System.out.println(result);
        return (int) result;
    }

    public static Bribe entryFromJson(JsonObject object) {
        Ingredient material = Ingredient.fromJson(object.get("material"));
        float multiplier = GsonHelper.getAsFloat(object, "multiply");
        String statisticBoostId = GsonHelper.getAsString(object, "stat_boost", null);
        Stat<?> statisticBoost = statisticBoostId != null
                ? ((StatTypeAccessor<?>) Stats.CUSTOM).getMap().get(ResourceLocation.tryParse(statisticBoostId))
                : null;
        return new Bribe(material, multiplier, statisticBoost);
    }

    public static List<Bribe> fromJson(JsonObject object) {
        JsonArray items = GsonHelper.getAsJsonArray(object, "items");
        List<Bribe> result = new ArrayList<>();
        for (JsonElement item : items) {
            JsonObject entry = item.getAsJsonObject();
            result.add(entryFromJson(entry));
        }
        return result;
    }
}
