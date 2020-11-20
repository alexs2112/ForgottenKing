package creatures;

import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import screens.ActivateAbilityScreen;
import screens.CastSpellScreen;
import screens.ReadSpellbookScreen;
import screens.Screen;
import screens.ThrowAtScreen;
import spells.Spell;

public class Hotkey {
	private Item item;
	public Item item() { return item; }
	private Ability ability;
	public Ability ability() { return ability; }
	private Spell spell;
	public Spell spell() { return spell; }
	public Hotkey(Item item) {
		this.item = item;
	}
	public Hotkey(Ability ability) {
		this.ability = ability;
	}
	public Hotkey(Spell spell) {
		this.spell = spell;
	}
	public Image image() {
		if (item != null)
			return item.image();
		if (ability != null)
			return ability.icon();
//		if (spell != null)
//			return spell.icon();
		return null;
	}
	
	public Screen use(Group root, Player player, int scrollX, int scrollY) {
		if (item != null) {
			if (item.equippable()) {
				player.equip(item);
			}
			if (item.spells() != null) {
				return new ReadSpellbookScreen(player, item);
			}
			if (item.is(ItemTag.THROWING)) {
				return new ThrowAtScreen(root, player, scrollX, scrollY, item, player.getAutoTarget());
			}
			if (item.type() == ItemType.POTION) {
				player.quaff(item);
			}
			return null;
		}
		if (ability != null) {
			if (ability.time() > 0) {
				player.notify(ability.name() + " is still on cooldown.");
				return null;
			}
			return new ActivateAbilityScreen(root, player, "Activate " + ability.name(), scrollX, scrollY, ability);
		}
		if (spell != null) {
			if (player.canCastSpell(spell))
				return new CastSpellScreen(root, player, "Cast " + spell.name(), scrollX, scrollY, spell);
    		else
    			return null;
		}
		return null;	//This shouldn't happen
	}

}
