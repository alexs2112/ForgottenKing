package assembly;

import creatures.Attribute;
import creatures.Creature;
import creatures.Type;
import spells.Effect;

public final class Effects {
	public static Effect health(int amount) {
		return new Effect("Healing", 1){
			public void start(Creature creature){
				creature.modifyHP(amount);
				creature.doAction("look healthier");
			}
		};
	}
	public static Effect damage(int amount, Type type) {
		return new Effect("Damage", 1){
			public void start(Creature creature){
				creature.modifyHP(-creature.getDamageReceived(amount, type), this.owner);
				creature.doAction("take " + amount + " " + type.text() + " damage");
			}
		};
	}
	public static Effect healOverTime(int duration, int amount) {
		Effect e = new Effect("Healing", duration) {
			public void start(Creature creature) {
				creature.doAction("begin to heal");
			}
			public void update(Creature creature) {
				super.update(creature);
				creature.modifyHP(amount);
			}
			public void end(Creature creature) {
				creature.doAction("stop healing");
			}
		};
		e.setImage(Loader.healingIcon);
		return e;
	}
	public static Effect mana(int amount) {
		return new Effect("Mana", 1){
	        public void start(Creature creature){
	            creature.modifyMana(amount);
	            creature.doAction("glow with energy");
	        }
	    };
	}
	public static Effect poisoned(int duration, int strength, int resistMod) {
		Effect e = new Effect("Poisoned", duration){
			public void start(Creature creature){
				if (Math.random() * 100 < creature.getToughness() * 10 + resistMod) {
					creature.doAction("resist being poisoned");
					duration = 0;
				}
				else {
					if (creature.getResistance(Type.POISON) < 0)
						creature.doAction("look invigorated");
					else
						creature.doAction("look sick");
				}
			}

			public void update(Creature creature){
				super.update(creature);
				int d = creature.getDamageReceived(strength, Type.POISON);
				if (d < 0)
					creature.doAction("heal from poison");
				creature.modifyHP(-d, this.owner);
			}
			public void end(Creature creature) {
				creature.doAction("look better");
			}
		};
		e.setImage(Loader.poisonedIcon);
		return e;
	}
	public static Effect burning(int duration, int strength) {
		Effect e = new Effect("Burning", duration){
			public void start(Creature creature){
				creature.doAction("catch fire");
			}

			public void update(Creature creature){
				super.update(creature);
				int d = creature.getDamageReceived(strength, Type.FIRE);
				creature.modifyHP(-d, this.owner);
				creature.doAction("burn");
			}
			public void end(Creature creature) {
				creature.doAction("put the fire out");
			}
		};
		e.setImage(Loader.burningIcon);
		return e;
	}
	public static Effect strong(int duration, int amount) {
		Effect e = new Effect("Strong", duration){
			public void start(Creature creature){
				creature.modifyAttribute(Attribute.STR, amount);
				creature.doAction("look stronger");
			}
			public void end(Creature creature){
				creature.modifyAttribute(Attribute.STR, -amount);
				creature.doAction("look less strong");
			}
		};
		e.setImage(Loader.strongIcon);
		return e;
	}
	public static Effect weak(int duration, int amount) {
		Effect e = new Effect("Weak", duration){
			public void start(Creature creature){
				creature.modifyAttribute(Attribute.STR, -amount);
				creature.doAction("look weak");
			}
			public void end(Creature creature){
				creature.modifyAttribute(Attribute.STR, +amount);
				creature.doAction("look stronger");
			}
		};
		e.setImage(Loader.weakIcon);
		return e;
	}
	public static Effect slowed(int duration, int amount) {
		Effect e = new Effect("Slowed", duration) {
			public void start(Creature creature) {
				creature.modifyMovementDelay(amount);
				creature.modifyAttackDelay(amount);
				creature.doAction("move slower");
			}
			public void end(Creature creature) {
				creature.modifyMovementDelay(-amount);
				creature.modifyAttackDelay(-amount);
				creature.doAction("move faster");
			}
		};
		e.setImage(Loader.slowedIcon);
		return e;
	}
	public static Effect blind(int duration) {
		Effect e = new Effect("Blind", duration) {
			public void start(Creature creature) {
				creature.modifyVisionRadius(-8);
				creature.doAction("become blind");
			}
			public void end(Creature creature) {
				creature.modifyVisionRadius(8);
				creature.doAction("restore sight");
			}
		};
		e.setImage(Loader.blindIcon);
		return e;
	}
	public static Effect vulnerable(int duration, int strength) {
		Effect e = new Effect("Vulnerable", duration) {
			public void start(Creature creature) {
				creature.modifyArmorValue(-strength);
				creature.doAction("look vulnerable");
			}
			public void end(Creature creature) {
				creature.modifyArmorValue(strength);
				creature.doAction("look less vulnerable");
			}
		};
		e.setImage(Loader.vulnerableIcon);
		return e;
	}
	
	public static Effect glowing(int duration, int amount) {
		Effect e = new Effect("Glowing", duration){
			public void start(Creature creature) {
				creature.doAction("begin to glow with an inner light");
				creature.modifyEvasion(-amount);
			}
			public void end(Creature creature) {
				creature.doAction("stop glowing");
				creature.modifyEvasion(amount);
			}
		};
		e.setImage(Loader.glowingIcon);
		return e;
	}
	public static Effect curePoison() {
		return new Effect("Curing", 1) {
			public void start(Creature creature) {
				creature.modifyHP(creature.level());
				for (Effect e : creature.effects()) {
					if (e.name().equals("Poisoned")) {
						e.end(creature);
						creature.effects().remove(e);
						return;
					}
				}
			}
		};
	}
	public static Effect raging() {
		Effect e = new Effect("Raging", 12) {
			public void start(Creature creature) {
				creature.modifyAttribute(Attribute.STR, 4);
				creature.modifyAttackDelay(-3);
				creature.doAction("go berserk");
			}
			public void end(Creature creature) {
				creature.modifyAttribute(Attribute.STR, -4);
				creature.modifyAttackDelay(3);
				creature.doAction("calm down");
				creature.addDelayedEffect(weak(10, 4));
				creature.addDelayedEffect(slowed(10, 5));
			}
		};
		e.setImage(Loader.ragingIcon);
		return e;
	}
}
