package assembly;

import creatures.Creature;
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
		s.setRange(4);
		return s;
	}
	public static Spell moltenFire() {
		Spell s = new Spell("Molten Fire", 3, 2);
		s.setType(Type.FIRE);
		s.setDamage(1, 3);
		s.setEffect(Effects.burning(4, 3), 75);
		s.setTargetType(TargetType.PROJECTILE);
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
