package net.diamonddev.democracy.rules;

import com.mojang.serialization.Codec;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.voting.rules.OneShotRule;
import net.minecraft.voting.rules.Rule;
import net.minecraft.voting.rules.RuleChange;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class DumbOneShotConsumerRule extends OneShotRule {

    private final Codec<DumbOneShotConsumerRuleChange> codec;
    private final DumbOneShotConsumerRuleChange change;

    public DumbOneShotConsumerRule(Consumer<MinecraftServer> runConsumer, String translationKey) {
        this.change = new DumbOneShotConsumerRuleChange(runConsumer, translationKey);
        this.codec = Codec.unit(change);
    }

    @Override
    public Codec<RuleChange> codec() {
        return Rule.puntCodec(codec);
    }

    @Override
    public Stream<RuleChange> randomApprovableChanges(MinecraftServer minecraftServer, RandomSource randomSource, int i) {
        return Stream.of(change);
    }

    private class DumbOneShotConsumerRuleChange extends OneShotRuleChange {
        private final Consumer<MinecraftServer> consumer;
        private final String translationKey;

        public DumbOneShotConsumerRuleChange(Consumer<MinecraftServer> consumer, String translationKey) {
            this.consumer = consumer;
            this.translationKey = translationKey;
        }

        @Override
        protected Component description() {
            return Component.translatable(translationKey);
        }

        @Override
        public void run(MinecraftServer minecraftServer) {
            consumer.accept(minecraftServer);
        }
    }
}
