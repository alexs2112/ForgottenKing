package screens;

import creatures.Player;
import javafx.scene.Group;
import spells.TargetType;
import tools.Point;

public class FireWeaponScreen extends TargetBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	public FireWeaponScreen(Group root, Player player, int sx, int sy, Point p) {
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
