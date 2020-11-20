package screens;

import java.util.EnumSet;

import creatures.Player;
import creatures.Tag;
import creatures.Type;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StatsScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	private Player player;
	private Font font;
	private Font fontL;
	private Font fontXS;
	private Font fontXL;
	private EnumSet<Type> types;

	public StatsScreen(Player player) {
		this.player = player;
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
		fontL = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		fontXS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 12);
		fontXL = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 26);
		types = EnumSet.allOf(Type.class);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.statsScreenFull, 0, 0);
		draw(root, player.largeIcon(), 470, 132);
		write(root, "the " + player.title().toUpperCase(), 40, 58, fontXL, Color.WHITE);
		writeCentered(root, player.level() + "", 256, 100, font, Color.WHITE);
		writeCentered(root, (player.z+1) + "", 324, 116, font, Color.WHITE);
		writeCentered(root, "XP: " + player.xp() + "/" + player.nextLevelXP(), 188, 129, fontXS, Color.WHITE);
		writeCentered(root, player.hp() + "/" + player.maxHP(), 310, 180, font, Color.WHITE);
		writeCentered(root, player.mana() + "/" + player.maxMana(), 310, 228, font, Color.WHITE);
		writeCentered(root, player.movementDelay() + "", 372, 288, font, Color.WHITE);
		writeCentered(root, player.attackDelay() + "", 372, 336, font, Color.WHITE);
		writeCentered(root, player.evasion() + "", 310, 398, font, Color.WHITE);
		writeCentered(root, player.armorValue() + "", 310, 446, font, Color.WHITE);
		
		//Attributes
		int x = 808;
		int y = 122;
		writeCentered(root, "" + player.strength(), x, y, font, Color.WHITE);
		writeCentered(root, "" + player.dexterity(), x, y+=98, font, Color.WHITE);
		writeCentered(root, "" + player.intelligence(), x, y+=98, font, Color.WHITE);
		
		//Base Stat Values
		x = 1060;
		y = 96;
		Color c = Color.LIGHTGREY;
		writeCentered(root, "" + player.toughness(), x, y, font, c);
		writeCentered(root, "" + player.brawn(), x, y+=48, font, c);
		writeCentered(root, "" + player.agility(), x, y+=51, font, c);
		writeCentered(root, "" + player.accuracy(), x, y+=48, font, c);
		writeCentered(root, "" + player.will(), x, y+=51, font, c);
		writeCentered(root, "" + player.spellcasting(), x, y+=48, font, c);
		//Modifier of Stat Values
		x = 1110;
		y = 96;
		c = Color.LIGHTGREY;
		writeCentered(root, "" + (player.getToughness() - player.strength() - player.toughness()), x, y, font, c);
		writeCentered(root, "" + (player.getBrawn() - player.strength() - player.brawn()), x, y+=48, font, c);
		writeCentered(root, "" + (player.getAgility() - player.dexterity() - player.agility()), x, y+=51, font, c);
		writeCentered(root, "" + (player.getAccuracy() - player.dexterity() - player.accuracy()), x, y+=48, font, c);
		writeCentered(root, "" + (player.getWill() - player.intelligence() - player.will()), x, y+=51, font, c);
		writeCentered(root, "" + (player.getSpellcasting() - player.intelligence() - player.spellcasting()), x, y+=48, font, c);
		//Total Stat Values
		x = 1178;
		y = 96;
		c = Color.WHITE;
		writeCentered(root, "" + (player.getToughness()), x, y, font, c);
		writeCentered(root, "" + (player.getBrawn()), x, y+=48, font, c);
		writeCentered(root, "" + (player.getAgility()), x, y+=51, font, c);
		writeCentered(root, "" + (player.getAccuracy()), x, y+=48, font, c);
		writeCentered(root, "" + (player.getWill()), x, y+=51, font, c);
		writeCentered(root, "" + (player.getSpellcasting()), x, y+=48, font, c);
		
		
		//Magic Skills
		x = 1088;
		y = 478;
		writeCentered(root, ""+player.magic().get(Type.FIRE), x, y, fontXL, c);
		writeCentered(root, ""+player.magic().get(Type.AIR), x, y+66, fontXL, c);
		writeCentered(root, ""+player.magic().get(Type.LIGHT), x, y+132, fontXL, c);
		writeCentered(root, ""+player.magic().get(Type.COLD), x+=68, y, fontXL, c);
		writeCentered(root, ""+player.magic().get(Type.POISON), x, y+66, fontXL, c);
		writeCentered(root, ""+player.magic().get(Type.DARK), x, y+132, fontXL, c);
		
		int i = 0;
		for (Tag t : player.tags())
			if (t.isPerk()) {
				write(root, t.text(), 522, 444 + 32*i, fontL, c);
				i++;
			}
		if (i == 0)
			write(root, "You have no perks!", 522, 444, fontL, Color.ANTIQUEWHITE);
		
		i = 0;
		for (Type t : types) {
			int n = player.getResistance(t);
			if (n != 0) {
				Color temp = Color.WHITE;
				String s = t.text();
				if (n <= -1) {
					s += "-";
					temp = Color.RED;
				} else if (n >= 0) {
					for (int z = 0; z < Math.min(n, 4); z++)
						s += "+";
					temp = Color.FORESTGREEN;
				}
				if (n >= 4)
					temp = Color.LIMEGREEN;
				write(root, s, 50, 530 + i*32, fontL, temp);
				i++;
			}
		}
		if (i == 0)
			write(root, "No Resistances!", 50, 530, fontL, Color.ANTIQUEWHITE);
		
		if (player.statPoints() > 0 || player.attributePoints() > 0)
			writeCentered(root, "[enter] to Level Up!", 640, 764, fontL, Color.AQUA);
		else
			writeCentered(root, "[enter] to view stats breakdown!", 640, 764, fontL, Color.ANTIQUEWHITE);
		constructCloseButton();
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		if (code.equals(KeyCode.ENTER))
			return new LevelUpScreen(player);
	    else if (code.equals(KeyCode.ESCAPE))
            return null;
        else
            return this;
	}

}
