package screens;

import creatures.Creature;
import creatures.Player;
import items.Item;
import items.ItemTag;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class LeaveScreen extends Screen {
	Player player;
	public LeaveScreen(Player player) {
		this.player = player;
	}
	
	public void displayOutput(Stage stage) {
		Group root = new Group();
		String s = getWinOrLose(player);
		Screen.draw(root, new Image(this.getClass().getResourceAsStream(s)), 0, 0);
		scene = new Scene(root,1280,800);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ENTER))
			return new StartScreen();
    	return this;
    }
	
	private String getWinOrLose(Creature player) {
		for (Item i : player.inventory().getUniqueItems())
			if (i.is(ItemTag.VICTORYITEM)) {
				return "resources/win_screen.png";
			}
		return "resources/lose_screen.png";
	}
}
