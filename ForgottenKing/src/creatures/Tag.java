package creatures;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

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
	SPELLCASTER("Spellcaster", "This creature is capable of casting spells against you.", Loader.spellcasterIcon),
	ERRATIC("Erratic", "This creature moves in a random direction 40% of the time.", Loader.erraticIcon),
	VENOMOUS("Venomous", "This creature has a chance to poison you when they hit you.", Loader.venomousIcon),
	UNDEAD("Undead", "This creature heals when inflicted with poison damage.", Loader.undeadIcon),
	LEGENDARY("Legendary", ""),	//Maybe add a description and icon at some point, mostly here for grammar atm
	
	//Perks
	QUICK_LEARNER("Quick Learner", "You gain an additional 10% experience from all actions.", Loader.quickLearnerIcon),	//Starting perk for the adventurer
	FASTENED_ARMOR("Fastened Armor", "Your armor blocks an extra 8% of physical damage you take.", Loader.fastenArmorIcon), //Starting perk for the fighter
	IMPROVED_CRITICAL("Improved Critical", "Your critical chance is increased by 10%.", Loader.improvedCriticalIcon), 		//Starting perk for the ranger
	IMPROVED_CLEAVE("Improved Cleave", "Your cleaving damage is increased to 80%, instead of 50%.", Loader.improvedCleaveIcon), //Starting perk for the berserker
	ACOLYTES_MANA("Acolyte's Mana", "Your maximum mana pool is increased by five.", Loader.acolytesManaIcon),
	DEADLY_CRITICAL("Deadly Critical", "You deal an additional DEX-Accuracy damage on critical hits.", Loader.deadlyCriticalIcon),
	STRONG_ARROWS("Strong Arrows", "The chance for your fired arrows to break is reduced by 12%.", Loader.strongArrowsIcon),
	LIGHT_ARMOR_PROFICIENCY("Light Armor Proficiency", "Equipping items with the Light Armor property increases your evasion by 2.", Loader.lightArmorProficiencyIcon),
	MEDIUM_ARMOR_SKILL("Medium Armor Skill", "You can equip items with the Medium Armor property without penalty.", Loader.mediumArmorSkillIcon),
	HEAVY_ARMOR_SKILL("Heavy Armor Skill", "You can equip items with the Heavy Armor property without penalty.", Loader.heavyArmorSkillIcon),
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
	
	public static List<Tag> listOfPerks() {
		return new ArrayList<Tag>(EnumSet.range(QUICK_LEARNER, HEAVY_ARMOR_SKILL));
	}
	
	public boolean isPerk() {
		return listOfPerks().contains(this);
	}

}
