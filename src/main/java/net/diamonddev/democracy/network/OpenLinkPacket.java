package net.diamonddev.democracy.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public class OpenLinkPacket {
    public static FriendlyByteBuf write(String link) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeComponent(Component.literal(link));
        return buf;
    }

    public static OpenLinkPacketData read(FriendlyByteBuf buf) {
        OpenLinkPacketData data = new OpenLinkPacketData();
        data.link = buf.readComponent().getString();
        return data;
    }


    public static class OpenLinkPacketData {
        public String link;
    }
}
