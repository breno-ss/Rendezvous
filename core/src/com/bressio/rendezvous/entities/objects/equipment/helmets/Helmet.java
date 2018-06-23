package com.bressio.rendezvous.entities.objects.equipment.helmets;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.objects.EntityObject;
import com.bressio.rendezvous.scenes.Match;

public abstract class Helmet extends EntityObject {

    private int armorPoints;
    private int damage;

    Helmet(Match match) {
        super(match);
    }

    @Override
    public abstract boolean transformSoldier(Soldier soldier);

    public int getArmorPoints() {
        return armorPoints - damage;
    }

    void setArmorPoints(int armorPoints) {
        this.armorPoints = armorPoints;
    }

    public int getDamage() {
        return damage;
    }

    void setDamage(int damage) {
        this.damage = damage;
    }
}