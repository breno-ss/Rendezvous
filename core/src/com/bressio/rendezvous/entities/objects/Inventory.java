package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

public class Inventory {

    private Match match;
    private ArrayList<EntityObject> items;
    private boolean isSelectedBeingUsed;

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

    public boolean isSelectedBeingUsed() {
        return isSelectedBeingUsed;
    }

    public void setSelectedBeingUsed(boolean selectedBeingUsed) {
        isSelectedBeingUsed = selectedBeingUsed;
    }

    public void useSelectedItem() {
        if (getItem(match.getHud().getSelectedSlot()).getClass() == Medkit.class) {
            isSelectedBeingUsed = true;
            match.getPlayer().blockActions();
            match.getProgress().setActivity("healing");
        }
    }

    public void consumeSelectedItem(){
        getItem(match.getHud().getSelectedSlot()).transformSoldier(match.getPlayer());
        items.set(match.getHud().getSelectedSlot(), new Empty(match));
    }

    public ArrayList<EntityObject> getItems() {
        return items;
    }

    public EntityObject getItem(int index) {
        return items.get(index);
    }
}
