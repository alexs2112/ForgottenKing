package items;

public enum ItemType {
	RUNESTONE("Runestone"),		//Runestones
	ARROW("Arrow"),				//Ammunition for Bows
	BOLT("Bolt"),				//Ammunition for Crossbows
	STONE("Stone"),				//Ammunition for slings, usually with throwing tag
	POTION("Potion"),			//Quaffed to apply an effect
	BOOK("Book"),				//Read to learn from its spell list	
	
	WEAPON("Weapon"),			//Generic weapon type
	OFFHAND("Off-Hand"),		//Off-Hand for shields and dual wielding
	
	ARMOR("Armor"),				//
	RING("Ring"),				//
	AMULET("Amulet"),			// Equipment Slots
	BOOTS("Boots"),				//
	CLOAK("Cloak"),				//
	GLOVES("Gloves"),			//
	HEAD("Head"),				//
	;
	
	private String text;
	public String text() { return text; }
	private ItemType(String text) {
		this.text = text;
	}
	public boolean isAmmo() {
		return this == ARROW || this == BOLT || this == STONE;
	}
}
