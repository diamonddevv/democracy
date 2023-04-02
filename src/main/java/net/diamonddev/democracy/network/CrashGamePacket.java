package net.diamonddev.democracy.network;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;

public class CrashGamePacket {
    public static FriendlyByteBuf write(String string, boolean epicRiff) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeComponent(Component.literal(string));
        buf.writeBoolean(epicRiff);
        return buf;
    }

    public static FriendlyByteBuf write(String string) {
        return write(string, false);
    }

    public static CrashGamePacketData read(FriendlyByteBuf buf) {
        CrashGamePacketData data = new CrashGamePacketData();
        data.string = buf.readComponent().getString();
        data.epicRiff = buf.readBoolean();
        return data;
    }


    public static class CrashGamePacketData {
        public String string;
        public boolean epicRiff;
    }
}
