package assembly;

import items.Item;
import items.ItemTag;
import items.ItemType;
import tools.Icon;

public class ArmorFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private Icon leatherArmorIcon = new Icon("items/armor-full.png", 0, 0);
	private Icon studdedLeatherArmorIcon = new Icon("items/armor-full.png", 32, 0);
	private Icon copperBreastplateIcon = new Icon("items/armor-full.png", 0, 192);
	private Icon paddedLeatherArmorIcon = new Icon("items/armor-full.png", 128, 0);
	private Icon ironCuirassIcon = new Icon("items/armor-full.png", 128, 160);
	private Icon chainMailIcon = new Icon("items/armor-full.png", 0, 256);
	
	private Icon leatherBootIcon = new Icon("items/boot-full.png", 160, 0);
	
	private Icon gloveIcon = new Icon("items/gloves-full.png", 0, 0);
	
	private Icon capIcon = new Icon("items/hat-full.png", 0, 32);
	
	private Icon bucklerIcon = new Icon("items/shield-full.png", 0, 0);
	private Icon shieldIcon = new Icon("items/shield-full.png", 64, 0);
	
	private Icon devBreastplateIcon = new Icon("items/armor-full.png", 224, 192);
	
	public Item newRandomArmor() {
		switch((int)(Math.random()*7)) {
		case 0: return newLeatherArmor();
		case 1: return newStuddedLeatherArmor();
		case 2: return newLeatherBoot();
		case 3: return newGlove();
		case 4: return newCap();
		case 5: return newBuckler();
		default: return newCopperBreastplate();
		}
	}
	
	public Item newLeatherArmor() {
		Item item = new Item("Leather Armor", ItemType.ARMOR, leatherArmorIcon);
		item.modifyArmorValue(2);
		item.addTag(ItemTag.LIGHTARMOR);
		item.setDescription("A suit made from layers of tanned animal hide, this light armour provides basic protection with almost no hindrance to movement.");
		item.setWeight(2);
		return item;
	}
	public Item newStuddedLeatherArmor() {
		Item item = new Item("Studded Leather Armor", ItemType.ARMOR, studdedLeatherArmorIcon);
		item.modifyArmorValue(4);
		item.addTag(ItemTag.MEDIUMARMOR);
		item.setWeight(2);
		item.setDescription("A set of leather armor enhanced with metal studs providing additional defense, at the sake of swift movement.");
		return item;
	}
	public Item newCopperBreastplate() {
		Item item = new Item("Copper Breastplate", ItemType.ARMOR, copperBreastplateIcon);
		item.modifyArmorValue(6);
		item.addTag(ItemTag.HEAVYARMOR);
		item.setWeight(3);
		item.setDescription("A set of interlocking copper plates. It is decent armor for the dungeon, if a bit heavy.");
		return item;
	}
	
	public Item newLeatherBoot() {
		Item item = new Item("Leather Boots", ItemType.BOOTS, leatherBootIcon);
		item.modifyArmorValue(1);
		item.setDescription("A pair of leather boots. They mildly increases your armor without hindering your movement.");
		return item;
	}
	
	public Item newGlove() {
		Item item = new Item("Gloves", ItemType.GLOVES, gloveIcon);
		item.modifyArmorValue(1);
		item.setDescription("A pair of leather gloves. They mildly increases your armor without hindering your movement.");
		return item;
	}
	
	public Item newCap() {
		Item item = new Item("Cap", ItemType.HEAD, capIcon);
		item.modifyArmorValue(1);
		item.setDescription("A small leather cap. It mildly increases your armor without hindering your movement.");
		return item;
	}

	public Item newBuckler() {
		Item item = new Item("Buckler", ItemType.OFFHAND, bucklerIcon);
		item.modifyArmorValue(2);
		item.modifyAttackValue(-2);
		item.setDescription("A small wooden shield. Effective for blocking attacks while slightly impeding your offensive capabilities.");
		item.addTag(ItemTag.SHIELD);
		return item;
	}
	
	public Item newPaddedLeatherArmor() {
		Item item = new Item("Padded Leather Armor", ItemType.ARMOR, paddedLeatherArmorIcon);
		item.modifyArmorValue(4);
		item.addTag(ItemTag.LIGHTARMOR);
		item.setDescription("Layers of cloth padding to increase the potency of standard leather.");
		item.setWeight(2);
		return item;
	}
	
	public Item newIronCuirass() {
		Item item = new Item("Iron Cuirass", ItemType.ARMOR, ironCuirassIcon);
		item.modifyArmorValue(6);
		item.addTag(ItemTag.MEDIUMARMOR);
		item.setDescription("A small piece of iron plate, strapped together and worn around the chest. Protects your vitals without hindering movement too much.");
		item.setWeight(3);
		return item;
	}
	
	public Item newChainMail() {
		Item item = new Item("Chain Mail", ItemType.ARMOR, chainMailIcon);
		item.modifyArmorValue(8);
		item.addTag(ItemTag.HEAVYARMOR);
		item.setDescription("A suit made entirely out of tiny metal rings. Offering strong protection against weapons, claws and carnassials, chain mail hinders quick and stealthy movement.");//*
		item.setWeight(4);
		return item;
	}
	
	public Item newShield() {
		Item item = new Item("Shield", ItemType.OFFHAND, shieldIcon);
		item.modifyArmorValue(4);
		item.modifyAttackValue(-3);
		item.setDescription("A piece of wood and metal, to be strapped on one arm for defence. It is cumbersome to wear."); //*
		item.addTag(ItemTag.SHIELD);
		return item;
	}
	
	
	public Item newDevBreastplate() {
		Item armor = new Item("Armor of the Dev", ItemType.ARMOR, devBreastplateIcon);
		armor.modifyArmorValue(500);
		return armor;
	}
}
