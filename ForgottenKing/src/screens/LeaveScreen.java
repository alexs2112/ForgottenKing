package screens;

import creatures.Creature;
import items.Item;
import items.ItemTag;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class LeaveScreen extends Screen {
	public LeaveScreen(Creature player) {
		Group root = new Group();
		String s = getWinOrLose(player);
		Screen.draw(root, new Image(this.getClass().getResourceAsStream(s)), 0, 0);
		write(root, "[enter] to start a new game", 370, 632, 22, Color.WHITE);
		if (s.equals("resources/win_screen.png")) {
			write(root, "Congratulations!", 470, 530, 22, Color.WHITE);
			write(root, "You have succeeded in your quest!", 290, 580, 22, Color.WHITE);
		} else {
			write(root, "Cowardice!", 510, 530, 22, Color.WHITE);
			write(root, "You will be remembered for failing!", 290, 580, 22, Color.WHITE);
		}
			
		scene = new Scene(root,1280,800);
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ENTER))
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
