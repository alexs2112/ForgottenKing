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
	}
	
	public void selectWorldCoordinate(){
        player.castSpell(spell, this.targets);
    }

}
