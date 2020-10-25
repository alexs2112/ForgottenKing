package screens;

import creatures.Player;
import items.Item;
import screens.Screen;

public class DropScreen extends InventoryBasedScreen {

	public DropScreen(Player player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "drop";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.drop(item);
		return null;
	}

}
