package libs.GameWorld;

import libs.Player.Player;
import libs.Field.Ladang;
import libs.Deck.ActiveDeck;
import libs.Card.CardFactory;
import controller.MainController;
import javafx.application.Platform;
import libs.Card.Card;

public class BearAttack extends Thread {
    private boolean running;
    private Player player;
    private int rowstart;
    private int rowend;
    private int colstart;
    private int colend;
    private int duration;
    private MainController mc;

    public BearAttack(int rowstart, int rowend, int colstart, int colend, int duration, MainController mc) {
        this.player = GameWorld.getInstance().getCurrentPlayer();
        this.rowend = rowend;
        this.rowstart = rowstart;
        this.colend = colend;
        this.colstart = colstart;
        this.duration = duration;
        this.mc = mc;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void run() {
        running = true;
        Ladang ladang = player.getField();
        long startTime = System.currentTimeMillis();
        mc.onTimer();
        while (running && System.currentTimeMillis() - startTime < duration * 1000) {
            try {
                // 100 ms or 0.1 seconds
                Thread.sleep(100);
                long elapsedTime = System.currentTimeMillis() - startTime;
                long remainingTime = (duration * 1000) - elapsedTime;

                for (int i = rowstart; i <= rowend; i++) {
                    for (int j = colstart; j <= colend; j++) {
                        if (ladang.getHarvestable(i, j) != null) {
                            if (ladang.getHarvestable(i, j).isTrapped()) {
                                running = false;
                                ActiveDeck deck = player.getActiveDeck();
                                Card card = CardFactory.createCard("BERUANG");
                                if (!deck.isFull()) {
                                    deck.add(card);
                                }
                            }
                        }
                    }
                }

                // Update the timer label on the JavaFX Application Thread
                if (remainingTime > 0) {

                    Platform.runLater(() -> {
                        mc.setTimer(String.format("%.1f", ((float) remainingTime) / 1000));
                    });

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (this) {

            for (int i = rowstart; i <= rowend; i++) {
                for (int j = colstart; j <= colend; j++) {
                    if (ladang.getHarvestable(i, j) != null) {
                        if (!(ladang.getHarvestable(i, j).isProtected())
                                && !(ladang.getHarvestable(i, j).isTrapped())) {
                            System.out.println(ladang.getHarvestable(i, j).isProtected());
                            ladang.removeHarvestable(i, j);
                        }
                    }

                }
            }
            Platform.runLater(() -> {
                mc.offTimer();
                GameWorld.getInstance().movePhase(1);
            });
        }
    }
}
