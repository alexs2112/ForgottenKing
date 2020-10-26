package assembly;

import creatures.Ability;
import creatures.Creature;
import items.ItemTag;
import spells.TargetType;

public class Abilities {
	public static Ability rage() {
		Ability a = new Ability("Rage", null, 20) {
			public void activate(Creature owner, Creature other) {
				if (owner.hasItemTag(ItemTag.MEDIUMARMOR) || owner.hasItemTag(ItemTag.HEAVYARMOR)) {
					owner.notify("You cannot activate this ability while wearing medium or heavy armor.");
					this.setTime(0);
					return;
				}
				owner.addEffect(Effects.raging());
			}
		};
		a.setTargetType(TargetType.SELF);
		a.setDescription("You fly into a berserk rage, giving you massively increased strength for a time.");
		return a;
	}
	public static Ability reach() {
		Ability a = new Ability("Reach Attack", null, 0) {
			public void activate(Creature owner, Creature other) {
				owner.attack(other);
			}
		};
		a.setTargetType(TargetType.PROJECTILE);
		a.setDescription("You can attack enemies two tiles away.");
		a.setRange(2);
		return a;
	}
}
