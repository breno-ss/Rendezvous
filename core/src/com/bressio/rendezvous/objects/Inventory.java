package com.bressio.rendezvous.objects;

import com.bressio.rendezvous.graphics.ResourceHandler;

import java.util.ArrayList;

public class Inventory {

    private ArrayList<EntityObject> items;

    public Inventory(ResourceHandler resources) {
        items = new ArrayList<>();
        items.add(new Empty(resources));
        items.add(new Empty(resources));
        items.add(new Empty(resources));
        items.add(new Empty(resources));
        items.add(new Empty(resources));
        items.add(new Empty(resources));
    }

    public ArrayList<EntityObject> getItems() {
        return items;
    }
}
