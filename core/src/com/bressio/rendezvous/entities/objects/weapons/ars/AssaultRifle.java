package com.bressio.rendezvous.entities.objects.weapons.ars;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.objects.ammo.FiveFiveSix;
import com.bressio.rendezvous.entities.objects.weapons.Weapon;
import com.bressio.rendezvous.scenes.Match;

public abstract class AssaultRifle extends Weapon {

    private FiveFiveSix ammo;

    public AssaultRifle(Match match) {
        super(match);
    }

    @Override
    public abstract boolean transformSoldier(Soldier soldier);
}
