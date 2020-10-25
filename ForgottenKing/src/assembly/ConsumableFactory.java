package assembly;

import creatures.Creature;
import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.image.Image;
import spells.Effect;
import world.World;

public class ConsumableFactory {
	private World world;
	
	private Image potionsIconFull = new Image(this.getClass().getResourceAsStream("resources/items/potions_full.png"));
	private Image potionOfHealingIcon = tools.ImageCrop.cropImage(potionsIconFull, 0, 0, 32, 32);
	private Image potionOfManaIcon = tools.ImageCrop.cropImage(potionsIconFull, 0, 32, 32, 32);
	private Image poisonPotionIcon = tools.ImageCrop.cropImage(potionsIconFull, 160, 0, 32, 32);
	private Image potionOfStrengthIcon = tools.ImageCrop.cropImage(potionsIconFull, 64, 0, 32, 32);
	private Image potionOfCuringIcon = tools.ImageCrop.cropImage(potionsIconFull, 32, 0, 32, 32);

	public ConsumableFactory(World world) {
		this.world = world;
	}
	
	public Item newRandomPotion(int z){
		switch ((int)(Math.random() * 5)){
		case 0: return newPotionOfHealing(z);
		case 1: return newPotionOfMana(z);
		case 2: return newPotionOfPoison(z);
		case 3: return newPotionOfStrength(z);
		default: return newPotionOfCuring(z);
		}
	}
	
	public Item newPotionOfHealing(int z){
	    Item item = new Item("Potion of Healing", ItemType.POTION, potionOfHealingIcon);
	    item.setEffect(Effects.health(15));
	    item.setDescription("Upon quaffing this potion you immediately heal 15 points of health.");
	    world.addAtEmptyLocation(item, z);
	    return item;
	}
	
	public Item newPotionOfMana(int z){
	    Item item = new Item("Potion of Mana", ItemType.POTION, potionOfManaIcon);
	    item.setEffect(Effects.mana(15));
	    item.setDescription("Upon quaffing this potion you immediately regenerate 15 points of mana.");
	    world.addAtEmptyLocation(item, z);
	    return item;
	}
	
	public Item newPotionOfPoison(int z){
	    Item item = new Item("Poison Potion", ItemType.POTION, poisonPotionIcon);
	    item.setEffect(Effects.poisoned(20,1, -100));
	    item.setDescription("Upon quaffing this potion you become poisoned for 20 turns, taking 1 damage per turn.");
	    item.addTag(ItemTag.THROWING);
	    world.addAtEmptyLocation(item, z);
	    return item;
	}
	
	public Item newPotionOfStrength(int z){
	    Item item = new Item("Potion of Strength", ItemType.POTION, potionOfStrengthIcon);
	    item.setEffect(Effects.strong(15,4));
	    item.setDescription("Upon quaffing this potion your strength increases by 4 for 15 turns.");
	    world.addAtEmptyLocation(item, z);
	    return item;
	}
	public Item newPotionOfCuring(int z){
	    Item item = new Item("Potion of Curing", ItemType.POTION, potionOfCuringIcon);
	    item.setEffect(Effects.curePoison());
	    item.setDescription("Quaffing this potion cures all toxic poisons currently effecting you.");
	    world.addAtEmptyLocation(item, z);
	    return item;
	}

}
