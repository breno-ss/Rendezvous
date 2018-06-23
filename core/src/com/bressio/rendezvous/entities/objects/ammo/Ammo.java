package com.bressio.rendezvous.entities.objects.ammo;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.objects.EntityObject;
import com.bressio.rendezvous.scenes.Match;

public abstract class Ammo extends EntityObject {

    private int amount;

    public Ammo(Match match) {
        super(match);
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
