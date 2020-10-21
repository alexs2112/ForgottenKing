package items;

public enum ItemType {
	RUNESTONE("Runestone"),		//Runestones
	AMMO("Ammunition"),			//Ammunition tag to see if you can quiver them, this can be removed at some point
	ARROW("Arrow"),				//Ammunition for Bows
	BOLT("Bolt"),				//Ammunition for Crossbows
	STONE("Stone"),				//Ammunition for slings, usually with throwing tag
	POTION("Potion"),			//Quaffed to apply an effect
	BOOK("Book"),				//Read to learn from its spell list	
	WEAPON("Weapon"),			//
	ARMOR("Armor"),				//
	RING("Ring"),				//
	AMULET("Amulet"),			// Equipment Slots
	BOOTS("Boots"),				//
	CLOAK("Cloak"),				//
	GLOVES("Gloves");			//
	
	private String text;
	public String text() { return text; }
	private ItemType(String text) {
		this.text = text;
	}
}
