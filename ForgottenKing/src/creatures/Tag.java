package creatures;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import assembly.Abilities;
import javafx.scene.image.Image;
import screens.Loader;

public enum Tag {
	//Player Character Types
	PLAYER("Player", "You, the Player"),
	ADVENTURER("Adventurer", "Your chosen character"),
	RANGER("Ranger", "Your chosen character"),
	FIGHTER("Fighter", "Your chosen character"),
	BERSERKER("Berserker", "Your chosen character"),
	ELEMENTALIST("Elementalist", "Your chosen character"),
	
	//Creature Behaviour Tags
	ALLY("Ally", "This creature is friendly to you and will help you against your foes.", Loader.allyIcon),
	SPELLCASTER("Spellcaster", "This creature is capable of casting spells against you.", Loader.spellcasterIcon) {
		public void unlock(Creature player) { player.setMagic(); }
	},
	ERRATIC("Erratic", "This creature moves in a random direction 40% of the time.", Loader.erraticIcon),
	VENOMOUS("Venomous", "This creature has a chance to poison you when they hit you.", Loader.venomousIcon),
	UNDEAD("Undead", "This creature heals when inflicted with poison damage.", Loader.undeadIcon),
	LEGENDARY("Legendary", ""),	//Maybe add a description and icon at some point, mostly here for grammar atm
	
	//Temporary Modifiers
	STUNNED(),	//Cannot take actions on your turn
	CONFUSED(),	//Each turn you move in a random direction and can't do anything else
	NOCAST(),	//Cannot cast spells or meditate
	NOQUAFF(),	//Cannot drink potions
	
	//Perks
	QUICK_LEARNER("Quick Learner", "You gain an additional 10% experience from all actions.", Loader.quickLearnerIcon),	//Starting perk for the adventurer
	FASTENED_ARMOR("Fastened Armor", "Your armor blocks an extra 8% of physical damage you take.", Loader.fastenArmorIcon), //Starting perk for the fighter
	IMPROVED_CRITICAL("Improved Critical", "Your critical chance is increased by 10%.", Loader.improvedCriticalIcon), 		//Starting perk for the ranger
	IMPROVED_CLEAVE("Improved Cleave", "Your cleaving damage is increased to 80%, instead of 50%.", Loader.improvedCleaveIcon), //Starting perk for the berserker
	ACOLYTES_MANA("Acolyte's Mana", "Your maximum mana pool is increased by five.", Loader.acolytesManaIcon) {
		public void unlock(Creature player) { player.setMana(player.mana()+5, player.maxMana()+5); }
		public boolean canUnlock(Creature player) { return (player.will() + player.intelligence() + player.spellcasting() >= 6); }
		public String prerequisites() { return "6 combined Will, Spellcasting and Intelligence"; }
	},	//Starting perk for the elementalist
	STRONG_ARROWS("Strong Arrows", "The chance for your fired arrows to break is reduced by 12%.", Loader.strongArrowsIcon),
	UNARMED_TRAINING("Unarmed Training", "Melee attacks without a weapon gain additional attack and damage.", Loader.unarmedTrainingIcon),
	SHIELD_TRAINING("Shield Training", "Shields do not decrease your attack modifier.", Loader.shieldTrainingIcon),
	THICK_SKIN("Thick Skin", "Your toughness increases by 1 and you regenerate health faster.", Loader.thickSkinIcon) {
		public void unlock(Creature player) { player.modifyStat(Stat.TOUGHNESS, 1); }
	},
	MEDIUM_ARMOR_MASTERY("Medium Armor Mastery", "Medium armor gives you +1 armor and +1 evasion while equipped.", Loader.mediumArmorSkillIcon),
	LIGHT_ARMOR_MASTERY("Light Armor Mastery", "Equipping items with the Light Armor property increases your evasion by 2.", Loader.lightArmorProficiencyIcon) {
		public boolean canUnlock(Creature player) { return (player.agility()+player.dexterity() >= 5); }
		public String prerequisites() { return "5 combined Agility and Dexterity"; }
	},
	HEAVY_ARMOR_MASTERY("Heavy Armor Mastery", "Heavy armor gives you 1 resistance to all slashing, crushing, and piercing damage.", Loader.heavyArmorSkillIcon) {
		public boolean canUnlock(Creature player) { return (player.brawn()+player.strength() >= 9); }
		public String prerequisites() { return "9 combined Brawn and Strength"; }
	},
	DEADLY_CRITICAL("Deadly Critical", "You deal an additional DEX-Accuracy damage on critical hits.", Loader.deadlyCriticalIcon) {
		public boolean canUnlock(Creature player) { return (player.is(Tag.IMPROVED_CRITICAL) && player.accuracy() >= 5); }
		public String prerequisites() { return "[Improved Critical] perk and 5 base Accuracy"; }
	},
	POLEARM_MASTER("Polearm Master", "When activating the reach attack ability, you can attack creatures 3 tiles away instead of 2.", Loader.polearmMasterIcon) {
		public boolean canUnlock(Creature player) { return (player.brawn() + player.strength() >= 5); }
		public String prerequisites() { return "5 combined Brawn and Strength"; }
	},
	STRONG_ARM("Strong Arm", "Throwing weapons deal additional damage equal to your level.", Loader.strongArmIcon) {
		public boolean canUnlock(Creature player) { return player.brawn() >= 4 || player.accuracy() >= 4; }
		public String prerequisites() { return "Either 4 base Brawn or 4 base Accuracy"; }
	},
	KNOCKBACK_ALL("Knockback All", "You gain the ability to knock back all enemies adjacent to you.", Loader.knockbackAllIcon) {
		public boolean canUnlock(Creature player) { return player.brawn() >= 4; }
		public String prerequisites() { return "4 base Brawn"; }
		public void unlock(Creature player) { player.addAbility(Abilities.knockbackAll()); }
	},
	IMPROVED_KNOCKBACK_ALL("Improved Knockback All", "When you activate the knockback all ability, you also make an attack against each adjacent enemy.", Loader.improvedKnockbackAllIcon) {
		public boolean canUnlock(Creature player) { return player.brawn() >= 6 && player.is(Tag.KNOCKBACK_ALL); }
		public String prerequisites() { return "[Knockback All] perk and 6 base Brawn"; }
		public void unlock(Creature player) { player.addAbility(Abilities.improvedKnockbackAll()); }
	},
	;
	
	private String text;
	public String text() { return text; }
	private String description;
	public String description() { return description; }
	private Image icon;
	public Image icon() { return icon; }
	
	private Tag(String text, String description, Image icon) {
		this.text = text;
		this.description = description;
		this.icon = icon;
	}
	private Tag(String text, String description) {
		this(text, description, null);
	}
	private Tag() {
		this("", "", null);
	}
	
	public static List<Tag> listOfPerks() {
		return new ArrayList<Tag>(EnumSet.range(QUICK_LEARNER, IMPROVED_KNOCKBACK_ALL));
	}
	
	public boolean isPerk() {
		return listOfPerks().contains(this);
	}
	
	public boolean canUnlock(Creature player) { return true; }	//To be overridden with prerequisites
	public String prerequisites() { return ""; }				//
	public void unlock(Creature player) { }						//

}
