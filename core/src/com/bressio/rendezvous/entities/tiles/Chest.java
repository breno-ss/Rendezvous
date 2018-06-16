package com.bressio.rendezvous.entities.tiles;

import com.badlogic.gdx.math.Rectangle;
import com.bressio.rendezvous.entities.objects.Empty;
import com.bressio.rendezvous.entities.objects.Medkit;
import com.bressio.rendezvous.scenes.Match;

public class Chest extends Loot {

    public Chest(Rectangle bounds, Match match) {
        super(bounds, match);
        setUserData();
        addItems();
    }

    private void setUserData() {
        getFixture().setUserData(this);
    }

    private void addItems() {
        getItems().add(new Medkit(getMatch()));
        getItems().add(new Empty(getMatch()));
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
