package screens;

import audio.Audio;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PauseScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private int select = 0;
	public Audio audio() { return Audio.INTRO; }
	private PlayScreen ps;
	
	public PauseScreen(PlayScreen playScreen) {
		this.ps = playScreen;
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.screenBorder.image(), 0, 0);
		int y = 248;
		
		writeCentered(root, "PAUSED", 640, 200, font36, Color.WHITE);
		
		Image close = Loader.darkButton.image();
		if (select == 0)
			close = Loader.lightButton.image();
		draw(root, close, 480, y+=68, clickContinue, mouseOver(0), null);
		writeCentered(root, "Continue", 640, y+44, font22, Color.WHITE, clickContinue, mouseOver(0), null);
		
		Image load = Loader.darkButton.image();
		if (select == 1)
			load = Loader.lightButton.image();
		draw(root, load, 480, y+=68, clickSaveGame, mouseOver(1), null);
		writeCentered(root, "Save and Quit", 640, y+44, font22, Color.WHITE, clickSaveGame, mouseOver(1), null);

		Image help = Loader.darkButton.image();
		if (select == 2)
			help = Loader.lightButton.image();
		draw(root, help, 480, y += 68, clickHelp, mouseOver(2), null);
		writeCentered(root, "[?] Help", 640, y+44, font22, Color.WHITE, clickHelp, mouseOver(2), null);
		
		stage.setScene(scene);
	    stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ENTER)) {
			if (select == 0)
				return null;
			else if (select == 1) {
				ps.serialize();
				Platform.exit();
				return null;
			}
			else if (select == 2) {
				return new HelpScreen();
			}
		}
		else if (code.equals(KeyCode.DOWN))
			select = Math.min(select+1, 2);
		else if (code.equals(KeyCode.UP))
			select = Math.max(select-1, 0);
		else if (code.equals(KeyCode.ESCAPE))
			return null;
    	return this;
    }
	
	private EventHandler<MouseEvent> clickSaveGame = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me) { 
			//ps.serialize();
			//Serialized as part of exiting
			Platform.exit();
		}
	};
	private EventHandler<MouseEvent> clickHelp = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me) { 
			refreshScreen = new HelpScreen();
		}
	};
	private EventHandler<MouseEvent> clickContinue = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me) { 
			refreshScreen = null;
		}
	};
	private EventHandler<MouseEvent> mouseOver(int index) {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				if (select != index) {
					select = index;
					refreshScreen = returnThis();
				}
			}
		};
	}
}
