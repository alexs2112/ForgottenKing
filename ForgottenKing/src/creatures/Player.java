package creatures;

import java.util.ArrayList;
import java.util.List;

import items.Item;
import javafx.scene.image.Image;
import spells.Spell;
import tools.Point;
import world.World;

public class Player extends Creature {
    public Player(World world, String name, int level, int xp, int hp, int evasion, int armorValue, int baseAttackValue, int baseDamageMin, int baseDamageMax, Image image) {
    	super(world,name,level,xp,hp,evasion,armorValue,baseAttackValue,baseDamageMin,baseDamageMax,image);
    	spells = new ArrayList<Spell>();
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
    	notify("You level up!");
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
    public void castSpell(Spell spell, List<Point> points) {
    	super.castSpell(spell, points);
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
    	for (int x = this.x - visionRadius(); x < this.x + visionRadius(); x++) {
    		for (int y = this.y - visionRadius(); y < this.y + visionRadius(); y++) {
    			if (x < 0 || x >= world().width() || y < 0 || y >= world().height())
    				continue;
    			if (creature(x, y, z) != null && creature(x,y,z) != this && !creature(x,y,z).is(Tag.ALLY))
    				return true;
    		}
    	}
    	return false;
    }

}
