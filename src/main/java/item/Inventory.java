package item;

import java.util.*;

public class Inventory {

    private final byte MAX_ITEMS = 4;

    private Item[] items;

    public Inventory() {
        items = new Item[MAX_ITEMS];
    }

    public void addItem(Item item) {
        for (byte i = 0; i < MAX_ITEMS; i++) {
            if (items[i] == null) {
                items[i] = item;
                break;
            }
        }
    }

    public void removeItem(Item item) {
        for (byte i = 0; i < MAX_ITEMS; i++) {
            if (items[i] == null) continue;

            if (items[i].getName().equals(item.getName())) {
                items[i] = null;
                break;
            }
        }
    }

    public byte getMaxItems() {
        return MAX_ITEMS;
    }

    public Item[] getItems() {
        return items;
    }

    public Item getItem(int i) {
        return items[i];
    }

}
