package screens;

import java.util.ArrayList;

import creatures.Creature;
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

public class InspectScreen extends Screen {
	private Item item;
	private Screen previousScreen;
	private Creature player;

	public InspectScreen(Creature player, Item item, Screen previousScreen) {
		this.player = player;
		this.item = item;
		this.previousScreen = previousScreen;
	}
	public InspectScreen(Item item) {
		this.item = item;
	}
	
	public void displayOutput(Stage stage) {
    	root = new Group();
    	draw(root, Loader.screenBorder, 0, 0);
    	Font titleFont = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 24);
    	Font font = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), 20);
    	
    	write(root, item.name(), 48, 48, titleFont, Color.WHITE);
    	write(root, item.description(), 48, 80, font, Color.ANTIQUEWHITE);
    	draw(root, new Image(this.getClass().getResourceAsStream("resources/icon_box.png")), 592, 40);
    	draw(root, item.image(), 600, 48);
    	
    	ArrayList<String> texts = useTexts();
    	for (int i = 0; i < texts.size(); i++) {
    		write(root, texts.get(i), 48, 112+i*32, font, Color.WHITE);
    	}
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
	
	public ArrayList<String> useTexts() {
		ArrayList<String> list = new ArrayList<String>();
		if (item.equippable()) {
			if (item.type() == ItemType.WEAPON)
				list.add("[w]: wield");
			else
				list.add("[w]: wear");
		}
		if (item.spells() != null)
			list.add("[r]: read");
		if (item.type() == ItemType.POTION)
			list.add("[q]: quaff");
		if (item.type() == ItemType.POTION || item.is(ItemTag.THROWING))
			list.add("[t]: throw");	//Other items can be thrown, only shows t on the most useful ones for throwing
		list.add("[d]: drop");
		return list;
	}

}
