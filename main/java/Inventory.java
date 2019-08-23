package main.java;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory(){
        items = new ArrayList<>();
    }

    public ArrayList<Item> getItems(){
        return items;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void clearInventory(){
        items.clear();
    }
}
