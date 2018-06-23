package com.bressio.rendezvous.entities.objects.weapons.pistols;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class G21 extends Pistol {

    public G21(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("g21"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.G21));
        setDamage(5);
        setRateOfFire(2);
        setReloadTime(2);
        setMagCapacity(15);
        setAccuracy(3);
    }
}