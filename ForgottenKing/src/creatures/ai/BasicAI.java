package creatures.ai;

import creatures.Creature;
import creatures.Tag;

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
