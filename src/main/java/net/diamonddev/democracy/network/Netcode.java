package net.diamonddev.democracy.network;

import net.diamonddev.democracy.Democracy;
import net.diamonddev.democracy.fuckmojmaps.Identifier;

public class Netcode {
    public static Identifier CRASH_PACKET_CHANNEL = Democracy.id("crash_packet");
    public static Identifier SEND_LINK_CHANNEL = Democracy.id("send_link_packet");
}
