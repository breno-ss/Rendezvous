package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.entities.objects.ammo.Ammo;
import com.bressio.rendezvous.entities.objects.ammo.FiveFiveSix;
import com.bressio.rendezvous.entities.objects.equipment.armor.Armor;
import com.bressio.rendezvous.entities.objects.equipment.helmets.Helmet;
import com.bressio.rendezvous.entities.objects.weapons.Weapon;
import com.bressio.rendezvous.entities.objects.weapons.ars.W16A;
import com.bressio.rendezvous.scenes.Match;

import java.util.ArrayList;

public abstract class Inventory {

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
        items.add(new W16A(match));
        items.add(new FiveFiveSix(match));
        items.add(new FiveFiveSix(match));
        items.add(new FiveFiveSix(match));
        items.add(new FiveFiveSix(match));
        items.add(new FiveFiveSix(match));

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

    public abstract void useSelectedItem();

    public abstract void reloadSelectedWeapon();

    protected boolean hasAmmoForWeaponType(Weapon weapon) {
        for (EntityObject item : items) {
            if (Ammo.class.isAssignableFrom(item.getClass()) && weapon.getAmmoType() == item.getClass()) {
                return true;
            }
        }
        return false;
    }

    public abstract void applyAction();

    public abstract void transferAmmo(int bulletsInWeapon);

    public void update(float delta) {
        setArmorPoints(delta);
    }

    private void setArmorPoints(float delta) {
        int armorPoints = 0;
        armorPoints += equipmentItems.get(0).getClass() != Empty.class ? ((Helmet)equipmentItems.get(0)).getArmorPoints() : 0;
        armorPoints += equipmentItems.get(1).getClass() != Empty.class ? ((Armor)equipmentItems.get(1)).getArmorPoints() : 0;
        match.getPlayer().setArmor(armorPoints);
    }

    public abstract String getBulletsInMagazine();

    public abstract String getBulletsInAmmoBoxes();

    public ArrayList<EntityObject> getItems() {
        return items;
    }

    public ArrayList<EntityObject> getEquipmentItems() {
        return equipmentItems;
    }

    public EntityObject getItem(int index) {
        return items.get(index);
    }

    public Match getMatch() {
        return match;
    }
}
