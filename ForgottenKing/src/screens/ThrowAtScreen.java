package screens;

import creatures.Creature;
import items.Item;
import javafx.scene.Group;
import spells.TargetType;

public class ThrowAtScreen extends TargetBasedScreen {
	private Item item;

	public ThrowAtScreen(Group root, Creature player, int sx, int sy, Item item) {
		super(root, player, "Throw " + item.name() + " at?", sx, sy);
		this.item = item;
		this.targetType = TargetType.PROJECTILE;
	}

    public void selectWorldCoordinate(){
    	if (targets != null && targets.size() > 0)
    		player.throwItem(item, targets.get(targets.size()-1).x, targets.get(targets.size()-1).y, player.z);
    }

}
