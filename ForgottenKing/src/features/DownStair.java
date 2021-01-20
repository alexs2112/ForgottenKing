package features;

import creatures.Creature;
import creatures.Player;
import javafx.scene.paint.Color;
import world.World;

public class DownStair extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;
	private UpStair other;
	private int x;
	private int y;
	private int z;
	public int x() { return x; }
	public int y() { return y; }
	public int z() { return z; }

	public DownStair(UpStair upStair, int x, int y, int z) {
		super("Down Stair", "A staircase leading down", Loader.downStairs);
		setType(Type.DOWNSTAIR);
		this.other = upStair;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setUpStair(UpStair other) {
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
		if (creature instanceof Player) {
			if (world.hasEntered[other.z()] == false) {
				((Player)creature).fillBloodstone();
				world.hasEntered[other.z()] = true;
				creature.notify("You take a moment to rest while descending!", Color.WHITE);
				creature.notify("Bloodstone has regenerated!", Color.AQUAMARINE);
				creature.fillHP();
			}
		}
	}
	

}
