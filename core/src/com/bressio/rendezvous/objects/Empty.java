package com.bressio.rendezvous.objects;

import com.bressio.rendezvous.graphics.ResourceHandler;

public class Empty extends EntityObject {

    public Empty(ResourceHandler resources) {
        super();
        setName(getI18n().getBundle().get("empty"));
        setIcon(resources.getTexture(ResourceHandler.TexturePath.EMPTY_SLOT));
    }
}
