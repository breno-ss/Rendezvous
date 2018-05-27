package com.bressio.rendezvous.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

class KeyFrameIndexer {

    private Animation<TextureRegion> movingAnimation;

    KeyFrameIndexer(Texture texture, AnimationRegion animationRegion) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = animationRegion.getStartColumn(); i <= animationRegion.getAmountFrames(); i++) {
                frames.add(new TextureRegion(texture, i * animationRegion.getFrameSize(), animationRegion.getStartRow(),
                        animationRegion.getFrameSize(), animationRegion.getFrameSize()));
        }
        movingAnimation = new Animation<>(animationRegion.getFrameDuration(), frames);
        frames.clear();
    }

    Animation<TextureRegion> getMovingAnimation() {
        return movingAnimation;
    }
}
