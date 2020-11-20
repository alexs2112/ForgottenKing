package assembly;

import creatures.Creature;
import creatures.Type;
import spells.Hazard;

@SuppressWarnings("serial")
public final class Hazards {
	public static Hazard smallFire(int strength) {
		Hazard h = new Hazard("Fire", 3, Effects.burning(4, strength), Loader.fire) {
			@Override
			public boolean takeEffect(Creature c) {
				if ((int)(Math.random()*100) + c.getToughness()*8 > 90)
					return false;
				int amount = c.getDamageReceived(3, Type.FIRE);
				c.modifyHP(-amount);
				c.doAction("burn");
				return true;
			}
		};
		h.setDescription("A small fire");
		h.setVariance(3);
		return h;
	}
	public static Hazard poisonCloud(int strength) {
		Hazard h = new Hazard("Poison Cloud", 5, Effects.poisoned(4, strength), Loader.poisonCloud) {
			@Override
			public boolean takeEffect(Creature c) {
				return true;
			}
		};
		h.setDescription("A cloud of poisonous vapours");
		h.setVariance(3);
		return h;
	}
}
