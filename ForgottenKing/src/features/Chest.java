package features;

import creatures.Creature;
import items.Inventory;
import items.Item;
import tools.Icon;
import world.World;

//A chest includes other objects that contain items and break when bumped into, such as barrels and pots
public class Chest extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;
	private Inventory items;
	public void setItems(Inventory i) { items = i; }
	public void addItem(Item i) {
		if (items == null)
			items = new Inventory();
		items.add(i);
	}
	private boolean open;
	private Icon openIcon;
	public enum ChestType {
		CHEST,
		BARREL,
		POT;
	}
	private ChestType chestType;
	public ChestType chestType() { return chestType; }

	public Chest(ChestType chestType) {
		super("", "", null);
		this.chestType = chestType;
		open = false;
		setType(Type.BUMP);
		if (chestType == ChestType.BARREL) {
			icon = Loader.closedBarrel;
			openIcon = Loader.brokenBarrel;
			name = "Barrel";
			desc = "A barrel";
		} else { //if (chestType == ChestType.CHEST) {
			icon = Loader.closedChest;
			openIcon = Loader.openChest;
			name = "Chest";
			desc = "A chest";
		} 
	}

	@Override
	public boolean blockLineOfSight() {
		return false;
	}

	@Override
	public boolean blockMovement() {
		return !open;
	}

	@Override
	public void interact(Creature creature, World world, int x, int y, int z) {
		if (chestType == ChestType.BARREL)
			creature.doAction("break open the " + name);
		else
			creature.doAction("open the " + name);
		setType(null);
		open = true;
		this.icon = openIcon;
		if (items != null)
			for (Item i : items.getItems())
				world.addItem(i, x, y, z);
	}
	

}
