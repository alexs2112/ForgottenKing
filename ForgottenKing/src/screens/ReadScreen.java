package screens;

import creatures.Player;
import items.Item;
import items.ItemType;

public class ReadScreen extends InventoryBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	public ReadScreen(Player player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "read";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.spells() != null;
	}

	@Override
	protected Screen use(Item item) {
		if (item.type() == ItemType.BOOK)
			return new ReadSpellbookScreen(player, item);
		else
			return null;
	}

}
