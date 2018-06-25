package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.entities.objects.ammo.Ammo;
import com.bressio.rendezvous.entities.objects.ammo.Nine;
import com.bressio.rendezvous.entities.objects.ammo.SevenSixTwo;
import com.bressio.rendezvous.entities.objects.equipment.armor.Armor;
import com.bressio.rendezvous.entities.objects.equipment.helmets.Helmet;
import com.bressio.rendezvous.entities.objects.weapons.Weapon;
import com.bressio.rendezvous.entities.objects.weapons.ars.STAR;
import com.bressio.rendezvous.entities.objects.weapons.ars.W16A;
import com.bressio.rendezvous.entities.objects.weapons.pistols.G21;
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
        items.add(new STAR(match));
        items.add(new G21(match));
        items.add(new SevenSixTwo(match));
        items.add(new Nine(match));
        items.add(new W16A(match));
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
        Object selectedSlot = getItem(match.getHud().getSelectedSlot());
        if (selectedSlot.getClass() == Medkit.class) {
            isSelectedBeingUsed = true;
            match.getProgress().setProgressSpeed(Medkit.timeToTransform());
            match.getPlayer().blockActions();
            match.getProgress().setActivity("healing");
        } else if (Weapon.class.isAssignableFrom(selectedSlot.getClass())) {
            ((Weapon)selectedSlot).shoot();
        }
    }

    public void reloadSelectedWeapon() {
        Object selectedSlotClass = getItem(match.getHud().getSelectedSlot());

         if (hasAmmoForWeaponType((Weapon)selectedSlotClass)) {
            isSelectedBeingUsed = true;
            match.getProgress().setProgressSpeed(((Weapon)selectedSlotClass).getTimeToTransform());
            match.getPlayer().slowDown();
            match.getProgress().setActivity("reloading");
        }
    }

    private boolean hasAmmoForWeaponType(Weapon weapon) {
        for (EntityObject item : items) {
            if (Ammo.class.isAssignableFrom(item.getClass()) && weapon.getAmmoType() == item.getClass()) {
                return true;
            }
        }
        return false;
    }

    public void applyAction(){
        getItem(match.getHud().getSelectedSlot()).transformSoldier(match.getPlayer());
        if (items.get(match.getHud().getSelectedSlot()).getClass() == Medkit.class) {
            items.set(match.getHud().getSelectedSlot(), new Empty(match));
        }
    }

    public void transferAmmo(int bulletsInWeapon) {
        int bullets = bulletsInWeapon;
        int amountNeeded = ((Weapon)getItem(match.getHud().getSelectedSlot())).getMagCapacity() - bulletsInWeapon;
        for (int i = 0; i < items.size(); i++) {

            if (Ammo.class.isAssignableFrom(items.get(i).getClass()) && amountNeeded > 0 &&
                    ((Weapon)getItem(match.getHud().getSelectedSlot())).getAmmoType() == items.get(i).getClass()) {

                Ammo ammoBox = ((Ammo)items.get(i));
                if (ammoBox.getAmount() >= amountNeeded) {
                    bullets += amountNeeded;
                    ammoBox.useAmount(amountNeeded);
                    amountNeeded = 0;
                } else {
                    bullets += ammoBox.getAmount();
                    ammoBox.useAll();
                    amountNeeded -= ammoBox.getAmount();
                }
                if (ammoBox.getAmount() == 0) {
                    items.set(i, new Empty(match));
                } else {
                    ammoBox.updateName();
                }
            }
        }
        ((Weapon)getItem(match.getHud().getSelectedSlot())).setBullets(bullets);
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

    public String getBulletsInMagazine() {
        if (Weapon.class.isAssignableFrom(getItem(match.getHud().getSelectedSlot()).getClass())) {
            return String.valueOf(((Weapon)getItem(match.getHud().getSelectedSlot())).getBullets());
        } else {
            return null;
        }
    }

    public String getBulletsInAmmoBoxes() {
        if (Weapon.class.isAssignableFrom(getItem(match.getHud().getSelectedSlot()).getClass())) {
            int bullets = 0;
            for (EntityObject item : items) {
                if (Ammo.class.isAssignableFrom(item.getClass()) &&
                        ((Weapon)getItem(match.getHud().getSelectedSlot())).getAmmoType() == item.getClass()) {
                        bullets += ((Ammo)item).getAmount();
                }
            }
            return String.valueOf(bullets);
        } else {
            return null;
        }
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
