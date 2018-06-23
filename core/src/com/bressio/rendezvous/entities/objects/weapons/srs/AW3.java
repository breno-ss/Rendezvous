package com.bressio.rendezvous.entities.objects.weapons.srs;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class AW3 extends SniperRifle {

    public AW3(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("aW3"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.AW3));
        setDamage(60);
        setRateOfFire(1);
        setReloadTime(5);
        setMagCapacity(1);
        setAccuracy(1);
    }
}