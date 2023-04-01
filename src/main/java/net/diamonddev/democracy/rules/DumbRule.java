package net.diamonddev.democracy.rules;

import com.mojang.serialization.Codec;
import net.diamonddev.democracy.network.CrashGamePacket;
import net.diamonddev.democracy.network.Netcode;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.voting.rules.OneShotRule;
import net.minecraft.voting.rules.Rule;
import net.minecraft.voting.rules.RuleChange;

import java.util.stream.Stream;

public class DumbRule extends OneShotRule {

    private final Codec<DumbRule.DumbRuleChange> codec;
    private final DumbRuleChange change = new DumbRuleChange();

    public DumbRule() {
        this.codec = Codec.unit(change);
    }

    @Override
    public Codec<RuleChange> codec() {
        return Rule.puntCodec(codec);
    }

    @Override
    public Stream<RuleChange> randomApprovableChanges(MinecraftServer minecraftServer, RandomSource randomSource, int i) {
        return Stream.empty();
    }

    private class DumbRuleChange extends OneShotRuleChange {

        @Override
        protected Component description() {
            return Component.translatable("rule.dumb");
        }

        @Override
        public void run(MinecraftServer minecraftServer) {
            minecraftServer.getPlayerList().getPlayers().forEach(serverPlayer ->
                    ServerPlayNetworking.send(serverPlayer, Netcode.CRASH_PACKET_CHANNEL, CrashGamePacket.write("skidoosh", serverPlayer)));
        }
    }
}
