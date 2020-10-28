package assembly;

import creatures.Creature;
import creatures.Tag;
import creatures.Type;
import spells.Spell;
import spells.TargetType;

public final class Spells {
	/**
	 * FIRE SPELLS
	 */
	public static Spell embers() {
		Spell s = new Spell("Embers", 1, 1);
		s.setType(Type.FIRE);
		s.setDamage(2, 5);
		s.setTargetType(TargetType.PROJECTILE);
		s.setRange(5);
		return s;
	}
	public static Spell moltenFire() {
		Spell s = new Spell("Molten Fire", 3, 2);
		s.setType(Type.FIRE);
		s.setDamage(1, 3);
		s.setEffect(Effects.burning(4, 3), 75);
		s.setTargetType(TargetType.PROJECTILE);
		s.setRange(4);
		return s;
	}
	public static Spell flameWave() {
		Spell s = new Spell("Flame Wave", 4, 2);
		s.setType(Type.FIRE);
		s.setDamage(3, 7);
		s.setEffect(Effects.burning(4, 2), 60);
		s.setTargetType(TargetType.SELF);
		s.setRadius(1);
		return s;
	}
	public static Spell heatbeam() {
		Spell s = new Spell("Heatbeam", 4, 2);
		s.setType(Type.FIRE);
		s.setDamage(4, 7);
		s.setTargetType(TargetType.BEAM);
		s.setRange(4);
		return s;
	}
	public static Spell fireball() {
		Spell s = new Spell("Fireball", 6, 3);
		s.setType(Type.FIRE);
		s.setDamage(7, 10);
		s.setTargetType(TargetType.TARGET);
		s.setRadius(1);
		return s;
	}
	
	/**
	 * COLD SPELLS
	 */
	public static Spell chill() {
		Spell s = new Spell("Chill", 1, 1);
		s.setType(Type.COLD);
		s.setDamage(1, 2);
		s.setTargetType(TargetType.TARGET);
		s.setRange(6);
		s.setEffect(Effects.slowed(6, 5), 80);
		return s;
	}
	public static Spell iceShard() {
		Spell s = new Spell("Ice Shard", 2, 1);
		s.setType(Type.COLD);
		s.setDamage(2, 5);
		s.setTargetType(TargetType.PROJECTILE);
		s.setEffect(Effects.slowed(3,3), 30);
		return s;
	}
	public static Spell summonSimulacrum(CreatureFactory f) {
		Spell s = new Spell("Summon Simulacrum", 3, 2) {
			public void casterEffect(Creature caster) {
				int mx = 0;
                int my = 0;
                int trials = 0;
                do
                {
                    mx = (int)(Math.random() * 5) - 2;
                    my = (int)(Math.random() * 5) - 2;
                    trials++;
                }
                while ((!caster.canEnter(caster.x+mx, caster.y+my, caster.z)
                        && caster.canSee(caster.x+mx, caster.y+my, caster.z)) && trials <= 50);
                if (trials >= 50) {
                	caster.notify("Your simulacrum failed to materialize!");
                	return;
                }
                if (caster.is(Tag.PLAYER)) {
                	Creature c = f.newFriendlySimulacrum(caster.z);
                	c.x = mx + caster.x;
                	c.y = my + caster.y;
                }
			}
		};
		s.setType(Type.COLD);
		s.setUseText("summon a simulacrum");
		return s;
	}
	public static Spell armorOfFrost() {
		Spell s = new Spell("Armor of Frost", 3, 2);
		s.setType(Type.COLD);
		s.setTargetType(TargetType.SELF);
		s.setEffect(Effects.armorOfFrost(),100);
		return s;
	}
	public static Spell icicle() {
		Spell s = new Spell("Icicle", 3, 2);
		s.setType(Type.COLD);
		s.setDamage(4, 6);
		s.setTargetType(TargetType.PROJECTILE);
		s.setEffect(Effects.slowed(5,4), 70);
		return s;
	}
	public static Spell massChill() {
		Spell s = new Spell("Chill", 4, 3);
		s.setType(Type.COLD);
		s.setDamage(0, 2);
		s.setTargetType(TargetType.SELF);
		s.setRadius(4);
		s.setEffect(Effects.slowed(8, 6), 80);
		return s;
	}
	
