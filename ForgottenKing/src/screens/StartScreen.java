package screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

import audio.Audio;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class StartScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private int select = 0;
	public Audio audio() { return Audio.INTRO; }
	private Stage stage;
	private int min = 0;
	
	public StartScreen() {
		if (new File("savegame.ser").isFile())
			min = 0;
		else
			min = 1;
		select = min;
	}
	
	public void displayOutput(Stage stage) {
		Group root = new Group();
		this.stage = stage;
		scene = new Scene(root,1280,800);
		draw(root, Loader.startScreenFull.image(), 0, 0);
		int y = 320;
		
		if (min == 0) {
			Image load = Loader.darkButton.image();
			if (select == 0)
				load = Loader.lightButton.image();
			draw(root, load, 480, y+=68, clickLoadGame, mouseOver(0), null);
			writeCentered(root, "Continue", 640, y+44, font22, Color.WHITE, clickLoadGame, mouseOver(0), null);
		}
		
		Image start = Loader.darkButton.image();
		if (select == 1)
			start = Loader.lightButton.image();
		draw(root, start, 480, y+=68, clickNewGame, mouseOver(1), null);
		writeCentered(root, "New Game", 640, y+44, font22, Color.WHITE, clickNewGame, mouseOver(1), null);

		Image quit = Loader.darkButton.image();
		if (select == 2)
			quit = Loader.lightButton.image();
		draw(root, quit, 480, y += 68, clickQuit, mouseOver(2), null);
		writeCentered(root, "Quit", 640, y+44, font22, Color.WHITE, clickQuit, mouseOver(2), null);
		
		writeCentered(root, "[?] in game for help", 640, 740, font20, Color.WHITE);
		stage.setScene(scene);
	    stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ENTER)) {
			if (select == 1)
				return new CharacterSelectionScreen();
			else if (select == 2) {
				Platform.exit();
				return null;
			} else {
				try {
					return deserialize();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if (code.equals(KeyCode.DOWN))
			select = Math.min(select+1, 2);
		if (code.equals(KeyCode.UP))
			select = Math.max(select-1,  min);
    	return this;
    }
	
	private static PlayScreen deserialize() throws java.io.IOException, ClassNotFoundException {
		try (FileInputStream file = new FileInputStream("savegame.ser");
			ObjectInputStream o = new ObjectInputStream(file))
		{
			return (PlayScreen)o.readObject();
		}
	}
	
	private EventHandler<MouseEvent> clickQuit = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me) { 
			stage.close();
		}
	};
	private EventHandler<MouseEvent> clickNewGame = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me) { 
			refreshScreen = new CharacterSelectionScreen();
		}
	};
	private EventHandler<MouseEvent> clickLoadGame = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent me) { 
			try {
				refreshScreen = deserialize();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
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
