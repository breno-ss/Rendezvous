package com.bressio.rendezvous.entities.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.bressio.rendezvous.entities.objects.equipment.armor.MilitaryVest;
import com.bressio.rendezvous.entities.objects.equipment.armor.SoftVest;
import com.bressio.rendezvous.entities.objects.equipment.helmets.CombatHelmet;
import com.bressio.rendezvous.entities.objects.equipment.helmets.HalfHelmet;
import com.bressio.rendezvous.scenes.Match;

public class Crate extends Loot {

    public Crate(Rectangle bounds, Match match) {
        super(bounds, match);
        setUserData();
        addItems();
    }

    private void setUserData() {
        getFixture().setUserData(this);
    }

    private void addItems() {
        getItems().add(new MilitaryVest(getMatch()));
        getItems().add(new SoftVest(getMatch()));
        getItems().add(new CombatHelmet(getMatch()));
        getItems().add(new HalfHelmet(getMatch()));
    }

    @Override
    public void onPlayerEnter() {
        super.onPlayerEnter();
    }

    @Override
    public void onPlayerLeave() {
        super.onPlayerLeave();
    }
}
