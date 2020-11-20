package screens;

import creatures.Player;
import javafx.scene.Group;
import spells.Spell;
import spells.TargetType;

public class CastSpellScreen extends TargetBasedScreen {
	private static final long serialVersionUID = 7769423305067121315L;
	private Spell spell;

	public CastSpellScreen(Group root, Player player, String caption, int sx, int sy, Spell spell) {
		super(root, player, caption, sx, sy);
		this.spell = spell;
		this.targetType = spell.targetType();
		this.radius = spell.radius();
		this.range = spell.range();
		if (spell.targetType() != TargetType.SELF && !spell.isBeneficial()) {
			x = player.getAutoTarget().x - player.x;
			y = player.getAutoTarget().y - player.y;
		}
	}
	
	public Screen selectWorldCoordinate(){
        player.castSpell(spell, getCreatureLocations(), targets);
        return null;
    }

}
