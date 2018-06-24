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
    private int bullets;

    public Weapon(Match match) {
        super(match);
    }

    public boolean transformSoldier(Soldier soldier) {
        soldier.getInventory().transferAmmo(bullets);
        return true;
    }

    public abstract Object getAmmoType();

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

    public void setBullets(int bullets) {
        this.bullets = bullets;
    }

    public abstract float getTimeToTransform();

    public int getBullets() {
        return bullets;
    }

    public int getMagCapacity() {
        return magCapacity;
    }
}
