package net.diamonddev.democracy.registry;

import net.diamonddev.democracy.Democracy;
import net.diamonddev.democracy.fuckmojmaps.Identifier;
import net.diamonddev.democracy.rules.DumbRule;
import net.diamonddev.democracy.rules.ExplodingItemRule;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.voting.rules.Rule;

public class InitVoteRules {

    public static ExplodingItemRule EXPLODE_ITEM;
    public static DumbRule CRASH;

    public static void register() {
        EXPLODE_ITEM = createVoteRule(Democracy.id("explode_item"), new ExplodingItemRule());
        CRASH = createVoteRule(Democracy.id("crash"), new DumbRule());
    }


    public static <T extends Rule> T createVoteRule(Identifier id, T rule) {
        return Registry.register(BuiltInRegistries.RULE, id.toResLoc(), rule);
    }
}
