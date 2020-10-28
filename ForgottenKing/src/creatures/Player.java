package creatures;

import java.util.ArrayList;

import items.Item;
import javafx.scene.image.Image;
import spells.Spell;
import world.World;

public class Player extends Creature {
    public Player(World world, String name, int level, int xp, int hp, int evasion, int armorValue, int baseAttackValue, int baseDamageMin, int baseDamageMax, Image image) {
    	super(world,name,level,xp,hp,evasion,armorValue,baseAttackValue,baseDamageMin,baseDamageMax,image);
    	spells = new ArrayList<Spell>();
    }
    
    private int perkPoints;
    public int perkPoints() { return perkPoints; }
    public void modifyPerkPoints(int x) { perkPoints += x; }
    
    /**
     * INVENTORY STUFF
     */
    private Item lastThrown;
    public Item lastThrown() { return lastThrown; }
    public void throwItem(Item item, int wx, int wy, int wz) {
    	this.lastThrown = item;
    	super.throwItem(item, wx, wy, wz);
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
