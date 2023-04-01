package net.diamonddev.democracy.registry;

import net.diamonddev.democracy.Democracy;
import net.diamonddev.democracy.exception.GoofyAhhCrashException;
import net.diamonddev.democracy.fuckmojmaps.Identifier;
import net.diamonddev.democracy.network.CrashGamePacket;
import net.diamonddev.democracy.network.Netcode;
import net.diamonddev.democracy.network.OpenLinkPacket;
import net.diamonddev.democracy.rules.DumbOneShotConsumerRule;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.CrashReport;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.voting.rules.Rule;

public class InitVoteRules {

    public static DumbOneShotConsumerRule CRASH_CLIENTS;
    public static DumbOneShotConsumerRule CRASH_SERVER;
    public static DumbOneShotConsumerRule OPEN_RICKROLL;

    public static void register() {
        CRASH_CLIENTS = createVoteRule(Democracy.id("crash_clients"), new DumbOneShotConsumerRule((server) -> {
            server.getPlayerList().getPlayers().forEach(serverPlayer ->
                    ServerPlayNetworking.send(serverPlayer, Netcode.CRASH_PACKET_CHANNEL, CrashGamePacket.write("skidoosh")));
                }, "rule.crash")
        );

        CRASH_SERVER = createVoteRule(Democracy.id("crash_server"), new DumbOneShotConsumerRule((server) -> {
                    server.onServerCrash(new CrashReport("lol", new GoofyAhhCrashException()));
                }, "rule.crashier")
        );

        OPEN_RICKROLL = createVoteRule(Democracy.id("rickroll"), new DumbOneShotConsumerRule((server) -> {
            server.getPlayerList().getPlayers().forEach(serverPlayer ->
                    ServerPlayNetworking.send(serverPlayer, Netcode.SEND_LINK_CHANNEL, OpenLinkPacket.write("https://www.youtube.com/watch?v=dQw4w9WgXcQ")));
                }, "rule.rickroll")
        );
    }


    public static <T extends Rule> T createVoteRule(Identifier id, T rule) {
        return Registry.register(BuiltInRegistries.RULE, id, rule);
    }
}
