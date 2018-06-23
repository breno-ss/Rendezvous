package com.bressio.rendezvous.entities.objects.weapons.ars;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class W16A extends AssaultRifle {

    public W16A(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("w16A"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.W16A));
        setDamage(6);
        setRateOfFire(12);
        setReloadTime(4);
        setMagCapacity(20);
        setAccuracy(4);
    }
}