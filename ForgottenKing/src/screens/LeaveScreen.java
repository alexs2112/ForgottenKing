package screens;

import application.Main;
import creatures.Player;
import items.Item;
import items.ItemTag;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LeaveScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	Player player;
	public LeaveScreen(Player player) {
		this.player = player;
	}
	
	public void displayOutput(Stage stage) {
		Group root = new Group();
		String s = getWinOrLose(player);
		Screen.draw(root, new Image(Main.class.getResourceAsStream("resources/screens/full-screens/" + s)), 0, 0);
		scene = new Scene(root,1280,800);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ENTER)) {
			PlayScreen.deleteSave();
			return new StartScreen();
		}
    	return this;
    }
	
	private String getWinOrLose(Player player) {
		for (Item i : player.inventory().getUniqueItems())
			if (i.is(ItemTag.VICTORYITEM)) {
				return "win_screen.png";
			}
		if (player.hasWon)
			return "win_screen.png";
		return "lose_screen.png";
	}
}
