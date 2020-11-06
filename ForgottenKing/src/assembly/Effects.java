package assembly;

import creatures.Attribute;
import creatures.Creature;
import creatures.Stat;
import creatures.Tag;
import creatures.Type;
import javafx.scene.paint.Color;
import spells.Effect;

public final class Effects {
	public static Effect health(int amount) {
		Effect e = new Effect("Healing", 1){
			public void start(Creature creature){
				creature.modifyHP(amount);
				creature.doAction("look healthier");
			}
		};
		e.setDescription("Heals for " + amount + " health.");
		e.setColour(Color.RED);
		return e;
	}
	public static Effect damage(int amount, Type type) {
		Effect e = new Effect("Damage", 1){
			public void start(Creature creature){
				creature.modifyHP(-creature.getDamageReceived(amount, type), this.owner);
				creature.doAction("take " + amount + " " + type.text() + " damage");
			}
		};
		e.setDescription("Deals " + amount + " " + type.name() + " damage.");
		e.setColour(Color.RED);
		return e;
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
		e.setStrength(amount);
		e.setDescription("Heals for " + amount + " health every turn, for " + duration + " turns.");
		e.setColour(Color.RED);
		return e;
	}
	public static Effect mana(int amount) {
		Effect e = new Effect("Mana", 1){
	        public void start(Creature creature){
	            creature.modifyMana(amount);
	            creature.doAction("glow with energy");
	        }
	    };
	    e.setDescription("Regenerates " + amount + " mana.");
	    e.setColour(Color.BLUE);
	    return e;
	}
	public static Effect poisoned(int duration, int strength) {
		Effect e = new Effect("Poisoned", duration){
			public void start(Creature creature){
				if (Math.random() * 100 < creature.getToughness() * 10) {
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
		e.setStrength(strength);
		e.setColour(Color.GREEN);
		e.setDescription("Poisoned creatures have a chance to resist based on their toughness. Poisoned creatures take " + strength + " poison damage per turn, for " + duration + " turns.");
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
		e.setStrength(strength);
		e.setColour(Color.ORANGE);
		e.setDescription("Burning creatures catch fire, taking " + strength + " fire damage per turn for " + duration + " turns.");
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
		e.setStrength(amount);
		e.setColour(Color.RED);
		e.setDescription("Strong creatures have increased strength by " + amount + " for " + duration + " turns.");
		return e;
	}
	public static Effect weak(int duration, int amount) {
		Effect e = new Effect("Weak", duration){
			public void start(Creature creature){
				creature.modifyStat(Stat.BRAWN, -amount);
				creature.doAction("look weak");
			}
			public void end(Creature creature){
				creature.modifyStat(Stat.BRAWN, +amount);
				creature.doAction("look stronger");
			}
		};
		e.setImage(Loader.weakIcon);
		e.setStrength(amount);
		e.setColour(Color.DARKBLUE);
		e.setDescription("Weakened creatures have decreased Brawn by " + amount + " for " + duration + " turns.");
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
		e.setStrength(amount);
		e.setColour(Color.DARKBLUE);
		e.setDescription("Slowed creatures have increased movement and attack delay by " + amount + " for " + duration + " turns.");
		return e;
	}
	public static Effect shocked(int duration) {
		Effect e = new Effect("Shocked", duration) {
			public void start(Creature creature) {
				creature.modifyMovementDelay(10);
				creature.modifyAttackDelay(10);
				creature.doAction("get shocked");
			}
			public void end(Creature creature) {
				creature.modifyMovementDelay(-10);
				creature.modifyAttackDelay(-10);
			}
		};
		e.setImage(Loader.shockedIcon);
		e.setColour(Color.BLUE);
		e.setDescription("The next action of a shocked creature takes an additional 10 ticks.");
		return e;
	}
	public static Effect swift(int duration, int amount) {
		Effect e = new Effect("Swift", duration) {
			public void start(Creature creature) {
				creature.modifyMovementDelay(-amount);
				creature.doAction("move faster");
			}
			public void end(Creature creature) {
				creature.modifyMovementDelay(amount);
				creature.doAction("move slower");
			}
		};
		e.setImage(Loader.swiftIcon);
		e.setStrength(amount);
		e.setColour(Color.BLUE);
		e.setDescription("Swift creatures have decreased movement delay by " + amount + " for " + duration + " turns.");
		return e;
	}
	public static Effect stun(int duration) {
		Effect e = new Effect("Stunned", duration) {
			public void start(Creature creature) {
				creature.addTag(Tag.STUNNED);
				creature.doAction("become stunned");
				for (Tag t : creature.tags())
					System.out.println(t.name());
			}
			public void end(Creature creature) {
				creature.removeTag(Tag.STUNNED);
				creature.doAction("shake off the stun");
				for (Tag t : creature.tags())
					System.out.println(t.name());
			}
		};
		e.setImage(Loader.stunnedIcon);
		e.setColour(Color.BLUE);
		e.setDescription("Stunned creatures can take no options until it wears off.");
		return e;
	}
	public static Effect confused(int duration) {
		Effect e = new Effect("Confused", duration) {
			public void start(Creature creature) {
				creature.addTag(Tag.CONFUSED);
				creature.doAction("become confused");
			}
			public void end(Creature creature) {
				creature.removeTag(Tag.CONFUSED);
				creature.doAction("shake off the confusion");
			}
		};
		e.setImage(Loader.confusedIcon);
		e.setColour(Color.DARKBLUE);
		e.setDescription("Confused creatures can only move in random directions every turn.");
		return e;
	}
	public static Effect blind(int duration) {
		Effect e = new Effect("Blind", duration) {
			private int num;
			public void start(Creature creature) {
				num = creature.visionRadius();
				creature.modifyVisionRadius(-num);
				creature.doAction("become blind");
			}
			public void end(Creature creature) {
				creature.modifyVisionRadius(num);
				creature.doAction("restore sight");
			}
		};
		e.setImage(Loader.blindIcon);
		e.setColour(Color.DARKBLUE);
		e.setDescription("Blind creatures have their sight reduced to 0 for " + duration + " turns.");
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
		e.setStrength(strength);
		e.setColour(Color.DARKORANGE);
		e.setDescription("Vulnerable creatures have their Armor reduced by " + strength + " for " + duration + " turns.");
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
		e.setStrength(amount);
		e.setColour(Color.YELLOW);
		e.setDescription("Glowing creatures are easier to hit, reducing their evasion by " + amount + " for " + duration + " turns.");
		return e;
	}
	public static Effect curePoison() {
		Effect e = new Effect("Curing", 1) {
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
		e.setColour(Color.GREEN);
		e.setDescription("Removes all Poisoned effects currently afflicting the creature.");
		return e;
	}
	public static Effect raging() {
		Effect e = new Effect("Raging", 15) {
			public void start(Creature creature) {
				creature.modifyAttribute(Attribute.STR, 4);
				creature.modifyAttackDelay(-3);
				creature.addTag(Tag.NOCAST);
				creature.addTag(Tag.NOQUAFF);
				creature.doAction("go berserk");
			}
			public void end(Creature creature) {
				creature.modifyAttribute(Attribute.STR, -4);
				creature.modifyAttackDelay(3);
				creature.doAction("calm down");
				creature.removeTag(Tag.NOCAST);
				creature.removeTag(Tag.NOQUAFF);
				creature.addDelayedEffect(weak(10, 4));
				creature.addDelayedEffect(slowed(10, 5));
			}
		};
		e.setImage(Loader.ragingIcon);
		e.setColour(Color.DARKRED);
		e.setDescription("Raging creatures have increased strength and attack faster, while being unable to cast spells or quaff potions. When the rage ends, the creature is slowed and weakened.");
		return e;
	}
	public static Effect armorOfFrost() {
		Effect e = new Effect("Frost Armor", 12) {
			public void start(Creature creature) {
				creature.modifyAttackDelay(2);
				creature.modifyMovementDelay(2);
				creature.modifyArmorValue(3);
				creature.doAction("coat with ice");
			}
			public void end(Creature creature) {
				creature.modifyAttackDelay(-2);
				creature.modifyMovementDelay(-2);
				creature.modifyArmorValue(-3);
				creature.doAction("lose the icy shell");
			}
		};
		e.setImage(Loader.armorOfFrostIcon);
		e.setColour(Color.BLUE);
		e.setDescription("Creatures with an armor of frost have increased armor, however they are slightly slowed by the weight of it.");
		return e;
	}
	
	public static Effect temporarySummon(int duration) {
		Effect e = new Effect("Temporary", duration) {
			public void end(Creature creature) {
				creature.doAction("crumble to dust");
				creature.modifyHP(-creature.hp());
			}
		};
		e.setDescription("This creature is temporary and will crumble to dust after " + duration + " turns.");
		return e;
	}
}
