package screens;

import java.util.ArrayList;

import creatures.Creature;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Spell;

public class SelectSpellScreen extends Screen {

	protected Creature player;
    private String letters;
    private Group playRoot;
    private int sx;
    private int sy;
    
    public SelectSpellScreen(Group playRoot, Creature player, int sx, int sy){
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
	    
	    ArrayList<Spell> spells = player.spells();
	    
        int x = 64;
        int y = 50;
        write(root, "What would you like to cast?", 48, y, font,  Color.WHITE);
        write(root, "MP: " + player.mana() + "/" + player.maxMana(), 700, y, font, Color.BLUE);
        if (spells == null) {
        	write(root, "You have no spells memorized!", x,y + 32, font, Color.ANTIQUEWHITE);
        	return;
        }
        for (int i = 0; i < spells.size(); i++) {
        	Spell spell = spells.get(i);
        	String line = letters.charAt(i) + " - " + spell.name() + " (" + spell.cost() + ")";
        	write(root, line, x, 32*i + y + 32, font, Color.ANTIQUEWHITE);
        }
    }
    
    public Screen respondToUserInput(KeyEvent key) {
    	char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	ArrayList<Spell> spells = player.spells();
    	if (letters.indexOf(c) > -1
    			&& spells.size() > letters.indexOf(c)) {
    		Spell spell = spells.get(letters.indexOf(c));
    		return new CastSpellScreen(playRoot, player, "Cast " + spell.name(), sx, sy, spell);
    	} else if (key.getCode().equals(KeyCode.ESCAPE)) {
            return null;
        } else {
            return this;
        }
    }

}
