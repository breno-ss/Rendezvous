package com.bressio.rendezvous.entities.objects.weapons.srs;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class M20 extends SniperRifle {

    public M20(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("m20"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.M20));
        setDamage(15);
        setRateOfFire(1);
        setReloadTime(4);
        setMagCapacity(5);
        setAccuracy(1);
    }
}