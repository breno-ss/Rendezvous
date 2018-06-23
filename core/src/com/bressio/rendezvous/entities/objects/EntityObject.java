package com.bressio.rendezvous.entities.objects;

import com.badlogic.gdx.graphics.Texture;
import com.bressio.rendezvous.entities.Soldier;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.languages.Internationalization;
import com.bressio.rendezvous.scenes.Match;

public abstract class EntityObject {

    private Match match;
    private String name;
    private Texture icon;
    private ResourceHandler resources;
    private Internationalization i18n;

    public EntityObject(Match match) {
        this.match = match;
        init();
    }

    private void init() {
        resources = match.getResources();
        i18n = match.getI18n();
    }

    public abstract boolean transformSoldier(Soldier soldier);

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(Texture icon) {
        this.icon = icon;
    }

    public Texture getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public ResourceHandler getResources() {
        return resources;
    }

    public Internationalization getI18n() {
        return i18n;
    }
}
