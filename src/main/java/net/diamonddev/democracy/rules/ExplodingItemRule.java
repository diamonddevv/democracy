package net.diamonddev.democracy.rules;


import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.voting.rules.OneShotRule;
import net.minecraft.voting.rules.Rule;
import net.minecraft.voting.rules.RuleChange;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.Optional;
import java.util.stream.Stream;

public class ExplodingItemRule extends OneShotRule {

    private final Codec<ExplodingItemRuleChange> codec;

    public ExplodingItemRule() {
        this.codec = ItemStack.CODEC.xmap(ExplodingItemRuleChange::new, (pickUpItemRule) -> pickUpItemRule.stack);
    }


    @Override
    public Codec<RuleChange> codec() {
        return Rule.puntCodec(codec);
    }

    @Override
    public Stream<RuleChange> randomApprovableChanges(MinecraftServer minecraftServer, RandomSource randomSource, int i) {
        return Stream.empty();
    }

    private class ExplodingItemRuleChange extends OneShotRule.OneShotRuleChange {
        private final Item item;
        private final Component description;

        protected ExplodingItemRuleChange(Item item) {
            this.item = item;
            this.description = description();
        }

        @Override
        protected Component description() {
            return Component.translatable("rule.exploding_item", item.get);
        }

        @Override
        public void run(MinecraftServer minecraftServer) {
            minecraftServer.getAllLevels().iterator().forEachRemaining(
                    level -> level.getEntities(EntityTypeTest.forClass(ItemEntity.class), itemEntity -> itemEntity.getItem() == stack).forEach(itemEntity -> {
                        level.explode(itemEntity, 10, 10, 10, 10f, Level.ExplosionInteraction.TNT);
                    }));
        }
    }
}
