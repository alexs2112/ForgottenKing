package screens;

import java.util.ArrayList;
import java.util.List;

import creatures.Creature;
import creatures.Tag;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Spell;

public class PerkScreen extends Screen {
	private Creature player;
	private List<Tag> allPerks;
	private List<Tag> perks;
	private int select;
	
	public PerkScreen(Creature player) {
		this.select = 0;
		this.player = player;
		this.allPerks = Tag.listOfPerks();
		this.perks = new ArrayList<Tag>();
		initializeLists();
	}
	
	/**
	 * Sorts the list of perks to have all the perks the player already has at the top
	 */
	private void initializeLists() {
		for (Tag t : player.tags())
			if (allPerks.contains(t))
				perks.add(t);
		for (Tag t : allPerks)
			if (!perks.contains(t))
				perks.add(t);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
		draw(root, Loader.screenBorder, 0, 0);

		int x = 64;
		int y = 50;
		write(root, "Available Perk Points: [" + player.perkPoints() + "]", 48, y, font,  Color.WHITE);
		y+=12;
		for (int i = 0; i < perks.size(); i++) {
			Tag t = perks.get(i);
			if (player.tags().contains(t))
				draw(root, Loader.perkBoxBlue, x, y + 124*i);
			else
				draw(root, Loader.perkBox, x, y + 124*i);
			if (select == i)
				draw(root, Loader.perkBoxGreen, x, y + 124*i);
			if (t.icon() != null)
				draw(root, t.icon(), x+20, y+124*i+20);
			writeWrapped(root, t.text() + ": " + t.description(), x+72, y+42 + 124*i, 432, fontS, Color.ANTIQUEWHITE);
		}
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.DOWN))
    		select = Math.min(perks.size()-1, select+1);
    	if (key.getCode().equals(KeyCode.UP))
    		select = Math.max(0, select-1);
    	if (key.getCode().equals(KeyCode.ENTER)) {
			if (player.perkPoints() > 0 && !player.is(perks.get(select))) {
				player.addTag(perks.get(select));
				player.modifyPerkPoints(-1);
				return this;
			}
    	}
    	if (key.getCode().equals(KeyCode.ESCAPE))
			return null;
		return this;
    }
	
	

}
