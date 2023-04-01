package net.diamonddev.democracy.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public class CrashGamePacket {
    public static FriendlyByteBuf write(String string) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeComponent(Component.literal(string));
        return buf;
    }

    public static CrashGamePacketData read(FriendlyByteBuf buf) {
        CrashGamePacketData data = new CrashGamePacketData();
        data.string = buf.readComponent().getString();
        return data;
    }


    public static class CrashGamePacketData {
        public String string;
    }
}
