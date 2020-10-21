package features;

import creatures.Creature;
import world.World;

public class Entrance extends Feature {
	private int x;
	private int y;
	private int z;
	public int x() { return x; }
	public int y() { return y; }
	public int z() { return z; }

	public Entrance(int x, int y, int z) {
		super("Entrance", "An archway leading out of the dungeon", Loader.entrance);
		setType("UpStair");
		this.x = x;
		this.y = y;
		this.z = z;
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
		
	}

}
