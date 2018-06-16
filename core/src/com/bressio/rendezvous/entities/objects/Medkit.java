package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class Medkit extends EntityObject{

    public Medkit(Match match) {
        super(match);
        setAttributes();
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("medkit"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.MEDKIT_ICON));
    }
}
