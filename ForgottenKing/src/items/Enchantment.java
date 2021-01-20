package items;

import java.util.Arrays;
import java.util.List;

import assembly.CreatureFactory;
import assembly.Effects;
import creatures.Type;

/**
 * Enchantments come with a Bonus, Effect and Base on weapons
 */

public class Enchantment implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private List<String> fireBaseStrings;
	private List<String> coldBaseStrings;
	private List<String> airBaseStrings;
	private List<String> poisonBaseStrings;
	private List<String> lightBaseStrings;
	private List<String> darkBaseStrings;
	private CreatureFactory creature;
	private Type tempBase;
	private Type tempEffect;
	private GetWeaponImages getImages;
	
	public Enchantment() {
		prepareBases();
		getImages = new GetWeaponImages();
	}
	public void setCreatureFactory(CreatureFactory f) { creature = f; }
	
	public Item enchantWeapon(Item item, int tier, int bonus, boolean effect, boolean base) {
		tempBase = null;
		tempEffect = null;
		String effectString = null;
		String baseString = null;
		if (bonus != 0) {
			if (item.isRanged()) {
				item.modifyRangedAttackValue(bonus);
				item.setRangedDamage(item.rangedDamage()[0]+bonus, item.rangedDamage()[1]+bonus);
			} else {
				item.modifyAttackValue(bonus);
				item.setDamage(item.minDamage()+bonus, item.maxDamage()+bonus);
			}
		}
		if (effect) {
			effectString = setEffectOfWeapon(item, tier);
		}
		if (base) {
			baseString = setBaseOfWeapon(item, tier);
		}
		
		String s = "";
		if (bonus > 0)
			s += "+" + bonus;
		else if (bonus < 0)
			s += bonus;
		if (effectString != null)
			s += " " + effectString;
		s += " " + item.name();
		if (baseString != null)
			s += " of " + baseString;
		item.setName(s.trim());
		getImages.setWeaponImageAndEffect(item, bonus, tempBase, tempEffect);
		return item;
	}
	
	private String setEffectOfWeapon(Item item, int tier) {
		String s = null;
		switch((int)(Math.random()*7)) {
		case 0:
			s = "Flaming";
			item.addTrigger(Triggers.applyEffect(Effects.burning(3, tier), 55));
			item.setDamageType(Type.FIRE);
			tempEffect = Type.FIRE;
			break;
		case 1:
			s = "Freezing";
			item.addTrigger(Triggers.applyEffect(Effects.slowed(3, tier*2), 55));
			item.setDamageType(Type.COLD);
			tempEffect = Type.COLD;
			break;
		case 2:
			s = "Electric";
			item.addTrigger(Triggers.applyEffect(Effects.shocked(2), 55));
			item.setDamageType(Type.AIR);
			tempEffect = Type.AIR;
			break;
		case 3:
			s = "Poisonous";
			item.addTrigger(Triggers.applyEffect(Effects.poisoned(3, tier), 90));	//Poisoned is considerably higher cause they can resist it
			item.setDamageType(Type.POISON);
			tempEffect = Type.POISON;
			break;
		case 4:
			s = "Draining";
			item.addTrigger(Triggers.applyEffect(Effects.drained(3, tier), 65));
			item.setDamageType(Type.DARK);
			tempEffect = Type.DARK;
			break;
		case 5:
			s = "Hasty";
			item.modifyWeaponDelay(-3);
			item.addTrigger(Triggers.justDescription("It attacks quickly, reducing your attack delay by 3."));
			tempEffect = Type.BLUE;
			break;
		case 6:
			s = "Precise";
			item.addTag(ItemTag.HIGHCRIT);
			item.addTrigger(Triggers.justDescription("It is very precise, increasing your critical chance."));
			tempEffect = Type.BLUE;
			break;
		}
		
		return s;
	}
	private String setBaseOfWeapon(Item item, int tier) {
		switch((int)(Math.random()*6)) {
		case 0: return setBaseOfWeaponFire(item, tier);
		case 1: return setBaseOfWeaponCold(item, tier);
		case 2: return setBaseOfWeaponAir(item, tier);
		case 3: return setBaseOfWeaponPoison(item, tier);
		case 4: return setBaseOfWeaponLight(item, tier);
		default: return setBaseOfWeaponDark(item, tier);
//		default: return setBaseOfWeaponFire(item, tier);
		}
	}
	private String setBaseOfWeaponFire(Item item, int tier) {
		tempBase = Type.FIRE;
		Trigger t = null;
		switch((int)(Math.random()*2)) {
		case 0: t = Triggers.startFire(tier, 55); break;
		default: t = Triggers.takeFireDamage(tier * 3, tier * 6, 65); break;
		}
		
		item.addTrigger(t);
		return fireBaseStrings.get((int)(Math.random()*fireBaseStrings.size()));
	}
	private String setBaseOfWeaponCold(Item item, int tier) {
		tempBase = Type.COLD;
		item.addTrigger(Triggers.slow(tier * 2 + 1, 80));
		return coldBaseStrings.get((int)(Math.random()*coldBaseStrings.size()));
	}
	private String setBaseOfWeaponAir(Item item, int tier) {
		tempBase = Type.AIR;
		Trigger t = null;
		switch((int)(Math.random()*2)) {
		case 0: t = Triggers.stun(tier, 15); break;
		default: t = Triggers.knockback(50); break;
		}
		item.addTrigger(t);
		return airBaseStrings.get((int)(Math.random()*airBaseStrings.size()));
	}
	private String setBaseOfWeaponPoison(Item item, int tier) {
		tempBase = Type.POISON;
		Trigger t = null;
		switch((int)(Math.random()*2)) {
		case 0: t = Triggers.createPoisonCloud(tier, 55); break;
		default: t = Triggers.confuse(tier+1, 15); break;
		}
		item.addTrigger(t);
		return poisonBaseStrings.get((int)(Math.random()*poisonBaseStrings.size()));
	}
	private String setBaseOfWeaponLight(Item item, int tier) {
		tempBase = Type.LIGHT;
		Trigger t = null;
		switch((int)(Math.random()*3)) {
		case 0: t = Triggers.healOnHit(tier, tier*2, 50); break;
		case 1: t = Triggers.smiting(tier); break;
		default: t = Triggers.manaOnHit(tier*2,tier*4, 50); break;
		}
		item.addTrigger(t);
		return lightBaseStrings.get((int)(Math.random()*lightBaseStrings.size()));
	}
	private String setBaseOfWeaponDark(Item item, int tier) {
		tempBase = Type.DARK;
		Trigger t = null;
		switch((int)(Math.random()*2)) {
		case 0: t = Triggers.summonImp(creature, 10); break;
		default: t = Triggers.drainMana(tier*3,tier*5, 70); break;
		}
		item.addTrigger(t);
		return darkBaseStrings.get((int)(Math.random()*darkBaseStrings.size()));
	}
	
	private void prepareBases() {
		fireBaseStrings = Arrays.asList("Conflagration", "Embers", "Combustion", "Heat", "Inferno", "Coals", "Ignition",
		"Detonation", "Obliteration", "Fire");
		
		coldBaseStrings = Arrays.asList("Chills", "Snow", "Glaciation", "Frigidity", "Frost", "Frostbite", "Hail", "Ice", 
		"Slush");
		
		airBaseStrings = Arrays.asList("Winds", "Static", "Sparks", "Energy", "Thunder", "Awe", "Electricity", "Power");
		
		poisonBaseStrings = Arrays.asList("Venom", "Toxin", "Blight", "Infection", "Contamination", "Miasma",
		"Necrotizing", "Pestilence", "Plague", "Affliction");
		
		lightBaseStrings = Arrays.asList("Hope", "Prayer", "Incandescence", "Daylight", "Sunlight", "Sunrise", "Brilliance", 
		"Dawn", "Luster", "Splendor", "Accusation", "Blessing", "Smiting", "Life");
		
		darkBaseStrings = Arrays.asList("Death", "Malice", "Strife", "Oblivion", "Termination", "Bereavement",
		"Annihilation", "Darkness", "Necrosis", "Mortality", "Fatality", "Loss", "Ruin", "Ruination", "Release", 
		"Damnation", "Anathema", "Sacrilege", "Decay", "Horror");
	}
	
}
