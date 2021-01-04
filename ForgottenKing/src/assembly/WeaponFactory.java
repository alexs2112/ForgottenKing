package assembly;

import creatures.Type;
import items.BaseItem;
import items.Enchantment;
import items.Item;
import items.ItemTag;
import items.ItemType;
import tools.Icon;

public class WeaponFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private Enchantment enchant;
	public void setCreatureFactory(CreatureFactory f) { 
		enchant.setCreatureFactory(f);
	}
	
	//Images	
	private Icon daggerIcon = new Icon("items/weapons-full.png", 0, 0);
	private Icon shortSwordIcon = new Icon("items/weapons-full.png", 0, 32);
	private Icon spearIcon = new Icon("items/weapons-full.png", 0, 64);
	private Icon handaxeIcon = new Icon("items/weapons-full.png", 0, 96);
	private Icon maceIcon = new Icon("items/weapons-full.png", 0, 128);
	private Icon shortbowIcon = new Icon("items/weapons-full.png", 0, 160);
	private Icon handCrossbowIcon = new Icon("items/weapons-full.png", 0, 192);
	
	private Icon orcishDaggerIcon = new Icon("items/weapons-full.png", 0, 224);
	private Icon cutlassIcon = new Icon("items/weapons-full.png", 0, 256);
	//private Icon boneSwordIcon = new Icon("items/weapons-full.png", 0, 288);
	private Icon boardingAxeIcon = new Icon("items/weapons-full.png", 0, 320);
	private Icon orcWarAxeIcon = new Icon("items/weapons-full.png", 0, 352);
	//private Icon musketIcon = new Icon("items/weapons-full.png", 0, 384);
	private Icon flintlockIcon = new Icon("items/weapons-full.png", 0, 416);
	//private Icon crossbowIcon = new Icon("items/weapons-full.png", 0, 448);
	private Icon longbowIcon = new Icon("items/weapons-full.png", 0, 480);
	
	private Icon devSwordIcon = new Icon("items/long_weapon_full.png", 160, 32);
	
	public WeaponFactory() {
		enchant = new Enchantment();
	}
	
	public Item newRandomMeleeWeapon(int z) {
		Item i = null;
		int tier = (z/5) + 1;
		if (tier == 1) {
			switch((int)(Math.random()*5)) {
			case 0: i = newDagger(); break;
			case 1: i = newShortSword(); break;
			case 2: i = newHandaxe(); break;
			case 3: i = newMace(); break;
			default: i = newSpear(); break;
			}
		} else if (tier == 2) {
			switch((int)(Math.random()*4)) {
			case 0: i = newOrcishDagger(); break;
			case 1: i = newCutlass(); break;
			case 2: i = newBoardingAxe(); break;
			default: i = newOrcWarAxe(); break;
			}
		}
		return i;
	}
	
	public Item newRandomWeapon(int z) {
		return newRandomWeapon(z,false);
	}
	public Item newRandomWeapon(int z, boolean forceEnchant) {
		Item i = null;
		int tier = (z/5) + 1;
		if (tier == 1) {
			switch((int)(Math.random()*7)) {
			case 0: i = newDagger(); break;
			case 1: i = newShortSword(); break;
			case 2: i = newShortbow(); break;
			case 3: i = newHandaxe(); break;
			case 4: i = newMace(); break;
			case 5: i = newHandCrossbow(); break;
			default: i = newSpear(); break;
			}
		} else if (tier == 2) {
			switch((int)(Math.random()*6)) {
			case 0: i = newOrcishDagger(); break;
			case 1: i = newCutlass(); break;
			case 2: i = newBoardingAxe(); break;
			case 3: i = newOrcWarAxe(); break;
			case 4: i = newFlintlock(); break;
			default: i = newLongbow(); break;
			}
		}
		/**
		 * Handle Enchantments
		 */
		if (forceEnchant || Math.random() * 100 < 10 + (z%5) * 5 + z) {
			int t = (z/4) + 1;
			int bonus = 0;
			boolean effect = false;
			boolean base = false;
			if (Math.random()*100 < 80) {
				bonus = (int)(Math.random()*3) + t;
			} if (Math.random()*100 < 30) {
				effect = true;
			} if (Math.random()*100 < 40) {
				base = true;
			}
			
			//GET RID OF THIS LATER
			if (i.isRanged()) {
				effect = false;
				base = false;
			}
			
			enchant.enchantWeapon(i, t, bonus, effect, base);
		}
		return i;
	}
	
	public Item newDagger() {
		Item item = new Item("Dagger", ItemType.WEAPON, daggerIcon);
		item.setBaseItem(BaseItem.DAGGER);
		item.modifyAttackValue(2);
		item.setDamage(1,2);
		item.modifyWeaponDelay(-2);
		item.addTag(ItemTag.DAGGER);
		item.addTag(ItemTag.LIGHT);
		item.addTag(ItemTag.HIGHCRIT);
		item.setDamageType(Type.PIERCING);
		item.setDescription("A short double-edged fighting knife with a sharp point. Ideal for quick strikes or stabbing unaware foes.");
		return item;
	}
	
	public Item newShortSword() {
		Item item = new Item("Short Sword", ItemType.WEAPON, shortSwordIcon);
		item.setBaseItem(BaseItem.SHORTSWORD);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.SWORD);
		item.addTag(ItemTag.LIGHT);
		item.addTag(ItemTag.VERSATILE);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A short and light blade. Perfect for your off hand, or for slashing an enemy.");
		return item;
	}
	
	public Item newSpear() {
		Item item = new Item("Spear", ItemType.WEAPON, spearIcon);
		item.setBaseItem(BaseItem.SPEAR);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.POLEARM);
		item.setDescription("A long spear. Gives you the ability to attack enemies two tiles away.");
		item.setAbility(Abilities.reach());
		return item;
	}
	public Item newHandaxe() {
		Item item = new Item("Handaxe", ItemType.WEAPON, handaxeIcon);
		item.setBaseItem(BaseItem.HANDAXE);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A small and mundane axe, it is still perfectly serviceable for cleaving enemies in two.");
		return item;
	}
	public Item newMace() {
		Item item = new Item("Mace", ItemType.WEAPON, maceIcon);
		item.setBaseItem(BaseItem.MACE);
		item.modifyAttackValue(2);
		item.setDamage(3, 5);
		item.addTag(ItemTag.MACE);
		item.setDamageType(Type.CRUSHING);
		item.setDescription("A short club with a metal head. While simple, it retains the age old solution to conflict by crushing skulls.");
		return item;
	}
	
	public Item newShortbow() {
		Item item = new Item("Shortbow", ItemType.WEAPON, shortbowIcon);
		item.setBaseItem(BaseItem.SHORTBOW);
		item.modifyRangedAttackValue(3);
		item.setDamage(1, 3);			
		item.setRangedDamage(2, 4);		//Standard Arrows typically add 2 damage
		item.addTag(ItemTag.BOW);
		item.addTag(ItemTag.TWOHANDED);
		item.setDescription("A short piece of rope attached to curved wood designed to fire arrows.");
		return item;
	}
	public Item newHandCrossbow() {
		Item item = new Item("Hand Crossbow", ItemType.WEAPON, handCrossbowIcon);
		item.setBaseItem(BaseItem.HANDCROSSBOW);
		item.modifyRangedAttackValue(2);
		item.setDamage(1,3);			
		item.setRangedDamage(2, 4);		//Bolts add 2-3 damage
		item.addTag(ItemTag.CROSSBOW);
		item.addTag(ItemTag.LOADING);
		item.setDescription("A compact piece of machinery, used for firing bolts. It takes some time to load and fire.");
		return item;
	}
	
	public Item newOrcishDagger() {
		Item item = new Item("Orcish Dagger", ItemType.WEAPON, orcishDaggerIcon);
		item.setBaseItem(BaseItem.ORCISHDAGGER);
		item.modifyAttackValue(2);
		item.setDamage(3,5);
		item.modifyWeaponDelay(-1);
		item.addTag(ItemTag.DAGGER);
		item.addTag(ItemTag.LIGHT);
		item.addTag(ItemTag.HIGHCRIT);
		item.setDamageType(Type.PIERCING);
		item.setDescription("A crude dagger forged by orc smiths. It holds a longer blade, with a cruel hook.");
		return item;
	}
	public Item newCutlass() {
		Item item = new Item("Cutlass", ItemType.WEAPON, cutlassIcon);
		item.setBaseItem(BaseItem.CUTLASS);
		item.modifyAttackValue(3);
		item.setDamage(4,7);
		item.addTag(ItemTag.SWORD);
		item.addTag(ItemTag.LIGHT);
		item.addTag(ItemTag.VERSATILE);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A short, broad sabre designed for heavy slashes.");
		return item;
	}
	public Item newBoardingAxe() {
		Item item = new Item("Boarding Axe", ItemType.WEAPON, boardingAxeIcon);
		item.setBaseItem(BaseItem.BOARDINGAXE);
		item.modifyAttackValue(3);
		item.setDamage(5,8);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A large one-handed axe designed to hook on wooden vessels, chop thick ropes, and smash heads.");
		return item;
	}
	public Item newOrcWarAxe() {
		Item item = new Item("Orc War Axe", ItemType.WEAPON, orcWarAxeIcon);
		item.setBaseItem(BaseItem.ORCWARAXE);
		item.modifyAttackValue(2);
		item.setDamage(7,9);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.addTag(ItemTag.TWOHANDED);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A large ivory war axe for the orcs, the head is quite crude with a large spike on the back end.");		
		return item;
	}
	public Item newFlintlock() {
		Item item = new Item("Flintlock", ItemType.WEAPON, flintlockIcon);
		item.setBaseItem(BaseItem.FLINTLOCK);
		item.modifyRangedAttackValue(2);
		item.setDamage(1,3);			
		item.setRangedDamage(4, 8);
		item.addTag(ItemTag.GUN);
		item.addTag(ItemTag.LOADING);
		item.addTag(ItemTag.BLACKPOWDER);
		item.setDescription("A small handheld black powder weapon, designed to fire metal shot in close quarters.");
		return item;
	}
	public Item newLongbow() {
		Item item = new Item("Longbow", ItemType.WEAPON, longbowIcon);
		item.setBaseItem(BaseItem.LONGBOW);
		item.modifyRangedAttackValue(5);
		item.setDamage(1, 2);			
		item.setRangedDamage(3, 5);
		item.addTag(ItemTag.BOW);
		item.addTag(ItemTag.TWOHANDED);
		item.setDescription("A long, strong bow made of yew. It does excellent damage in combat and a skilled archer can use it to great effect.");
		return item;
	}
	
	public Item newDevSword() {
		Item sword = new Item("Sword of the Dev", ItemType.WEAPON, devSwordIcon);
		sword.modifyAttackValue(400);
		sword.setDamage(50, 80);
		enchant.enchantWeapon(sword, 1, 1, false, true);
		sword.addTag(ItemTag.SWORD);
		sword.addTag(ItemTag.CLEAVING);
		sword.setDamageType(Type.SLASHING);
		return sword;
	}
	
}
