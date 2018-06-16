package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

public class Inventory {

    private Match match;
    private ArrayList<EntityObject> items;

    public Inventory(Match match) {
        this.match = match;
        addItems();
    }

    private void addItems() {
        items = new ArrayList<>();
        items.add(new Medkit(match));
        items.add(new Empty(match));
        items.add(new Medkit(match));
        items.add(new Empty(match));
        items.add(new Empty(match));
        items.add(new Empty(match));
    }

    public ArrayList<EntityObject> getItems() {
        return items;
    }
}
