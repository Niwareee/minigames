package fr.niware.gamesapi.utils.packet;

import fr.niware.gamesapi.utils.reflect.FieldAccessor;
import fr.niware.gamesapi.utils.reflect.ReflectionUtil;
import net.minecraft.network.protocol.Packet;
import org.bukkit.entity.Player;

@SuppressWarnings("rawtypes")
public class PacketEvent<T extends Packet> {

    private final T packet;
    private final Player player;

    private boolean cancelled;

    PacketEvent(T packet, Player player) {
        this.packet = packet;
        this.player = player;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public T getPacket() {
        return this.packet;
    }

    public Player getPlayer() {
        return this.player;
    }

    public <U> U getField(Class<U> clazz, String fieldName) {
        try {
            FieldAccessor field = ReflectionUtil.getFieldAccessor(this.packet.getClass(), fieldName);
            return clazz.cast(field.getObject(this.packet));
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    public void setField(String fieldName, Object value) {
        try {
            FieldAccessor field = ReflectionUtil.getFieldAccessor(this.packet.getClass(), fieldName);
            field.setObject(this.packet, value);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex);
        }
    }
}

