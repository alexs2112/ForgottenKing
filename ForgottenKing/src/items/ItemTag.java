package items;

import javafx.scene.image.Image;

public enum ItemTag {
	//Weapon Tags + Images
	DAGGER("Dagger", Loader.daggerIconGold),
	SWORD("Sword", Loader.swordIconGold),
	AXE("Axe", Loader.axeIconGold),
	MACE("Mace", Loader.maceIconGold),
	POLEARM("Polearm", Loader.polearmIconGold),
	BOW("Bow", Loader.bowIconGold),
	CROSSBOW("Crossbow", Loader.crossbowIconGold),
	SLING("Sling", Loader.slingIconGold),
	
	LIGHTARMOR("Light Armor"),		//If you have the light armor perk, increases your armor
	MEDIUMARMOR("Medium Armor"),	//Reduces ev by 5 - str unless you have medium armor perk
	HEAVYARMOR("Heavy Armor"),		//Reduces ev by 10 - str unless you have the heavy armor perk 
	THROWING("Throwing"),			//Can be thrown by enemies
	TWOHANDED("Two-Handed"),		//When shields are implemented, cannot use a shield with this
	LIGHT("Light"), 				//Can use accuracy for attack and damage mod if accuracy > brawn
	CLEAVING("Cleaving"),			//Hits enemies adjacent to both you and the target for 50% damage
	HIGHCRIT("High Critical"),		//Has a 10% increase in chance to crit
	VICTORYITEM("Victory Item");	//What the player needs to win, return to the surface with this in your possession	
	
	private String text;
	public String text() { return text; }
	private Image icon;
	public Image icon() { return icon; }
	
	private ItemTag(String text) {
		this(text, null);
	}
	private ItemTag(String text, Image icon) {
		this.text = text;
		this.icon = icon;
	}
	public boolean isWeapon() {
		return this == DAGGER || this == SWORD || this == AXE || this == MACE ||
				this == POLEARM || this == BOW || this == CROSSBOW || this == SLING;
	}

}
