package com.bressio.rendezvous.events;

import com.bressio.rendezvous.gui.HUD;
import com.bressio.rendezvous.languages.Internationalization;
import com.bressio.rendezvous.scheme.PhysicsAdapter;

public class RendezvousController {

    private float timeCount;
    private int secondsToNextEvent;
    private int event;
    private String rendezvousLabel;

    private Internationalization i18n;
    private HUD hud;

    public RendezvousController(Internationalization i18n, HUD hud) {
        this.i18n = i18n;
        this.hud = hud;
        secondsToNextEvent = 5;
        event = 1;
        rendezvousLabel = i18n.getBundle().get("nextRendezvous");

        hud.updateEventLabel(rendezvousLabel, isInRendezvous());
        hud.updateTimeLabel(PhysicsAdapter.formatSeconds(secondsToNextEvent, false));
    }

    public void update(float delta) {
        if (event < 10) {
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
                    rendezvousLabel = i18n.getBundle().get("fourthRendezvous");
                } else if (event == 10) {
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

    private boolean isInRendezvous() {
        return event % 2 == 0;
    }
}
