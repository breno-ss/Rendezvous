package com.bressio.rendezvous.entities.objects.weapons.pistols;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.entities.objects.ammo.Nine;
import com.bressio.rendezvous.entities.objects.weapons.Weapon;
import com.bressio.rendezvous.scenes.Match;

public abstract class Pistol extends Weapon {

    private Nine ammo;

    public Pistol(Match match) {
        super(match);
    }

    @Override
    public abstract boolean transformSoldier(Soldier soldier);
}
