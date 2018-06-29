package com.bressio.rendezvous.entities.objects.equipment.armor;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class MilitaryVest extends Armor {

    public MilitaryVest(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("militaryVest"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.MILITARY_VEST));
        setArmorPoints(40);
        setDamage(0);
        setRarity(6);
    }
}