	/**
	 * AIR SPELLS
	 */
	public static Spell shockingTouch() {
		Spell s = new Spell("Shocking Touch", 1, 1);
		s.setType(Type.AIR);
		s.setDamage(2, 5);
		s.setTargetType(TargetType.PROJECTILE);
		s.setRange(1);
		s.setEffect(Effects.shocked(2), 80);
		return s;
	}
	public static Spell swiftness() {
		Spell s = new Spell("Swiftness", 2, 2);
		s.setType(Type.AIR);
		s.setTargetType(TargetType.SELF);
		s.setEffect(Effects.swift(6,5),100);
		return s;
	}
	public static Spell minorStun() {
		Spell s = new Spell("Minor Stun", 3, 2);
		s.setType(Type.AIR);
		s.setTargetType(TargetType.TARGET);
		s.setRange(4);
		s.setEffect(Effects.stun(4),70);
		return s;
	}
	public static Spell blink() {
		Spell s = new Spell("Blink", 3, 2) {
			@Override
			public void casterEffect(Creature caster) {
				int mx = 0;
                int my = 0;
                do
                {
                    mx = (int)(Math.random() * 13) - 6;
                    my = (int)(Math.random() * 13) - 6;
                }
                while (!caster.canEnter(caster.x+mx, caster.y+my, caster.z)
                        && caster.canSee(caster.x+mx, caster.y+my, caster.z));
                caster.moveBy(mx, my, 0);
                caster.doAction("blink");
			}
		};
		s.setType(Type.AIR);
		s.setTargetType(TargetType.SELF);
		return s;
	}
	public static Spell lightningBolt() {
		Spell s = new Spell("Lightning Bolt", 5, 3);
		s.setType(Type.AIR);
		s.setDamage(7, 10);
		s.setTargetType(TargetType.BEAM);
		s.setRange(5);
		s.setEffect(Effects.shocked(2), 75);
		return s;
	}
	
	/**
	 * POISON SPELLS
	 */
	public static Spell sting() {
		Spell s = new Spell("Sting", 1, 1);
		s.setType(Type.POISON);
		s.setEffect(Effects.poisoned(5, 1, 0), 90);
		s.setDamage(2, 4);
		s.setRange(4);
		s.setTargetType(TargetType.PROJECTILE);
		return s;
	}
	public static Spell minorConfuse() {
		Spell s = new Spell("Minor Confuse", 3, 2);
		s.setType(Type.POISON);
		s.setEffect(Effects.confused(4), 70);
		s.setRange(4);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	
	
	/**
	 * LIGHT SPELLS
	 */
	public static Spell curePoison() {
		Spell s = new Spell("Cure Poison", 2, 1);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.curePoison(), 100);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell innerGlow() {
		Spell s = new Spell("Inner Glow", 2, 1);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.glowing(6,4), 80);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell regenerateHealth() {
		Spell s = new Spell("Regenerate", 5, 2);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.healOverTime(8, 2), 100);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell heroism() {
		Spell s = new Spell("Heroism", 4, 2);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.strong(8, 2), 100);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	
	/**
	 * DARK SPELLS
	 */
	public static Spell slow(int cost, int duration, int amount) {
		Spell s = new Spell("Slow", cost, 1);
		s.setType(Type.DARK);
		s.setEffect(Effects.slowed(duration, amount), 75);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell weaken() {
		Spell s = new Spell("Weaken", 3, 2);
		s.setType(Type.DARK);
		s.setEffect(Effects.weak(6, 4), 70);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell blind() {
		Spell s = new Spell("Blind", 4, 2);
		s.setType(Type.DARK);
		s.setEffect(Effects.blind(4), 60);
		s.setRange(4);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell vulnerable() {
		Spell s = new Spell("Vulnerability", 4, 2);
		s.setType(Type.DARK);
		s.setEffect(Effects.vulnerable(6, 4), 75);
		s.setTargetType(TargetType.TARGET);
		return s;
	}
	public static Spell soulSiphon() {
		Spell s = new Spell("Soul Siphon", 5, 3) {
			public void casterEffect(Creature caster) {
				caster.modifyHP(caster.getDamageReceived(-6, Type.DARK));
				caster.doAction("Heal from darkness.");
			}
		};
		s.setType(Type.DARK);
		s.setEffect(Effects.weak(6, 3), 70);
		s.setRange(6);
		s.setDamage(4, 8);
		s.setAttackValue(6);
		s.setTargetType(TargetType.PROJECTILE);
		return s;
	}
	
	
	/**
	 * MONSTER SPECIFIC SPELLS
	 */
	public static Spell homunculiSlow() {
		Spell slow = slow(4,4,5);
		slow.setUseText("gaze at you");
		return slow;		
	}
}
