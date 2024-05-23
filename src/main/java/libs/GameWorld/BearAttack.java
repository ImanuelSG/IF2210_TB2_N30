package libs.GameWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import libs.Player.Player;
import libs.Field.Field;

public class BearAttack extends Thread {
    private int duration;
    private boolean running;
    private Player player;

    public BearAttack(Player player) {
        this.player = player;
        Random random = new Random();
        this.duration = random.nextInt(60 - 30 + 1) + 30;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public void run() {
        Random random = new Random();
        boolean found = false;
        int startX; int startY;
        int endX; int endY;
        int result;
        do {
            startX = random.nextInt(5);
            startY = random.nextInt(4);

            endX = random.nextInt(5);
            endY = random.nextInt(4);

            result = Math.abs(startX-endX) * Math.abs(startY-endY);
            if (result <= 6 && result >= 0) {
                found = true;
            }
        } while (!found);


        long startTime = System.currentTimeMillis();
        while (running && System.currentTimeMillis() - startTime < duration * 1000) {
            try {
                // 100 ms atau 0.1 
                Thread.sleep(100);
                long elapsedTime = System.currentTimeMillis() - startTime;
                long remainingTime = (duration * 1000) - elapsedTime;

                // seharusnya display nih pas ini
                System.out.println("Sisa waktu: " + remainingTime / 1000 + " detik");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        synchronized (this) {
            Field field = player.getField();
            int minX = Math.min(startX, endX);
            int minY = Math.min(startY, endY);
            int maxX = Math.max(startX, endX);
            int maxY = Math.max(startY, endY);
            for (int i = minY; i < maxY; i++) {
                for (int j = minX; j < maxX; j++) {
                    field.removeHarvestable(i, j);
                }
            }
        }
    }
};
