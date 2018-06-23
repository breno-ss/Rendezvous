package com.bressio.rendezvous.entities.objects.weapons.pistols;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class P26 extends Pistol {

    public P26(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("p26"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.P26));
        setDamage(7);
        setRateOfFire(3);
        setReloadTime(1);
        setMagCapacity(17);
        setAccuracy(2);
    }
}