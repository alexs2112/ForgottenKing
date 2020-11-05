package audio;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import screens.Screen;

public class AudioPlayer {
	private List<Audio> audio;

	public AudioPlayer() {
		audio = new ArrayList<Audio>(EnumSet.allOf(Audio.class));
	}
	
	/**
	 * Starts playing each mediaplayer on loop, muting the non-main ones
	 */
	public void begin() {
		for (Audio p : audio) {
			p.setAutoPlay(true);
			p.setVolume(0);
		}
		Audio.INTRO.setVolume(Audio.INTRO.on());
	}
	
	/**
	 * The method that is called whenever the player hits a key to determine if changes to the music should be made
	 */
	public void update(Screen screen) {
		for (Audio a : audio) {
			if (screen.audio() == a)
				a.fadeTo(a.on());
			else
				a.fadeTo(0);
		}
	}
	
}
