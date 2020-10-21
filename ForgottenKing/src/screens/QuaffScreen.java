package screens;

import creatures.Creature;
import items.Item;
import items.ItemType;

public class QuaffScreen extends InventoryBasedScreen {

	public QuaffScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "quaff";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.type() == ItemType.POTION;
	}

	@Override
	protected Screen use(Item item) {
		player.quaff(item);
		return null;
	}

}
