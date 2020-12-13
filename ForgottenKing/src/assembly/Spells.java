package assembly;

import creatures.Creature;
import creatures.Tag;
import creatures.Type;
import javafx.scene.image.Image;
import spells.Spell;
import spells.TargetType;
import tools.ImageCrop;

@SuppressWarnings("serial")
public final class Spells {
	private static int fireStartY = 0;
	private static int coldStartY = 32;
	private static int airStartY = 64;
	private static int poisonStartY = 96;
	private static int lightStartY = 128;
	private static int darkStartY = 160;
	private static Image spellsFull = new Image(ItemFactory.class.getResourceAsStream("resources/icons/spells-full.png"));
	private static Image embersIcon = ImageCrop.cropImage(spellsFull, 0, fireStartY, 32, 32);
	private static Image moltenFireIcon = ImageCrop.cropImage(spellsFull, 32, fireStartY, 32, 32);
	private static Image flameWaveIcon = ImageCrop.cropImage(spellsFull, 64, fireStartY, 32, 32);
	private static Image heatbeamIcon = ImageCrop.cropImage(spellsFull, 96, fireStartY, 32, 32);
	private static Image summonImpIcon = ImageCrop.cropImage(spellsFull, 128, fireStartY, 32, 32);
	private static Image fireballIcon = ImageCrop.cropImage(spellsFull, 160, fireStartY, 32, 32);
	
	private static Image chillIcon = ImageCrop.cropImage(spellsFull, 0, coldStartY, 32, 32);
	private static Image iceShardIcon = ImageCrop.cropImage(spellsFull, 32, coldStartY, 32, 32);
	private static Image summonSimulacrumIcon = ImageCrop.cropImage(spellsFull, 64, coldStartY, 32, 32);
	private static Image armorOfFrostIcon = ImageCrop.cropImage(spellsFull, 96, coldStartY, 32, 32);
	private static Image icicleIcon = ImageCrop.cropImage(spellsFull, 128, coldStartY, 32, 32);
	private static Image massChillIcon = ImageCrop.cropImage(spellsFull, 160, coldStartY, 32, 32);
	
	private static Image shockingTouchIcon = ImageCrop.cropImage(spellsFull, 0, airStartY, 32, 32);
	private static Image swiftnessIcon = ImageCrop.cropImage(spellsFull, 32, airStartY, 32, 32);
	private static Image minorStunIcon = ImageCrop.cropImage(spellsFull, 64, airStartY, 32, 32);
	private static Image blinkIcon = ImageCrop.cropImage(spellsFull, 96, airStartY, 32, 32);
	private static Image whirlwindIcon = ImageCrop.cropImage(spellsFull, 128, airStartY, 32, 32);
	private static Image lightningBoltIcon = ImageCrop.cropImage(spellsFull, 160, airStartY, 32, 32);

	private static Image stingIcon = ImageCrop.cropImage(spellsFull, 0, poisonStartY, 32, 32);
	private static Image toxicCloudIcon = ImageCrop.cropImage(spellsFull, 32, poisonStartY, 32, 32);
	private static Image minorConfuseIcon = ImageCrop.cropImage(spellsFull, 64, poisonStartY, 32, 32);
	
	private static Image curePoisonIcon = ImageCrop.cropImage(spellsFull, 0, lightStartY, 32, 32);
	private static Image innerGlowIcon = ImageCrop.cropImage(spellsFull, 32, lightStartY, 32, 32);
	private static Image regenerateIcon = ImageCrop.cropImage(spellsFull, 64, lightStartY, 32, 32);
	private static Image heroismIcon = ImageCrop.cropImage(spellsFull, 96, lightStartY, 32, 32);

	private static Image slowIcon = ImageCrop.cropImage(spellsFull, 0, darkStartY, 32, 32);
	private static Image weakenIcon = ImageCrop.cropImage(spellsFull, 32, darkStartY, 32, 32);
	private static Image blindIcon = ImageCrop.cropImage(spellsFull, 64, darkStartY, 32, 32);
	private static Image vulnerabilityIcon = ImageCrop.cropImage(spellsFull, 96, darkStartY, 32, 32);
	private static Image darksmiteIcon = ImageCrop.cropImage(spellsFull, 128, darkStartY, 32, 32);
	private static Image soulSiphonIcon = ImageCrop.cropImage(spellsFull, 160, darkStartY, 32, 32);
	
