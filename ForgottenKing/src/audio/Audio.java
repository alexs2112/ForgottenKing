package audio;

import java.net.URISyntaxException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public enum Audio {
	MAIN("main.mp3", 0.2),
	INTRO("intro.mp3", 0.3),
	COMBAT("combat.mp3", 0.4),
	PAUSE("pause.mp3", 1),
	BOSS("boss.mp3", 0.4);
	private static final long serialVersionUID = 7769423305067121315L;
	private double onVolume;
	public double on() { return onVolume; }
	private MediaPlayer mediaPlayer;
	private Audio(String filename, double onVolume) {
		try {
			mediaPlayer = new MediaPlayer(new Media(AudioPlayer.class.getResource("audio/"+filename).toURI().toString()));
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		this.onVolume = onVolume;
	}
	
	public void setAutoPlay(boolean autoPlay) { mediaPlayer.setAutoPlay(autoPlay); }
	public void setVolume(double volume) { mediaPlayer.setVolume(volume); }
	public double getVolume() { return mediaPlayer.getVolume(); }
	
	public void fadeTo(double result) {
		Timeline timeline = new Timeline(
				new KeyFrame(Duration.seconds(1),
						new KeyValue(mediaPlayer.volumeProperty(), result)));
		timeline.play();
	}
}
