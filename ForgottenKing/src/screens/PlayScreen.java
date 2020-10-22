package screens;

import java.util.ArrayList;
import java.util.List;

import assembly.CreatureFactory;
import assembly.ItemFactory;
import creatures.Creature;
import creatures.Tag;
import features.Feature;
import items.Item;
import items.ItemTag;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Effect;
import tools.FieldOfView;
import world.World;
import world.WorldBuilder;

public class PlayScreen extends Screen {
    private int screenWidth;
    private int screenHeight;
    private World world;
    private List<String> messages;
    private Creature player;
    private CreatureFactory creatureFactory;
    private ItemFactory itemFactory;
    private FieldOfView fov;
    private Screen subscreen;
    private boolean devMode = true;

    public PlayScreen(){
        screenWidth = 32;
        screenHeight = 22;
        messages = new ArrayList<String>();
        createWorld();
        fov = new FieldOfView(world);
        itemFactory = new ItemFactory(world);
        creatureFactory = new CreatureFactory(world, itemFactory);
        populate();
        setUpPlayer();
    }
    private void createWorld(){
        world = new WorldBuilder(90, 31, 5)
        	  .makeDungeon()
              .build();
    }
    
	private void setUpPlayer() {
		world.setEntrance(player.x, player.y);
    	player.setMagic();
        player.addEquipment(itemFactory.equipment().newDagger(-1));
        player.addEquipment(itemFactory.equipment().newLeatherArmor(-1));
        player.fillMana();
        if (devMode) {
        	/*
        	player.addEquipment(itemFactory.trinket().newDevRing(-1));
        	player.addEquipment(itemFactory.equipment().newDevSword(-1));
        	player.addEquipment(itemFactory.equipment().newDevBreastplate(-1));
        	*/
        	player.addItemToInventory(itemFactory.consumable().newPotionOfStrength(-1));
        	player.addItemToInventory(itemFactory.consumable().newPotionOfPoison(-1));
        	player.addEquipment(itemFactory.equipment().newShortbow(-1));
        	for (int i = 0; i < 10; i++) {
        		player.addItemToInventory(itemFactory.ammo().newArrow(-1));
        	}
        }
        messages.clear();
        player.notify("Welcome to the Dungeon!");
        player.notify("I hope you enjoy your stay");
    }
    
	public void displayOutput(Stage stage) {
		int left = getScrollX();
	    int top = getScrollY();
	    root = new Group();
	    scene = new Scene(root, 1280, 800, Color.BLACK);
	    displayTiles(left, top);
	    displayStats();
	    displayMessages(messages);
	    stage.setScene(scene);
		stage.show();
		
		if (subscreen != null) {
			subscreen.displayOutput(stage);
			scene.setRoot(subscreen.root());
		}
	}
	public int getScrollX() {
	    return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
	}
	public int getScrollY() {
	    return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
	}
	
	private void displayTiles(int left, int top) {
		fov.update(player.x, player.y, player.z, player.visionRadius());
	    for (int x = 0; x < screenWidth; x++){
	        for (int y = 0; y < screenHeight; y++){
	            int wx = x + left;
	            int wy = y + top;
	            if (player.canSee(wx,wy,player.z)) {
	            	draw(root, world.tileImage(wx,wy, player.z), x*32, y*32);
	            	if (world.feature(wx, wy, player.z) != null)
	            		draw(root, world.feature(wx, wy, player.z).getImage(), x*32, y*32);
	            	if (world.items(wx,wy,player.z) != null)
	            		if (world.items(wx,wy,player.z).getFirstItem() != null) {
	            			draw(root, world.items(wx,wy,player.z).getFirstItem().image(), x*32, y*32);
	            			if (world.items(wx,wy,player.z).numberOfItems() > 1)
	            				draw(root, Loader.multi_item_icon, x*32, y*32);
	            		}
	            } else {
	            	if (fov.hasSeen(wx, wy, player.z)) {
	            		draw(root, fov.tileImage(wx,wy, player.z), x*32, y*32, -0.7);
	            		if (world.feature(wx, wy, player.z) != null)
	            			draw(root, world.feature(wx, wy, player.z).getImage(), x*32, y*32, -0.7);
	            		if (world.items(wx,wy,player.z) != null)
	            			if (world.items(wx,wy,player.z).getFirstItem() != null) {
	            				draw(root, world.items(wx,wy,player.z).getFirstItem().image(), x*32, y*32, -0.7);
	            				if (world.items(wx,wy,player.z).numberOfItems() > 1)
	            					draw(root, Loader.multi_item_icon, x*32, y*32, -0.7);
	            			}
	            	}
	            }
	        }
	    }
	    for (Creature c : world.creatures()) {
	    	if (c.z != player.z)
	    		continue;
        	if (c.x >= left && c.x < left + screenWidth &&
        			c.y >= top && c.y < top + screenHeight &&
        			player.canSee(c.x, c.y, c.z)) {
        		draw(root, c.image(), (c.x-left)*32, (c.y-top)*32 - ((int)c.image().getHeight()-32));
        		Image healthbar = getCreatureHealthIcon(c);
        		if (healthbar != null)
        			draw(root, healthbar, (c.x-left)*32, (c.y-top)*32);
        		if (c.equipment().size() > 0 && !c.is(Tag.PLAYER))
        			draw(root, Loader.armedEnemyIcon, (c.x-left)*32, (c.y-top)*32);
        	}
        }
	}
	
