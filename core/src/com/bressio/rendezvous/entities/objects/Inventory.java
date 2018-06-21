package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

public class Inventory {

    private Match match;
    private ArrayList<EntityObject> items;
    private ArrayList<EntityObject> equipmentItems;
    private boolean isSelectedBeingUsed;

    public Inventory(Match match) {
        this.match = match;
        addItems();
    }

    private void addItems() {
        items = new ArrayList<>();
        items.add(new Empty(match));
        items.add(new Empty(match));
        items.add(new Empty(match));
        items.add(new Empty(match));
        items.add(new Empty(match));
        items.add(new Empty(match));

        equipmentItems = new ArrayList<>();
        equipmentItems.add(new Empty(match));
        equipmentItems.add(new Empty(match));
    }

    public boolean isSelectedBeingUsed() {
        return isSelectedBeingUsed;
    }

    public void setSelectedBeingUsed(boolean selectedBeingUsed) {
        isSelectedBeingUsed = selectedBeingUsed;
    }

    public void useSelectedItem() {
        Object selectedSlotClass = getItem(match.getHud().getSelectedSlot()).getClass();
        if (selectedSlotClass == Medkit.class) {
            isSelectedBeingUsed = true;
            match.getPlayer().blockActions();
            match.getProgress().setActivity("healing");
        }
    }

    public void consumeSelectedItem(){
        getItem(match.getHud().getSelectedSlot()).transformSoldier(match.getPlayer());
        items.set(match.getHud().getSelectedSlot(), new Empty(match));
    }

    public void update(float delta) {
        setArmorPoints(delta);
    }

    private void setArmorPoints(float delta) {
        int armorPoints = 0;
        armorPoints += equipmentItems.get(0).getClass() != Empty.class ? ((Helmet)equipmentItems.get(0)).getArmorPoints() : 0;
        armorPoints += equipmentItems.get(1).getClass() != Empty.class ? ((Armor)equipmentItems.get(1)).getArmorPoints() : 0;
        match.getPlayer().setArmor(armorPoints);
    }

    public ArrayList<EntityObject> getItems() {
        return items;
    }

    public ArrayList<EntityObject> getEquipmentItems() {
        return equipmentItems;
    }

    public EntityObject getItem(int index) {
        return items.get(index);
    }
}
