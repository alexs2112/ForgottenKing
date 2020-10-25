package screens;

import creatures.Creature;
import javafx.scene.Group;
import spells.TargetType;
import tools.Point;

public class FireWeaponScreen extends TargetBasedScreen {

	public FireWeaponScreen(Group root, Creature player, int sx, int sy, Point p) {
		super(root, player, "Fire a " + player.quiver().name() + " at?", sx, sy);
		x = p.x - player.x;
		y = p.y - player.y;
		this.targetType = TargetType.PROJECTILE;
	}

    public Screen selectWorldCoordinate(){
        if (targets != null && targets.size() > 0)
    		player.fireItem(player.quiver(), targets.get(targets.size()-1).x, targets.get(targets.size()-1).y, player.z);
        return null;
    }

}
