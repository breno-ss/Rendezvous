package com.bressio.rendezvous.entities.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.bressio.rendezvous.entities.objects.Medkit;
import com.bressio.rendezvous.entities.objects.weapons.ars.W16A;
import com.bressio.rendezvous.entities.objects.weapons.pistols.G21;
import com.bressio.rendezvous.entities.objects.weapons.srs.AW3;
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
        getItems().add(new Medkit(getMatch()));
        getItems().add(new G21(getMatch()));
        getItems().add(new AW3(getMatch()));
        getItems().add(new W16A(getMatch()));
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
