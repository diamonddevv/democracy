package net.diamonddev.democracy.mixin;

import net.diamonddev.democracy.Democracy;
import net.diamonddev.democracy.registry.InitVoteRules;
import net.minecraft.voting.rules.Rule;
import net.minecraft.voting.rules.Rules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Rules.class)
public class RulesMixin {
    @Inject(method = "register", at = @At("HEAD"))
    private static <R extends Rule> void democracy$injectJankyInitializerOfCustomRules(String string, int i, R rule, CallbackInfoReturnable<R> cir) {
        if (!Democracy.isCustomRulesInitialized) {
            InitVoteRules.register();
            Democracy.isCustomRulesInitialized = true;
        }
    }
}