	/**
	 * FIRE SPELLS
	 */
	public static Spell embers() {
		Spell s = new Spell("Embers", 1, 1, embersIcon);
		s.setType(Type.FIRE);
		s.setDamage(1, 4);
		s.setTargetType(TargetType.PROJECTILE);
		s.setRange(5);
		s.setDescription("You fling forward a pile of burning embers at a target, dealing MINDAMAGE - MAXDAMAGE fire damage.");
		return s;
	}
	public static Spell moltenFire() {
		Spell s = new Spell("Molten Fire", 3, 2, moltenFireIcon);
		s.setType(Type.FIRE);
		s.setDamage(0, 2);
		s.setEffect(Effects.burning(4, 3), 75);
		s.setTargetType(TargetType.PROJECTILE);
		s.setRange(4);
		s.setDescription("Hurl a globe of molten fire towards your target, dealing MINDAMAGE - MAXDAMAGE fire damage, with a chance to light the target on fire.");
		return s;
	}
	public static Spell flameWave() {
		Spell s = new Spell("Flame Wave", 4, 2, flameWaveIcon);
		s.setType(Type.FIRE);
		s.setDamage(1, 3);
		s.setEffect(Effects.burning(4, 2), 60);
		s.setTargetType(TargetType.SELF);
		s.setHazard(Hazards.smallFire(1), 60);
		s.setDescription("You blast a wave of fire around yourself, dealing MINDAMAGE - MAXDAMAGE fire damage to each adjacent creature, with a chance to light them on fire.");
		s.setRadius(1);
		s.setVolume(7);
		return s;
	}
	public static Spell heatbeam() {
		Spell s = new Spell("Heatbeam", 4, 2, heatbeamIcon);
		s.setType(Type.FIRE);
		s.setDamage(4, 7);
		s.setTargetType(TargetType.BEAM);
		s.setDescription("You summon a beam of pure heat, dealing MINDAMAGE - MAXDAMAGE fire damage to every creature in a line.");
		s.setRange(4);
		s.setVolume(4);
		return s;
	}
	public static Spell summonImp(CreatureFactory f) {
		Spell s = new Spell("Summon Imp", 3, 2, summonImpIcon) {
			public void casterEffect(Creature caster) {
				if (caster.is(Tag.PLAYER))
					summonCreature(caster, f.newFriendlyImp(caster.z, caster.getSpellcasting()/2 + caster.magic().get(Type.FIRE)));
				else
					summonCreature(caster, f.newImp(caster.z));
			}
		};
		s.setType(Type.FIRE);
		s.setTargetType(TargetType.SELF);
		s.setDescription("Summon a lesser imp, a small fire elemental that can cast spells and fight alongside you for a short time.");
		s.setUseText("summon a lesser imp");
		return s;
	}
	public static Spell fireball() {
		Spell s = new Spell("Fireball", 6, 3, fireballIcon);
		s.setType(Type.FIRE);
		s.setDamage(7, 10);
		s.setTargetType(TargetType.TARGET);
		s.setRadius(1);
		s.setHazard(Hazards.smallFire(1), 80);
		s.setDescription("You flick a tiny globe of fire towards your target, which quickly blossoms into a fiery explosion dealing MINDAMAGE - MAXDAMAGE fire damage to every creature caught in the blast.");
		s.setVolume(8);
		return s;
	}
	
