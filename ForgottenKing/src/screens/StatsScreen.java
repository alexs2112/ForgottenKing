package screens;

import creatures.Creature;
import creatures.Stat;
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
		draw(root, Loader.screenBorder, 0, 0);
		int x = 32;
		int y = 48;
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
		
		
		x+=410;
		y = 64;
		draw(root, Loader.strengthIcon, x, y+=4);
		write(root, "STR: " + player.strength(), x+40, y+=28, font, Color.WHITE);
		draw(root, Loader.dexterityIcon, x, y+=36);
		write(root, "DEX: " + player.dexterity(), x+40, y+=28, font, Color.WHITE);
		draw(root, Loader.intelligenceIcon, x, y+=36);
		write(root, "INT: " + player.intelligence(), x+40, y+=28, font, Color.WHITE);
		
		
		
		x += 248;
		y = 48;
		write(root, "Toughness: " + player.getToughness() + " (" + player.stats().get(Stat.TOUGHNESS) + ")", x, y+=32, font, Color.WHITE);
		write(root, "Brawn: " + player.getBrawn() + " (" + player.stats().get(Stat.BRAWN) + ")", x, y+=32, font, Color.WHITE);
		write(root, "Agility: " + player.getAgility() + " (" + player.stats().get(Stat.AGILITY) + ")", x, y+=32, font, Color.WHITE);
		write(root, "Accuracy: " + player.getAccuracy() + " (" + player.stats().get(Stat.ACCURACY) + ")", x, y+=32, font, Color.WHITE);
		write(root, "Will: " + player.getWill() + " (" + player.stats().get(Stat.WILL) + ")", x, y+=32, font, Color.WHITE);
		write(root, "Spellcasting: " + player.getSpellcasting() + " (" + player.stats().get(Stat.SPELLCASTING) + ")", x, y+=32, font, Color.WHITE);
		
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
