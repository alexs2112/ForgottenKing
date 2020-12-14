package features;

import creatures.Creature;
import creatures.Player;
import tools.Point;
import world.World;

public class Portal extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;

	public Portal() {
		super("Portal", "A portal to the next area", Loader.portalImage);
		setType(Type.DOWNSTAIR);
	}

	@Override
	public boolean blockLineOfSight() {
		return false;
	}

	@Override
	public boolean blockMovement() {
		return false;
	}

	@Override
	public void interact(Creature creature, World world, int x, int y, int z) {
		if (world.depth() == creature.z + 1) {
			((Player)creature).hasWon = true;
			return;
		}
		Point p = world.getEmptyLocation(z+1);
		creature.moveTo(p.x, p.y, z+1);
	}

}
