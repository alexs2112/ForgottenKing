package screens;

import java.util.ArrayList;

import creatures.Player;
import items.Item;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spells.Spell;

public class ReadSpellbookScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
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
    	draw(root, Loader.screenBorder.image(), 0, 0);
	    
	    ArrayList<Spell> spells = getSpells();
	    
        int x = 96;
        int y = 50;
        
        write(root, "What would you like to memorize?", 48, y, font22,  Color.WHITE);
        write(root, "Spell Slots: ", 960, y, font20, Color.WHITE);
        write(root, (player.totalSpellSlots()-player.remainingSpellSlots()) + "/" + player.totalSpellSlots(), 1036, y+32, font20, Color.WHITE);
        for (int i = 0; i < spells.size(); i++) {
        	Spell spell = spells.get(i);
        	String line = letters.charAt(i) + " - " + spell.name();
        	String typeLine = "[" + spell.type().text() + " : " + spell.level() + "]";
        	Color c = Color.WHITE;
        	if (player.remainingSpellSlots() < spell.level())
        		c = Color.GREY;
        	write(root, line, x, 32*i + y + 32, font22, c);
        	write(root, typeLine, 732, 32*i + y + 32, font22, c);
        	if (i == select)
        		draw(root, Loader.arrowRight.image(), x-48, y + 4 + 32*i);
        }
        
        if (spells.size() == 0) {
        	write(root, "You know all the spells in this book already!", 48, y+=32, font22, Color.ANTIQUEWHITE);
        	return;
        }
        
        y += 32*spells.size() + 24;
        Spell s = spells.get(select);
        displaySpellInfo(root, player, s, y);
        Color c = Color.WHITE;
    	if (player.remainingSpellSlots() < s.level())
    		c = Color.GREY;
        writeCentered(root, "Press [enter] to learn " + s.name(), 640, 764, font20, c);
        constructCloseButton();
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
    
    @Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
    	ArrayList<Spell> spells = getSpells();
    	if (letters.indexOf(c) > -1
    			&& spells.size() > letters.indexOf(c)) {
    		select = letters.indexOf(c);
    	} else if (code.equals(KeyCode.ESCAPE)) {
            return null;
    	} else if (code.equals(KeyCode.ENTER)) {
    		player.addSpell(spells.get(select));
    		return null;
    	} else if (code.equals(KeyCode.DOWN))
    		select = Math.min(spells.size()-1, select+1);
    	else if (code.equals(KeyCode.UP))
    		select = Math.max(0, select-1);
        return this;
    }
    
    public static void displaySpellInfo(Group root, Player player, Spell s, int y) {
    	draw(root, Loader.screenSeparator.image(), 0, y);
    	int x = 64;
    	y += 32;
    	write(root, s.name(), x, y+=32, font22, Color.WHITE);
    	Color c = Color.WHITE;
    	if (player.magic().get(s.type()) < s.level())
    		c = Color.LIGHTGREY;
    	write(root, s.type().text() + " : " + s.level(), 960, y, font20, c);
    	write(root, "Mana: " + s.cost(), 960, y+32, font20, Color.WHITE);

    	String string = s.description(player);
    	if (s.range() != 0)
    		string += "\n\n" + s.name() + " has a range of " + s.range();
    	if (s.effect() != null && s.effect().description() != null)
    		string += "\n\n" + s.effect().description();
    	if (s.volume() > 0) {
    		string += "\n\n";
    		if (s.volume() > 8)
    			string += "It is extremely loud";
    		else if (s.volume() > 5)
    			string += "It is very loud";
    		else if (s.volume() > 2)
    			string += "It is moderately loud";
    		else
    			string += "It is slightly loud";
    	}
    	writeWrapped(root, string, x, y+=32, 800, font20, Color.ANTIQUEWHITE);
    }

}
