package creatures.ai;

import creatures.Creature;
import creatures.Tag;
import tools.Point;

public class BasicAI extends CreatureAI {
	protected Creature player;

	public BasicAI(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}

	protected void action() {
		isWandering = false;	//Always sets to false, then checks to see if it really is wandering
		if (creature.canSee(player.x, player.y, player.z))
			lastSeenAt = new Point(player.x, player.y, player.z);
		
		//Erratic creatures randomly wander on their turn 40% of the time
		if (creature.is(Tag.ERRATIC) && Math.random()*100 < 40) {
			wander();
			if (lastSeenAt == null && getNearestEnemy() == null)
				isWandering = true;
			return;
		}
		
		//If the creature can see an enemy, try to kill it
		Creature c = getNearestEnemy();
		if (c != null) {
			if (canRangedWeaponAttack(c))
				creature.fireItem(creature.quiver(), c.x, c.y, c.z);
			else if (canThrowAt(c))
				creature.throwItem(getWeaponToThrow(), c.x, c.y, c.z);
			else if (creature.canSee(c.x, c.y, c.z))
				moveTo(c.x, c.y);
		} else {
			//Make the creatures follow the player around so they dont lose interest
			if (lastSeenAt != null && creature.x == lastSeenAt.x && creature.y == lastSeenAt.y && creature.z == lastSeenAt.z)
				lastSeenAt = null;
			else if (lastSeenAt != null && !creature.canSee(player.x, player.y, player.z)) {
				moveTo(lastSeenAt.x, lastSeenAt.y);
			} else {
				wander();
				isWandering = true;
			}
		}
	}
	
	public Creature getNearestEnemy() {
		Creature nearest = null;
		int distance = 100;
		for (int x = creature.x - creature.visionRadius(); x <= creature.x + creature.visionRadius(); x++) {
			for (int y = creature.y - creature.visionRadius(); y <= creature.y + creature.visionRadius(); y++) {
				if (x < 0 || x >= creature.world().width() || y < 0 || y >= creature.world().height())
					continue;
				Creature c = creature.creature(x,y,creature.z);
				if (c != null && c != creature && (c.is(Tag.PLAYER) || c.is(Tag.ALLY))) {
					if (nearest == null) {
						nearest = c;
						distance = Math.max(Math.abs(creature.x - c.x), Math.abs(creature.y - c.y));
						continue;
					}
					if (Math.max(Math.abs(creature.x - c.x), Math.abs(creature.y - c.y)) < distance) {
						nearest = c;
						distance = Math.max(Math.abs(creature.x - c.x), Math.abs(creature.y - c.y));
					}
				}
			}
		}
		return nearest;
	}

}
