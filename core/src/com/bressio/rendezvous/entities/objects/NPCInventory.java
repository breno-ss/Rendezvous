package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.scenes.Match;

public class NPCInventory extends Inventory{

    public NPCInventory(Match match) {
        super(match);
    }

    @Override
    public void useSelectedItem() {

    }

    @Override
    public void reloadSelectedWeapon() {

    }

    @Override
    public void applyAction() {

    }

    @Override
    public void transferAmmo(int bulletsInWeapon) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public String getBulletsInMagazine() {
        return null;
    }

    @Override
    public String getBulletsInAmmoBoxes() {
        return null;
    }
}
