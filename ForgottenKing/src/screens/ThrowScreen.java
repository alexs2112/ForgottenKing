package screens;

import creatures.Creature;
import items.Item;
import javafx.scene.Group;
import tools.Point;

public class ThrowScreen extends InventoryBasedScreen {
	private int sx;
	private int sy;
	private Group root;

	public ThrowScreen(Group root, Creature player, int sx, int sy) {
		super(player);
		this.sx = sx;
		this.sy = sy;
		this.root = root;
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
