package screens;

import creatures.Player;
import items.Inventory;
import items.Item;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import screens.Screen;

public abstract class InventoryBasedScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	protected Player player;
	protected Inventory inventory;
	private String letters;
	private int height = 22;
	protected int select;
	protected abstract String getVerb();
    protected abstract boolean isAcceptable(Item item);
    protected abstract Screen use(Item item);
    
    public InventoryBasedScreen(Player player){
        this.player = player;
        this.inventory = player.inventory();
        init();
    }
    public InventoryBasedScreen(Inventory inventory){
        this.inventory = inventory;
        init();
    }
    private void init() {
    	this.letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
    
    public void displayOutput(Stage stage) {
    	root = new Group();
    	draw(root, Loader.screenBorder.image(), 0, 0);
	    
	    Item[] items = getItems();
    	
	    
        int x = 104;
        int y = 50;
        write(root, "What would you like to " + getVerb() + "?", 64, y, font22);
        if (player != null)
        	writeCentered(root, String.format("%.1f/%.1f", inventory.totalWeight(), player.carryWeight()), 1000, 50, font18, Color.WHITE);
        int num = 0;
        int top = Math.min(Math.max(0, select-14), Math.max(0, numOfItems(items) - height));
        for (int i = top; i < items.length; i++) {
        	if (num >= height)
        		break;
        	Item item = items[i];
        	if (item == null)
        		continue;
        	String line = letters.charAt(i) + "-";
        	
        	if (inventory.quantityOf(item) > 1)
        		line += inventory.quantityOf(item) + " ";
        	line += item.name();
        	if (inventory.quantityOf(item) > 1) {
				if (line.contains(" of "))
					line = line.replaceAll(" of ", "s of ");
				else
					line += "s";
			}
        	Color equipColour = Color.WHITE;
            if (player != null && player.hasEquipped(item)) {
            	line += " (Equipped)";
            	equipColour = Color.LAWNGREEN;
            }
            if (player != null && player.quiver() == item) {
            	line += " (Quivered)";
            	equipColour = Color.DEEPSKYBLUE;
            }
            if (player != null && player.lastWielded()==item) {
            	line += " (Tab)";
            	equipColour = Color.FORESTGREEN;
            }
            draw(root, item.image(), x-44, 32*num + y + 4);
            if (item.effectIcon() != null)
        		draw(root, item.effectIcon().image(), x-44, 32*num + y + 4);
        	write(root, line, x, 32*num + y + 32, font18, equipColour);
        	writeCentered(root, String.format("%.1f",item.weight()*inventory.quantityOf(item)), 1000, 32*num+y+32, font18, Color.WHITE);
        	if (select == i) {
        		draw(root, Loader.arrowRight.image(), 20, 32*num+y+4);
        	}
        	draw(root, Loader.inventoryEmptyLine.image(), 0, 32*num + y + 4, click(item), enter(inventory.indexOf(item)), null);
        	num++;
        }
        if (top > 0)
        	draw(root, Loader.arrowUp.image(), 20, 32);
        if (top < numOfItems(items)-height)
        	draw(root, Loader.arrowDown.image(), 20, 744);
        
        
        constructCloseButton();
    }
    private Item[] getItems() {
    	Item[] items = null;
    	items = inventory.items();
    	Item[] list = new Item[items.length];
    	for (int i = 0; i < items.length; i++) {
    		Item item = items[i];
    		if (item == null || !isAcceptable(item))
    			continue;
    		list[i] = item;
    	}
    	return list;
    }
    
    @Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
    	Item[] items = null;
    	if (player == null)
    		items = inventory.items();
    	else
    		items = player.inventory().items();
    	if (code.equals(KeyCode.DOWN)) {
    		select = getNextIndex(items);
    		return this;
    	} else if (code.equals(KeyCode.UP)) {
    		select = getPrevIndex(items);
    		return this;
    	} else if (letters.indexOf(c) > -1
             && items.length > letters.indexOf(c)
             && items[letters.indexOf(c)] != null
             && isAcceptable(items[letters.indexOf(c)])) {
    		return use(items[letters.indexOf(c)]);
    	} else if (code.equals(KeyCode.ENTER)) {
    		if (select != -1)
    			return use(items[select]);
    	} else if (code.equals(KeyCode.SLASH) && shift) {
    		if (select != -1)
    			return new InspectItemScreen(player, items[select], this);
    	} else if (code.equals(KeyCode.ESCAPE))
            return null;
        return this;
    }
    
    private int getNextIndex(Item[] items) {
    	for (int i = select+1; i<items.length; i++) {
    		if (items[i] != null && isAcceptable(items[i]))
    			return i;
    	}
    	return select;
    }
    private int getPrevIndex(Item[] items) {
    	for (int i = select-1; i>=0; i--) {
    		if (items[i] != null && isAcceptable(items[i]))
    			return i;
    	}
    	return select;
    }
    private int numOfItems(Item[] items) {
    	int x = 0;
    	for (int i = 0; i < items.length; i++) {
    		if (items[i] != null && isAcceptable(items[i]))
    			x++;
    	}
    	return x;
    }
    
    private EventHandler<MouseEvent> click(Item item) {
    	return new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent me) {
    			if (me.getButton() == MouseButton.PRIMARY)
    				refreshScreen = respondToUserInput(KeyCode.ENTER, '-', false);
    			else
    				refreshScreen = respondToUserInput(KeyCode.SLASH, '/', true);
			}
    	};
    }
    private EventHandler<MouseEvent> enter(int index) {
    	return new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent me) {
    			if (index != select) {
    				select = index;
    				refreshScreen = returnThis();
    			}
			}
    	};
    }

}
