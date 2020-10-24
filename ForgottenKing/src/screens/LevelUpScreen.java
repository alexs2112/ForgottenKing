package screens;

import creatures.Attribute;
import creatures.Creature;
import creatures.Stat;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LevelUpScreen extends Screen {
	private Group baseRoot;
	private Creature player;
	private Screen returnScreen;

	public LevelUpScreen(Creature player, Screen returnScreen) {
		this.baseRoot = new Group(returnScreen.root());
		this.player = player;
		this.returnScreen = returnScreen;
	}
	
	public void displayOutput(Stage stage) {
		root = new Group(baseRoot);
		
		draw(root, Loader.levelUpBox, 460, 200);
		Font title = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		Font f = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
		Font s = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
		for (int i = 0; i < 3; i++) {
			if ((player.level() + 1) % 3 == 0)
				draw(root, Loader.levelUpOptionFull, 470, 312 + 58 * i);
			else
				draw(root, Loader.levelUpOptionHalf, 470, 312 + 58 * i);
		}
			
		write(root, "Level Up!", 560, 240, title, Color.GOLDENROD);
		write(root, "Welcome to level " + (player.level()+1) + "!", 490, 294, s, Color.ANTIQUEWHITE);
		if ((player.level() + 1) % 3 == 0) {
			write(root, "a)   Strength", 490, 352, f, Color.WHITE);
			draw(root, Loader.strengthIcon, 542, 325);
			write(root, "b)   Dexterity", 490, 410, f, Color.WHITE);
			draw(root, Loader.dexterityIcon, 542, 383);
			write(root, "c)   Intelligence", 490, 469, f, Color.WHITE);
			draw(root, Loader.intelligenceIcon, 542, 441);
		}
		else {
			write(root, "a)", 510, 352, f, Color.WHITE);
			draw(root, Loader.toughnessIcon, 562, 325);
			write(root, "c)", 510, 410, f, Color.WHITE);
			draw(root, Loader.agilityIcon, 562, 383);
			write(root, "e)", 510, 469, f, Color.WHITE);
			draw(root, Loader.willIcon, 562, 441);
			
			write(root, "b)", 700, 352, f, Color.WHITE);
			draw(root, Loader.brawnIcon, 752, 325);
			write(root, "d)", 700, 410, f, Color.WHITE);
			draw(root, Loader.accuracyIcon, 752, 383);
			write(root, "f)", 700, 469, f, Color.WHITE);
			draw(root, Loader.spellcastingIcon, 752, 441);
		}
		scene = new Scene(root, 1280, 800, Color.BLACK);
		stage.setScene(scene);
		stage.show();
	}

	@Override
	public Screen respondToUserInput(KeyEvent key) {
		char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	if ((player.level() + 1) % 3 == 0) {
    		if (c == 'a') {
    			handleLevelUp(Attribute.STR);
    			return returnScreen;
    		} else if (c == 'b') {
    			handleLevelUp(Attribute.DEX);
    			return returnScreen;
    		} else if (c == 'c') {
    			handleLevelUp(Attribute.INT);
    			return returnScreen;
    		}
    	} else {
    		if (c == 'a') {
    			handleLevelUp(Stat.TOUGHNESS);
    			return returnScreen;
    		} else if (c == 'b') {
    			handleLevelUp(Stat.BRAWN);
    			return returnScreen;
    		} else if (c == 'c') {
    			handleLevelUp(Stat.AGILITY);
    			return returnScreen;
    		} else if (c == 'd') {
    			handleLevelUp(Stat.ACCURACY);
    			return returnScreen;
    		} else if (c == 'e') {
    			handleLevelUp(Stat.WILL);
    			return returnScreen;
    		} else if (c == 'f') {
    			handleLevelUp(Stat.SPELLCASTING);
    			return returnScreen;
    		}
    	}
    		
    	return this;
    }
	
	private void handleLevelUp(Attribute a) {
		addLevel();
		player.modifyAttribute(a, 1);
	}
	private void handleLevelUp(Stat s) {
		addLevel();
		player.modifyStat(s, 1);
	}
	private void addLevel() {
		player.modifyXP(-player.nextLevelXP());
		player.modifyLevel(1);
		int x = (int)(Math.random() * 6) + 1;
		player.modifyMaxHP(x);
		player.modifyHP(x);
		if (player.level() % 4 == 0)
			player.modifyPerkPoints(1);
	}
}