	@Override
	public Screen respondToUserInput(KeyEvent key) {
		if (player.hp() < 1)
		    return new LoseScreen(root);

		KeyCode code = key.getCode();
    	boolean endAfterUserInput = true;
    	char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
    	if (subscreen != null) {
    		subscreen = subscreen.respondToUserInput(key);
    	} else {
    		if (code.equals(KeyCode.LEFT) || c == 'h' || code.equals(KeyCode.NUMPAD4)) { player.moveBy(-1,0,0); }
    		else if (code.equals(KeyCode.RIGHT) || c == 'l' || code.equals(KeyCode.NUMPAD6)) { player.moveBy(1,0,0); }
    		else if (code.equals(KeyCode.UP) || c == 'k' || code.equals(KeyCode.NUMPAD8)) { player.moveBy(0,-1,0); }
    		else if (code.equals(KeyCode.DOWN) || c == 'j' || code.equals(KeyCode.NUMPAD2)) { player.moveBy(0,1,0); }
    		else if (c == 'y' || code.equals(KeyCode.NUMPAD7)) { player.moveBy(-1,-1,0); }
    		else if (c == 'u' || code.equals(KeyCode.NUMPAD9)) { player.moveBy(1,-1,0); }
    		else if (c == 'b' || code.equals(KeyCode.NUMPAD1)) { player.moveBy(-1,1,0); }
    		else if (c == 'n' || code.equals(KeyCode.NUMPAD3)) { player.moveBy(1,1,0); }
    		else if (c == 'g') {
    			if (world.items(player.x,player.y, player.z) != null) {
    				if(world.items(player.x,player.y, player.z).getUniqueItems().size() > 1)
    					subscreen = new PickUpScreen(world, player);
    				else
    					player.pickup();
    				player.modifyTime(5);
    			} else
    				endAfterUserInput = false;
    		} else if (c == '.' && !key.isShiftDown()) { /*Wait 1 turn*/ } 
    		else if (c == 'd')
    			subscreen = new DropScreen(player);
    		else if (c == 'q' && !key.isShiftDown()) {
    			endAfterUserInput = false;
    			subscreen = new QuaffScreen(player);
    		}
    		else if (c == 'q' && key.isShiftDown())
    			subscreen = new QuiverScreen(player);
    		else if (c == 'i')
    			subscreen = new InventoryScreen(player);
    		else if (c == 'w')
    			subscreen = new EquipScreen(player);
    		else if (c == 'r')
    			subscreen = new ReadScreen(player);
    		else if (c == 's')
    			subscreen = new StatsScreen(player);
    		else if (c == 'm')
    			subscreen = new MagicScreen(player);
    		else if (c == 'z')
    			subscreen = new SelectSpellScreen(root, player, getScrollX(), getScrollY());
    		else if (c == 'c') {
     			closeDoor();
    		} else if (key.isShiftDown() && c == '/') {
    			subscreen = new HelpScreen();
    		} else if (key.isShiftDown() && code.equals(KeyCode.PERIOD)) {
    			if (world.feature(player.x, player.y, player.z) != null && world.feature(player.x, player.y, player.z).type().equals("DownStair"))
    				world.feature(player.x,player.y, player.z).interact(player, world, player.x, player.y, player.z);
    		} else if (key.isShiftDown() && code.equals(KeyCode.COMMA)) {
    			if (world.feature(player.x, player.y, player.z) != null && world.feature(player.x, player.y, player.z).type().equals("UpStair")) {
    				if (world.feature(player.x, player.y, player.z).name().equals("Entrance"))
    					return new LeaveScreen(player);	//If the player tries to leave
    				else
    					world.feature(player.x,player.y, player.z).interact(player, world, player.x, player.y, player.z);
    			}
    		} else if (c == 'x') {
    			endAfterUserInput = false;
    			subscreen = new ExamineScreen(root, player, "Looking", 
    					getScrollX(),
    					getScrollY());
    		} else if (c == 't') {
    			player.modifyTime(13 - player.agility());
    			subscreen = new ThrowScreen(root, player,
    					getScrollX(),
    					getScrollY());
    		} else if (c == 'f') {
    			if (player.weapon() == null || player.weapon().rangedAttackValue() == 0) {
    				player.notify("You don't have a ranged weapon equipped");
    				endAfterUserInput = false;
    			} else  if (player.quiver() == null) {
    				player.notify("You are out of ammo");
    				endAfterUserInput = false;
    			} else
    				subscreen = new FireWeaponScreen(root, player,
    						getScrollX(),
    						getScrollY());
    		}
    		else if (c == '0' && devMode)
    			player.fillHP();
    		else if (c == '9' && devMode)
    			player.modifyXP(player.nextLevelXP());
    		else if (c == '8' && devMode)
    			player.modifyArmorValue(player.armorValue() * 10);
    		
    		else
    			endAfterUserInput = false;
    	}
		
		if (endAfterUserInput && subscreen == null) {
			player.update();
			if (player.time() <= 0)	//A failsafe
				player.modifyTime(player.movementDelay());
			while (player.time() > 0) {
				world.update(player.z);
				player.modifyTime(-1);
			}
		}
		
		if (player.xp() >= player.nextLevelXP()) {
			player.notify("You leveled up!");
			return new LevelUpScreen(player, this);
		}
    	return this;
    }
	
