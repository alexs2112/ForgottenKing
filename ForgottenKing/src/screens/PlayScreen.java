package screens;

//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import assembly.CreatureFactory;
import assembly.ItemFactory;
import assembly.Spells;
import audio.Audio;
import creatures.Ability;
import creatures.ClassSelection;
import creatures.Creature;
import creatures.Player;
import creatures.Tag;
import creatures.Type;
import features.Feature;
import items.Item;
import items.ItemTag;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.Effect;
import tools.FieldOfView;
import tools.KeyBoardCommand;
import tools.Message;
import tools.Point;
import world.World;
import world.WorldBuilder;

public class PlayScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
    private int screenWidth;
    private int screenHeight;
    private World world;
    private List<Message> messages;
    private Player player;
    private CreatureFactory creatureFactory;
    private ItemFactory itemFactory;
    private FieldOfView fov;
    private Screen subscreen;
    private boolean devMode = false;
    private String hotkeyNumbers = "1234567890";
    public Audio audio() {
    	if (subscreen != null)
    		return subscreen.audio();
    	return player.songToPlayByEnemy();
    }

    public PlayScreen(ClassSelection character){
        screenWidth = 32;
        screenHeight = 24;
        messages = new ArrayList<Message>();
        createWorld();
        fov = new FieldOfView(world);
        itemFactory = new ItemFactory(world);
        creatureFactory = new CreatureFactory(world, itemFactory);
        itemFactory.setCreatureFactory(creatureFactory);
        setUpPlayer(character);
        populate();
        prepareButtons();
    }
    private void createWorld(){
        world = new WorldBuilder(90, 31, 5)
        	  .makeDungeon()
              .build();
    }
    
	private void setUpPlayer(ClassSelection character) {
		player = creatureFactory.newPlayer(messages, 0, fov, character);
		creatureFactory.setPlayer(player);
		world.setEntrance(player.x, player.y);
		player.setMagic();
        itemFactory.equipPlayer(player);
        if (character.tags() != null)
        	for (Tag t : character.tags())
        		player.addTag(t);
        if (character.abilities() != null)
        	for (Ability a : character.abilities())
        		player.addAbility(a);
        if (player.is(Tag.ELEMENTALIST)) {
        	player.magic().modify(Type.FIRE, 1);
        	player.addSpell(Spells.embers());
        }
        if (devMode) {
//        	player.addEquipment(itemFactory.weapon().newDevSword(-1));
//        	player.addEquipment(itemFactory.armor().newDevBreastplate(-1));
        	for (int i = 0; i < 10; i++)
        		player.addItemToInventory(itemFactory.weapon().getRandomEnchant());
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
	    displayHazards(left, top);
		displayCreatures(left, top);
		displayStats();
		displayMouse(left, top);
		handleButtons();
		scene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {public void handle(KeyEvent me) { messages.clear(); } });
		if (subscreen != null) {
			subscreen.displayOutput(stage);
			scene.setRoot(subscreen.root());
			scene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					if (subscreen != null && subscreen.refreshScreen() != null)
						subscreen = subscreen.refreshScreen();
					if (subscreen != null && subscreen.exitThis())
						subscreen = null;
				}
			});
			scene.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) {
					if (subscreen != null && subscreen.refreshScreen() != null)
						subscreen = subscreen.refreshScreen();
				}
			});
		} else {
			displayMessages(messages);
			scene.addEventFilter(MouseEvent.MOUSE_MOVED, getMouseTile());
			scene.addEventFilter(MouseEvent.MOUSE_CLICKED, handleMouse());
		}
		
		stage.setScene(scene);
		stage.show();
	}
	public int getScrollX() {
	    return Math.max(0, Math.min(player.x - screenWidth / 2, world.width() - screenWidth));
	}
	public int getScrollY() {
	    return Math.max(0, Math.min(player.y - screenHeight / 2, world.height() - screenHeight));
	}
	
	Font fontXS = Font.loadFont(this.getClass().getResourceAsStream("resources/DejaVuSansMono.ttf"), 14);
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
	            	if (world.items(wx,wy,player.z) != null) {
	            		Item i = world.items(wx,wy,player.z).getFirstItem();
	            		if (i != null) {
	            			draw(root, i.image(), x*32, y*32);
	            			if (i.effectImage() != null)
	            	    		draw(root, i.effectImage(), x*32, y*32);
	            			if (world.items(wx,wy,player.z).numberOfItems() > 1)
	            				draw(root, Loader.multi_item_icon, x*32, y*32);
	            		}
	            	}
	            } else {
	            	if (fov.hasSeen(wx, wy, player.z)) {
	            		draw(root, fov.tileImage(wx,wy, player.z), x*32, y*32, -0.7);
	            		if (world.feature(wx, wy, player.z) != null)
	            			draw(root, world.feature(wx, wy, player.z).getImage(), x*32, y*32, -0.7);
	            		if (world.items(wx,wy,player.z) != null) {
	            			Item i = world.items(wx,wy,player.z).getFirstItem();
	            			if (i != null) {
	            				draw(root, i.image(), x*32, y*32, -0.7);
	            				if (i.effectImage() != null)
	            					draw(root, i.effectImage(), x*32, y*32, -0.7);
	            				if (world.items(wx,wy,player.z).numberOfItems() > 1)
	            					draw(root, Loader.multi_item_icon, x*32, y*32, -0.7);
	            			}
	            		}
	            	}
	            }
	        }
	    }
	}
	
	private void displayCreatures(int left, int top) {
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
        		if (c.weapon() != null && !c.is(Tag.PLAYER))
        			draw(root, Loader.armedEnemyIcon, (c.x-left)*32 + 24, (c.y-top)*32);
        		if (c.isWandering())
        			draw(root, Loader.wanderingEnemyIcon, (c.x-left)*32 + 16, (c.y-top)*32+16);
        		HashMap<String, Color> h = c.getStatements();
        		c.clearStatements();
        		if (h != null) {
        			int i = 0;
        			for (String s : h.keySet()) {
        				writeCentered(root, s, (c.x-left)*32+12, (c.y-top)*32-2-(14*i), fontXS, h.get(s));
        				i++;
        			}
        		}
        	}
        }
	}
	private void displayHazards(int left, int top) {
		for (int x = 0; x < screenWidth; x++){
	        for (int y = 0; y < screenHeight; y++){
	            int wx = x + left;
	            int wy = y + top;
	            if (player.canSee(wx,wy,player.z)) {
	            	if (world.hazard(wx, wy, player.z) != null) {
	            		draw(root, world.hazard(wx, wy, player.z).image(),x*32, y*32);
	            	}
	            }
	        }
		}
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
		//messages.clear();
		if (player.hp() < 1)
		    return new LoseScreen(root);
    	boolean endAfterUserInput = true;
    	if (subscreen != null) {
    		subscreen = subscreen.respondToUserInput(code, c, shift);
    		if (player.meditating() > 0)
    			meditatePlayer();
    	} else if (player.isStunned()) {
    		endAfterUserInput = true;
    		player.notify("You are stunned!");
    	} else if (player.isConfused()) {
    		endAfterUserInput = true;
    		player.notify("You are confused!");
    		player.confusedWander();
    	} else if (player.meditating() > 0) {
    		meditatePlayer();
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
    				pickupItems();
    			} else
    				endAfterUserInput = false;
    		} else if (c == '.' && !shift) { /*Wait 1 turn*/ } 
    		else if (c == 'd')
    			subscreen = new DropScreen(player);
    		else if (c == 'q' && !shift) {
    			endAfterUserInput = false;
    			if (player.is(Tag.NOQUAFF))
    				player.notify("You cannot drink potions");
    			else
    				subscreen = new QuaffScreen(player);
    		}
    		else if (c == 'q' && shift)
    			subscreen = new QuiverScreen(player);
    		else if (c == 'i')
    			subscreen = new InventoryScreen(player);
    		else if (c == 'w')
    			subscreen = new EquipScreen(player);
    		else if (code.equals(KeyCode.TAB)) {
    			if (player.lastWielded() != null && player.inventory().contains(player.lastWielded()))
    				player.equip(player.lastWielded());
    		}
    		else if (c == 'r' && shift) {
    			player.setResting(true);
    			playerRest();
    		} else if (c == 'r' && !shift)
    			subscreen = new ReadScreen(player);
    		else if (c == 's')
    			subscreen = new StatsScreen(player);
    		else if (c == 'm')
    			subscreen = new MagicScreen(player);
    		else if (c == 'p')
    			subscreen = new PerkScreen(player);
    		else if (c == 'c' && shift)
     			closeDoor();
    		else if (c == 'c' && !shift) {
    			if (player.is(Tag.NOCAST)) {
    				player.notify("You cannot cast spells");
    				endAfterUserInput = false;
    			} else
    				subscreen = new SelectSpellScreen(root, player, getScrollX(), getScrollY());
    		} else if (c == 'a')
    			subscreen = new SelectAbilityScreen(root, player, getScrollX(), getScrollY());
    		else if (shift && c == '/') {
    			subscreen = new HelpScreen();
    		} else if (shift && code.equals(KeyCode.PERIOD)) {
    			if (world.feature(player.x, player.y, player.z) != null && world.feature(player.x, player.y, player.z).type().equals("DownStair"))
    				world.feature(player.x,player.y, player.z).interact(player, world, player.x, player.y, player.z);
    		} else if (shift && code.equals(KeyCode.COMMA)) {
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
    			if (player.canFire(-1,-1)) {
    				Point p = player.getAutoTarget();
    				subscreen = new FireWeaponScreen(root, player,
    						getScrollX(),
    						getScrollY(),
    						p);
    			} else
    				endAfterUserInput = false;
    		} else if (code.equals(KeyCode.SPACE)) {
    			Feature f = world.feature(player.x, player.y, player.z);
    			if (world.items(player.x,player.y, player.z) != null) {
    				pickupItems();
    			} else if (f != null && (f.type().equals("DownStair") || f.type().equals("UpStair"))) {
    				if (world.feature(player.x, player.y, player.z).name().equals("Entrance"))
    					return new LeaveScreen(player);
    				f.interact(player, world, player.x, player.y, player.z);
    			} else {
    				//Wait 1 turn
    			}
    		}
    		else if (hotkeyNumbers.indexOf(c) != -1)
    			return player.hotkey(hotkeyNumbers.indexOf(c)).use(root, player, getScrollX(), getScrollY());
    		else if (c == '0' && devMode && shift)
    			player.fillHP();
    		else if (c == '9' && devMode && shift)
    			player.modifyXP(player.nextLevelXP());
    		else
    			endAfterUserInput = false;
    	}
    	//This is a gimpy way to fix exiting out of menus
    	if (code.equals(KeyCode.ESCAPE))
    		endAfterUserInput = false;
    	
		if (endAfterUserInput && subscreen == null) {
			player.update();
			if (player.time() <= 0) //If the players action did not modify their time, set their time to their movement delay
				player.modifyTime(player.getMovementDelay());
			while (player.time() > 0) {
				world.update(player.z);
				player.modifyTime(-1);
			}
			if (player.hp() < player.maxHP()/4 && !player.resting())
				player.notify("Low Health!", Color.ORANGERED);
		}
		//If the player is dead, hit a command to jumpstart to the next screen.
		if (player.hp() <= 0)
			nextCommand = new KeyBoardCommand(KeyCode.PERIOD, '.', false);
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
	Font statFontXS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 14);
    private void displayStats() {
    	draw(root, Loader.playerUIFull, 1040, 0);
        writeCentered(root, "HP: " + player.hp() + "/" + player.maxHP(), 1160, 34, statFontM, Color.RED);
        writeCentered(root, "MP: " + player.mana() + "/" + player.maxMana(), 1160, 82, statFontM, Color.BLUE);
        writeCentered(root, "XP: " + player.xp() + "/" + player.nextLevelXP(), 1160, 115, statFontXS, Color.YELLOW);
        int y = 154;
        writeCentered(root, ""+player.movementDelay(), 1120, y, statFontS, Color.WHITE);
        writeCentered(root, ""+player.attackDelay(), 1200, y, statFontS, Color.WHITE);
        y += 48;
        writeCentered(root, ""+player.evasion(), 1114, y, statFontS, Color.WHITE);
        writeCentered(root, ""+player.armorValue(), 1204, y, statFontS, Color.WHITE);
        
        if (player.weapon() != null && player.weapon().isRanged())
        	writeCentered(root, "+" + player.getCurrentRangedAttackValue() + " [" + (player.getCurrentRangedDamage()[0]) + "-" + (player.getCurrentRangedDamage()[1]) + "]", 
                	1178, y += 48, statFontS, Color.WHITE);
        else
        	writeCentered(root, "+" + player.getCurrentAttackValue() + " [" + (player.getMinDamage()+player.getCurrentDamageMod()) + "-" + (player.getMaxDamage()+player.getCurrentDamageMod()) + "]", 
        			1178, y += 48, statFontS, Color.WHITE);
        
        if (player.weapon() != null)
        	for (ItemTag t : player.weapon().tags())
        		if (t.isWeapon() && t.icon() != null)
        			draw(root, t.icon(), 1049, 224);
        	
        y = 302;
        int x = 1150;
        int x2 = 1242;
        writeCentered(root, "" + player.getToughness(), x, y, statFontS, Color.WHITE);
        writeCentered(root, "" + player.getBrawn(), x2, y, statFontS, Color.WHITE);
        writeCentered(root, "" + player.getAgility(), x, y+=44, statFontS, Color.WHITE);
        writeCentered(root, "" + player.getAccuracy(), x2, y, statFontS, Color.WHITE);
        writeCentered(root, "" + player.getWill(), x, y+=44, statFontS, Color.WHITE);
        writeCentered(root, "" + player.getSpellcasting(), x2, y, statFontS, Color.WHITE);

        int num = 0;
        for (Item i : player.equipment().values()) {
        	draw(root, Loader.equipmentBox, 1040, 408 + 48*num);
        	draw(root, i.image(), 1049, 417 + 48*num);
			if (i.effectImage() != null)
	    		draw(root, i.effectImage(), 1049, 417 + 48*num);
        	write(root, i.shortDesc(player), 1090, 441 + 48*num, statFontXS, Color.ANTIQUEWHITE);
        	num++;
        	if (player.quiver() != null && i.isRanged()) {
        		draw(root, Loader.equipmentBoxBlue, 1040, 408 + 48*num);
            	draw(root, player.quiver().image(), 1049, 417 + 48*num);
    			if (i.effectImage() != null)
    	    		draw(root, player.quiver().effectImage(), 1049, 417 + 48*num);
            	write(root, "[" + player.inventory().quantityOf(player.quiver()) + "] " + player.quiver().shortDesc(player), 1090, 441 + 48*num, statFontXS, Color.ANTIQUEWHITE);
            	num++;
        	}
        }
        num = 0;
        for (Effect e : player.effects()) {
        	draw(root, Loader.effectBox, 764, num * 48);
        	if (e.image() != null)
        		draw(root, e.image(), 774, num*48 + 9);
        	write(root, e.name(), 814, num * 48 + 34, statFontS, Color.WHITE);
        	writeCentered(root, "" + e.duration(), 1015, num*48 + 34, statFontS, Color.WHITE);
        	num++;
        }
        int notificationY = 0;
        if (player.magic().floatingPoints() > 0) {
        	String s = "[m]: "+player.magic().floatingPoints()+" Free Spell Point";
        	if (player.magic().floatingPoints() > 1)
        		s += "s";
        	write(root, s, 8, notificationY+=24, statFontS, Color.AQUA);
        }
        if (player.perkPoints() > 0) {
        	String s = "[p]: "+player.perkPoints()+" Perk Point";
        	if (player.perkPoints() > 1)
        		s += "s";
        	s += " Available!";
        	write(root, s, 8, notificationY+=24, statFontS, Color.AQUA);
        }
        if (player.attributePoints() > 0) {
        	String s = "[s]: "+player.attributePoints()+" Attribute Point";
        	if (player.attributePoints() > 1)
        		s += "s";
        	s += " Available!";
        	write(root, s, 8, notificationY+=24, statFontS, Color.AQUA);
        }
        if (player.statPoints() > 0) {
        	String s = "[s]: "+player.statPoints()+" Stat Point";
        	if (player.attributePoints() > 1)
        		s += "s";
        	s += " Available!";
        	write(root, s, 8, notificationY+=24, statFontS, Color.AQUA);
        }
    }
    
    private Image getCreatureHealthIcon(Creature creature) {
    	boolean poisoned = false;
    	boolean burning = false;
    	for (String n : creature.getEffectNames()) {
    		if (n.equals("Poisoned"))
    			poisoned = true;
    		else if (n.equals("Burning"))
    			burning = true;
    	}
    	if (creature.hp() == creature.maxHP()) {
    		if (poisoned)
    			return Loader.poisonedHealthBarFull;
    		else if (burning)
    			return Loader.burningHealthBarFull;
    		return null;
    	} if (creature.hp() > 4*creature.maxHP()/5) {
    		if (poisoned)
    			return Loader.poisonedHealthBarFull;
    		else if (burning)
    			return Loader.burningHealthBarFull;
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarFull;
    		return Loader.healthBarFull;
    	} else if (creature.hp() > 3 * (creature.maxHP()/5)) {
    		if (poisoned)
    			return Loader.poisonedHealthBarThreeQuarter;
    		else if (burning)
    			return Loader.burningHealthBarThreeQuarter;
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarThreeQuarter;
    		return Loader.healthBarThreeQuarter;
    	} else if (creature.hp() > 1*creature.maxHP()/4) {
    		if (poisoned)
    			return Loader.poisonedHealthBarHalf;
    		else if (burning)
    			return Loader.burningHealthBarHalf;
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarHalf;
    		return Loader.healthBarHalf;
    	} else {
    		if (poisoned)
    			return Loader.poisonedHealthBarQuarter;
    		else if (burning)
    			return Loader.burningHealthBarQuarter;
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarQuarter;
    		return Loader.healthBarQuarter;
    	}
    }
    
    private void displayMessages(List<Message> messages) {
    	if (messages.size() == 0)
    		return;
    	int messageHeight = 24;
    	int top = 760 - messages.size() * messageHeight;
    	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 14);
    	int i = 0;
    	for (Message m : messages) {
    		write(root, m.message(), 10, top+i*messageHeight, font, m.colour());
    		i++;
    	}
    }
	
	private void populate() {
		for (int z = 0; z < world.depth(); z++) {
			//Each level has 9 creatures of a lower level, 15 creatures of that level, and 6 creatures of a higher level
			for (int i = 0; i < 9; i++)
				creatureFactory.newRandomCreature(z, z-1);
			for (int i = 0; i < 15; i++)
				creatureFactory.newRandomCreature(z, z);
			for (int i = 0; i < 6; i++)
				creatureFactory.newRandomCreature(z, z+1);
			for (int i = 0; i < 8; i++){
	            itemFactory.ammo().newRock(z);
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
			for (int i = 0; i < 3; i++) {
				itemFactory.trinket().newRandomRing(z);
			}
		}
		creatureFactory.newGrisstok(world.depth()-1);	//Spawn in the boss, have a more interesting way of doing this later
	}
	
	//A method that repeats the last key press (5) until the player is done resting
	private void playerRest() {
		if (player.resting() && !player.creatureInSight() && (player.hp() < player.maxHP()||player.mana() < player.maxMana()))
			nextCommand = new KeyBoardCommand(KeyCode.R, 'r', true);
    	else if (player.resting()) {
    		player.setResting(false);
    		if (player.creatureInSight())
    			player.notify("There are creatures in view");
    		else {
    			player.notify("Finished resting!");
    		}
    	}
	}
	private void meditatePlayer() {
		if (!player.creatureInSight() && player.meditating() > 0) {
			player.modifyMeditating(-1);
			if (player.meditating() > 0)
				nextCommand = new KeyBoardCommand(KeyCode.PERIOD, '.', false);
			else
	    		player.notify("Done meditating");
		} else {
			player.stopMeditating();
			if (player.creatureInSight())
				player.notify("Enemies interrupt your meditation!");
		}
	}
	private void pickupItems() {
		if(world.items(player.x,player.y, player.z).getUniqueItems().size() > 1)
			subscreen = new PickUpScreen(world, player);
		else
			player.pickup();
		player.modifyTime(5);
	}
	
	
	
	//Button Handling can go all the way down here
	Font tooltipFont = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 14);
	private boolean[] mouseOverButtons = new boolean[12];
	private List<Image> buttonIcons;
	private List<Image> buttonIconsSelected;
	private List<String> tooltips;
	private void handleButtons() {
		draw(root, Loader.buttonBar, 0, 756);
		for (int i = 0; i < 11; i++) {
			Image image = buttonIcons.get(i);
			if (mouseOverButtons[i])
				image = buttonIconsSelected.get(i);
			draw(root, image, 40*i, 760, setMouseClick(i), setMouseOver(i, true), setMouseOver(i, false));
		}
		//A special case for swapping weapons
		int i = 11;
		Image image = buttonIcons.get(i);
		if (mouseOverButtons[i])
			image = buttonIconsSelected.get(i);
		draw(root, image, 40*i, 760, setMouseClick(i), setMouseOver(i, true), setMouseOver(i, false)); 
		if (player.lastWielded() != null) {
			for (ItemTag t : player.lastWielded().tags())
				if (t.isWeapon() && t.icon() != null) {
					draw(root, t.icon(), 40*i+4, 764, setMouseClick(i), setMouseOver(i, true), setMouseOver(i, false));
					break;
				}
		}
	}
	private void prepareButtons() {
		buttonIcons = new ArrayList<Image>();
		buttonIcons.add(Loader.inventoryIcon);
		buttonIcons.add(Loader.wearIcon);
		buttonIcons.add(Loader.quaffIcon);
		buttonIcons.add(Loader.readIcon);
		buttonIcons.add(Loader.meditateIcon);
		buttonIcons.add(Loader.statsIcon);
		buttonIcons.add(Loader.perksIcon);
		buttonIcons.add(Loader.restIcon);
		buttonIcons.add(Loader.throwIcon);
		buttonIcons.add(Loader.fireWeaponIcon);
		buttonIcons.add(Loader.castIcon);
		buttonIcons.add(Loader.swapWeaponIcon);
		buttonIconsSelected = new ArrayList<Image>();
		buttonIconsSelected.add(Loader.inventoryIconSelected);
		buttonIconsSelected.add(Loader.wearIconSelected);
		buttonIconsSelected.add(Loader.quaffIconSelected);
		buttonIconsSelected.add(Loader.readIconSelected);
		buttonIconsSelected.add(Loader.meditateIconSelected);
		buttonIconsSelected.add(Loader.statsIconSelected);
		buttonIconsSelected.add(Loader.perksIconSelected);
		buttonIconsSelected.add(Loader.restIconSelected);
		buttonIconsSelected.add(Loader.throwIconSelected);
		buttonIconsSelected.add(Loader.fireWeaponIconSelected);
		buttonIconsSelected.add(Loader.castIconSelected);
		buttonIconsSelected.add(Loader.swapWeaponIconSelected);
		tooltips = new ArrayList<String>();
		tooltips.add("[i]nventory");
		tooltips.add("[w]ear/[w]ield");
		tooltips.add("[q]uaff");
		tooltips.add("[r]ead");
		tooltips.add("[m]editate");
		tooltips.add("[s]tats");
		tooltips.add("[p]erks");
		tooltips.add("[R]est");
		tooltips.add("[t]hrow");
		tooltips.add("[a]bility");
		tooltips.add("[c]ast Spell");
		tooltips.add("Swap Weapon");
	}
	private EventHandler<MouseEvent> setMouseOver(int index, boolean b) {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				if (mouseOverButtons[index] != b) {
					mouseOverButtons[index] = b;
					refreshScreen = returnThis();
				}
				if (b)
					tooltip = tooltips.get(index);
				else
					tooltip = null;
			}
		};
	}
	private EventHandler<MouseEvent> setMouseClick(int index) {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				refreshScreen = mouseClick(index);
			}
		};
	}
	/**
	 * Returns a screen depending what button the player clicks
	 * By Index, Right to Left:
	 * 0: Inventory [i]
	 * 1: Wield [w]
	 * 2: Quaff [q]
	 * 3: Read [r]
	 * 4: Meditate [m]
	 * 5: Stats [s]
	 * 6: Perks [p]
	 * 7: Rest [R]
	 * 8: Throw [t]
	 * 9: Activate [a]
	 * 10: Cast [c]
	 * 11: Swap to last weapon [tab]
	 */
	private Screen mouseClick(int index) {
		switch(index) {
		case 0: return respondToUserInput(KeyCode.I, 'i', false);
		case 1: return respondToUserInput(KeyCode.W, 'w', false);
		case 2: return respondToUserInput(KeyCode.Q, 'q', false);
		case 3: return respondToUserInput(KeyCode.R, 'r', false);
		case 4: return respondToUserInput(KeyCode.M, 'm', false);
		case 5: return respondToUserInput(KeyCode.S, 's', false);
		case 6: return respondToUserInput(KeyCode.P, 'p', false);
		case 7: return respondToUserInput(KeyCode.R, 'r', true);
		case 8: return respondToUserInput(KeyCode.T, 't', false);
		case 9: return respondToUserInput(KeyCode.A, 'a', false);
		case 10: return respondToUserInput(KeyCode.C, 'c', false);
		case 11: return respondToUserInput(KeyCode.TAB, '-', false);
		}
		return returnThis();
	}
	
	//The X and Y coordinates of the mouse, in terms of tiles
	private int mouseX = -1;
	private int mouseY = -1;
	private int mouseSX = 0;
	private int mouseSY = 0;
	private EventHandler<MouseEvent> getMouseTile() {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				//First get the mouse coordinates
				if(setMouseCoordinates((int)me.getX(), (int)me.getY()))		
					refreshScreen = returnThis();
				
				//Then find and display any tooltips
				int x = getScrollX() + mouseX;
				int y = getScrollY() + mouseY;
				if (mouseX != -1 && mouseY != -1) {
					if (player.creature(x, y, player.z) != null)
						tooltip = player.creature(x, y, player.z).name();
					else if (player.items(x, y, player.z) != null && !player.items(x, y, player.z).isEmpty())
						tooltip = player.items(x, y, player.z).getFirstItem().name();
					else if (player.feature(x,y,player.z) != null)
						tooltip = player.feature(x, y, player.z).name();
					else if (player.hazard(x,y,player.z) != null)
						tooltip = player.hazard(x, y, player.z).name();
					else
						tooltip = null;
				}
			}
		};
	}
	private boolean setMouseCoordinates(int x, int y) {
		mouseSX = x;
		mouseSY = y;
		if (x > screenWidth*32 || y > screenHeight*32 || x < 0 || y < 0) {
			mouseX = -1;
			mouseY = -1;
			return false;
		}
		for (int wx = screenWidth-1; wx >= 0; wx--) {
			for (int wy = screenHeight-1; wy >= 0; wy--) {
				if (x > wx * 32 && y > wy * 32) {
					boolean r = false;
					if (mouseY != wy) {
						mouseY = wy;
						r = true;
					}
					if (mouseX != wx) {
						mouseX = wx;
						r = true;
					}
					return r;
				}
			}
		}
		return false;
	}
	private String tooltip;
	private void displayMouse(int left, int top) {
		if (tooltip != null)
			writeCentered(root, tooltip, mouseSX, mouseSY-32, tooltipFont, Color.WHITE);
		if (mouseX == -1 || mouseY == -1)
			return;
		Image i = Loader.yellowSelection;
		if (player.creature(left+mouseX, top+mouseY, player.z) != null && player.creature(left+mouseX, top+mouseY, player.z) != player)
			i = Loader.redSelection;
		draw(root, i, mouseX*32, mouseY*32);
	}
