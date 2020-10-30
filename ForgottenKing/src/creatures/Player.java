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
     * PERK POINTS
     */
    private int perkPoints;
    public int perkPoints() { return perkPoints; }
    public void modifyPerkPoints(int x) { perkPoints += x; }
    
    /**
     * SPELL SLOTS
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
