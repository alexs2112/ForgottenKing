package screens;

import creatures.Creature;
import items.Inventory;
import items.Item;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import screens.Screen;

public abstract class InventoryBasedScreen extends Screen {
	protected Creature player;
	protected Inventory inventory;
	private String letters;
	
	private int[] quantities;

	protected abstract String getVerb();
    protected abstract boolean isAcceptable(Item item);
    protected abstract Screen use(Item item);

    public InventoryBasedScreen(Creature player){
        this.player = player;
        this.letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
    public InventoryBasedScreen(Inventory inventory){
        this.inventory = inventory;
        this.letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
    
    public void displayOutput(Stage stage) {
    	root = new Group();
    	draw(root, Loader.screenBorder, 0, 0);
	    
	    Item[] items = getItems();
    	
	    Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
	    
        int x = 64;
        int y = 50;
        int num = 0;
        write(root, "What would you like to " + getVerb() + "?", 48, y, font,  Color.WHITE);
        for (int i = 0; i < items.length; i++) {
        	Item item = items[i];
        	if (item == null)
        		continue;
        	String line = letters.charAt(i) + " - ";
        	if (quantities[i] > 1)
        		line += quantities[i] + " ";
        	line += item.name();
        	if (quantities[i] > 1)
        		line += "s";
        	Color equipColour = Color.ANTIQUEWHITE;
            if (player != null && player.hasEquipped(item)) {
            	line += " (Equipped)";
            	equipColour = Color.LAWNGREEN;
            }
            draw(root, item.image(), x-44, 32*num + y + 4);
        	write(root, line, x, 32*num + y + 32, font, equipColour);
        	num++;
        }
    }
    private Item[] getItems() {
    	Item[] items = null;
    	int[] quant = null;
    	if (player == null) {
    		items = inventory.items();
    		quant = inventory.quantity();
    	} else {
    		items = player.inventory().items();
    		quant = player.inventory().quantity();
    	}
    	Item[] list = new Item[items.length];
    	quantities = new int[items.length];
    	for (int i = 0; i < items.length; i++) {
    		Item item = items[i];
    		if (item == null || !isAcceptable(item))
    			continue;
    		list[i] = item;
    		quantities[i] = quant[i];
    	}
    	return list;
    }
    
    public Screen respondToUserInput(KeyEvent key) {
    	char c = '-';
    	if (key.getText().length() == 1)
    		c = key.getText().charAt(0);

    	Item[] items = null;
    	if (player == null)
    		items = inventory.items();
    	else
    		items = player.inventory().items();
    
        if (letters.indexOf(c) > -1
             && items.length > letters.indexOf(c)
             && items[letters.indexOf(c)] != null
             && isAcceptable(items[letters.indexOf(c)]))
            return use(items[letters.indexOf(c)]);
        else if (key.getCode().equals(KeyCode.ESCAPE))
            return null;
        else
            return this;
    }

}
