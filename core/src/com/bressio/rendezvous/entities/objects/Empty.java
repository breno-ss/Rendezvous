package com.bressio.rendezvous.entities.objects;

import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.scenes.Match;

public class Empty extends EntityObject {

    public Empty(Match match) {
        super(match);
        setAttributes();
    }

    private void setAttributes() {
        setName(getI18n().getBundle().get("empty"));
        setIcon(getResources().getTexture(ResourceHandler.TexturePath.EMPTY_SLOT));
    }
}
