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
	private static final long serialVersionUID = 7769423305067121315L;

	protected Player player;
    private String letters;
    private Group playRoot;
    private int sx;
    private int sy;
    private int select = 0;
    
    public SelectSpellScreen(Group playRoot, Player player, int sx, int sy){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
        y+=32;
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
        	
        	
        	draw(root, spell.icon(), x, 32*(i-top) + y - 26);
        	write(root, line, x+48, 32*(i-top) + y, font, c);
        	if (i == select)
        		draw(root, Loader.arrowRight, x-44, 32*(i-top)+y-26);
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
        constructCloseButton();
    }
    
    @Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
    	ArrayList<Spell> spells = player.spells();
    	if (letters.indexOf(c) > -1
    		&& spells != null
    		&& spells.size() > letters.indexOf(c)) {
    		Spell spell = spells.get(letters.indexOf(c));
    		if (player.canCastSpell(spell))
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
    		if (player.canCastSpell(spell))
    			return new CastSpellScreen(playRoot, player, "Cast " + spell.name(), sx, sy, spell);
    		else
    			return null;
    	}
    	else if (code.equals(KeyCode.ESCAPE)) {
            return null;
    	}
        return this;
    }
}
