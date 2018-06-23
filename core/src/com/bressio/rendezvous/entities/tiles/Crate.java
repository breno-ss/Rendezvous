package com.bressio.rendezvous.entities.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.bressio.rendezvous.entities.objects.weapons.ars.STAR;
import com.bressio.rendezvous.entities.objects.weapons.ars.W16A;
import com.bressio.rendezvous.entities.objects.weapons.pistols.G21;
import com.bressio.rendezvous.entities.objects.weapons.pistols.P26;
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
        getItems().add(new G21(getMatch()));
        getItems().add(new P26(getMatch()));
        getItems().add(new STAR(getMatch()));
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
