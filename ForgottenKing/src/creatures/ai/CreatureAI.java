package creatures.ai;

import java.util.List;

import creatures.Creature;
import creatures.Tag;
import features.Feature;
import items.Item;
import items.ItemTag;
import spells.Spell;
import spells.TargetType;
import tools.Line;
import tools.Path;
import tools.Point;
import world.Tile;

public class CreatureAI {
	protected Creature creature;
	protected Point lastSeenAt;

    public CreatureAI(Creature creature){
        this.creature = creature;
        this.creature.setCreatureAI(this);
    }
    
    public boolean canSee(int wx, int wy, int wz) {
        if (creature.z != wz)
            return false;
    
        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;
    
        for (Point p : new Line(creature.x, creature.y, wx, wy)){
        	if (creature.world().feature(p.x, p.y, wz) != null && creature.world().feature(p.x, p.y, wz).blockLineOfSight() == true)
        		return false;
            if (creature.realTile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
                continue;
        
            return false;
        }
    
        return true;
    }

    public void onEnter(int x, int y, int z, Tile tile){
    	Feature feat = creature.world().feature(x, y, z);
		if (feat != null && feat.blockMovement()) {
			creature.doAction("bump into a " + feat.name().toLowerCase());
			return;
		}
        if (tile.isGround()){
             creature.x = x;
             creature.y = y;
             creature.z = z;
        } else {
             creature.doAction("bump into a wall");
        }
    }
    
    public void wander(){
    	for (int i = 0; i < 50; i++) {
    		int mx = (int)(Math.random() * 3) - 1;
    		int my = (int)(Math.random() * 3) - 1;
    		if (creature.canEnter(creature.x+mx,creature.y+my,creature.z)) {
    			creature.moveBy(mx, my, 0);
    			return;
    		}
    	}
    }
    public void confusedWander(){
    	int mx = (int)(Math.random() * 3) - 1;
    	int my = (int)(Math.random() * 3) - 1;
    	creature.moveBy(mx, my, 0);
    	return;
    }
    
    public void moveTo(int x, int y) {
    	List<Point> points = new Path(creature, x, y).points();
		if (points == null || points.size() == 0)
			return;
		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;
		creature.moveBy(mx, my, 0);
	}
    
    /**
     * A method that makes an ally follow the player safely if they can see them, move towards the player but dont hit the player
     */
    public void followSafe(int x, int y) {
    	List<Point> points = new Path(creature, x, y).points();
		if (points == null || points.size() == 0)
			return;
		Creature c = creature.creature(points.get(0).x, points.get(0).y, creature.z);
		if (c != null && (c.is(Tag.PLAYER) || c.is(Tag.ALLY)))
			return;
		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;
		creature.moveBy(mx, my, 0);
	}
    
    public Tile rememberedTile(int wx, int wy, int wz) {
    	return Tile.UNKNOWN;
    }
    
    protected boolean canRangedWeaponAttack(Creature target) {
		return creature.weapon() != null 
				&& creature.weapon().isRanged()
				&& creature.quiver() != null
				&& !adjacentTo(target.x, target.y)
				&& canDrawLine(target.x, target.y, target.z);
	}
	protected boolean canThrowAt(Creature target) {
		return canDrawLine(target.x, target.y, target.z)
				&& getWeaponToThrow() != null
				&& !adjacentTo(target.x, target.y);
	}
	protected Item getWeaponToThrow() {
		for (Item item : creature.inventory().getUniqueItems()) {
			if (item == null || creature.weapon() == item)
				continue;
			if (item.is(ItemTag.THROWING))
				return item;
		}
		return null;
	}
    
	protected boolean canDrawLine(int x, int y, int z) {
		if (!creature.canSee(x,y,z))
			return false;
		for (Point p : new Line(creature.x, creature.y, x, y)) {
			if (!creature.realTile(p.x, p.y, z).isGround() ||
				(creature.world().feature(p.x, p.y, z) != null && creature.world().feature(p.x, p.y, z).blockMovement()) ||
				(creature.creature(p.x, p.y, z) != null && p.x != x && p.y != y && creature.creature(p.x, p.y, z) != creature)) 
				return false;
		}
		return true;
	}
	protected boolean adjacentTo(int x, int y) {
		return new Line(creature.x, creature.y, x, y).getPoints().size() <= 2;
	}
	protected boolean canCastSpell(Spell spell, int x, int y, int z) {
		if (spell.cost() > creature.mana() || !inRange(spell.range(), x, y))
			return false;
		if (spell.targetType() == TargetType.PROJECTILE ||
			spell.targetType() == TargetType.BEAM) {
			return canDrawLine(x,y,z);
		} else {
			return creature.canSee(x, y, z);
		}
	}
	
	protected boolean inRange(int range, int wx, int wy) {
		if (range == 0)
			return true;
		return new Line(creature.x, creature.y, wx, wy).getPoints().size() <= range;
	}
	
    public void onUpdate() { 
    	if (creature.isStunned())
    		return;
    	if (creature.isConfused()) {
			confusedWander();
			return;
		}
    	action();
    }
    
    public void onNotify(String s) { }
    
    protected void action() { }
}
