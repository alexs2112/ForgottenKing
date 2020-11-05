package application;
import audio.AudioPlayer;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import screens.Screen;
import screens.StartScreen;

public class Main extends Application {
	private Screen screen;
	private Stage primaryStage;
	private AudioPlayer audio;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			primaryStage.setTitle("Forgotten King");

			
			
			//primaryStage.setResizable(false); 
			primaryStage.getIcons().add(new Image(Screen.class.getResourceAsStream("resources/icon.png")));
			screen = new StartScreen();
			screen.displayOutput(primaryStage);
			addKeyHandler(screen);
			audio = new AudioPlayer();
			audio.begin();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void repaint() {
		screen.displayOutput(primaryStage);
	}

	public void keyPressed(KeyEvent key) {
		Screen sc2 = screen.respondToUserInput(key); 
		screen = sc2;
		if (screen == null)
			return;
		repaint();
		addKeyHandler(screen);
		audio.update(screen);
		if (screen.repeatKeyPress) {
			screen.repeatKeyPress = false;
			keyPressed(key);
		}
	}

	private void addKeyHandler(Screen screen) {
		screen.scene.setOnKeyPressed(key -> {
			keyPressed(key);
        });
	}

}
