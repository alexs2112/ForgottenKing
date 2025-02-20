package screens;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
import features.Portal;
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
import javafx.stage.Stage;
import spells.Effect;
import tools.DrawMinimap;
import tools.FieldOfView;
import tools.Icon;
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
    transient private Screen subscreen;
    private boolean devMode = false;
    //private String hotkeyNumbers = "1234567890";
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
        itemFactory = new ItemFactory();
        creatureFactory = new CreatureFactory(world, itemFactory);
        itemFactory.setCreatureFactory(creatureFactory);
        setUpPlayer(character);
        world.populate(creatureFactory, itemFactory);
        prepareButtons();
    }
    private void createWorld(){
        world = new WorldBuilder(82, 39, 10)
        		//new WorldBuilder(90,55,2)	//For screenshots of maps
        	  .makeDungeon()
              .build();
    }
    
	private void setUpPlayer(ClassSelection character) {
		player = creatureFactory.newPlayer(messages, 0, fov, character);
		player.setBloodstone(5, 5);
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
        	player.addEquipment(itemFactory.weapon().newDevSword());
        	player.addEquipment(itemFactory.armor().newDevBreastplate());
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
		displaySpaceText();
		DrawMinimap.draw(root, player, 1030, 269);
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
	            			if (i.effectIcon() != null)
	            	    		draw(root, i.effectIcon().image(), x*32, y*32);
	            			if (world.items(wx,wy,player.z).numberOfItems() > 1)
	            				draw(root, Loader.multi_item_icon.image(), x*32, y*32);
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
	            				if (i.effectIcon() != null)
	            					draw(root, i.effectIcon().image(), x*32, y*32, -0.7);
	            				if (world.items(wx,wy,player.z).numberOfItems() > 1)
	            					draw(root, Loader.multi_item_icon.image(), x*32, y*32, -0.7);
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
        		
        		if (world.tile(c.x, c.y, c.z).isWater() && !c.is(Tag.FLYING))
        			drawWaterOverlay(root, world.tileImage(c.x, c.y, c.z), (c.x-left)*32, (c.y-top)*32);
        		if (healthbar != null)
        			draw(root, healthbar, (c.x-left)*32, (c.y-top)*32);
        		if (c.weapon() != null && !c.is(Tag.PLAYER))
        			draw(root, Loader.armedEnemyIcon.image(), (c.x-left)*32 + 24, (c.y-top)*32);
        		if (c.isWandering())
        			draw(root, Loader.wanderingEnemyIcon.image(), (c.x-left)*32 + 16, (c.y-top)*32+16);
        		HashMap<String, Color> h = c.getStatements();
        		c.clearStatements();
        		if (h != null) {
        			int i = 0;
        			for (String s : h.keySet()) {
        				writeCentered(root, s, (c.x-left)*32+12, (c.y-top)*32-2-(14*i), font14, h.get(s));
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
		if (player.hp() < 1) {
			deleteSave();
		    return new LoseScreen(root);
		}
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
    		else if (c == 'r' && shift)
    			playerRest();
    		else if (c == 'r' && !shift)
    			subscreen = new ReadScreen(player);
    		else if (c == 's' && !shift)
    			subscreen = new StatsScreen(player);
    		else if (c == 's' && shift)
    			serialize();
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
    			if (world.feature(player.x, player.y, player.z) != null && world.feature(player.x, player.y, player.z).type() == Feature.Type.DOWNSTAIR)
    				world.feature(player.x,player.y, player.z).interact(player, world, player.x, player.y, player.z);
    		} else if (shift && code.equals(KeyCode.COMMA)) {
    			if (world.feature(player.x, player.y, player.z) != null && world.feature(player.x, player.y, player.z).type() == Feature.Type.UPSTAIR) {
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
    			} else if (f != null && (f.type() == Feature.Type.DOWNSTAIR || f.type() == Feature.Type.UPSTAIR)) {
    				if (world.feature(player.x, player.y, player.z).name().equals("Entrance"))
    					return new LeaveScreen(player);
    				f.interact(player, world, player.x, player.y, player.z);
    			} else if (nearbyDoors()) {
    				closeDoor();
    			} else {
    				//Wait 1 turn
    			}
    		} else if (code.equals(KeyCode.ESCAPE))
    			subscreen = new PauseScreen(this);
//    		else if (hotkeyNumbers.indexOf(c) != -1)
//    			return player.hotkey(hotkeyNumbers.indexOf(c)).use(root, player, getScrollX(), getScrollY());
    		else if (c == '0' && devMode && shift)
    			player.fillHP();
    		else if (c == '9' && devMode && shift)
    			player.modifyXP(player.nextLevelXP());
    		else if (c == '8' && devMode && shift)
    			world.setFeature(new Portal(), player.x, player.y, player.z);
    		else if (c == '7' && devMode && shift)
    			subscreen = new DevMapScreen(world, player.z);
    		else if (c == '6' && shift)
    			serialize();
    		else
    			endAfterUserInput = false;
    	}
    	//This is a gimpy way to fix exiting out of menus
    	if (code.equals(KeyCode.ESCAPE))
    		endAfterUserInput = false;
    	
    	if (player.hasWon)
    		return new LeaveScreen(player);
    	
		if (endAfterUserInput && subscreen == null) {
			player.update();
			if (player.time() <= 0) //If the players action did not modify their time, set their time to their movement delay
				player.modifyTime(player.getMovementDelay());
			while (player.time() > 0) {
				world.update(player.z);
				player.modifyTime(-1);
			}
			if (player.hp() < player.maxHP()/4 && player.resting <= 0)
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
    			if (feat != null && feat.type() == Feature.Type.CANCLOSE) {
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
	private boolean nearbyDoors() {
		for (int wx = -1; wx < 2; wx++)
    		for (int wy = -1; wy < 2; wy++) {
    			if (wx == 0 && wy == 0)
    				 continue;
    			Feature feat = world.feature(player.x - wx, player.y - wy, player.z);
    			if (feat != null && feat.type() == Feature.Type.CANCLOSE) {
    				//A potential bug here if there are two doors, one with a dude and one without
    				if (world.creature(player.x-wx, player.y-wy, player.z) != null)
   					 	return false;
    				return true;
    			}
    		}
		return false;
	}
	
    private void displayStats() {
    	draw(root, Loader.playerUIFull.image(), 1024, 0);
        writeCentered(root, "HP: " + player.hp() + "/" + player.maxHP(), 1150, 33, font18, Color.RED);
        writeCentered(root, "MP: " + player.mana() + "/" + player.maxMana(), 1118, 76, font18, Color.BLUE);
        if (player.bloodstone() > 0)
        	writeCentered(root, "" + player.bloodstone(), 1236, 76, font16, Color.ORANGE);
        else
        	writeCentered(root, "0", 1236, 76, font16, Color.ORANGERED);
        writeCentered(root, "XP: " + player.xp() + "/" + player.nextLevelXP(), 1150, 113, font14, Color.YELLOW);
        int y = 154;
        writeCentered(root, ""+player.movementDelay(), 1110, y, font16, Color.WHITE);
        writeCentered(root, ""+player.attackDelay(), 1199, y, font16, Color.WHITE);
        y += 48;
        writeCentered(root, ""+player.evasion(), 1102, y, font16, Color.WHITE);
        writeCentered(root, ""+player.armorValue(), 1201, y, font16, Color.WHITE);
        
        if (player.weapon() != null && player.weapon().isRanged())
        	writeCentered(root, "+" + player.getCurrentRangedAttackValue() + " [" + (player.getCurrentRangedDamage()[0]) + "-" + (player.getCurrentRangedDamage()[1]) + "]", 
                	1170, y += 48, font16, Color.WHITE);
        else
        	writeCentered(root, "+" + player.getCurrentAttackValue() + " [" + (player.getMinDamage()+player.getCurrentDamageMod()) + "-" + (player.getMaxDamage()+player.getCurrentDamageMod()) + "]", 
        			1170, y += 48, font16, Color.WHITE);
        
        if (player.weapon() != null)
        	for (ItemTag t : player.weapon().tags())
        		if (t.isWeapon() && t.image() != null)
        			draw(root, t.image(), 1033, 224);

        int num = 0;
        for (Item i : player.equipment().values()) {
        	draw(root, Loader.equipmentBox.image(), 1024, 430 + 36*num);
        	draw(root, i.image(), 1026, 432 + 36*num);
			if (i.effectIcon() != null)
	    		draw(root, i.effectIcon().image(), 1026, 432 + 36*num);
        	write(root, i.shortDesc(player), 1066, 456 + 36*num, font14, Color.ANTIQUEWHITE);
        	num++;
        	if (player.quiver() != null && i.isRanged()) {
        		draw(root, Loader.equipmentBoxBlue.image(), 1024, 430 + 36*num);
            	draw(root, player.quiver().image(), 1026, 432 + 36*num);
    			if (i.effectIcon() != null)
    	    		draw(root, player.quiver().effectIcon().image(), 1026, 432 + 36*num);
            	write(root, "[" + player.inventory().quantityOf(player.quiver()) + "] " + player.quiver().shortDesc(player), 1066, 456 + 36*num, font14, Color.ANTIQUEWHITE);
            	num++;
        	}
        }
        num = 0;
        for (Effect e : player.effects()) {
        	draw(root, Loader.effectBox.image(), 748, num * 48);
        	if (e.image() != null)
        		draw(root, e.image(), 758, num*48 + 9);
        	write(root, e.name(), 798, num * 48 + 34, font16, Color.WHITE);
        	writeCentered(root, "" + e.duration(), 999, num*48 + 34, font16, Color.WHITE);
        	num++;
        }
        int notificationY = 0;
        if (player.magic().floatingPoints() < 0) {
        	String s = "[m]: Overloaded Spell Points!";
        	write(root, s, 8, notificationY+=24, font16, Color.RED);
        }
        if (player.magic().floatingPoints() > 0) {
        	String s = "[m]: "+player.magic().floatingPoints()+" Free Spell Point";
        	if (player.magic().floatingPoints() > 1)
        		s += "s";
        	write(root, s, 8, notificationY+=24, font16, Color.AQUA);
        }
        if (player.perkPoints() > 0) {
        	String s = "[p]: "+player.perkPoints()+" Perk Point";
        	if (player.perkPoints() > 1)
        		s += "s";
        	s += " Available!";
        	write(root, s, 8, notificationY+=24, font16, Color.AQUA);
        }
        if (player.attributePoints() > 0) {
        	String s = "[s]: "+player.attributePoints()+" Attribute Point";
        	if (player.attributePoints() > 1)
        		s += "s";
        	s += " Available!";
        	write(root, s, 8, notificationY+=24, font16, Color.AQUA);
        }
        if (player.statPoints() > 0) {
        	String s = "[s]: "+player.statPoints()+" Stat Point";
        	if (player.attributePoints() > 1)
        		s += "s";
        	s += " Available!";
        	write(root, s, 8, notificationY+=24, font16, Color.AQUA);
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
    			return Loader.poisonedHealthBarFull.image();
    		else if (burning)
    			return Loader.burningHealthBarFull.image();
    		return null;
    	} if (creature.hp() > 4*creature.maxHP()/5) {
    		if (poisoned)
    			return Loader.poisonedHealthBarFull.image();
    		else if (burning)
    			return Loader.burningHealthBarFull.image();
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarFull.image();
    		return Loader.healthBarFull.image();
    	} else if (creature.hp() > 3 * (creature.maxHP()/5)) {
    		if (poisoned)
    			return Loader.poisonedHealthBarThreeQuarter.image();
    		else if (burning)
    			return Loader.burningHealthBarThreeQuarter.image();
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarThreeQuarter.image();
    		return Loader.healthBarThreeQuarter.image();
    	} else if (creature.hp() > 1*creature.maxHP()/4) {
    		if (poisoned)
    			return Loader.poisonedHealthBarHalf.image();
    		else if (burning)
    			return Loader.burningHealthBarHalf.image();
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarHalf.image();
    		return Loader.healthBarHalf.image();
    	} else {
    		if (poisoned)
    			return Loader.poisonedHealthBarQuarter.image();
    		else if (burning)
    			return Loader.burningHealthBarQuarter.image();
    		if (creature.is(Tag.ALLY))
    			return Loader.allyHealthBarQuarter.image();
    		return Loader.healthBarQuarter.image();
    	}
    }
    
    private void displayMessages(List<Message> messages) {
    	if (messages.size() == 0)
    		return;
    	int messageHeight = 24;
    	int top = 760 - messages.size() * messageHeight;
    	int i = 0;
    	if (spaceText() != null)
    		top -= messageHeight;
    	for (Message m : messages) {
    		write(root, m.message(), 10, top+i*messageHeight, font14, m.colour());
    		i++;
    	}
    }
	
	//A method that repeats the last key press until the player is done resting
	private void playerRest() {
		if (player.hp() >= player.maxHP()) {
			player.notify("You are already at full health!");
			return;
		}
		
		if (player.creatureInSight()) {
			if (player.resting == 0)
				player.notify("You can't rest now, there are enemies in view!");
			else {
				player.notify("Enemies interrupt your rest!");
				player.resting = 0;
			}
			return;
		}

		if (player.resting == 0)
			player.resting = 10;
		if (player.resting > 0) {
			player.resting--;
			if (player.resting > 0)
				nextCommand = new KeyBoardCommand(KeyCode.R, 'r', true);
		}
		if (player.resting <= 0) {
			player.modifyBloodstone(-1);
			if (player.bloodstone() < 0) {
				world.createBloodstoneEnemies(player, creatureFactory);
				player.notify("The power of the bloodstone has drawn enemies to you.");
			}
			player.modifyHP(player.maxHP() * 3 / 4);
			player.notify("Finished Resting!");
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
	
	//Display the correct [space] text under current messages if the player has actions
	private String spaceText() {
		if (player.hp() <= 0)
			return null;
		//Handle picking up items
		items.Inventory i = world.items(player.x,player.y, player.z);
		if (i != null) {
			if (i.getUniqueItems().size() > 1)
				return "[g/space]: pick up items.";
			else if (i.getFirstItem() != null)
				return "[g/space]: pick up " + i.getFirstItem().name() + ".";
		}
		
		//Handle traversing staircases
		Feature f = world.feature(player.x, player.y, player.z);
		if (f != null && f.type() == Feature.Type.DOWNSTAIR) {
			if (world.feature(player.x, player.y, player.z).name().equals("Entrance"))
				return "[</space]: exit the dungeon.";
			return "[</space]: go up the staircase.";
		} else if (f != null && f.type() == Feature.Type.DOWNSTAIR)
			return "[>/space]: go down the staircase.";
		
		//Handle closing nearby doors
		if (nearbyDoors())
    		return "[space]: close nearby doors.";
		
		return null;
	}
	private void displaySpaceText() {
		String s = spaceText();
		if (s == null)
			return;
		write(root, s, 10, 732, font14, Color.WHITE);
	}
	
	
	//Button Handling can go all the way down here
	private boolean[] mouseOverButtons = new boolean[12];
	private List<Icon> buttonIcons;
	private List<Icon> buttonIconsSelected;
	private List<String> tooltips;
	private void handleButtons() {
		draw(root, Loader.buttonBar.image(), 0, 756);
		for (int i = 0; i < 11; i++) {
			Image image = buttonIcons.get(i).image();
			if (mouseOverButtons[i])
				image = buttonIconsSelected.get(i).image();
			draw(root, image, 40*i, 760, setMouseClick(i), setMouseOver(i, true), setMouseOver(i, false));
		}
		//A special case for swapping weapons
		int i = 11;
		Image image = buttonIcons.get(i).image();
		if (mouseOverButtons[i])
			image = buttonIconsSelected.get(i).image();
		draw(root, image, 40*i, 760, setMouseClick(i), setMouseOver(i, true), setMouseOver(i, false)); 
		if (player.lastWielded() != null) {
			for (ItemTag t : player.lastWielded().tags())
				if (t.isWeapon() && t.image() != null) {
					draw(root, t.image(), 40*i+4, 764, setMouseClick(i), setMouseOver(i, true), setMouseOver(i, false));
					break;
				}
		}
	}
	private void prepareButtons() {
		buttonIcons = new ArrayList<Icon>();
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
		buttonIconsSelected = new ArrayList<Icon>();
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
			writeCentered(root, tooltip, mouseSX, mouseSY-32, font14, Color.WHITE);
		if (mouseX == -1 || mouseY == -1)
			return;
		Image i = Loader.yellowSelection.image();
		if (player.creature(left+mouseX, top+mouseY, player.z) != null && player.creature(left+mouseX, top+mouseY, player.z) != player)
			i = Loader.redSelection.image();
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
	
	
	
	//Saving the game!
	public void serialize() {
		try {
			FileOutputStream fileOut = new FileOutputStream("savegame.ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.println("Serialized data is saved in savegame.ser");
			player.notify("Game Saved!", Color.GOLD);
		} catch (IOException i) {
			i.printStackTrace();
			player.notify("Error: Game Failed to Save!", Color.RED);
		}
	}
	public static void deleteSave() {
		File f = new File("savegame.ser");
		if (f.delete())
			System.out.println("Savegame deleted!");
		else
			System.out.println("Savegame failed to delete");
	}
	
}
