package net.diamonddev.democracy.mixin;

import net.minecraft.core.Holder;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.voting.rules.Rule;
import net.minecraft.voting.rules.Rules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Rules.class)
public interface RulesAccessor {
    @Accessor("BUILDER")
    static SimpleWeightedRandomList.Builder<Holder.Reference<Rule>> accessWeightedListBuilder() {
        throw new RuntimeException("oop");
    }
}
