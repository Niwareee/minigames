package fr.niware.gamesapi.utils.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PageController {

    private final RInventory<?> rInventory;
    private final Map<Integer, List<RInventoryData>> map;

    private List<RInventoryData> list;
    private int[] board;
    private int page;

    public PageController(RInventory<?> rInventory) {
        this.rInventory = rInventory;
        this.map = new HashMap<>();

        this.list = new ArrayList<>();
        this.page = 0;
        this.board = new int[]{};
    }

    public void setBoard(int slotFrom, int length, int width) {
        this.board = this.rInventory.getBoard(slotFrom, length, width);
    }

    public void setBoard(int... ints) {
        this.board = ints;
    }

    public void setItemStacks(List<RInventoryData> list) {
        this.list = list;
    }

    public void nextPage() {
        if (!this.isLast()) {
            this.page++;
            this.setPage(this.page);
        }
    }

    public void previousPage() {
        if (!this.isFirst()) {
            this.page--;
            this.setPage(this.page);
        }
    }

    private void setPage(int page) {
        for (int i : this.board) {
            this.rInventory.getSharedMap().remove(i);
            this.rInventory.getInventory().clear(i);
        }
        List<RInventoryData> rInventoryData = this.map.get(page);
        for (int i = 0; i < rInventoryData.size(); i++) {
            RInventoryData data = rInventoryData.get(i);
            this.rInventory.setItem(this.board[i], data.getItemStack(), data.getConsumer());
        }
    }

    public void setUp() throws NumberFormatException {
        if (this.board.length == 0) {
            throw new NumberFormatException("The board is empty");
        }
        this.map.clear();
        this.page = 0;
        int localPage = 0;
        int size = 0;
        for (RInventoryData rInventoryData : this.list) {
            if (size == this.board.length) {
                localPage++;
                size = 0;
            }

            this.map.computeIfAbsent(localPage, e -> new ArrayList<>());
            this.map.get(localPage).add(rInventoryData);
            size++;
        }
        if (!this.map.isEmpty()) {
            this.setPage(0);
        }
    }

    public int getPage() {
        return this.page;
    }

    public int getMaxPage() {
        return this.map.size();
    }

    public boolean isFirst() {
        return this.page == 0;
    }

    public boolean isLast() {
        return (this.getPage() + 1) == (this.getMaxPage() == 0 ? 1 : this.getMaxPage());
    }
}
