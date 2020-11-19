package items;

import java.util.HashMap;

import creatures.Type;
import javafx.scene.image.Image;
import tools.ImageCrop;

public class GetWeaponImages {
	private HashMap<Type, Integer> bases;
	private HashMap<Type, Integer> effects;
	private HashMap<BaseItem, Integer> items;
	private static Image weapons = new Image(Item.class.getResourceAsStream("resources/weapons-full.png"));
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
	}
	
	public void setWeaponImageAndEffect(Item item, int bonus, Type base, Type effect) {
		if (items.get(item.baseItem()) == null) {
			return;
		}
		Image newBase = item.image();
		if (base != null)
			newBase = ImageCrop.cropImage(weapons, bases.get(base), items.get(item.baseItem()), 32, 32);
		Image newEffect = null;
		if (effect != null && effect != Type.BLUE && effect != Type.GREEN)
			newEffect = ImageCrop.cropImage(weapons, effects.get(effect), items.get(item.baseItem()), 32, 32);
		else if (effect == Type.BLUE || effect == Type.GREEN) {
			if (base == null) {
				int x = 32;
				if (effect == Type.BLUE)
					x = 64;
				item.setImage(ImageCrop.cropImage(weapons, x, items.get(item.baseItem()), 32, 32));
				return;
			}
		}
		
		if (effect == null && base == null && bonus > 0) {
			item.setImage(ImageCrop.cropImage(weapons, 32, items.get(item.baseItem()), 32, 32));
			return;
		}
		
		item.setImage(newBase);
		item.setEffectImage(newEffect);
	}
}
