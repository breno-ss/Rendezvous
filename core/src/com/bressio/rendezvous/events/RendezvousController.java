package com.bressio.rendezvous.events;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.bressio.rendezvous.graphics.ResourceHandler;
import com.bressio.rendezvous.gui.HUD;
import com.bressio.rendezvous.languages.Internationalization;
import com.bressio.rendezvous.scheme.PhysicsAdapter;

import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScale;
import static com.bressio.rendezvous.scheme.PhysicsAdapter.pScaleCenter;

public class RendezvousController {

    private float timeCount;
    private int secondsToNextEvent;
    private int event;
    private String rendezvousLabel;

    private Internationalization i18n;
    private HUD hud;

    private SpriteBatch batch;
    private Texture safezone;
    private Texture dangerZone;

    public RendezvousController(SpriteBatch batch, Internationalization i18n, HUD hud, ResourceHandler resources) {
        this.batch = batch;
        this.i18n = i18n;
        this.hud = hud;
        secondsToNextEvent = 5;
        event = 1;
        rendezvousLabel = i18n.getBundle().get("nextRendezvous");

        hud.updateEventLabel(rendezvousLabel, isInRendezvous());
        hud.updateTimeLabel(PhysicsAdapter.formatSeconds(secondsToNextEvent, false));

        safezone = resources.getTexture(ResourceHandler.TexturePath.SAFEZONE);
        dangerZone = resources.getTexture(ResourceHandler.TexturePath.DANGER_ZONE);
    }

    public void update(float delta) {
        if (event < 8) {
            timeCount += delta;

            if (secondsToNextEvent == -1) {
                event++;
                if (event == 2) {
                    rendezvousLabel = i18n.getBundle().get("firstRendezvous");
                } else if (event == 4) {
                    rendezvousLabel = i18n.getBundle().get("secondRendezvous");
                } else if (event == 6) {
                    rendezvousLabel = i18n.getBundle().get("thirdRendezvous");
                } else if (event == 8) {
                    rendezvousLabel = i18n.getBundle().get("finalRendezvous");
                } else {
                    rendezvousLabel = i18n.getBundle().get("nextRendezvous");
                }
                hud.updateEventLabel(rendezvousLabel, isInRendezvous());
                secondsToNextEvent = 5;
                hud.updateTimeLabel(PhysicsAdapter.formatSeconds(secondsToNextEvent, false));
            }

            if (timeCount >= 1) {
                hud.updateTimeLabel(PhysicsAdapter.formatSeconds(secondsToNextEvent - 1, false));
                secondsToNextEvent--;
                timeCount = 0;
            }
        }
    }

    public void updateGraphics(float delta) {
        batch.begin();

        switch (event) {
            case 2:
                drawSafezone(2, 2, 0, 4);
                break;
            case 4:
                drawSafezone(2.5f, 1.5f, .5f, 3.5f);
                break;
            case 6:
                drawSafezone(3, 1, 1, 3);
                break;
            case 8:
                drawSafezone(3.5f, .5f, 1.5f, 2.5f);
                break;
        }
        batch.end();
    }

    private void drawSafezone(float sZXScale, float sZSScale, float dZP1Scale, float dZP2Scale) {

        float safezoneWidth = pScale(safezone.getWidth());
        float dangerZoneWidth = pScale(dangerZone.getWidth());

        int safezoneSize = safezone.getWidth();
        int dangerZoneSize = 1000;

        batch.draw(safezone,
                pScaleCenter(safezoneSize * sZXScale) - safezoneWidth,
                pScaleCenter(safezoneSize * sZXScale) - safezoneWidth,
                safezoneWidth * sZSScale,
                safezoneWidth * sZSScale);
        batch.draw(dangerZone,
                pScaleCenter(safezoneSize * dZP1Scale),
                -pScaleCenter(safezoneSize * 2),
                -dangerZoneWidth * dangerZoneSize,
                dangerZoneWidth * dangerZoneSize);
        batch.draw(dangerZone,
                pScaleCenter(safezoneSize * dZP2Scale),
                -pScaleCenter(safezoneSize * 2),
                dangerZoneWidth * dangerZoneSize,
                dangerZoneWidth * dangerZoneSize);
        batch.draw(dangerZone,
                pScaleCenter(safezoneSize * dZP1Scale),
                pScaleCenter(safezoneSize * dZP2Scale),
                safezoneWidth * sZSScale,
                dangerZoneWidth * dangerZoneSize);
        batch.draw(dangerZone,
                pScaleCenter(safezoneSize * dZP1Scale),
                pScaleCenter(safezoneSize * dZP1Scale),
                safezoneWidth * sZSScale,
                -dangerZoneWidth * dangerZoneSize);
    }

    private boolean isInRendezvous() {
        return event % 2 == 0;
    }
}
