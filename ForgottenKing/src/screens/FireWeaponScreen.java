package screens;

import creatures.Creature;
import javafx.scene.Group;
import spells.TargetType;

public class FireWeaponScreen extends TargetBasedScreen {

	public FireWeaponScreen(Group root, Creature player, int sx, int sy) {
		super(root, player, "Fire a " + player.quiver().name() + " at?", sx, sy);
		this.targetType = TargetType.PROJECTILE;
	}

    public void selectWorldCoordinate(){
        if (targets != null && targets.size() > 0)
    		player.fireItem(player.quiver(), targets.get(targets.size()-1).x, targets.get(targets.size()-1).y, player.z);
    }

}
