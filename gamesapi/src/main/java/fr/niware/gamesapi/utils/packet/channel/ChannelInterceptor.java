package fr.niware.gamesapi.utils.packet.channel;

import fr.niware.gamesapi.utils.packet.PacketManager;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;

@SuppressWarnings("rawtypes")
public class ChannelInterceptor extends ChannelDuplexHandler {

    private final PacketManager packetManager;
    private final Player player;

    public ChannelInterceptor(PacketManager packetManager, Player player) {
        this.packetManager = packetManager;
        this.player = player;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!this.packetManager.handlePacket((Packet) msg, this.player)) {
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!this.packetManager.handlePacket((Packet) msg, this.player)) {
            super.write(ctx, msg, promise);
        }
    }
}

