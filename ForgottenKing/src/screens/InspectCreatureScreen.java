package screens;

import java.util.ArrayList;
import java.util.List;

import creatures.Creature;
import creatures.Tag;
import items.Item;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import screens.Screen;
import spells.Effect;

public class InspectCreatureScreen extends Screen {
	private Creature creature;

	public InspectCreatureScreen(Creature creature) {
		this.creature = creature;
	}

	public void displayOutput(Stage stage) {
    	root = new Group();
    	draw(root, Loader.screenBorder, 0, 0);
    	Font titleFont = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 24);
    	Font font = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 20);
    	Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
    	
    	int y = 64;
    	write(root, creature.name(), 96, y, titleFont, Color.WHITE);
    	draw(root, new Image(this.getClass().getResourceAsStream("resources/icon_box.png")), 40, y-36);
    	draw(root, creature.image(), 48, y-28);
    	y += 48;
    	writeWrapped(root, creature.description(), 48, y, 1184, font, Color.ANTIQUEWHITE);
    	y += 64;
    	for (String s : getDescriptionLines())
    		write(root, s, 48, y+=20, fontS, Color.WHITE);
    	
    	y += 48;
    	int x = 275;
    	if (creature.tags() != null)
    		for (Tag t : creature.tags()) {
    			if (t.description().length() <= 1)
    				continue;
    			draw(root, Loader.itemTagBox, 275, y);
    			if (t.icon() != null)
    				draw(root, t.icon(), x+20, y+20);
    			Font f = fontS;
    			if (t.description().length() < 100)
    				f = font;
    			writeWrapped(root, t.text() + ": " + t.description(), x+72, y+42, 632, f, Color.ANTIQUEWHITE);
    			y+=122;
    		}
    	y+=48;
    	write(root, "[esc] to exit", 48, y, font, Color.WHITE);
    }
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ESCAPE))
            return null;
		else
			return this;
	}
	
	private List<String> getDescriptionLines() {
		List<String> text = new ArrayList<String>();
		String t = "It looks ";
		if (creature.hp() == creature.maxHP())
			t += "undamaged.";
		else if (creature.hp() > 4*creature.maxHP()/5)
    		t += "lightly damaged.";
    	else if (creature.hp() > 3 * (creature.maxHP()/5))
    		t += "moderately damaged.";
    	else if (creature.hp() > 1*creature.maxHP()/4)
    		t += "heavily damaged.";
    	else
    		t += "nearly dead.";
		text.add(t);
		if (creature.movementDelay() >= 13) {
			if (creature.movementDelay() >= 15)
				text.add("It is very slow.");
			else
				text.add("It is slow.");
		} if (creature.movementDelay() <= 7) {
			if (creature.movementDelay() <= 5)
				text.add("It is very fast.");
			else
				text.add("It is fast.");
		}
		if (creature.equipment().size() > 0) {
			t = "It has the following equipment: ";
			for (Item i : creature.equipment().values()) {
				t += i.name() + ", ";
			}
			text.add(t);
		}
		for (Effect e : creature.effects()) {
			text.add("It is " + e.name() + ".");
		}
		text.add("Killing this creature would usually award you with " + creature.xp() + " experience.");
		return text;
	}

}
