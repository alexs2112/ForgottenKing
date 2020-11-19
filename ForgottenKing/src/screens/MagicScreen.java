package screens;

import java.util.Arrays;
import java.util.List;

import creatures.Magic;
import creatures.Player;
import creatures.Type;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Spell;

public class MagicScreen extends Screen {
	private Player player;
	private Magic magic;
	private Font font;
	private Font fontS;
	private String letters;
	private char selection;
	private int[] floatingValues;
	private List<Type> types;
	private int points;

	public MagicScreen(Player player) {
		this.magic = player.magic();
		this.player = player;
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
		letters = "abcdef";
		selection = '-';
		floatingValues = new int[6];
		points = magic.floatingPoints();
		types = Arrays.asList(Type.FIRE, Type.COLD, Type.AIR, Type.POISON, Type.LIGHT, Type.DARK);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.screenBorder, 0, 0);
		int x = 32;
		int y = 80;
		write(root, "Allocate Spell Points", x, y, font, Color.WHITE);
		
		y+= 12;
		if (selection != '-')	//Display which type the player is selecting
			draw(root, Loader.arrowRight, x, y + 72*letters.indexOf(selection)+20);
		x+=36;
		for (int i = 0; i < 6; i++) {
			draw(root, Loader.magicTypeBox, x, y + 72*i);
			draw(root, types.get(i).largeIcon(), x+6, y+5 + 72*i);
			write(root, types.get(i).text(), x+100, y+48 + 72*i, font, Color.WHITE);
			int wx = x+334;
			int value = magic.get(types.get(i)) + floatingValues[i];
			if (value > 9)
				wx-= 10;
			Color c = Color.WHITE;
			if (value > magic.get(types.get(i)))
				c = Color.LIMEGREEN;
			else if (value < magic.get(types.get(i)))
				c = Color.RED;
			write(root, ""+value, wx, y + 48 + 72*i, font, c);
			if (i == letters.indexOf(selection)) {
				wx = x + 377;
				if (points > 0) {
					draw(root, Loader.plusIcon, wx, y + 72*i + 18);
					wx += 32;
				} if (value > 0)
					draw(root, Loader.minusIcon, wx, y + 72*i + 18);
			}
		}
		if (points > 0) {
			draw(root, Loader.spellPointsBox, x, y += 452);
			write(root, "[" + points + "] Free Points", x + 14, y += 42, font, Color.DEEPSKYBLUE);
		}
		
		x = 682;
		y = 80;
		write(root, "Memorized Spells:",x,y, font, Color.WHITE);
		x+=18;
		for (int i = 0; i < player.spells().size(); i++) {
			Spell s = player.spells().get(i);
			Color c = Color.WHITE;
			if (magic.get(s.type()) + floatingValues[types.indexOf(s.type())] < s.level())
				c = Color.DARKGREY;
			write(root, s.name() + " [" + s.type() + ":" + s.level()+"]", x, y+=22, fontS, c);
		}
		
		write(root, "Spell Slots: " + (player.totalSpellSlots() - player.remainingSpellSlots()) + "/" + player.totalSpellSlots(), 64, 664, font, Color.WHITE);
		
		writeCentered(root, "[esc] to exit", 640, 700, font, Color.WHITE);
		if (changesPresent()) {
			if (player.creatureInSight())
				writeCentered(root, "Enemies in sight!", 640, 732, font, Color.GREY);
			else
				writeCentered(root, "[enter] to start meditating", 640, 732, font, Color.WHITE);
		}
		constructCloseButton();
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ESCAPE))
            return null;
		if (code.equals(KeyCode.ENTER)) {
			player.meditate(floatingValues);
			return null;
		}
		if (code.equals(KeyCode.DOWN)) {
			int i = letters.indexOf(selection);
			int n = i + 1;
			if (n > 5)
				n = 0;
			selection = letters.charAt(n);
		}
		if (code.equals(KeyCode.UP)) {
			int i = letters.indexOf(selection);
			int n = i - 1;
			if (n < 0)
				n = 5;
			selection = letters.charAt(n);
		}
		if (letters.indexOf(c) != -1)
			selection = c;
		if (shift && c == '=' && letters.indexOf(selection) != -1)
			modifyType(letters.indexOf(selection), 1);
		if (code.equals(KeyCode.MINUS) && c == '-' && letters.indexOf(selection) != -1)
			modifyType(letters.indexOf(selection), -1);
		return this;
	}
	private void modifyType(int index, int x) {
		if (x < 0) {
			if (magic.get(types.get(index)) + floatingValues[index] > 0) {
				floatingValues[index]--;
				points++;
			}
		} else if (x > 0) {
			if (points > 0) {
				floatingValues[index]++;
				points--;
			}
		}
	}
	private boolean changesPresent() {
		for (int i = 0; i < floatingValues.length; i++)
			if (floatingValues[i] != 0)
				return true;
		return false;
	}

}
