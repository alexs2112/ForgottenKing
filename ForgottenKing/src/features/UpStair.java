package features;

import creatures.Creature;
import world.World;

public class UpStair extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;
	private DownStair other;
	private int x;
	private int y;
	private int z;
	public int x() { return x; }
	public int y() { return y; }
	public int z() { return z; }

	public UpStair(DownStair downStair, int x, int y, int z) {
		super("Up Stair", "A staircase leading up", Loader.upStairs);
		setType("UpStair");
		this.other = downStair;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setDownStair(DownStair other) {
		this.other = other;
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
		creature.moveTo(other.x(), other.y(), other.z());
	}

}
