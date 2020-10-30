package screens;

import creatures.Creature;
import creatures.Type;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class StatsScreen extends Screen {
	private Creature player;
	private Font font;

	public StatsScreen(Creature player) {
		this.player = player;
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		draw(root, Loader.statsScreenFull, 0, 0);
		int x = 32;
		int y = 16;
		write(root, "Stats:", x, y+=32, font, Color.WHITE);
		write(root, "Level: " + player.level(), x, y+=32, font, Color.WHITE);
		write(root, "XP: " + player.xp() + "/" + player.nextLevelXP(), x, y+=32, font, Color.WHITE);
		write(root, "HP: " + player.hp() + "/" + player.maxHP(), x, y+=32, font, Color.WHITE);
		write(root, "Mana: " + player.mana() + "/" + player.maxMana(), x, y+=32, font, Color.WHITE);
		write(root, "Evasion: " + player.evasion(), x, y += 32, font, Color.WHITE);
		write(root, "Armor: " + player.armorValue(), x, y += 32, font, Color.WHITE);
		write(root, "Attack: " + player.getCurrentAttackValue(), x, y += 32, font, Color.WHITE);
		write(root, "Damage: " + (player.getMinDamage()+player.getCurrentDamageMod()) + " - " + (player.getMaxDamage()+player.getCurrentDamageMod()), x, y += 32, font, Color.WHITE);
		y+=32;
		write(root, "Movement Delay: " + player.movementDelay(), x, y += 32, font, Color.WHITE);
		write(root, "Attack Delay: " + player.attackDelay(), x, y += 32, font, Color.WHITE);
		
		
		
		//Attributes
		x = 800;
		y = 122;
		write(root, "" + player.strength(), x, y, font, Color.WHITE);
		write(root, "" + player.dexterity(), x, y+=98, font, Color.WHITE);
		write(root, "" + player.intelligence(), x, y+=98, font, Color.WHITE);
		
		//Base Stat Values
		x = 1050;
		y = 98;
		Color c = Color.LIGHTGREY;
		write(root, "" + player.toughness(), x, y, font, c);
		write(root, "" + player.brawn(), x, y+=48, font, c);
		write(root, "" + player.agility(), x, y+=51, font, c);
		write(root, "" + player.accuracy(), x, y+=48, font, c);
		write(root, "" + player.will(), x, y+=51, font, c);
		write(root, "" + player.spellcasting(), x, y+=48, font, c);
		//Modifier of Stat Values
		x = 1100;
		y = 98;
		c = Color.LIGHTGREY;
		write(root, "" + (player.getToughness() - player.strength() - player.toughness()), x, y, font, c);
		write(root, "" + (player.getBrawn() - player.strength() - player.brawn()), x, y+=48, font, c);
		write(root, "" + (player.getAgility() - player.dexterity() - player.agility()), x, y+=51, font, c);
		write(root, "" + (player.getAccuracy() - player.dexterity() - player.accuracy()), x, y+=48, font, c);
		write(root, "" + (player.getWill() - player.intelligence() - player.will()), x, y+=51, font, c);
		write(root, "" + (player.getSpellcasting() - player.intelligence() - player.spellcasting()), x, y+=48, font, c);
		//Total Stat Values
		x = 1168;
		y = 98;
		c = Color.WHITE;
		write(root, "" + (player.getToughness()), x, y, font, c);
		write(root, "" + (player.getBrawn()), x, y+=48, font, c);
		write(root, "" + (player.getAgility()), x, y+=51, font, c);
		write(root, "" + (player.getAccuracy()), x, y+=48, font, c);
		write(root, "" + (player.getWill()), x, y+=51, font, c);
		write(root, "" + (player.getSpellcasting()), x, y+=48, font, c);
		
		y += 32;
		write(root, "Fire: " + player.magic().get(Type.FIRE), x+=48, y+=32, font, Color.WHITE);
		write(root, "Cold: " + player.magic().get(Type.COLD), x, y+=32, font, Color.WHITE);
		write(root, "Air: " + player.magic().get(Type.AIR), x, y+=32, font, Color.WHITE);
		write(root, "Poison: " + player.magic().get(Type.POISON), x, y+=32, font, Color.WHITE);
		write(root, "Light: " + player.magic().get(Type.LIGHT), x, y+=32, font, Color.WHITE);
		write(root, "Dark: " + player.magic().get(Type.DARK), x, y+=32, font, Color.WHITE);
	}
	
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ESCAPE))
            return null;
        else
            return this;
	}

}
