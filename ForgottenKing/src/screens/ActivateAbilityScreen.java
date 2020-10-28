package screens;

import creatures.Ability;
import creatures.Creature;
import javafx.scene.Group;
import spells.TargetType;
import tools.Point;

public class ActivateAbilityScreen extends TargetBasedScreen {
	private Ability ability;

	public ActivateAbilityScreen(Group root, Creature player, String caption, int sx, int sy, Ability ability) {
		super(root, player, caption, sx, sy);
		this.ability = ability;
		this.targetType = ability.targetType();
		this.spellRadius = ability.radius();
		this.range = ability.range();
		if (targetType == TargetType.PROJECTILE) {
			Point p = player.getAutoTarget();
			x = p.x - player.x;
			y = p.y - player.y;
		}
	}
	
	public Screen selectWorldCoordinate(){
        player.activateAbility(ability, getCreatureLocations());
        return null;
    }

}
