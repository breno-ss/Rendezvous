package com.bressio.rendezvous.entities.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.bressio.rendezvous.entities.objects.ammo.FiveFiveSix;
import com.bressio.rendezvous.entities.objects.ammo.Nine;
import com.bressio.rendezvous.entities.objects.ammo.SevenSixTwo;
import com.bressio.rendezvous.entities.objects.weapons.ars.W16A;
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
        getItems().add(new FiveFiveSix(getMatch()));
        getItems().add(new Nine(getMatch()));
        getItems().add(new SevenSixTwo(getMatch()));
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
