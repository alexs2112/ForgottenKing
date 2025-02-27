package screens;

import creatures.Player;
import items.Item;
import items.ItemType;

public class QuaffScreen extends InventoryBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	public QuaffScreen(Player player) {
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
