package gregtech.api.net;

import gregtech.api.net.NetworkHandler.Packet;

public class PacketClipboard implements Packet {

    public final String text;

    public PacketClipboard(final String text) {
        this.text = text;
    }
}
