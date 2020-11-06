package screens;

import java.util.ArrayList;

import creatures.Player;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Spell;

public class SelectSpellScreen extends Screen {

	protected Player player;
    private String letters;
    private Group playRoot;
    private int sx;
    private int sy;
    private int select = 0;
    
    public SelectSpellScreen(Group playRoot, Player player, int sx, int sy){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.playRoot = playRoot;
        this.sx = sx;
        this.sy = sy;
        if (player.lastCast() != null && player.spells().contains(player.lastCast()))
        	select = player.spells().indexOf(player.lastCast());
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
        x += 32;
        
        int top = Math.min(Math.max(0, select-5), Math.max(0, spells.size()-10));
        for (int i = top; i < top+10; i++) {
        	if (i >= spells.size())
        		break;
        	Spell spell = spells.get(i);
        	String line = letters.charAt(i) + " - " + spell.name() + " (" + spell.cost() + " Mana) [" + spell.type().text() + ":" + spell.level() + "]";
        	Color c = Color.WHITE;
        	if (player.magic().get(spell.type()) < spell.level() ||
        			player.mana() < spell.cost())
        		c = Color.DARKGREY;
        	write(root, line, x, 32*(i-top) + y + 32, font, c);
        	if (i == select)
        		draw(root, Loader.arrowRight, x-48, 32*(i-top)+y+4);
        }
        
        y = 396;
        if (spells.size() > 0) {
        	Spell s = spells.get(select);
        	ReadSpellbookScreen.displaySpellInfo(root, player, s, y);
        	Color c = Color.WHITE;
        	if (player.magic().get(s.type()) < s.level() || player.mana() < s.cost())
        		c = Color.DARKGREY;
        	writeCentered(root, "[enter] to cast " + s.name(), 640, 764, font, c);
        }
    }
    
    @Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
    	ArrayList<Spell> spells = player.spells();
    	if (letters.indexOf(c) > -1
    		&& spells != null
    		&& spells.size() > letters.indexOf(c)) {
    		Spell spell = spells.get(letters.indexOf(c));
    		if (canCastSpell(spell))
    			return new CastSpellScreen(playRoot, player, "Cast " + spell.name(), sx, sy, spell);
    		else
    			return null;
    	}
    	else if (code.equals(KeyCode.DOWN))
    		select = Math.min(select+1, spells.size()-1);
    	else if (code.equals(KeyCode.UP))
    		select = Math.max(select-1, 0);
    	else if (code.equals(KeyCode.ENTER) && select != -1) {
    		Spell spell = spells.get(select);
    		if (canCastSpell(spell))
    			return new CastSpellScreen(playRoot, player, "Cast " + spell.name(), sx, sy, spell);
    		else
    			return null;
    	}
    	else if (code.equals(KeyCode.ESCAPE)) {
            return null;
    	}
        return this;
    }

    private boolean canCastSpell(Spell spell) {
    	if (spell.level() > player.magic().get(spell.type())) {
			player.notify("Your " + spell.type().text() + " skill is not high enough to cast " + spell.name());
			return false;
		} 
    	if (spell.cost() > player.mana()) {
			player.notify("You don't have enough mana to cast " + spell.name());
			return false;
		}
		return true;
    }
}