//	private EventHandler<MouseEvent> getTooltip(String s, boolean enter) {
//		return new EventHandler<MouseEvent>() {
//			public void handle(MouseEvent me) {
//				if (enter)
//					tooltip = s;
//				else
//					tooltip = null;
//			}
//		};
//	}
	private EventHandler<MouseEvent> handleMouse() {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				if (mouseX == -1 || mouseY == -1)
					return;
				messages.clear();
				if (me.getButton() == MouseButton.SECONDARY) {
					int x = getScrollX() + mouseX;
					int y = getScrollY() + mouseY;
					Creature c = player.creature(x, y, player.z);
					if (c != null) {
						if (c == player)
							subscreen = new StatsScreen(player);
						else
							subscreen = new InspectCreatureScreen(c);
						refreshScreen = returnThis();
						return;
					}
					if (player.items(x, y, player.z) != null) {
						Item i = player.items(x, y, player.z).getFirstItem();
						subscreen = new InspectItemScreen(i);
						refreshScreen = returnThis();
						return;
					}

				} else if (me.getButton() == MouseButton.PRIMARY){
					int x = getScrollX() + mouseX;
					int y = getScrollY() + mouseY;
					Creature c = player.creature(x, y, player.z);
					if (c != null) {
						if (c == player) {
							respondToUserInput(KeyCode.SPACE, ' ', false);
							return;
						}
						if (player.distanceTo(c.x, c.y) <= player.meleeRange()+1) {
							player.attack(c);
							respondToUserInput(KeyCode.PERIOD, '.', false);
							return;
						}
//						if (player.canFire(x, y)) {
//							player.fireItem(player.quiver(), c.x, c.y, c.z);
//							respondToUserInput(KeyCode.PERIOD, '.', false);
//							return;
//						}
//					} else {
//						player.moveTo(x,y,player.z);
//						return;
					}
				}
			}
		};
	}
	
	
}
