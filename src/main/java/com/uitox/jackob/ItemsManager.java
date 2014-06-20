package com.uitox.jackob;

import java.util.Arrays;


public class ItemsManager {

    public Item[] items = new Item[1];
    public int count;

    public void add(Item item) {

        if (count >= items.length) {

            int newlen = (items.length * 3) / 2 + 1;
            items = Arrays.copyOf(items, newlen);

        }
        items[count] = item;
        count++;

    }

    public Item[] get_Items() {
        Item[] newitems = new Item[count];

        for (int i = 0; i < newitems.length; i++) {

            newitems[i] = items[i];

        }
        return newitems;

    }

    public Item get_ItemById(String itemid) {
        Item newitembyid = null;
        for (Item item : items) {
            if (itemid.equals(item.get_itemid())) {
                newitembyid = item;
                break;
            }
        }

        if (newitembyid == null) {
            newitembyid = new Item("", 0, "");
        }

        return newitembyid;
    }
}
