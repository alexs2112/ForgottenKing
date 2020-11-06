package creatures.ai;

import creatures.Ally;
import creatures.Creature;
import creatures.Tag;
import tools.Point;

public class AllyAI extends CreatureAI {
	protected Creature player;
	private Ally creature;

	public AllyAI(Ally creature, Creature player) {
		super(creature);
		this.creature = creature;
		this.player = player;
	}
	
	protected void action() {
		if (creature.is(Tag.ERRATIC) && Math.random()*100 < 40) {
			wander();
			return;
		}
		
		//If the ally can see an enemy, try to kill it
		Creature c = creature.getNearestEnemy();
		if (c != null && !c.is(Tag.ALLY)) {
			if (canRangedWeaponAttack(c))
				creature.fireItem(creature.quiver(), c.x, c.y, c.z);
			else if (canThrowAt(c))
				creature.throwItem(getWeaponToThrow(), c.x, c.y, c.z);
			else if (creature.canSee(c.x, c.y, c.z))
				moveTo(c.x, c.y);
		} else {
			//If there is nothing to kill, your ally follows you
			if (creature.canSee(player.x, player.y, player.z)) {
				lastSeenAt = new Point(player.x, player.y, player.z);
				followSafe(player.x, player.y);
			}
			if (lastSeenAt != null && creature.x == lastSeenAt.x && creature.y == lastSeenAt.y && creature.z == lastSeenAt.z)
				lastSeenAt = null;
			else if (lastSeenAt != null && !creature.canSee(player.x, player.y, player.z)) {
				moveTo(lastSeenAt.x, lastSeenAt.y);
				return;
			}
		}
	}

}

