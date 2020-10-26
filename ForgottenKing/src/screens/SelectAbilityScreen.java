package screens;

import java.util.ArrayList;
import java.util.List;

import creatures.Creature;
import items.Item;
import creatures.Ability;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SelectAbilityScreen extends Screen {

	protected Creature player;
    private String letters;
    private Group playRoot;
    private int sx;
    private int sy;
    
    public SelectAbilityScreen(Group playRoot, Creature player, int sx, int sy){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.playRoot = playRoot;
        this.sx = sx;
        this.sy = sy;
    }
    
    public void displayOutput(Stage stage) {
    	root = new Group();
    	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
    	draw(root, Loader.screenBorder, 0, 0);
	    
	    List<Ability> abilities = getList();
	    
        int x = 64;
        int y = 50;
        write(root, "What would you like to activate?", 48, y, font,  Color.WHITE);
        if (abilities == null) {
        	write(root, "You have learned no abilities!", x,y + 32, font, Color.ANTIQUEWHITE);
        	return;
        }
        for (int i = 0; i < abilities.size(); i++) {
        	Ability a = abilities.get(i);
        	String line = letters.charAt(i) + " - " + a.name() + " (" + a.cooldown() + ")";
        	Color c = Color.WHITE;
        	if (a.time() > 0) {
        		c = Color.DARKGREY;
        		line += " [" + a.time() + "]";
        	}
        	write(root, line, x, 32*i + y + 32, font, c);
        }
    }
    
    public Screen respondToUserInput(KeyEvent key) {
    	char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	List<Ability> abilities = getList();
    	if (letters.indexOf(c) > -1
    		&& abilities != null
    		&& abilities.size() > letters.indexOf(c)) {
    		Ability a = abilities.get(letters.indexOf(c));
    		if (a.time() > 0) {
    			player.notify(a.name() + " is still on cooldown.");
    			return null;
    		}
    		return new ActivateAbilityScreen(playRoot, player, "Activate " + a.name(), sx, sy, a);
    	} else if (key.getCode().equals(KeyCode.ESCAPE)) {
            return null;
        } else {
            return this;
        }
    }
    
    private List<Ability> getList() {
    	List<Ability> list;
    	if (player.abilities() != null)
    		list = new ArrayList<Ability>(player.abilities());
    	else
    		list = new ArrayList<Ability>();
    	for (Item i : player.equipment().values()) {
    		if (i.ability() != null)
    			list.add(i.ability());
    	}
    	return list;
    }

}
