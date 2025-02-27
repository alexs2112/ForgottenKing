package screens;

import creatures.Player;
import items.Item;
import screens.Screen;

public class InventoryScreen extends InventoryBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	public InventoryScreen(Player player) {
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
		return new InspectItemScreen(player, item, this);
	}

}
