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
import tools.Icon;

@SuppressWarnings("serial")
public class TrinketFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;	
	
	private HashMap<Icon, String> descriptions;
	private List<Icon> images;
	
	public TrinketFactory() {
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
		Icon i = images.get(0);
		Item item = new Item("Ring of Poison Resistance", ItemType.RING, i) {
			public String shortDesc(Player player) {
				return "resist poison";
			}
		};
		item.addResistance(Type.POISON, 1);
		item.setDescription(descriptions.get(i) + ", it is etched with a serpentine design and glows a slight green.");
		item.setWeight(0.2);
		return item;
	}
	public Item newRingOfAttribute(int z) {
		Icon i;
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
		item.setWeight(0.2);
		return item;
	}
	public Item newRingOfStat(int z) {
		Icon i;
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
		item.setWeight(0.2);
		return item;
	}
	
	/**
	 * Set up new images and descriptions for every trinket every game
	 */
	private void initialize() {
		descriptions = new HashMap<Icon, String>();
		images = new ArrayList<Icon>();
		Icon brassRing  = new Icon("items/ring-full.png", 0, 0);
		Icon steelRing  = new Icon("items/ring-full.png", 32, 0);
		Icon goldDiamondRing  = new Icon("items/ring-full.png", 64, 0);
		Icon cobaltRing  = new Icon("items/ring-full.png", 96, 0);
		Icon glassRing  = new Icon("items/ring-full.png", 128, 0);
		Icon darkRing  = new Icon("items/ring-full.png", 160, 0);
		Icon bronzeRing  = new Icon("items/ring-full.png", 192, 0);
		Icon ivoryRing  = new Icon("items/ring-full.png", 224, 0);
		Icon silverOnyxRing  = new Icon("items/ring-full.png", 0, 32);
		Icon goldMoonRing  = new Icon("items/ring-full.png", 32, 32);
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
		for (Icon i : descriptions.keySet())
			images.add(i);
		Collections.shuffle(images);
	}

}
