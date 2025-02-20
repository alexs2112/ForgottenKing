package screens;

import creatures.Player;
import items.Item;
import javafx.scene.Group;

public class ThrowScreen extends InventoryBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	private int sx;
	private int sy;
	private Group root;
	private Item startAt;

	public ThrowScreen(Group root, Player player, int sx, int sy) {
		super(player);
		this.sx = sx;
		this.sy = sy;
		this.root = root;
		this.startAt = player.lastThrown();
		if (startAt != null) {
    		select = inventory.indexOf(startAt);
    	} else {
    		select = 0;
    	}
	}

	@Override
	protected String getVerb() {
		return "throw";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		return new ThrowAtScreen(root, player, sx, sy, item, player.getAutoTarget());
	}

}
