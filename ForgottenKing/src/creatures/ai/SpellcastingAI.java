package creatures.ai;

import creatures.Creature;
import spells.Spell;

public class SpellcastingAI extends BasicAI {
	private int castChance;

	public SpellcastingAI(Creature creature, Creature player, int castChance) {
		super(creature, player);
		this.castChance = castChance;
	}
	
	public void onUpdate() {
		if (creature.spells() != null && creature.spells().size() > 0 && 
				Math.random()*100 < castChance && creature.canSee(player.x, player.y, player.z)) {
			//cast a randomly selected spell
			Spell s = creature.spells().get((int)(Math.random()*creature.spells().size()));
			if (s.cost() <= creature.mana()) {
				creature.castSpell(s, player.x, player.y);
				return;
			}
		}
		action();
	}

}
