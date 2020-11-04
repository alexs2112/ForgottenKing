package screens;

import java.util.List;

import creatures.Player;
import creatures.Ability;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SelectAbilityScreen extends Screen {

	protected Player player;
    private String letters;
    private Group playRoot;
    private int sx;
    private int sy;
    private int select;
    private List<Ability> abilities;
    
    public SelectAbilityScreen(Group playRoot, Player player, int sx, int sy){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyz";
        this.playRoot = playRoot;
        this.sx = sx;
        this.sy = sy;
        abilities = player.getAbilities();
        if (player.lastActivated() != null && abilities.contains(player.lastActivated()))
        	select = abilities.indexOf(player.lastActivated());
    }
    
    public void displayOutput(Stage stage) {
    	root = new Group();
    	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
    	Font fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
    	draw(root, Loader.screenBorder, 0, 0);
	    
        int x = 64;
        int y = 50;
        write(root, "What would you like to activate?", 48, y, font,  Color.WHITE);
        if (abilities == null) {
        	write(root, "You have learned no abilities!", x,y + 32, font, Color.ANTIQUEWHITE);
        	return;
        }
        y += 36;
        int top = Math.min(Math.max(0, select-5), Math.max(0, abilities.size()-10));
        for (int i = top; i < top+10; i++) {
        	if (i >= abilities.size())
        		break;
        	Ability a = abilities.get(i);
        	String line = letters.charAt(i) + " - " + a.name() + " (" + a.cooldown() + ")";
        	Color c = Color.WHITE;
        	if (a.time() > 0) {
        		c = Color.DARKGREY;
        		line += " [" + a.time() + "]";
        	}
        	draw(root, a.icon(), x, 34*(i-top) + y - 26);
        	write(root, line, x+48, 34*(i-top) + y, font, c);
        	if (i == select)
        		draw(root, Loader.arrowRight, x-44, 32*(i-top)+y-26);
        }
        
        y=396;
        draw(root, Loader.screenSeparator, 0, y);
        
        if (abilities.size() > 0) {
        	x = 96;
        	y += 32;
        	Ability a = abilities.get(select);
        	write(root, a.name(), x, y+=32, font, Color.WHITE);
        	write(root, "Cooldown: " + a.cooldown(), 920, y+32, fontS, Color.WHITE);
        	String string = a.description();
        	writeWrapped(root, string, x, y+=32, 800, fontS, Color.ANTIQUEWHITE);
        	Color c = Color.WHITE;
        	if (a.time()>0)
        		c = Color.LIGHTGREY;
        	writeCentered(root, "[enter] to activate " + a.name(), 640, 764, fontS, c);
        }
    }
    
    public Screen respondToUserInput(KeyEvent key) {
    	char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	if (letters.indexOf(c) > -1
    		&& abilities != null
    		&& abilities.size() > letters.indexOf(c)) {
    		select = letters.indexOf(c);
    	} else if (key.getCode().equals(KeyCode.ESCAPE))
            return null;
    	else if (key.getCode().equals(KeyCode.ENTER)) {
    		Ability a = abilities.get(select);
    		if (a.time() > 0) {
    			player.notify(a.name() + " is still on cooldown.");
    			return null;
    		}
    		return new ActivateAbilityScreen(playRoot, player, "Activate " + a.name(), sx, sy, a);
    	}
    	else if (key.getCode().equals(KeyCode.DOWN))
    		select = Math.min(select+1, abilities.size()-1);
    	else if (key.getCode().equals(KeyCode.UP))
    		select = Math.max(select-1, 0);
        return this;
    }
    
    
    

}
