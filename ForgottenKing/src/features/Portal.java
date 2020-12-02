package features;

import creatures.Creature;
import tools.Point;
import world.World;

public class Portal extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;

	public Portal() {
		super("Portal", "A portal to the next area", Loader.portalImage);
		setType("DownStair");
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
		Point p = world.getEmptyLocation(z+1);
		creature.moveTo(p.x, p.y, z+1);
	}

}
