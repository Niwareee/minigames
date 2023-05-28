package fr.niware.gamesapi.utils.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public final class RInventoryData {

    private final ItemStack itemStack;
    private final Consumer<InventoryClickEvent> consumer;

    public RInventoryData(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    public ItemStack getItemStack() {
        return this.itemStack;
    }

    public Consumer<InventoryClickEvent> getConsumer() {
        return this.consumer;
    }
}
