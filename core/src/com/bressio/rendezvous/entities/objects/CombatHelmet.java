package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class CombatHelmet extends Helmet {

    public CombatHelmet(Match match) {
        super(match);
        setAttributes();
    }

    @Override
    public boolean transformSoldier(Soldier soldier) {
        return false;
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("combatHelmet"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.COMBAT_HELMET));
        setArmorPoints(60);
        setDamage(0);
    }
}
