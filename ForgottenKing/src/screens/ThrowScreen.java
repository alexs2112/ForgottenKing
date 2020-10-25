package screens;

import creatures.Player;
import items.Item;
import javafx.scene.Group;

public class ThrowScreen extends InventoryBasedScreen {
	private int sx;
	private int sy;
	private Group root;

	public ThrowScreen(Group root, Player player, int sx, int sy) {
		super(player);
		this.sx = sx;
		this.sy = sy;
		this.root = root;
		this.startAt = player.lastThrown();
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
