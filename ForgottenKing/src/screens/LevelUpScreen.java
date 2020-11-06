package screens;

import creatures.Attribute;
import creatures.Player;
import creatures.Stat;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LevelUpScreen extends Screen {
	private Player player;
	private int select;

	public LevelUpScreen(Player player) {
		this.player = player;
		setSelect();
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		Font f = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		Font s = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
		draw(root, new Image(Screen.class.getResourceAsStream("resources/full-screens/level-up-screen.png")), 0, 0);
		
		writeCentered(root, ""+player.attributePoints(), 396, 64, f, Color.WHITE);
		writeCentered(root, ""+player.statPoints(), 468, 412, f, Color.WHITE);
		
		int x = 374;
		writeCentered(root, ""+player.strength(), x, 130, s, Color.WHITE);
		writeCentered(root, ""+player.dexterity(), x, 230, s, Color.WHITE);
		writeCentered(root, ""+player.intelligence(), x, 328, s, Color.WHITE);
		
		x = 376;
		int y = 474;
		writeCentered(root, ""+player.toughness(), x, y, s, Color.WHITE);
		writeCentered(root, ""+player.brawn(), x, y+=52, s, Color.WHITE);
		writeCentered(root, ""+player.agility(), x, y+=55, s, Color.WHITE);
		writeCentered(root, ""+player.accuracy(), x, y+=56, s, Color.WHITE);
		writeCentered(root, ""+player.will(), x, y+=55, s, Color.WHITE);
		writeCentered(root, ""+player.spellcasting(), x, y+=52, s, Color.WHITE);
		x = 454;
		y = 474;
		writeCentered(root, ""+player.getToughness(), x, y, s, Color.WHITE);
		writeCentered(root, ""+player.getBrawn(), x, y+=52, s, Color.WHITE);
		writeCentered(root, ""+player.getAgility(), x, y+=55, s, Color.WHITE);
		writeCentered(root, ""+player.getAccuracy(), x, y+=56, s, Color.WHITE);
		writeCentered(root, ""+player.getWill(), x, y+=55, s, Color.WHITE);
		writeCentered(root, ""+player.getSpellcasting(), x, y+=52, s, Color.WHITE);
		
		//A really clunky way to handle where the selection arrow is
		if (select == -1)
			return;
		switch(select) {
		case 0: x=166; y=104; break;
		case 1: x=166; y=203; break;
		case 2: x=166; y=302; break;
		case 3: x=108; y=448; break;
		case 4: x=108; y=500; break;
		case 5: x=108; y=555; break;
		case 6: x=108; y=611; break;
		case 7: x=108; y=666; break;
		case 8: x=108; y=718; break;
		}
		draw(root, Loader.arrowRight, x, y);
	}

	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
    	if (code.equals(KeyCode.DOWN)) {
    		int max = -1;
    		if (player.statPoints() > 0)
    			max = 8;
    		else if (player.attributePoints() > 0)
    			max = 2;
    		select = Math.min(max, select+1);
    	} else if (code.equals(KeyCode.UP)) {
    		int min = -1;
    		if (player.attributePoints() > 0)
    			min = 0;
    		else if (player.statPoints() > 0)
    			min = 3;
    		select = Math.max(min, select-1);
    	} else if (code.equals(KeyCode.ESCAPE)) {
    		return null;
    	} else if (code.equals(KeyCode.ENTER)) {
    		if (select > -1 && select < 3 && player.attributePoints() > 0) {
    			player.modifyAttributePoints(-1);
    			if (select == 0)
    				player.modifyAttribute(Attribute.STR, 1);
    			else if (select == 1)
    				player.modifyAttribute(Attribute.DEX, 1);
    			else
    				player.modifyAttribute(Attribute.INT, 1);
    			if (player.attributePoints() == 0)
    				setSelect();
    		} else if (select > 2 && select < 9 && player.statPoints() > 0) {
    			player.modifyStatPoints(-1);
    			switch(select) {
    			case 3: player.modifyStat(Stat.TOUGHNESS, 1); break;
    			case 4: player.modifyStat(Stat.BRAWN, 1); break;
    			case 5: player.modifyStat(Stat.AGILITY, 1); break;
    			case 6: player.modifyStat(Stat.ACCURACY, 1); break;
    			case 7: player.modifyStat(Stat.WILL, 1); break;
    			case 8: player.modifyStat(Stat.SPELLCASTING, 1); break;
    			}
    			if (player.statPoints() == 0)
    				setSelect();
    		}
    	}
    	return this;
    }
	
	private void setSelect() {
		if (player.attributePoints() > 0)
			select = 0;
		else if (player.statPoints() > 0)
			select = 3;
		else
			select = -1;
	}
}
