package screens;

import creatures.Player;
import items.Item;
import screens.Screen;

public class EquipScreen extends InventoryBasedScreen {

	public EquipScreen(Player player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "equip";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.equippable();
	}

	@Override
	protected Screen use(Item item) {
		player.equip(item);
		return null;
	}

}
