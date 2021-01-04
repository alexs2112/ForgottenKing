package features;

import creatures.Creature;
import creatures.Tag;
import items.Item;
import javafx.scene.image.Image;
import tools.Point;
import world.World;

public class Door extends Feature {
	private static final long serialVersionUID = 7769423305067121315L;
	private boolean closed;
	private World world;
	private int x;
	private int y;
	private int z;
	
	/**
	 * Creates a door feature, initially closed
	 */
	public Door() {
		super("Door", "A door", Loader.closedDoorUp);
		setType(Type.BUMP);
		this.closed = true;
	}
	
	@Override
	public void setLocation(World world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void interact(Creature creature, World world, int x, int y, int z) {
		if (closed == true) {
			if (creature.is(Tag.NODOOR))
				return;
			closed = false;
			creature.doAction("open the door");
			setType(Type.CANCLOSE);
		} else {
			closed = true;
			creature.doAction("close the door");
			Point p = new Point(x,y,z);
			while (world.items(x,y,z) != null) {
				Item i = world.items(x,y,z).getFirstItem();
				world.items(x,y,z).remove(i);
				Point n = p.neighbors8().get(0);
				while (!world.tile(n.x, n.y, n.z).isGround())
					n = p.neighbors8().get(0);
				world.addAtEmptyLocation(i, n.x, n.y, n.z, 1);
				if (world.items(x,y,z).getItems().size() == 0)
					world.removeInventory(x,y,z);
			}
			setType(Type.BUMP);
		}
	}
	
	@Override
	public Image getImage() {
		if (world.tile(x+1, y, z).isWall() && world.tile(x-1, y, z).isWall()) {
			if (closed)
				return Loader.closedDoorUp.image();
			else
				return Loader.openDoorUp.image();
		} else {
			if (closed)
				return Loader.closedDoorSide.image();
			else
				return Loader.openDoorSide.image();
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
