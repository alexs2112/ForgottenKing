package creatures;

import java.util.ArrayList;
import java.util.List;

import assembly.Abilities;
import audio.Audio;
import items.Item;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import spells.Spell;
import tools.Line;
import tools.Point;
import world.World;

public class Player extends Creature {
	private static final long serialVersionUID = 7769423305067121315L;
    public Player(World world, String name, int level, int xp, int hp, int evasion, int armorValue, int baseAttackValue, int baseDamageMin, int baseDamageMax, Image image) {
    	super(world,name,level,xp,hp,evasion,armorValue,baseAttackValue,baseDamageMin,baseDamageMax,image);
    	spells = new ArrayList<Spell>();
    	abilities = new ArrayList<Ability>();
    	hotkeys = new Hotkey[10];
    }
    
    /**
     * FLAVOUR STUFF
     */
    private Image largeIcon;
    public Image largeIcon() { return largeIcon; }
    public void setLargeIcon(Image x) { largeIcon = x; }
    private String title;
    public String title() { return title; }
    public void setTitle(String s) { title = s; }
    
    /**
     * PERK POINTS
     */
    private int perkPoints;
    public int perkPoints() { return perkPoints; }
    public void modifyPerkPoints(int x) { perkPoints += x; }
    
    /**
     * LEVEL POINTS
     */
    private int statPoints;
    public int statPoints() { return statPoints; }
    public void modifyStatPoints(int x) { statPoints += x; }
    private int attributePoints;
    public int attributePoints() { return attributePoints; }
    public void modifyAttributePoints(int x) { attributePoints += x; }
    private void levelUp() {
    	notify("You level up!", Color.GOLD);
    	modifyXP(-nextLevelXP());
		modifyLevel(1);
		int x = (int)(Math.random() * 6) + 1;
		modifyMaxHP(x);
		modifyHP(x);
		if (level() % 4 == 0)
			modifyPerkPoints(1);
		if (level() % 3 == 0)
			modifyAttributePoints(1);
		else
			modifyStatPoints(1);
    }
    public void modifyXP(int x) {
    	super.modifyXP(x);
    	if (xp() > nextLevelXP())
    		levelUp();
    }
    
    /**
     * MAGIC
     */
    public int totalSpellSlots() { return getWill() + level(); }
    public int remainingSpellSlots() {
    	int i = 0;
    	for (Spell s : spells()) {
    		i += s.level();
    	}
    	return totalSpellSlots() - i;
    }
    public void addSpell(Spell newSpell) {
		if (remainingSpellSlots() >= newSpell.level()) {
			super.addSpell(newSpell);
		} else {
			notify("You don't have enough spell slots!");
		}
	}
    private int meditating;
    public int meditating() { return meditating; }
    public void modifyMeditating(int x) {
    	meditating += x;
    	if (meditating <= 0)
    		doneMeditating();
    }
    public void stopMeditating() {
    	meditating = 0;
    }
    private int[] magicChanges;
    public void meditate(int[] a) {
    	for (int i = 0; i < a.length; i++)
    		meditating += 8 * Math.abs(a[i]);
    	magicChanges = a;
    }
    public void doneMeditating() {
    	if (magicChanges != null && magicChanges.length > 5) {
    		magic.modify(Type.FIRE, magicChanges[0]);
    		magic.modify(Type.COLD, magicChanges[1]);
    		magic.modify(Type.AIR, magicChanges[2]);
    		magic.modify(Type.POISON, magicChanges[3]);
    		magic.modify(Type.LIGHT, magicChanges[4]);
    		magic.modify(Type.DARK, magicChanges[5]);
    	}
    }
    public boolean canCastSpell(Spell spell) {
    	if (spell.level() > magic().get(spell.type())) {
			notify("Your " + spell.type().text() + " skill is not high enough to cast " + spell.name());
			return false;
		} 
    	if (spell.cost() > mana()) {
			notify("You don't have enough mana to cast " + spell.name());
			return false;
		}
		return true;
    }
    
    /**
     * INVENTORY
     */
    public double carryWeight() { return 17 + getBrawn(); }
    public void pickup(Item item) {
    	if (item != null && item.weight() + inventory().totalWeight() > carryWeight()) {
    		notify("You can't carry more weight.");
    		return;
    	}
    	super.pickup(item);
    }
    
    /**
     * MEMORY STUFF
     */
    private Item lastThrown;
    public Item lastThrown() { return lastThrown; }
    public void throwItem(Item item, int wx, int wy, int wz) {
    	this.lastThrown = item;
    	super.throwItem(item, wx, wy, wz);
    }
    private Spell lastCast;
    public Spell lastCast() { return lastCast; }
    
