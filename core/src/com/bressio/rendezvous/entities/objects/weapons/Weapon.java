package com.bressio.rendezvous.entities.objects.weapons;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.objects.EntityObject;
import com.bressio.rendezvous.scenes.Match;

public abstract class Weapon extends EntityObject {

    private int damage;
    private int rateOfFire;
    private int reloadTime;
    private int magCapacity;
    private int accuracy;

    public Weapon(Match match) {
        super(match);
    }

    @Override
    public abstract boolean transformSoldier(Soldier soldier);

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRateOfFire(int rateOfFire) {
        this.rateOfFire = rateOfFire;
    }

    public void setReloadTime(int reloadTime) {
        this.reloadTime = reloadTime;
    }

    public void setMagCapacity(int magCapacity) {
        this.magCapacity = magCapacity;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
