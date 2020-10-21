package creatures.ai;

import java.util.List;

import creatures.Creature;
import features.Feature;
import items.Item;
import items.ItemTag;
import tools.Line;
import tools.Path;
import tools.Point;
import world.Tile;

public class CreatureAI {
	protected Creature creature;

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
        int mx = (int)(Math.random() * 3) - 1;
        int my = (int)(Math.random() * 3) - 1;
        creature.moveBy(mx, my, 0);
    }
    
    public void moveTo(int x, int y) {
		List<Point> points = new Path(creature, x, y).points();
		
		if (points == null || points.size() == 0)
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
				&& creature.weapon().rangedAttackValue() > 0
				&& creature.canSee(target.x, target.y, target.z);
	}
	protected boolean canThrowAt(Creature target) {
		return creature.canSee(target.x, target.y, target.z)
				&& getWeaponToThrow() != null;
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
    
    public void onUpdate() { }
    
    public void onNotify(String s) { }
}
