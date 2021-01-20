package items;

import java.util.HashMap;

import creatures.Type;
import tools.Icon;

public class GetWeaponImages implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private HashMap<Type, Integer> bases;
	private HashMap<Type, Integer> effects;
	private HashMap<BaseItem, Integer> items;
	
	public GetWeaponImages() {
		bases = new HashMap<Type, Integer>();
		effects = new HashMap<Type, Integer>();
		items = new HashMap<BaseItem, Integer>();
		bases.put(Type.FIRE, 96);
		bases.put(Type.COLD, 128);
		bases.put(Type.AIR, 160);
		bases.put(Type.POISON, 192);
		bases.put(Type.LIGHT, 224);
		bases.put(Type.DARK, 256);
		effects.put(Type.FIRE, 288);
		effects.put(Type.COLD, 320);
		effects.put(Type.AIR, 352);
		effects.put(Type.POISON, 384);
		effects.put(Type.LIGHT, 416);
		effects.put(Type.DARK, 448);
		items.put(BaseItem.DAGGER, 0);
		items.put(BaseItem.SHORTSWORD, 32);
		items.put(BaseItem.SPEAR, 64);
		items.put(BaseItem.HANDAXE, 96);
		items.put(BaseItem.MACE, 128);
		items.put(BaseItem.SHORTBOW, 160);
		items.put(BaseItem.HANDCROSSBOW, 192);
		items.put(BaseItem.ORCISHDAGGER, 224);
		items.put(BaseItem.CUTLASS, 256);
		//items.put(BaseItem.BONESWORD, 288);
		items.put(BaseItem.BOARDINGAXE, 320);
		items.put(BaseItem.ORCWARAXE, 352);
		//items.put(BaseItem.MUSKET, 384);
		items.put(BaseItem.FLINTLOCK, 416);
		//items.put(BaseItem.CROSSBOW, 448);
		items.put(BaseItem.LONGBOW, 480);
	}
	
	public void setWeaponImageAndEffect(Item item, int bonus, Type base, Type effect) {
		if (items.get(item.baseItem()) == null) {
			return;
		}
		
		Icon newBase = item.icon();
		
		if (base != null)
			newBase = new Icon("items/weapons-full.png", bases.get(base), items.get(item.baseItem()));
		Icon newEffect = null;
		if (effect != null && effect != Type.BLUE && effect != Type.GREEN)
			newEffect = new Icon("items/weapons-full.png", effects.get(effect), items.get(item.baseItem()));
		else if (effect == Type.BLUE || effect == Type.GREEN) {
			if (base == null) {
				int x = 32;
				if (effect == Type.BLUE)
					x = 64;
				item.setIcon(new Icon("items/weapons-full.png", x, items.get(item.baseItem()), 32, 32));
				return;
			}
		}
		
		if (effect == null && base == null && bonus > 0) {
			item.setIcon(new Icon("items/weapons-full.png", 32, items.get(item.baseItem()), 32, 32));
			return;
		}
		
		item.setIcon(newBase);
		item.setEffectIcon(newEffect);
	}
}