	/**
	 * COLD SPELLS
	 */
	public static Spell chill() {
		Spell s = new Spell("Chill", 1, 1, chillIcon);
		s.setType(Type.COLD);
		s.setDamage(1, 2);
		s.setTargetType(TargetType.TARGET);
		s.setRange(6);
		s.setEffect(Effects.slowed(6, 5), 80);
		s.setDescription("A target of your choice becomes slowed, taking MINDAMAGE - MAXDAMAGE cold damage in the process.");
		return s;
	}
	public static Spell iceShard() {
		Spell s = new Spell("Ice Shard", 2, 1, iceShardIcon);
		s.setType(Type.COLD);
		s.setDamage(2, 5);
		s.setTargetType(TargetType.PROJECTILE);
		s.setEffect(Effects.slowed(3,3), 50);
		s.setDescription("Throw a razor sharp shard of ice at a target, dealing MINDAMAGE - MAXDAMAGE cold damage, with a chance to become slowed.");
		return s;
	}
	public static Spell summonSimulacrum(CreatureFactory f) {
		Spell s = new Spell("Summon Simulacrum", 3, 2, summonSimulacrumIcon) {
			public void casterEffect(Creature caster) {
				if (caster.is(Tag.PLAYER))
					summonCreature(caster, f.newFriendlySimulacrum(caster.z, caster.getSpellcasting()/2 + caster.magic().get(Type.COLD)));
				else
					summonCreature(caster, f.newSimulacrum(caster.z));
			}
		};
		s.setType(Type.COLD);
		s.setTargetType(TargetType.SELF);
		s.setDescription("Summon a simulacrum, a weak ice elemental that will fight alongside you for a short time.");
		s.setUseText("summon a simulacrum");
		return s;
	}
	public static Spell armorOfFrost() {
		Spell s = new Spell("Armor of Frost", 3, 2, armorOfFrostIcon);
		s.setType(Type.COLD);
		s.setTargetType(TargetType.SELF);
		s.setEffect(Effects.armorOfFrost(),100);
		s.setDescription("Coat yourself with an armor made of ice. This increases your armor and slightly decreases your speed for a short time.");
		return s;
	}
	public static Spell icicle() {
		Spell s = new Spell("Icicle", 3, 2, icicleIcon);
		s.setType(Type.COLD);
		s.setDamage(4, 6);
		s.setTargetType(TargetType.PROJECTILE);
		s.setEffect(Effects.slowed(5,4), 70);
		s.setDescription("Hurl a giant icicle, dealing MINDAMAGE - MAXDAMAGE cold damage, with a chance to slow your target.");
		return s;
	}
	public static Spell massChill() {
		Spell s = new Spell("Mass Chill", 4, 3, massChillIcon);
		s.setType(Type.COLD);
		s.setDamage(0, 1);
		s.setTargetType(TargetType.SELF);
		s.setRadius(3);
		s.setEffect(Effects.slowed(8, 6), 80);
		s.setDescription("Every creature within 3 tiles of you that you can see takes MINDAMAGE - MAXDAMAGE cold damage, with a high chance to become slowed.");
		s.setVolume(6);
		return s;
	}
	
