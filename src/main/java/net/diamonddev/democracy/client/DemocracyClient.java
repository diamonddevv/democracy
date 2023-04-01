package net.diamonddev.democracy.client;

import net.diamonddev.democracy.network.CrashGamePacket;
import net.diamonddev.democracy.network.Netcode;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.CrashReport;

public class DemocracyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(Netcode.CRASH_PACKET_CHANNEL, ((client, handler, buf, responseSender) -> {
           // get packet
            CrashGamePacket.CrashGamePacketData data = CrashGamePacket.read(buf);

            client.delayCrash(new CrashReport(data.string, new Throwable("Intential Mod Design")));
        }));
    }
}
