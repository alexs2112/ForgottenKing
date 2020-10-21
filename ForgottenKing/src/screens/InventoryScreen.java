package screens;

import creatures.Creature;
import items.Item;
import screens.Screen;

public class InventoryScreen extends InventoryBasedScreen {

	public InventoryScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "view";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		return new InspectScreen(player, item, this);
	}

}
