package items;

import javafx.scene.image.Image;

public enum ItemTag {
	//Weapon Tags + Images
	DAGGER("Dagger", "", Loader.daggerIconGold),
	SWORD("Sword", "", Loader.swordIconGold),
	AXE("Axe", "", Loader.axeIconGold),
	MACE("Mace", "", Loader.maceIconGold),
	POLEARM("Polearm", "", Loader.polearmIconGold),
	BOW("Bow", "A bow, fires quivered arrows.", Loader.bowIconGold),
	CROSSBOW("Crossbow", "A crossbow, fires quivered bolts.", Loader.crossbowIconGold),
	SLING("Sling", "A sling, fires quivered stones.", Loader.slingIconGold),
	
	SHIELD("Shield", "Equipped in your off hand, reduces your attack by 2 if you do not have the [Shield Training] perk.", null),
	LIGHTARMOR("Light Armor", "The lightest of armor, you do not have to be trained in its use to be able to wear it.", Loader.lightArmorTag),
	MEDIUMARMOR("Medium Armor", "Applies a debuff of 2 to your speed, evasion and attack if your brawn is less than 5.", Loader.mediumArmorTag),
	HEAVYARMOR("Heavy Armor", "Applies a debuff of 5 to your speed, evasion and attack if your brawn is less than 8.", Loader.heavyArmorTag), 
	THROWING("Throwing", "A good object for throwing, this either deals additional damage when thrown or applies an effect.", Loader.throwingTag),
	TWOHANDED("Two-Handed", "A heavy weapon, you cannot equip your off hand when wielding this."),
	LIGHT("Light", "If your accuracy is greater than your brawn, it is applied for attack and damage rolls instead.", Loader.lightWeaponTag),
	VERSATILE("Versatile", "If you are wielding this one handed weapon with both hands, you gain +2 to attack."),
	CLEAVING("Cleaving", "Attacks with this weapon attack each enemy adjacent to you and your target for 50% damage.", Loader.cleavingTag),
	HIGHCRIT("High Critical", "Increases your critical chance by 10% when wielding this weapon.", Loader.highCritTag),
	VICTORYITEM("Victory Item", "The end of your quest is in sight, return to the surface with this in your possession to win");	
	
	private String text;
	public String text() { return text; }
	private Image icon;
	public Image icon() { return icon; }
	private String description;
	public String description() { return description; }
	
	private ItemTag(String text, String description) {
		this(text, description, null);
	}
	private ItemTag(String text, String description, Image icon) {
		this.text = text;
		this.icon = icon;
		this.description = description;
	}
	public boolean isWeapon() {
		return this == DAGGER || this == SWORD || this == AXE || this == MACE ||
				this == POLEARM || this == BOW || this == CROSSBOW || this == SLING;
	}

}
