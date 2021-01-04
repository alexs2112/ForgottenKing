package assembly;

import creatures.Type;
import items.Item;
import items.ItemTag;
import items.ItemType;
import tools.Icon;

public class AmmoFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	
	private Icon rockIcon = new Icon("items/rocks-full.png", 96, 0);
	private Icon arrowImage = new Icon("items/ranged-full.png", 0, 64);
	private Icon dartImage = new Icon("items/ranged-full.png", 0, 96);
	private Icon boltImage = new Icon("items/ranged-full.png", 96, 64);
	private Icon shotImage = new Icon("items/ranged-full.png", 64, 0);
	
	public Item newRandomAmmo(int z) {
		int n = (int)(Math.random()*5);
		if (n == 0)
			return newArrow(z);
		else if (n == 1)
			return newDart(z);
		else if (n == 2)
			return newBolt(z);
		else if (n == 3) {
			if (z > 4)
				return newShot(z);
			else
				return newRandomAmmo(z);
		} else
		 	return newRock(z);
	}
	
	public Item newRock(int z) {
		Item rock = new Item("Rock", ItemType.STONE, rockIcon);
		rock.modifyThrownAttackValue(1);
		rock.addTag(ItemTag.THROWING);
		rock.setWeight(0.2);
		rock.setDescription("A solid stone dredged from the earth. Fits perfectly in the palm of your hand, or perhaps a sling.");
		return rock;
	}

	public Item newArrow(int z) {
		Item item = new Item("Arrow", ItemType.ARROW, arrowImage);
		item.setDamageType(Type.PIERCING);
		item.setRangedDamage(2, 2);
		item.setWeight(0);
		item.setDescription("A long shafted projectile intended to be shot with a shortbow or longbow, fletched with feathers at one end and a point at the other.");
		item.setSpawnQuantity((int)(Math.random()*5 + 2));
		return item;
	}
	public Item newDart(int z) {
		Item item = new Item("Dart", null, dartImage);
		item.setDamageType(Type.PIERCING);
		item.addTag(ItemTag.THROWING);
		item.setThrownDamage(1, 3);
		item.setWeight(0);
		item.setDescription("A thin, razor sharp piece of metal. With skill it can be thrown with deadly accuracy. If coated with a harmful substance it can deliver poison directly into the bloodstream of its intended target.");
		item.setSpawnQuantity((int)(Math.random()*5 + 2));
		return item;
	}
	public Item newBolt(int z) {
		Item item = new Item("Bolt", ItemType.BOLT, boltImage);
		item.setDamageType(Type.PIERCING);
		item.setRangedDamage(2, 3);
		item.setWeight(0);
		item.setDescription("A metal projectile, shorter than an arrow, intended to be shot from a crossbow.");
		item.setSpawnQuantity((int)(Math.random()*5 + 2));
		return item;
	}
	public Item newShot(int z) {
		Item item = new Item("Shot", ItemType.SHOT, shotImage);
		item.setDamageType(Type.CRUSHING);
		item.setRangedDamage(3, 4);
		item.setWeight(0);
		item.setDescription("Small round metal shots to be fired from a black powder weapon.");
		item.setSpawnQuantity((int)(Math.random()*5 + 2));
		return item;
	}
	
}
