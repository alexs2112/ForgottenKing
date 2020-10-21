package features;

import creatures.Creature;
import items.Item;
import tools.Point;
import world.World;

public class Door extends Feature {
	private int facing;
	private boolean closed;

	/**
	 * Creates a closed door feature
	 * @param facing: 0 = Up, 1 = Side
	 */
	public Door(int facing) {
		super("Door", "A door",Loader.closedDoorUp);
		
		if (facing == 1)
			this.image = Loader.closedDoorSide;
			
		setType("Bump");
		this.facing = facing;
		this.closed = true;
	}

	@Override
	public void interact(Creature creature, World world, int x, int y, int z) {
		if (closed == true) {
			closed = false;
			creature.notify("You open the door");
			if (facing == 0)
				image = Loader.openDoorUp;
			else if (facing == 1)
				image = Loader.openDoorSide;
			setType("CanClose");
		} else {
			closed = true;
			creature.notify("You close the door");
			if (facing == 0)
				image = Loader.closedDoorUp;
			else if (facing == 1)
				image = Loader.closedDoorSide;
			Point p = new Point(x,y,z);
			while (world.items(x,y,z) != null) {
				Item i = world.items(x,y,z).getFirstItem();
				world.items(x,y,z).remove(i);
				Point n = p.neighbors8().get(0);
				while (!world.tile(n.x, n.y, n.z).isGround())
					n = p.neighbors8().get(0);
				world.addAtEmptyLocation(i, n.x, n.y, n.z);
				if (world.items(x,y,z).getItems().size() == 0)
					world.removeInventory(x,y,z);
			}
				
			setType("Bump");
		}
	}

	@Override
	public boolean blockLineOfSight() {
		return closed;
	}

	@Override
	public boolean blockMovement() {
		return closed;
	}

}
