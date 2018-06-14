package com.bressio.rendezvous.objects;

import com.bressio.rendezvous.graphics.ResourceHandler;

public class Medkit extends EntityObject{

    public Medkit(ResourceHandler resources) {
        super();
        setName(getI18n().getBundle().get("medkit"));
        setIcon(resources.getTexture(ResourceHandler.TexturePath.MEDKIT_ICON));
    }
}
