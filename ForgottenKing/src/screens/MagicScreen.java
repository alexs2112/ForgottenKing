package screens;

import creatures.Creature;
import creatures.Magic;
import creatures.Stat;
import creatures.Type;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MagicScreen extends Screen {
	private Magic magic;
	private Font font;
	private String letters;
	private char selection;
	private int[] floatingValues;
	private Type[] types;

	public MagicScreen(Creature player) {
		this.magic = player.magic();
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		letters = "abcdef";
		selection = '-';
		floatingValues = new int[6];
		types = new Type[6];
		types[0] = Type.FIRE;
		types[1] = Type.COLD;
		types[2] = Type.AIR;
		types[3] = Type.POISON;
		types[4] = Type.LIGHT;
		types[5] = Type.DARK;
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.screenBorder, 0, 0);
		int x = 32;
		int y = 48;
		write(root, "Allocate Spell Points", x, y+=32, font, Color.WHITE);
		
		y+= 12;
		if (selection != '-')	//Display which type the player is selecting
			draw(root, Loader.arrowRight, x, y + 72*letters.indexOf(selection)+20);
		x+=36;
		for (int i = 0; i < 6; i++) {
			draw(root, Loader.magicTypeBox, x, y + 72*i);
			draw(root, types[i].largeIcon(), x+6, y+5 + 72*i);
			write(root, types[i].text(), x+100, y+48 + 72*i, font, Color.WHITE);
			int wx = x+334;
			if (magic.get(types[i]) > 9)
				wx-= 10;
			write(root, ""+magic.get(types[i]), wx, y + 48 + 72*i, font, Color.WHITE);
			if (i == letters.indexOf(selection)) {
				wx = x + 377;
				if (magic.floatingPoints() > 0) {
					draw(root, Loader.plusIcon, wx, y + 72*i + 18);
					wx += 32;
				} if (magic.get(types[i]) > 0)
					draw(root, Loader.minusIcon, wx, y + 72*i + 18);
			}
		}
		if (magic.floatingPoints() > 0) {
			draw(root, Loader.spellPointsBox, x, y += 452);
			write(root, "[" + magic.floatingPoints() + "] Free Points", x + 14, y += 42, font, Color.DARKBLUE);
		}
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		char c = '/';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	
		if (key.getCode().equals(KeyCode.ESCAPE) || key.getCode().equals(KeyCode.ENTER))
            return null;
		if (key.getCode().equals(KeyCode.DOWN)) {
			int i = letters.indexOf(selection);
			int n = i + 1;
			if (n > 5)
				n = 0;
			selection = letters.charAt(n);
		}
		if (key.getCode().equals(KeyCode.UP)) {
			int i = letters.indexOf(selection);
			int n = i - 1;
			if (n < 0)
				n = 5;
			selection = letters.charAt(n);
		}
		if (letters.indexOf(c) != -1)
			selection = c;
		if (key.isShiftDown() && c == '=' && letters.indexOf(selection) != -1)
			floatingValues[letters.indexOf(selection)]++;
		if (c == '-' && letters.indexOf(selection) != -1)
			floatingValues[letters.indexOf(selection)]--;
		modifyTypes();
		return this;
	}
	
	private void modifyTypes() {
		magic.modify(Type.FIRE, floatingValues[0]);
		magic.modify(Type.COLD, floatingValues[1]);
		magic.modify(Type.AIR, floatingValues[2]);
		magic.modify(Type.POISON, floatingValues[3]);
		magic.modify(Type.LIGHT, floatingValues[4]);
		magic.modify(Type.DARK, floatingValues[5]);
		floatingValues = new int[6];
	}

}
