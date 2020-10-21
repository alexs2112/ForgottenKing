package screens;

import tools.Line;
import tools.Point;
import creatures.Creature;
import javafx.scene.Group;
import spells.TargetType;

public class FireWeaponScreen extends TargetBasedScreen {

	public FireWeaponScreen(Group root, Creature player, int sx, int sy) {
		super(root, player, "Fire " + player.weapon().name() + " at?", sx, sy);
		this.targetType = TargetType.PROJECTILE;
	}
	
	public boolean isAcceptable(int x, int y) {
        if (!player.canSee(x, y, player.z))
            return false;
    
        for (Point p : new Line(player.x, player.y, x, y)){
            if (!player.realTile(p.x, p.y, player.z).isGround())
                return false;
        }
    
        return true;
    }

    public void selectWorldCoordinate(){
    	Creature other = null;
    	if (creatures != null && creatures.size() > 0)
    		other = creatures.get(0);
    
        if (other == null)
            player.notify("There's no one there to fire at.");
        else
            player.rangedWeaponAttack(other);
    }

}
