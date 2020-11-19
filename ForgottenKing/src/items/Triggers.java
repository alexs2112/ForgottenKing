package items;

import assembly.CreatureFactory;
import assembly.Effects;
import assembly.Hazards;
import assembly.Spells;
import creatures.Creature;
import creatures.Tag;
import creatures.Type;
import spells.Effect;

public class Triggers {
	
	/**
	 * MISC TRIGGERS
	 */
	public static Trigger applyEffect(Effect e, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					other.addEffect(e);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to apply [" + e.name() + "]");
		return t;
	}
	public static Trigger justDescription(String desc) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) { }
		};
		t.setDescription(desc);
		return t;
	}
	
	/**
	 * FIRE TRIGGERS
	 */
	public static Trigger startFire(int strength, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					owner.doAction("start a fire");
					owner.world().setHazard(Hazards.smallFire(strength), other.x, other.y, other.z);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to start fires on dealing damage.");
		return t;
	}
	public static Trigger takeFireDamage(int min, int max, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					other.doAction("combust");
					other.modifyHP(other.getDamageReceived((int)(Math.random()*(max-min) + min), Type.FIRE), owner);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to deal additional fire damage on hits.");
		return t;
	}

	
	/**
	 * COLD TRIGGERS
	 */
	public static Trigger slow(int strength, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					other.addEffect(Effects.slowed(strength, 5));
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to slow for " + strength + " turns.");
		return t;
	}
	
	
	/**
	 * AIR TRIGGERS
	 */
	public static Trigger stun(int strength, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					String s = "stun";
					if (!other.is(Tag.LEGENDARY))
						s += " the";
					s += " " + other.name();
					owner.doAction(s);
					other.addEffect(Effects.stun(strength));
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to stun for " + strength + " turns.");
		return t;
	}
	public static Trigger knockback(int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					String s = "knockback";
					if (!other.is(Tag.LEGENDARY))
						s += " the";
					s += " " + other.name();
					owner.doAction(s);
					int cx = other.x - owner.x;
					int cy = other.y - owner.y;
					other.moveBy(cx, cy, 0);
					other.moveBy(cx, cy, 0);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to knockback enemies.");
		return t;
	}
	
	
	/**
	 * POISON TRIGGERS
	 */
	public static Trigger createPoisonCloud(int strength, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					owner.doAction("create a poison cloud");
					owner.world().setHazard(Hazards.poisonCloud(strength), other.x, other.y, other.z);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to create poison clouds on dealing damage.");
		return t;
	}
	public static Trigger confuse(int strength, int chance) {
		Trigger t = new Trigger() {
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					String s = "confuse";
					if (!other.is(Tag.LEGENDARY))
						s += " the";
					s += " " + other.name();
					owner.doAction(s);
					other.addEffect(Effects.confused(strength));
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to confuse for " + strength + " turns.");
		return t;
	}
	
	
	/**
	 * LIGHT TRIGGERS
	 */
	public static Trigger healOnHit(int min, int max, int chance) {
		Trigger t = new Trigger() {
			@Override
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance)
					owner.modifyHP((int)(Math.random()*(max-min)) + min);
			}
		};
		t.setDescription("It has a " + chance + "% chance to heal you on hits.");
		return t;
	}
	public static Trigger manaOnHit(int min, int max, int chance) {
		Trigger t = new Trigger() {
			@Override
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance)
					owner.modifyMana((int)(Math.random()*(max-min)) + min);
			}
		};
		t.setDescription("It has a " + chance + "% chance to regenerate mana on hits.");
		return t;
	}
	public static Trigger smiting(int tier) {
		Trigger t = new Trigger() {
			@Override
			public void trigger(Creature owner, Creature other) {
				if (other.is(Tag.UNDEAD)) {
					other.modifyHP(-(int)(Math.random()*(owner.level()+2) + 8 * tier), owner);
				}
			}
		};
		t.setDescription("It deals massively increased damage to the Undead.");
		return t;
	}
	
	
	/**
	 * DARK TRIGGERS
	 */
	public static Trigger summonImp(CreatureFactory f, int chance) {
		Trigger t = new Trigger() {
			@Override
			public void trigger(Creature owner, Creature other) {
				if (Math.random() * 100 < chance) {
					owner.doAction("summon an imp");
					Spells.summonImp(f).casterEffect(owner);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to summon a friendly imp on dealing damage.");
		return t;
	}
	public static Trigger drainMana(int min, int max, int chance) {
		Trigger t = new Trigger() {
			@Override
			public void trigger(Creature owner, Creature other) {
				if (Math.random()*100 < chance) {
					int amount = ((int)(Math.random()*(max-min)) + min);
					other.modifyMana(-amount);
					owner.modifyMana(amount);
				}
			}
		};
		t.setDescription("It has a " + chance + "% chance to drain mana.");
		return t;
	}
}
