package screens;

import creatures.Creature;
import items.Item;
import screens.Screen;
import world.World;

public class PickUpScreen extends InventoryBasedScreen {
	private Creature pickuper;
	public PickUpScreen(World world, Creature player) {
		super(world.items(player.x, player.y, player.z));
		pickuper = player;
	}

	@Override
	protected String getVerb() {
		return "pick up";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		pickuper.pickup(item);
		return null;
	}

}
