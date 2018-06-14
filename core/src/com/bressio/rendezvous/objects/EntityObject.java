package com.bressio.rendezvous.objects;

import com.badlogic.gdx.graphics.Texture;
import com.bressio.rendezvous.languages.Internationalization;

public class EntityObject {

    private String name;
    private Texture icon;
    private Internationalization i18n;

    EntityObject() {
        i18n = new Internationalization();
    }

    void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    Internationalization getI18n() {
        return i18n;
    }

    void setIcon(Texture icon) {
        this.icon = icon;
    }

    public Texture getIcon() {
        return icon;
    }
}
