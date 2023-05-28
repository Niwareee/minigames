package fr.niware.gamesapi.utils.packet;

import fr.niware.gamesapi.utils.packet.channel.ChannelInterceptor;
import net.minecraft.network.protocol.Packet;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"rawtypes", "BooleanMethodIsAlwaysInverted"})
public class PacketManager {

    private final Map<Class, Set<IPacketHandler>> handlers = new HashMap<>();

    public <T extends Packet> void addHandler(Class<T> packetClass, IPacketHandler<T> handler) {
        this.handlers.computeIfAbsent(packetClass, unused -> new HashSet<>()).add(handler);
    }

    public void addChannel(Player player) {
        ((CraftPlayer) player).getHandle().networkManager.channel.pipeline().addBefore("packet_handler", "ekalia_packet_handler", new ChannelInterceptor(this, player));
    }

    public <T extends Packet> boolean handlePacket(T packet, Player player) {
        Set<IPacketHandler> handlerList = this.handlers.get(packet.getClass());

        if (handlerList == null) {
            return false;
        }

        PacketEvent<T> event = new PacketEvent<>(packet, player);

        for (IPacketHandler handler : handlerList) {
            handler.handle(event);
        }

        return event.isCancelled();
    }
}