	private void closeDoor() {
    	for (int wx = -1; wx < 2; wx++) {
    		for (int wy = -1; wy < 2; wy++) {
    			Feature feat = world.feature(player.x - wx, player.y - wy, player.z);
    			if (feat != null && feat.type() == "CanClose") {
    				if (wx == 0 && wy == 0)
    					player.notify("You are standing in the way"); 
    				else {
    					if (world.creature(player.x-wx, player.y-wy, player.z) != null)
    						player.notify("There is a creature in the way");
    					else
    						feat.interact(player, world, player.x-wx, player.y-wy, player.z);
    				}
    			}
    		}
    	}
    }
	
	Font statFontM = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 20);
	Font statFontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 16);
	Font statFontXS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 12);
    private void displayStats() {
    	draw(root, Loader.playerUIFull, 1040, 0);
        write(root, "HP: " + player.hp() + "/" + player.maxHP(), 1060, 34, statFontM, Color.RED);
        write(root, "MP: " + player.mana() + "/" + player.maxMana(), 1060, 82, statFontM, Color.BLUE);
        write(root, "EXP: " + player.xp() + "/" + player.nextLevelXP(), 1060, 115, statFontXS, Color.YELLOW);
        int y = 154;
        int x = 1110;
        if (player.movementDelay() > 9)
        	x -= 8;
        write(root, ""+player.movementDelay(), x, y, statFontS, Color.WHITE);
        x = 1190;
        if (player.attackDelay() > 9)
        	x -= 8;
        write(root, ""+player.attackDelay(), x, y, statFontS, Color.WHITE);
        y += 48;
        write(root, ""+player.evasion(), 1096, y, statFontS, Color.WHITE);
        x = 1196;
        if (player.armorValue() > 9)
        	x -= 6;
        if (player.armorValue() > 99)
        	x -= 6;
        write(root, ""+player.armorValue(), x, y, statFontS, Color.WHITE);
        
        if (player.weapon() != null && player.weapon().isRanged())
        	write(root, "+" + player.getCurrentRangedAttackValue() + " [" + (player.getCurrentRangedDamage()[0]) + "-" + (player.getCurrentRangedDamage()[1]) + "]", 
                	1096, y += 48, statFontS, Color.WHITE);
        else
        	write(root, "+" + player.getCurrentAttackValue() + " [" + (player.getMinDamage()+player.getCurrentDamageMod()) + "-" + (player.getMaxDamage()+player.getCurrentDamageMod()) + "]", 
        			1096, y += 48, statFontS, Color.WHITE);
        
        if (player.weapon() != null)
        	for (ItemTag t : player.weapon().tags())
        		if (t.isWeapon() && t.icon() != null)
        			draw(root, t.icon(), 1049, 224);
        	
        
        y = 302;
        x = 1142;
        int x2 = x + 92;
        write(root, "" + player.getToughness(), x, y, statFontS, Color.WHITE);
        write(root, "" + player.getBrawn(), x2, y, statFontS, Color.WHITE);
        write(root, "" + player.getAgility(), x, y+=44, statFontS, Color.WHITE);
        write(root, "" + player.getAccuracy(), x2, y, statFontS, Color.WHITE);
        write(root, "" + player.getWill(), x, y+=44, statFontS, Color.WHITE);
        write(root, "" + player.getSpellcasting(), x2, y, statFontS, Color.WHITE);

        int num = 0;
        for (Item i : player.equipment().values()) {
        	draw(root, Loader.equipmentBox, 1040, 408 + 48*num);
        	draw(root, i.image(), 1049, 417 + 48*num);
        	write(root, i.shortDesc(), 1090, 441 + 48*num, statFontS, Color.ANTIQUEWHITE);
        	num++;
        	if (player.quiver() != null && i.isRanged()) {
        		draw(root, Loader.equipmentBoxBlue, 1040, 408 + 48*num);
            	draw(root, player.quiver().image(), 1049, 417 + 48*num);
            	write(root, "[" + player.inventory().quantityOf(player.quiver()) + "] " + player.quiver().shortDesc(), 1090, 441 + 48*num, statFontS, Color.ANTIQUEWHITE);
            	num++;
        	}
        }
        num = 0;
        int modX = 0;
        for (Effect e : player.effects()) {
        	draw(root, Loader.effectBox, 764, num * 48);
        	if (e.image() != null)
        		draw(root, e.image(), 774, num*48 + 9);
        	write(root, e.name(), 814, num * 48 + 34, statFontS, Color.WHITE);
        	if (e.duration() > 9)
        		modX = -9;
        	write(root, "" + e.duration(), 1008 + modX, num*48 + 34, statFontS, Color.WHITE);
        	num++;
        }
    }
    
    private Image getCreatureHealthIcon(Creature creature) {
    	boolean poisoned = false;
    	for (String n : creature.getEffectNames())
    		if (n.equals("Poisoned"))
    			poisoned = true;
    	if (creature.hp() == creature.maxHP()) {
    		if (poisoned)
    			return Loader.poisonedHealthBarFull;
    		return null;
    	} if (creature.hp() > 4*creature.maxHP()/5) {
    		if (poisoned)
    			return Loader.poisonedHealthBarFull;
    		return Loader.healthBarFull;
    	} else if (creature.hp() > 3 * (creature.maxHP()/5)) {
    		if (poisoned)
    			return Loader.poisonedHealthBarThreeQuarter;
    		return Loader.healthBarThreeQuarter;
    	} else if (creature.hp() > 1*creature.maxHP()/4) {
    		if (poisoned)
    			return Loader.poisonedHealthBarHalf;
    		return Loader.healthBarHalf;
    	} else {
    		if (poisoned)
    			return Loader.poisonedHealthBarQuarter;
    		return Loader.healthBarQuarter;
    	}
    }
    
    private void displayMessages(List<String> messages) {
    	if (messages.size() == 0)
    		return;
    	int messageHeight = 24;
    	int top = 800 - messages.size() * messageHeight;
    	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 14);
    	for (int i = 0; i < messages.size(); i++) {
    		write(root, messages.get(i), 10, top+i*messageHeight, font, Color.ANTIQUEWHITE);
    	}
    	messages.clear();
    }
	
	private void populate() {
		player = creatureFactory.newPlayer(messages, 0, fov);
		creatureFactory.setPlayer(player);
		for (int z = 0; z < world.depth(); z++) {
			for (int i = 0; i < 20; i++)
				creatureFactory.newRandomCreature(z, 1);
			for (int i = 0; i < 10; i++)
				creatureFactory.newRandomCreature(z, 2);
			for (int i = 0; i < 10; i++){
	            itemFactory.equipment().newRock(z);
	        }
			for (int i = 0; i < 5; i++){
				itemFactory.newRandomArmor(z);
				itemFactory.newRandomWeapon(z);
			}
			for (int i = 0; i < 3; i++) {
				itemFactory.newRandomPotion(z);
			}
			for (int i = 0; i < 2; i++) {
				itemFactory.book().newRandomBook(z);
			}
			for (int i = 0; i < 3; i++) {
				itemFactory.ammo().newArrow(z);
			}
		}
		itemFactory.newVictoryItem(world.depth()-1);
		for (Creature c : world.creatures()) {
			c.fillHP();
			c.fillMana();
		}
	}
	
}
