package screens;

import java.util.ArrayList;

import creatures.Creature;
import items.Item;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Spell;

public class ReadSpellbookScreen extends Screen {

	protected Creature player;
    private String letters;
    private Item item;
    
    public ReadSpellbookScreen(Creature player, Item item){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.item = item;
    }
    
    public void displayOutput(Stage stage) {
    	root = new Group();
    	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
    	draw(root, Loader.screenBorder, 0, 0);
	    
	    ArrayList<Spell> spells = getSpells();
	    
        int x = 64;
        int y = 50;
        
        write(root, "What would you like to memorize?", 48, y, font,  Color.WHITE);
        for (int i = 0; i < spells.size(); i++) {
        	Spell spell = spells.get(i);
        	String line = letters.charAt(i) + " - " + spell.name() + " (" + spell.cost() + ") [" + spell.type().text() + ":" + spell.level() + "]";
        	write(root, line, x, 32*i + y + 32, font, Color.ANTIQUEWHITE);
        }
    }
    
    private ArrayList<Spell> getSpells() {
    	ArrayList<Spell> spells = new ArrayList<Spell>();
    	ArrayList<String> spellsMemorized = new ArrayList<String>();
    	if (player.spells() != null)
    		for (Spell s : player.spells())
    			spellsMemorized.add(s.name());
    	for (Spell s : item.spells())
    		if (!spellsMemorized.contains(s.name()))
    			spells.add(s);
    	return spells;
    }
    
    public Screen respondToUserInput(KeyEvent key) {
    	char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	ArrayList<Spell> spells = getSpells();
    	if (letters.indexOf(c) > -1
    			&& spells.size() > letters.indexOf(c)) {
    		player.addSpell(spells.get(letters.indexOf(c)));
    		return null;
    	} else if (key.getCode().equals(KeyCode.ESCAPE)) {
            return null;
        } else {
            return this;
        }
    }

}