	/**
	 * AIR SPELLS
	 */
	public static Spell shockingTouch() {
		Spell s = new Spell("Shocking Touch", 1, 1, shockingTouchIcon);
		s.setType(Type.AIR);
		s.setDamage(1, 3);
		s.setTargetType(TargetType.PROJECTILE);
		s.setRange(1);
		s.setEffect(Effects.shocked(2), 80);
		s.setDescription("You reach out with a shocking grasp to deal MINDAMAGE - MAXDAMAGE air damage and shock your target for their next turn.");
		return s;
	}
	public static Spell swiftness() {
		Spell s = new Spell("Swiftness", 2, 1, swiftnessIcon);
		s.setType(Type.AIR);
		s.setTargetType(TargetType.SELF);
		s.setEffect(Effects.swift(6,5),100);
		s.setDescription("Empowered by the wind, your movement delay decreases by 5 for a short time.");
		return s;
	}
	public static Spell minorStun() {
		Spell s = new Spell("Minor Stun", 5, 2, minorStunIcon);
		s.setType(Type.AIR);
		s.setTargetType(TargetType.TARGET);
		s.setRange(4);
		s.setEffect(Effects.stun(3),70);
		s.setDescription("Use an unseen electrical current to try to stun a creature that you can see for a few turns.");
		return s;
	}
	public static Spell blink() {
		Spell s = new Spell("Blink", 3, 2, blinkIcon) {
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
                        || !caster.canSee(caster.x+mx, caster.y+my, caster.z));
                caster.moveTo(caster.x + mx, caster.y + my, caster.z);
                caster.doAction("blink");
			}
		};
		s.setType(Type.AIR);
		s.setTargetType(TargetType.SELF);
		s.setDescription("Pulled along by electrical currents, you randomly teleport to a tile within 5 tiles of you that you can see.");
		s.setVolume(4);
		return s;
	}
	public static Spell whirlwind() {
		Spell s = new Spell("Whirlwind", 3, 2, whirlwindIcon) {
			@Override
			public void casterEffect(Creature caster) {
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						Creature c = caster.creature(caster.x+x, caster.y+y, caster.z);
						if (c != null && c != caster) {
							c.moveBy(x, y, 0);
							c.moveBy(x, y, 0);
						}
					}
				}
			}
		};
		s.setType(Type.AIR);
		s.setTargetType(TargetType.SELF);
		s.setRadius(1);
		s.setDamage(2, 5);
		s.setDescription("Summon a mighty gust of air to blast back each creature adjacent to you, dealing MINDAMAGE - MAXDAMAGE to each of them.");
		s.setVolume(4);
		return s;
	}
	public static Spell lightningBolt() {
		Spell s = new Spell("Lightning Bolt", 5, 3, lightningBoltIcon);
		s.setType(Type.AIR);
		s.setDamage(7, 10);
		s.setTargetType(TargetType.BEAM);
		s.setRange(5);
		s.setEffect(Effects.shocked(2), 75); 
		s.setDescription("Hurl a mighty bolt of lightning. Each creature struck by it takes MINDAMAGE - MAXDAMAGE air damage, with a high chance to become shocked for their next turn.");
		s.setVolume(8);
		return s;
	}
	
	/**
	 * POISON SPELLS
	 */
	static Spell sting() {
		Spell s = new Spell("Sting", 1, 1, stingIcon);
		s.setType(Type.POISON);
		s.setEffect(Effects.poisoned(5, 1), 90);
		s.setDamage(1, 3);
		s.setRange(4);
		s.setTargetType(TargetType.PROJECTILE);
		s.setDescription("A short range poison spell, sting deals MINDAMAGE-MAXDAMAGE poison damage, with a high chance to poison the target for several turns.");
		return s;
	}
	public static Spell toxicCloud() {
		Spell s = new Spell("Toxic Cloud", 3, 2, toxicCloudIcon);
		s.setType(Type.POISON);
		s.setTargetType(TargetType.TARGET);
		s.setRadius(1);
		s.setHazard(Hazards.poisonCloud(1), 80);
		s.setDescription("You manipulate matter to create a cloud of toxic poison, dealing damage and poisoning all who stand within.");
		s.setVolume(3);
		return s;
	}
	public static Spell minorConfuse() {
		Spell s = new Spell("Minor Confuse", 3, 2, minorConfuseIcon);
		s.setType(Type.POISON);
		s.setEffect(Effects.confused(3), 70);
		s.setRange(4);
		s.setTargetType(TargetType.TARGET);
		s.setDescription("Cause a small amount of toxic fog to infect your targets brain, confusing them for a few turns.");
		return s;
	}
	
	
	/**
	 * LIGHT SPELLS
	 */
	public static Spell curePoison() {
		Spell s = new Spell("Cure Poison", 2, 1, curePoisonIcon);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.curePoison(), 100);
		s.setTargetType(TargetType.TARGET);
		s.setBeneficial(true);
		s.setDescription("Call on the power of light to purge all poisons from your body.");
		return s;
	}
	public static Spell innerGlow() {
		Spell s = new Spell("Inner Glow", 2, 1, innerGlowIcon);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.glowing(6,4), 80);
		s.setTargetType(TargetType.TARGET);
		s.setDescription("Your target begins to glow with an inner light, making them easier to hit in the gloominess of the dungeon.");
		return s;
	}
	public static Spell regenerateHealth() {
		Spell s = new Spell("Regenerate", 4, 2, regenerateIcon);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.healOverTime(8, 2), 100);
		s.setTargetType(TargetType.TARGET);
		s.setBeneficial(true);
		s.setDescription("You begin to regenerate, healing 2 HP every turn for 8 turns.");
		return s;
	}
	public static Spell heroism() {
		Spell s = new Spell("Heroism", 4, 2, heroismIcon);
		s.setType(Type.LIGHT);
		s.setEffect(Effects.strong(8, 2), 100);
		s.setTargetType(TargetType.TARGET);
		s.setBeneficial(true);
		s.setDescription("The power of light grants you heroic strength, making you stronger for 8 turns.");
		return s;
	}
	
	/**
	 * DARK SPELLS
	 */
	public static Spell slow(int cost, int duration, int amount) {
		Spell s = new Spell("Slow", cost, 1, slowIcon);
		s.setType(Type.DARK);
		s.setEffect(Effects.slowed(duration, amount), 75);
		s.setTargetType(TargetType.TARGET);
		s.setDescription("You sap the energy from your targets limbs, increasing their attack delay and movement delay by " + amount + " for " + duration + " turns.");
		return s;
	}
	public static Spell weaken() {
		Spell s = new Spell("Weaken", 3, 2, weakenIcon);
		s.setType(Type.DARK);
		s.setEffect(Effects.weak(6, 4), 70);
		s.setTargetType(TargetType.TARGET);
		s.setDescription("The power of darkness weakens your target, reducing their strength for a short period of time.");
		return s;
	}
	public static Spell blind() {
		Spell s = new Spell("Blind", 4, 2, blindIcon);
		s.setType(Type.DARK);
		s.setEffect(Effects.blind(4), 60);
		s.setRange(4);
		s.setTargetType(TargetType.TARGET);
		s.setDescription("Blind your target, making them unable to see anywhere further than their own tile.");
		return s;
	}
	public static Spell vulnerability() {
		Spell s = new Spell("Vulnerability", 4, 2, vulnerabilityIcon);
		s.setType(Type.DARK);
		s.setEffect(Effects.vulnerable(6, 4), 75);
		s.setTargetType(TargetType.TARGET);
		s.setDescription("Darkness is used to prey on your opponents defenses, reducing their armor for a short time.");
		return s;
	}
	public static Spell darksmite() {
		Spell s = new Spell("Darksmite", 4, 2, darksmiteIcon);
		s.setType(Type.DARK);
		s.setTargetType(TargetType.TARGET);
		s.setRange(6);
		s.setDamage(3, 7);
		s.setUseText("call upon darkness");
		s.setDescription("An unholy prayer similar to it's light counterpart causing darkness to strike your target from above, dealing MINDAMAGE - MAXDAMAGE dark damage.");
		s.setVolume(4);
		return s;
	}
	public static Spell soulSiphon() {
		Spell s = new Spell("Soul Siphon", 5, 3, soulSiphonIcon) {
			public void casterEffect(Creature caster) {
				caster.modifyHP(caster.getDamageReceived(6, Type.DARK));
				caster.doAction("heal from darkness.");
			}
		};
		s.setType(Type.DARK);
		s.setEffect(Effects.weak(6, 3), 70);
		s.setRange(6);
		s.setDamage(4, 8);
		s.setAttackValue(6);
		s.setTargetType(TargetType.PROJECTILE);
		s.setVolume(4);
		s.setDescription("You use dark magic to drain your target's life force directly, dealing MINDAMAGE - MAXDAMAGE dark damage and healing yourself for some of it.");
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
	public static Spell fireStomp() {
		Spell s = new Spell("Stomp", 4, 3, null) {
			@Override
			public void casterEffect(Creature caster) {
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						Creature c = caster.creature(caster.x+x, caster.y+y, caster.z);
						if (c != null && c != caster) {
							c.moveBy(x, y, 0);
							c.moveBy(x, y, 0);
							c.moveBy(x, y, 0);
							c.doAction("get blasted backwards");
						}
					}
				}
			}
		};
		s.setType(Type.FIRE);
		s.setTargetType(TargetType.TARGET);
		s.setRange(1);
		s.setRadius(1);
		s.setDamage(2, 5);
		s.setEffect(Effects.burning(3, 2), 5);
		s.setUseText("stomp the ground");
		s.setVolume(4);
		return s;
	}
	
	
	/**
	 * HELPER METHODS
	 */
	
	private static void summonCreature(Creature caster, Creature c) {
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
                || !caster.canSee(caster.x+mx, caster.y+my, caster.z)) && trials < 50);
        if (trials >= 50) {
        	caster.notify("Your " + c.name() + " failed to materialize!");
        	caster.world().remove(c);
        	return;
        }
        c.x = mx + caster.x;
        c.y = my + caster.y;
	}
}
