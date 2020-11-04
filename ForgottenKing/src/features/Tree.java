package features;

import creatures.Creature;
import world.World;

public class Tree extends Feature {
	public Tree() {
		super("Tree", "A tree", Loader.treeNESW);
	}

	@Override
	public boolean blockLineOfSight() {
		return true;
	}

	@Override
	public boolean blockMovement() {
		return true;
	}

	@Override
	public void interact(Creature creature, World world, int x, int y, int z) {
	}

}
