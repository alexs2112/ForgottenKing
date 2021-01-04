package screens;

import java.util.Arrays;
import java.util.List;

import creatures.Magic;
import creatures.Player;
import creatures.Type;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spells.Spell;

public class MagicScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private Player player;
	private Magic magic;
	private String letters;
	private char selection;
	private int[] floatingValues;
	private List<Type> types;
	private int points;

	public MagicScreen(Player player) {
		this.magic = player.magic();
		this.player = player;
		letters = "abcdef";
		selection = '-';
		floatingValues = new int[6];
		points = magic.floatingPoints();
		types = Arrays.asList(Type.FIRE, Type.COLD, Type.AIR, Type.POISON, Type.LIGHT, Type.DARK);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.screenBorder.image(), 0, 0);
		int x = 32;
		int y = 80;
		write(root, "Allocate Spell Points", x, y, font22, Color.WHITE);
		
		y+= 12;
		if (selection != '-')	//Display which type the player is selecting
			draw(root, Loader.arrowRight.image(), x, y + 72*letters.indexOf(selection)+20);
		x+=36;
		for (int i = 0; i < 6; i++) {
			draw(root, Loader.magicTypeBox.image(), x, y + 72*i);
			draw(root, types.get(i).largeIcon().image(), x+6, y+5 + 72*i);
			write(root, types.get(i).text(), x+100, y+48 + 72*i, font22, Color.WHITE);
			int wx = x+334;
			int value = magic.get(types.get(i)) + floatingValues[i];
			if (value > 9)
				wx-= 10;
			Color c = Color.WHITE;
			if (value > magic.get(types.get(i)))
				c = Color.LIMEGREEN;
			else if (value < magic.get(types.get(i)))
				c = Color.RED;
			write(root, ""+value, wx, y + 48 + 72*i, font22, c);
			if (i == letters.indexOf(selection)) {
				wx = x + 377;
				if (value > 0) {
					draw(root, Loader.minusIcon.image(), wx, y + 72*i + 18);
					wx += 32;
				} if (points > 0)
					draw(root, Loader.plusIcon.image(), wx, y + 72*i + 18);
			}
		}
		if (points > 0) {
			draw(root, Loader.spellPointsBox.image(), x, y += 452);
			write(root, "[" + points + "] Free Points", x + 14, y += 42, font22, Color.DEEPSKYBLUE);
		}
		
		x = 682;
		y = 80;
		write(root, "Memorized Spells:",x,y, font22, Color.WHITE);
		x+=18;
		for (int i = 0; i < player.spells().size(); i++) {
			Spell s = player.spells().get(i);
			Color c = Color.WHITE;
			if (magic.get(s.type()) + floatingValues[types.indexOf(s.type())] < s.level())
				c = Color.DARKGREY;
			write(root, s.name() + " [" + s.type() + ":" + s.level()+"]", x, y+=22, font18, c);
		}
		
		write(root, "Spell Slots: " + (player.totalSpellSlots() - player.remainingSpellSlots()) + "/" + player.totalSpellSlots(), 64, 664, font22, Color.WHITE);
		
		writeCentered(root, "[esc] to exit", 640, 700, font22, Color.WHITE);
		if (changesPresent()) {
			if (player.creatureInSight())
				writeCentered(root, "Enemies in sight!", 640, 732, font22, Color.GREY);
			else
				writeCentered(root, "[enter] to start meditating", 640, 732, font22, Color.WHITE);
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
		if (((shift && c == '=') || code.equals(KeyCode.RIGHT)) && letters.indexOf(selection) != -1)
			modifyType(letters.indexOf(selection), 1);
		if (((code.equals(KeyCode.MINUS) && c == '-') || code.equals(KeyCode.LEFT)) && letters.indexOf(selection) != -1)
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
