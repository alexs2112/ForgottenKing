package assembly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import creatures.Attribute;
import creatures.Player;
import creatures.Stat;
import creatures.Type;
import items.Item;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class TrinketFactory {
	private World world;
	
	private Image ringFull = new Image(this.getClass().getResourceAsStream("resources/items/ring_full.png"));
	private HashMap<Image, String> descriptions;
	private List<Image> images;
	
	private Image devRingIcon = tools.ImageCrop.cropImage(ringFull, 32, 32, 32, 32);
	
	public TrinketFactory(World world) {
		this.world = world;
		initialize();
	}
	public Item newRandomRing(int z) {
		int roll = (int)(Math.random() * 3);
		if (roll == 0)
			return newRingOfPoisonResistance(z);
		else if (roll == 1)
			return newRingOfStat(z);
		else {
			if (z > 1)
				return newRingOfAttribute(z);
			else
				return newRandomRing(z);
		}
	}
	
	public Item newRingOfPoisonResistance(int z) {
		Image i = images.get(0);
		Item item = new Item("Ring of Poison Resistance", ItemType.RING, i) {
			public String shortDesc(Player player) {
				return "resist poison";
			}
		};
		item.addResistance(Type.POISON, 1);
		item.setDescription(descriptions.get(i) + ", it is etched with a serpentine design and glows a slight green.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newRingOfAttribute(int z) {
		Image i;
		Attribute a;
		int strength = z/5 + 1;
		String descText = "";
		switch((int)(Math.random()*3)) {
		case 0: i = images.get(1); a = Attribute.STR; descText += "stronger"; break;
		case 1: i = images.get(2); a = Attribute.DEX; descText += "faster"; break;
		default: i = images.get(3); a = Attribute.INT; descText += "smarter"; break;
		}
		Item item = new Item("+" + strength + " Ring of " + a.text(), ItemType.RING, i);
		item.setDescription(descriptions.get(i) + ", you feel " + descText + " when you put it on.");
		item.setAttribute(a, strength);
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newRingOfStat(int z) {
		Image i;
		Stat a;
		int strength = z/3 + 1;
		String descText = "";
		switch((int)(Math.random()*6)) {
		case 0: i = images.get(4); a = Stat.TOUGHNESS; descText += "stronger"; break;
		case 1: i = images.get(5); a = Stat.BRAWN; descText += "stronger"; break;
		case 2: i = images.get(6); a = Stat.AGILITY; descText += "faster"; break;
		case 3: i = images.get(7); a = Stat.ACCURACY; descText += "faster"; break;
		case 4: i = images.get(8); a = Stat.WILL; descText += "smarter"; break;
		default: i = images.get(9); a = Stat.SPELLCASTING; descText += "smarter"; break;
		}
		Item item = new Item("+" + strength + " Ring of " + a.text(), ItemType.RING, i);
		item.setDescription(descriptions.get(i) + ", you feel " + descText + " when you put it on.");
		item.setStat(a, strength);
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	public Item newDevRing(int z) {
		Item ring = new Item("Ring of the Dev", ItemType.RING, devRingIcon);
		ring.modifyAttackValue(10);
		ring.modifyArmorValue(10);
		world.addAtEmptyLocation(ring, z);
		return ring;
	}
	
	/**
	 * Set up new images and descriptions for every trinket every game
	 */
	private void initialize() {
		descriptions = new HashMap<Image, String>();
		images = new ArrayList<Image>();
		Image brassRing  = tools.ImageCrop.cropImage(ringFull, 0, 0, 32, 32);
		Image steelRing  = tools.ImageCrop.cropImage(ringFull, 32, 0, 32, 32);
		Image goldDiamondRing  = tools.ImageCrop.cropImage(ringFull, 64, 0, 32, 32);
		Image cobaltRing  = tools.ImageCrop.cropImage(ringFull, 96, 0, 32, 32);
		Image glassRing  = tools.ImageCrop.cropImage(ringFull, 128, 0, 32, 32);
		Image darkRing  = tools.ImageCrop.cropImage(ringFull, 160, 0, 32, 32);
		Image bronzeRing  = tools.ImageCrop.cropImage(ringFull, 192, 0, 32, 32);
		Image ivoryRing  = tools.ImageCrop.cropImage(ringFull, 224, 0, 32, 32);
		Image silverOnyxRing  = tools.ImageCrop.cropImage(ringFull, 0, 32, 32, 32);
		Image goldMoonRing  = tools.ImageCrop.cropImage(ringFull, 32, 32, 32, 32);
		descriptions.put(brassRing, "A small brass ring");
		descriptions.put(steelRing, "A small steel ring");
		descriptions.put(goldDiamondRing, "A golden ring encrusted with a diamond");
		descriptions.put(cobaltRing, "A startlingly blue ring");
		descriptions.put(glassRing, "A ring made of glass");
		descriptions.put(darkRing, "A dark ring of blackened metal");
		descriptions.put(bronzeRing, "A small bronze ring");
		descriptions.put(ivoryRing, "A ring made of polished ivory");
		descriptions.put(silverOnyxRing, "A silver ring encrusted with a large black onyx");
		descriptions.put(goldMoonRing, "A golden ring encrusted with a shard of moonstone");
		for (Image i : descriptions.keySet())
			images.add(i);
		Collections.shuffle(images);
	}

}
