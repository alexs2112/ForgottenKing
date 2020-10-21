package application;
import javafx.application.Application;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import screens.Screen;
import screens.StartScreen;

public class Main extends Application {
	private Screen screen;
	private Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			primaryStage.setTitle("Forgotten King");
			primaryStage.setResizable(false);
			screen = new StartScreen();
			screen.displayOutput(primaryStage);
			addKeyHandler(screen);
			
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
		//System.out.println(key);
		screen = sc2;
		repaint();
		addKeyHandler(screen);
	}

	private void addKeyHandler(Screen screen) {
		screen.scene.setOnKeyPressed(key -> {
			keyPressed(key);
        });
	}

}
