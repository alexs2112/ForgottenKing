package screens;

import creatures.Creature;
import items.Item;
import screens.Screen;

public class QuiverScreen extends InventoryBasedScreen {

	public QuiverScreen(Creature player) {
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
