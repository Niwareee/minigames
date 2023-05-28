package fr.niware.gamesapi.utils.gui;

import fr.niware.gamesapi.AbstractPlugin;
import fr.niware.gamesapi.game.player.AbstractPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class RInventory<T extends AbstractPlayer<?>> implements InventoryHolder {

    private final Inventory inventory;
    private final PageController pageController;
    private final Map<Integer, Consumer<InventoryClickEvent>> sharedMap;
    private final List<RInventoryRunnable> runnableList;

    protected AbstractPlugin<?, T, ?> plugin;

    protected RInventory(AbstractPlugin<?, T, ?> plugin) {
        this.plugin = plugin;
        this.inventory = plugin.getServer().createInventory(this, this.getSize() * 9, Component.text(this.getName()));
        this.pageController = new PageController(this);
        this.sharedMap = new HashMap<>();
        this.runnableList = new ArrayList<>();
    }

    public void addItem(ItemStack itemStack) {
        int slot = this.inventory.firstEmpty();
        this.setItem(slot, itemStack);
    }

    public void addItem(ItemStack itemStack, Consumer<InventoryClickEvent> consumer) {
        int slot = this.inventory.firstEmpty();
        this.setItem(slot, itemStack, consumer);
    }

    public void setItem(int slot, ItemStack itemStack) {
        this.sharedMap.remove(slot);
        this.inventory.setItem(slot, itemStack);
    }

    public void setItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> event) {
        this.setItem(slot, itemStack);
        if (event != null) {
            this.sharedMap.put(slot, event);
        }
    }

    public void setItem(int[] slot, ItemStack itemStack) {
        this.setItem(slot, itemStack, null);
    }

    public void setItem(int[] slot, ItemStack itemStack, Consumer<InventoryClickEvent> event) {
        for (int i : slot) {
            this.setItem(i, itemStack, event);
        }
    }

    public void setPageController(Consumer<PageController> pageController) {
        pageController.accept(this.pageController);
        this.pageController.setUp();
    }

    public PageController getPageController() {
        return this.pageController;
    }

    public void update(Runnable runnable, int delay) {
        this.runnableList.add(new RInventoryRunnable(runnable, delay));
    }

    public int[] getBoard(int slotFrom, int length, int width) {
        int[] board = new int[length * width];
        int size = 0;
        int l = 0;
        int w = 0;
        while (board.length != size) {
            board[size] = slotFrom + w + l;
            l++;
            size++;
            if (l == length) {
                l = 0;
                w += 9;
            }
        }
        return board;
    }

    public void open(Player player) {
        player.openInventory(this.inventory);
    }

    protected abstract String getName();

    protected abstract int getSize();

    @Override
    @Nonnull
    public Inventory getInventory() {
        return this.inventory;
    }

    public Map<Integer, Consumer<InventoryClickEvent>> getSharedMap() {
        return this.sharedMap;
    }

    public List<RInventoryRunnable> getRunnableList() {
        return this.runnableList;
    }
}
