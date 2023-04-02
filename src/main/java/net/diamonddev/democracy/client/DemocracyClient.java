package net.diamonddev.democracy.client;

import net.diamonddev.democracy.exception.GoofyAhhCrashException;
import net.diamonddev.democracy.exception.TooMuchComedyException;
import net.diamonddev.democracy.network.CrashGamePacket;
import net.diamonddev.democracy.network.Netcode;
import net.diamonddev.democracy.network.OpenLinkPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.CrashReport;
import net.minecraft.Util;

import java.io.IOException;
import java.net.URL;

public class DemocracyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(Netcode.CRASH_PACKET_CHANNEL, ((client, handler, buf, responseSender) -> {
            CrashGamePacket.CrashGamePacketData data = CrashGamePacket.read(buf);
            Exception e = data.epicRiff ? new TooMuchComedyException() : new GoofyAhhCrashException();
            client.delayCrash(new CrashReport(data.string, e));
        }));

        ClientPlayNetworking.registerGlobalReceiver(Netcode.SEND_LINK_CHANNEL, ((client, handler, buf, responseSender) -> {
            OpenLinkPacket.OpenLinkPacketData data = OpenLinkPacket.read(buf);
            openWebpage(data.link);
        }));
    }

    public static void openWebpage(String urlString) {
        try {
            Util.getPlatform().openUrl(new URL(urlString));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
