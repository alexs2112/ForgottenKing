package application;
import audio.AudioPlayer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import screens.Screen;
import screens.StartScreen;
import tools.KeyBoardCommand;

public class Main extends Application {
	private Screen screen;
	private Stage primaryStage;
	private AudioPlayer audio;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			primaryStage.setTitle("Forgotten King");

			primaryStage.setResizable(false); 
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
		char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	changeScreen(screen.respondToUserInput(key.getCode(), c, key.isShiftDown()));
	}
	private void mouseClicked() {
		if (screen.refreshScreen() != null) {
			changeScreen(screen.refreshScreen());
		}
	}
	
	private void changeScreen(Screen newScreen) {
		screen = newScreen;
		if (screen == null)
			return;
		repaint();
		addKeyHandler(screen);
		audio.update(screen);
		if (screen.nextCommand != null) {
			KeyBoardCommand c = screen.getNextCommand();
			screen.nextCommand = null;
			changeScreen(screen.respondToUserInput(c.code, c.c, c.shift));
		}
	}

	private void addKeyHandler(Screen screen) {
		screen.scene.setOnKeyPressed(key -> {
			keyPressed(key);
        });
		screen.scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
			 public void handle(MouseEvent me) {
			        mouseClicked();
			    }
		});
		screen.scene.setOnMouseMoved(new EventHandler<MouseEvent>() {
			 public void handle(MouseEvent me) {
			        mouseClicked();
			    }
		});
	}

}
