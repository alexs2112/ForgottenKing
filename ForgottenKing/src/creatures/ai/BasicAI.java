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
	
	public void onUpdate() {
		action();
	}
	
	protected void action() {
		//Make the creatures follow the player around so they dont lose interest
		if (creature.canSee(player.x, player.y, player.z))
			lastSeenAt = new Point(player.x, player.y, player.z);
		if (lastSeenAt != null && creature.x == lastSeenAt.x && creature.y == lastSeenAt.y && creature.z == lastSeenAt.z)
			lastSeenAt = null;
		else if (lastSeenAt != null && !creature.canSee(player.x, player.y, player.z)) {
			moveTo(lastSeenAt.x, lastSeenAt.y);
			return;
		}
		
		//Erratic creatures randomly wander on their turn 40% of the time
		if (creature.is(Tag.ERRATIC) && Math.random() < 40) {
			wander();
			return;
		}
		
		
		if (canRangedWeaponAttack(player))
			creature.fireItem(creature.quiver(),player.x, player.y, player.z);
		else if (canThrowAt(player))
			creature.throwItem(getWeaponToThrow(), player.x, player.y, player.z);
		else if (creature.canSee(player.x, player.y, player.z))
			moveTo(player.x, player.y);
		else
			wander();
	}

}
