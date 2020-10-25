package screens;

import creatures.Creature;
import javafx.scene.Group;
import spells.Spell;

public class CastSpellScreen extends TargetBasedScreen {
	private Spell spell;

	public CastSpellScreen(Group root, Creature player, String caption, int sx, int sy, Spell spell) {
		super(root, player, caption, sx, sy);
		this.spell = spell;
		this.targetType = spell.targetType();
		this.spellRadius = spell.radius();
		this.range = spell.range();
	}
	
	public Screen selectWorldCoordinate(){
        player.castSpell(spell, getCreatureLocations());
        return null;
    }

}
