package libs.GameWorld;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioPlayer {
    private static AudioPlayer instance;
    private MediaPlayer mediaPlayerNormal;
    private MediaPlayer mediaPlayerBearAttack;

    // Private constructor to prevent instantiation
    private AudioPlayer(String normalAudioPath, String bearAttackAudioPath) {
        System.out.println(normalAudioPath);
        System.out.println(bearAttackAudioPath);
        Media mediaNormal = new Media(
                getClass().getResource(normalAudioPath).toExternalForm());
        Media mediaBearAttack = new Media(getClass().getResource(bearAttackAudioPath).toExternalForm());

        mediaPlayerNormal = new MediaPlayer(mediaNormal);
        mediaPlayerBearAttack = new MediaPlayer(mediaBearAttack);

        // Ensure audio loops indefinitely
        mediaPlayerNormal.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerBearAttack.setCycleCount(MediaPlayer.INDEFINITE);
    }

    // Public static method to provide access to the singleton instance
    public static synchronized AudioPlayer getInstance(String normalAudioPath, String bearAttackAudioPath) {
        if (instance == null) {
            instance = new AudioPlayer(normalAudioPath, bearAttackAudioPath);
        }
        return instance;
    }

    public void play() {
        stop(); // Stop any currently playing audio
        if (GameWorld.getInstance().isBearAttack()) {
            mediaPlayerBearAttack.play();
        } else {
            mediaPlayerNormal.play();
        }
    }

    public void stop() {
        if (mediaPlayerNormal.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerNormal.stop();
        }
        if (mediaPlayerBearAttack.getStatus() == MediaPlayer.Status.PLAYING) {
            mediaPlayerBearAttack.stop();
        }
    }
}
