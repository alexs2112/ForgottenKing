package screens;

import java.util.ArrayList;
import java.util.List;

import creatures.Player;
import creatures.Tag;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class PerkScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private Player player;
	private List<Tag> allPerks;
	private List<Tag> perks;
	private int select;
	
	public PerkScreen(Player player) {
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
				if (t.canUnlock(player))
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
		write(root, "Available Perk Points: [" + player.perkPoints() + "]               Prerequisites:", 48, y, font,  Color.WHITE);
		y+=12;
		int top = Math.min(Math.max(0, select-2), perks.size()-5);
		for (int i = top; i < top+5; i++) {
			Tag t = perks.get(i);
			if (player.tags().contains(t))
				draw(root, Loader.perkBoxBlue, x, y + 124*(i-top));
			else
				draw(root, Loader.perkBox, x, y + 124*(i-top));
			if (select == i)
				draw(root, Loader.perkBoxSelection, x, y + 124*(i-top));
			if (t.icon() != null)
				draw(root, t.icon(), x+20, y+124*(i-top)+20);
			Color textColour = Color.ANTIQUEWHITE;
			if (!t.canUnlock(player))
				textColour = Color.DARKGREY;
			writeWrapped(root, t.text() + ": " + t.description(), x+72, y+42 + 124*(i-top), 632, fontS, textColour);
			writeWrapped(root, t.prerequisites(), 832, y+44+123*(i-top), 400, fontS, Color.WHITE);
		}
		if (top + 5 < perks.size())
			draw(root, Loader.arrowDown, 20, y+662);
		if (top > 0)
			draw(root, Loader.arrowUp, 20, 62);
		constructCloseButton();
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.DOWN))
    		select = Math.min(perks.size()-1, select+1);
    	if (code.equals(KeyCode.UP))
    		select = Math.max(0, select-1);
    	if (code.equals(KeyCode.ENTER)) {
			if (player.perkPoints() > 0 && !player.is(perks.get(select)) && perks.get(select).canUnlock(player)) {
				player.addTag(perks.get(select));
				player.modifyPerkPoints(-1);
				return this;
			}
    	}
    	if (code.equals(KeyCode.ESCAPE))
			return null;
		return this;
    }
	
	

}
