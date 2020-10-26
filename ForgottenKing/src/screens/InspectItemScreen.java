package screens;

import java.util.ArrayList;
import java.util.List;

import creatures.Creature;
import creatures.Type;
import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import screens.Screen;

public class InspectItemScreen extends Screen {
	private Item item;
	private Screen previousScreen;
	private Creature player;

	public InspectItemScreen(Creature player, Item item, Screen previousScreen) {
		this.player = player;
		this.item = item;
		this.previousScreen = previousScreen;
	}
	public InspectItemScreen(Item item) {
		this.item = item;
	}
	
	public void displayOutput(Stage stage) {
    	root = new Group();
    	draw(root, Loader.screenBorder, 0, 0);
    	Font titleFont = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 24);
    	Font font = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 20);
    	Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
    	
    	int y = 64;
    	write(root, item.name(), 96, y, titleFont, Color.WHITE);
    	draw(root, new Image(this.getClass().getResourceAsStream("resources/icon_box.png")), 40, y-36);
    	draw(root, item.image(), 48, y-28);
    	y += 48;
    	writeWrapped(root, item.description(), 48, y, 1184, font, Color.ANTIQUEWHITE);
    	y += 64;
    	for (String s : getStatDescription())
    		write(root, s, 48, y+=20, fontS, Color.WHITE);
    	
    	
    	y += 48;
    	int x = 275;
    	if (item.tags() != null)
    		for (ItemTag t : item.tags()) {
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
    	if (player != null)
    		write(root, useTexts(), 48, y, font, Color.WHITE);
    	
    }
	public Screen respondToUserInput(KeyEvent key) {
		if (key.getCode().equals(KeyCode.ESCAPE))
            return previousScreen;
		if (player == null)
			return this;
		
		char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
		if (c == 'd') {
			player.drop(item);
			return previousScreen;
		} else if (c == 'w' && item.equippable()) {
			player.equip(item);
			return previousScreen;
		} else if (c == 'r' && item.spells() != null) {
			return new ReadSpellbookScreen(player, item);
		} else if (c == 'q' && item.type() == ItemType.POTION) {
			player.quaff(item);
			return previousScreen;
		} else {
			return this;
		}
	}
	
	private String useTexts() {
		String text = "";
		if (item.equippable()) {
			if (item.type() == ItemType.WEAPON)
				text += ("[w]: wield   ");
			else
				text +=("[w]: wear   ");
		}
		if (item.spells() != null)
			text +=("[r]: read   ");
		if (item.type() == ItemType.POTION)
			text +=("[q]: quaff   ");
		if (item.type() == ItemType.POTION || item.is(ItemTag.THROWING))
			text +=("[t]: throw   ");	//Other items can be thrown, only shows t on the most useful ones for throwing
		text +=("[d]: drop");
		return text;
	}
	private List<String> getStatDescription() {
		List<String> text = new ArrayList<String>();
		if (item.is(ItemTag.RUSTED))
			text.add("It is rusted, with decreased attack and damage.");
		if (item.is(ItemTag.PRISTINE))
			text.add("It is pristine, with increased attack and damage.");
		if (item.isRanged())
			text.add("It can be fired with [f] if you have suitable ammo quivered.");
		if (item.attackValue() != 0)
			text.add("It increases your melee attack by " + item.attackValue() + ".");
		if (item.rangedAttackValue() != 0)
			text.add("It increases your ranged attack by " + item.rangedAttackValue() + ".");
		if (item.minDamage() != 0 && item.maxDamage() != 0) {
			String t = "";
			t += "It increases your melee damage by " + item.minDamage() + "-" + item.maxDamage();
			if (item.damageType() != null)
				t += ", dealing " + item.damageType().text() + " damage";
			t += ".";
			text.add(t);
		}
		if (item.rangedDamage() != null) {
			String s = "It increases your ranged damage by " + item.rangedDamage()[0];
			if (item.rangedDamage()[0] != item.rangedDamage()[1])
				s += "-" + item.rangedDamage()[1];
			text.add(s + ".");
		}
		if (item.armorValue() != 0)
			text.add("It increases your armor value by " + item.armorValue() + ".");
		if (item.weaponDelay() < 0)
			text.add("It reduces your attack delay by " + Math.abs(item.weaponDelay()) + ".");
		else if (item.weaponDelay() > 0)
			text.add("It increases your attack delay by " + item.weaponDelay() + ".");
		if (item.effectOnHit() != null)
			text.add("It applies the effect [" + item.effectOnHit().name() + "] on successful hits.");
		if (item.spells() != null)
			text.add("You can learn spells by [r]eading it.");
		if (item.resistances() != null) {
			for (Type t : item.resistances().keySet())
				text.add("It increases your resistance to " + t.text() + " by " + item.getResistance(t) + ".");
		}
		return text;
	}

}
