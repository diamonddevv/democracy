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
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

public class InitVoteRules {

    public static DumbOneShotConsumerRule CRASH_CLIENTS;
    public static DumbOneShotConsumerRule CRASH_SERVER;
    public static DumbOneShotConsumerRule OPEN_RICKROLL;
    public static DumbOneShotConsumerRule MLG_MOMENT;
    public static DumbOneShotConsumerRule BOOM;
    public static DumbOneShotConsumerRule DIE;
    public static DumbOneShotConsumerRule EPIC_RIFF;

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

        MLG_MOMENT = createVoteRule(Democracy.id("mlg"), new DumbOneShotConsumerRule((server) -> {
            server.getPlayerList().getPlayers().forEach(serverPlayer ->
                    serverPlayer.teleportRelative(0, 500, 0));
        }, "rule.mlg"));

        BOOM = createVoteRule(Democracy.id("boom"), new DumbOneShotConsumerRule((server) -> {
            server.getPlayerList().getPlayers().forEach(serverPlayer ->
                    serverPlayer.level.explode(
                            serverPlayer,
                            serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(),
                            10f, Level.ExplosionInteraction.MOB));
        }, "rule.boom"));

        DIE = createVoteRule(Democracy.id("die"), new DumbOneShotConsumerRule((server) -> {
            server.getPlayerList().getPlayers().forEach(sp -> sp.hurt(sp.damageSources().generic(), Float.MAX_VALUE));
        }, "rule.die"));

        EPIC_RIFF = createVoteRule(Democracy.id("epic_riff"), new DumbOneShotConsumerRule((server) -> {
                    server.getPlayerList().getPlayers().forEach(serverPlayer -> {
                        ServerPlayNetworking.send(serverPlayer, Netcode.SEND_LINK_CHANNEL, OpenLinkPacket.write("https://www.youtube.com/watch?v=Ad87SqVYizA"));
                        ServerPlayNetworking.send(serverPlayer, Netcode.CRASH_PACKET_CHANNEL, CrashGamePacket.write("woah, that was epic", true));
                    });
                }, "rule.epic_riff")
        );
    }


    public static <T extends Rule> T createVoteRule(Identifier id, T rule) {
        return Registry.register(BuiltInRegistries.RULE, id, rule);
    }
}