    @Override
    public void castSpell(Spell spell, List<Point> points, List<Point> allPoints) {
    	super.castSpell(spell, points, allPoints);
    	lastCast = spell;
    }
    private Ability lastActivated;
    public Ability lastActivated() { return lastActivated; }
    public void activateAbility(Ability a, List<Point> points) {
    	super.activateAbility(a, points);
    	lastActivated = a;
    }
	
	/**
	 * AUTOPILOT
	 */
	private boolean resting;
    public boolean resting() { return resting; }
    public void setResting(boolean x) { resting = x; }
    
    public boolean creatureInSight() {
    	for (int x = this.x - visionRadius(); x <= this.x + visionRadius(); x++) {
    		for (int y = this.y - visionRadius(); y <= this.y + visionRadius(); y++) {
    			if (x < 0 || x >= world().width() || y < 0 || y >= world().height())
    				continue;
    			if (creature(x, y, z) != null && creature(x,y,z) != this && !creature(x,y,z).is(Tag.ALLY))
    				return true;
    		}
    	}
    	return false;
    }
    public Audio songToPlayByEnemy() {
    	Audio a = Audio.MAIN;
    	for (int x = this.x - visionRadius(); x <= this.x + visionRadius(); x++) {
    		for (int y = this.y - visionRadius(); y <= this.y + visionRadius(); y++) {
    			if (x < 0 || x >= world().width() || y < 0 || y >= world().height())
    				continue;
    			Creature c = creature(x,y,z);
    			if (c != null && c != this && !c.is(Tag.ALLY)) {
    				if (c.is(Tag.LEGENDARY))
    					a = Audio.BOSS;
    				else
    					a = Audio.COMBAT;
    			}
    		}
    	}
    	return a;
    }
    public int distanceTo(int x, int y) {
    	return new Line(this.x, this.y, x, y).getPoints().size();
    }
    
    /**
     * COMBAT
     */
    public boolean canFire(int x, int y) {
    	if (weapon() == null || weapon().rangedAttackValue() == 0) {
			notify("You don't have a ranged weapon equipped");
			return false;
    	} else  if (quiver() == null) {
			notify("You are out of ammo");
			return false;
    	}
    	if (x != -1 && y != -1) {
    		for (Point p : new Line(this.x,this.y,x,y))
    			if (p.x != x && p.y != y && creature(p.x,p.y,z) != null && creature(p.x,p.y,z) != this) {
    				notify("There is a " + creature(p.x,p.y,z).name() + " in your line of fire!");
    				return false;
    			}
    	}
		return true;
    }
    public int meleeRange() {
    	int r = 1;
    	if (abilities().contains(Abilities.reach()))
			r++;
    	else if (abilities().contains(Abilities.improvedReach()))
			r+=2;
    	return r;
    }
    public Creature getNearestEnemy() {
		Creature nearest = null;
		int distance = 100;
		for (int x = this.x - visionRadius(); x <= this.x + visionRadius(); x++) {
			for (int y = this.y - visionRadius(); y <= this.y + visionRadius(); y++) {
				if (x < 0 || x >= world().width() || y < 0 || y >= world().height())
					continue;
				Creature c = creature(x,y,z);
				if (c != null && c != this && !c.is(Tag.ALLY)) {
					if (nearest == null) {
						nearest = c;
						distance = Math.max(Math.abs(this.x - c.x), Math.abs(this.y - c.y));
						continue;
					}
					if (Math.max(Math.abs(this.x - c.x), Math.abs(this.y - c.y)) < distance) {
						nearest = c;
						distance = Math.max(Math.abs(this.x - c.x), Math.abs(this.y - c.y));
					}
				}
			}
		}
		return nearest;
	}
    
    @Override
    public Point getAutoTarget() {
    	if (lastAttacked() == null || lastAttacked() == this)
    		setLastAttacked(getNearestEnemy());
    	System.out.println(lastAttacked());
    	return super.getAutoTarget();
    }
    
    
    /**
     * HOTBAR
     */
    private Hotkey[] hotkeys;
    public void setHotkey(int index, Hotkey k) { hotkeys[index] = k; }
    public Hotkey hotkey(int index) { return hotkeys[index]; }
    public void removeHotkey(Hotkey k) {
    	for (int i = 0; i < hotkeys.length; i++) {
    		if (hotkeys[i] == k)
    			hotkeys[i] = null;
    	}
    }
}
