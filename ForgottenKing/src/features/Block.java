package features;

import creatures.Creature;
import world.World;

//A generic feature that blocks movement and line of sight
public class Block extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;
	public enum Type {
		TREE;
	}

	public Block(Type t) {
		super("", "", null);
		if (t == Type.TREE) {
			name = "Tree";
			desc = "A tree";
			image = Loader.treeNESW;
		}
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
