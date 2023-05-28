package fr.niware.gamesapi.utils.packet;

import net.minecraft.network.protocol.Packet;

@SuppressWarnings("rawtypes")
@FunctionalInterface
public interface IPacketHandler<T extends Packet> {

    void handle(PacketEvent<T> event);

}