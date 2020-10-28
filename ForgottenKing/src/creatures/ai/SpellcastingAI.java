package creatures.ai;

import creatures.Creature;
import spells.Spell;

public class SpellcastingAI extends BasicAI {
	private int castChance;

	public SpellcastingAI(Creature creature, Creature player, int castChance) {
		super(creature, player);
		this.castChance = castChance;
	}
	
	protected void action() {
		if (Math.random()*100 < castChance && creature.spells() != null && creature.spells().size() > 0) {
			//cast a randomly selected spell
			Spell s = creature.spells().get((int)(Math.random()*creature.spells().size()));
			if (canCastSpell(s, player.x, player.y, player.z)) {
				creature.castSpell(s, player.x, player.y);
				return;
			}
		}
		super.action();
	}

}
