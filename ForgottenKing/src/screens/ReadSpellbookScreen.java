package screens;

import java.util.ArrayList;

import creatures.Player;
import items.Item;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Spell;

public class ReadSpellbookScreen extends Screen {

	protected Player player;
    private String letters;
    private Item item;
    private int select;
    
    public ReadSpellbookScreen(Player player, Item item){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.item = item;
        this.select = 0;
    }
    
    public void displayOutput(Stage stage) {
    	root = new Group();
    	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
    	Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
    	draw(root, Loader.screenBorder, 0, 0);
	    
	    ArrayList<Spell> spells = getSpells();
	    
        int x = 96;
        int y = 50;
        
        write(root, "What would you like to memorize?", 48, y, font,  Color.WHITE);
        write(root, "Spell Slots: ", 960, y, fontS, Color.WHITE);
        write(root, (player.totalSpellSlots()-player.remainingSpellSlots()) + "/" + player.totalSpellSlots(), 1036, y+32, fontS, Color.WHITE);
        for (int i = 0; i < spells.size(); i++) {
        	Spell spell = spells.get(i);
        	String line = letters.charAt(i) + " - " + spell.name() + "\t\t[" + spell.type().text() + " : " + spell.level() + "]";
        	Color c = Color.WHITE;
        	if (player.remainingSpellSlots() < spell.level())
        		c = Color.GREY;
        	write(root, line, x, 32*i + y + 32, font, c);
        	if (i == select)
        		draw(root, Loader.arrowRight, x-48, y + 4 + 32*i);
        }
        
        if (spells.size() == 0) {
        	write(root, "You know all the spells in this book already!", 48, y+=32, font, Color.ANTIQUEWHITE);
        	return;
        }
        
        y += 32*spells.size() + 24;
        draw(root, Loader.screenSeparator, 0, y);
        y += 32;
        Spell s = spells.get(select);
        write(root, s.name(), x, y+=32, font, Color.WHITE);
        Color c = Color.WHITE;
        if (player.magic().get(s.type()) < s.level())
        	c = Color.LIGHTGREY;
        write(root, s.type().text() + " : " + s.level(), 960, y, fontS, c);
        write(root, "Mana: " + s.cost(), 960, y+32, fontS, Color.WHITE);
        
        String string = s.description(player);
        if (s.range() != 0)
        	string += "\n\n" + s.name() + " has a range of " + s.range();
        writeWrapped(root, string, x, y+=32, 800, fontS, Color.ANTIQUEWHITE);
        
        c = Color.WHITE;
    	if (player.remainingSpellSlots() < s.level())
    		c = Color.GREY;
        write(root, "Press [enter] to learn " + s.name(), 32, 764, fontS, c);
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
    		select = letters.indexOf(c);
    	} else if (key.getCode().equals(KeyCode.ESCAPE)) {
            return null;
    	} else if (key.getCode().equals(KeyCode.ENTER)) {
    		player.addSpell(spells.get(select));
    		return null;
    	} else if (key.getCode().equals(KeyCode.DOWN))
    		select = Math.min(spells.size()-1, select+1);
    	else if (key.getCode().equals(KeyCode.UP))
    		select = Math.max(0, select-1);
        return this;
    }

}
