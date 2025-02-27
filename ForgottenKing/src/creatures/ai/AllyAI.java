package creatures.ai;

import creatures.Ally;
import creatures.Creature;
import creatures.Tag;
import spells.Spell;
import tools.Point;

public class AllyAI extends CreatureAI {
	private static final long serialVersionUID = 7769423305067121315L;
	protected Creature player;
	private Ally creature;
	private int castChance;

	public AllyAI(Ally creature, Creature player) {
		super(creature);
		this.creature = creature;
		this.player = player;
	}
	public AllyAI(Ally creature, Creature player, int castChance) {
		this(creature, player);
		this.castChance = castChance;
	}
	
	protected void action() {
		if (creature.is(Tag.ERRATIC) && Math.random()*100 < 40) {
			wander();
			return;
		}
		//If the ally can see an enemy, try to kill it
		Creature c = creature.getNearestEnemy();
		if (c != null) {
			if (creature.is(Tag.SPELLCASTER) && Math.random()*100 < castChance && creature.spells() != null && creature.spells().size() > 0) {
				//cast a randomly selected spell
				Spell s = creature.spells().get((int)(Math.random()*creature.spells().size()));
				if (canCastSpell(s, c.x, c.y, c.z)) {
					creature.castSpell(s, c.x, c.y);
				}
			}
			else if (canRangedWeaponAttack(c))
				creature.fireItem(creature.quiver(), c.x, c.y, c.z);
			else if (canThrowAt(c))
				creature.throwItem(getWeaponToThrow(), c.x, c.y, c.z);
			else if (creature.canSee(c.x, c.y, c.z))
				moveTo(c.x, c.y);
		} else {
			//If there is nothing to kill, your ally follows you
			if (creature.canSee(player.x, player.y, player.z)) {
				destination = new Point(player.x, player.y, player.z);
				followSafe(player.x, player.y);
			}
			if (destination != null && creature.x == destination.x && creature.y == destination.y && creature.z == destination.z)
				destination = null;
			else if (destination != null && !creature.canSee(player.x, player.y, player.z)) {
				moveTo(destination.x, destination.y);
				return;
			}
		}
	}

}

