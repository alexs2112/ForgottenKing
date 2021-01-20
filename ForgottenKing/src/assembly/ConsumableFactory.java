package assembly;

import items.Item;
import items.ItemTag;
import items.ItemType;
import tools.Icon;

public class ConsumableFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	
	private Icon potionOfHealingIcon = new Icon("items/potions-full.png", 0, 0);
	private Icon potionOfManaIcon = new Icon("items/potions-full.png", 0, 32);
	private Icon poisonPotionIcon = new Icon("items/potions-full.png", 160, 0);
	private Icon potionOfStrengthIcon = new Icon("items/potions-full.png", 64, 0);
	private Icon potionOfCuringIcon = new Icon("items/potions-full.png", 32, 0);
	private Icon potionOfBloodIcon = new Icon("items/potions-full.png", 32, 32);
	
	public Item newRandomPotion(){
		switch ((int)(Math.random() * 6)){
		case 0: return newPotionOfHealing();
		case 1: return newPotionOfMana();
		case 2: return newPotionOfPoison();
		case 3: return newPotionOfStrength();
		case 4: return newPotionOfCuring();
		default: return newPotionOfBlood();
		}
	}
	
	public Item newPotionOfHealing(){
	    Item item = new Item("Potion of Healing", ItemType.POTION, potionOfHealingIcon);
	    item.setEffect(Effects.health(15));
	    item.setDescription("Upon quaffing this potion you immediately heal 15 points of health.");
	    item.setWeight(0.5);
	    return item;
	}
	
	public Item newPotionOfMana(){
	    Item item = new Item("Potion of Mana", ItemType.POTION, potionOfManaIcon);
	    item.setEffect(Effects.mana(15));
	    item.setDescription("Upon quaffing this potion you immediately regenerate 15 points of mana.");
	    item.setWeight(0.5);
	    return item;
	}
	
	public Item newPotionOfPoison(){
	    Item item = new Item("Poison Potion", ItemType.POTION, poisonPotionIcon);
	    item.setEffect(Effects.poisoned(20,1));
	    item.setDescription("Upon quaffing this potion you become poisoned for 20 turns, taking 1 damage per turn.");
	    item.setWeight(0.5);
	    item.addTag(ItemTag.THROWING);
	    return item;
	}
	
	public Item newPotionOfStrength(){
	    Item item = new Item("Potion of Strength", ItemType.POTION, potionOfStrengthIcon);
	    item.setEffect(Effects.strong(15,4));
	    item.setDescription("Upon quaffing this potion your strength increases by 4 for 15 turns.");
	    item.setWeight(0.5);
	    return item;
	}
	public Item newPotionOfCuring(){
	    Item item = new Item("Potion of Curing", ItemType.POTION, potionOfCuringIcon);
	    item.setEffect(Effects.curePoison());
	    item.setDescription("Quaffing this potion cures all toxic poisons currently effecting you.");
	    item.setWeight(0.5);
	    return item;
	}
	public Item newPotionOfBlood(){
	    Item item = new Item("Potion of Blood", ItemType.POTION, potionOfBloodIcon);
	    item.setEffect(Effects.addBloodstone(1));
	    item.setDescription("Quaffing this potion will charge your bloodstone by 1.");
	    item.setWeight(0.5);
	    return item;
	}

}
