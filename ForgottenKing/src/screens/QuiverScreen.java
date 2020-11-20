package screens;

import creatures.Player;
import items.Item;
import screens.Screen;

public class QuiverScreen extends InventoryBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	public QuiverScreen(Player player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "quiver";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.isCompatibleAmmoWith(player.weapon());
	}

	@Override
	protected Screen use(Item item) {
		player.setQuiver(item);
		return null;
	}

}
