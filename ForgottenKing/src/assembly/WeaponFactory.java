package assembly;

import creatures.Type;
import items.BaseItem;
import items.Enchantment;
import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class WeaponFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private World world;
	private Enchantment enchant;
	public void setCreatureFactory(CreatureFactory f) { 
		enchant.setCreatureFactory(f);
	}
	
	//Images	
	private Image weaponsFull = new Image(Item.class.getResourceAsStream("resources/weapons-full.png"));
	private Image daggerIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 0, 32, 32);
	private Image shortSwordIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 32, 32, 32);
	private Image spearIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 64, 32, 32);
	private Image handaxeIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 96, 32, 32);
	private Image maceIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 128, 32, 32);
	private Image shortbowIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 160, 32, 32);
	private Image handCrossbowIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 192, 32, 32);
	
	private Image orcishDaggerIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 224, 32, 32);
	private Image cutlassIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 256, 32, 32);
	private Image boneSwordIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 288, 32, 32);
	private Image boardingAxeIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 320, 32, 32);
	private Image orcWarAxeIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 352, 32, 32);
	private Image musketIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 384, 32, 32);
	private Image flintlockIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 416, 32, 32);
	private Image crossbowIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 448, 32, 32);
	private Image longbowIcon = tools.ImageCrop.cropImage(weaponsFull, 0, 480, 32, 32);
	
	private Image devSwordIcon = tools.ImageCrop.cropImage(new Image(this.getClass().getResourceAsStream("resources/items/long_weapon_full.png")), 160, 32, 32, 32);
	
	public WeaponFactory(World world) {
		this.world = world;
		enchant = new Enchantment();
	}
	
	public Item newRandomMeleeWeapon(int z, int tier) {
		Item i = null;
		if (tier == 1) {
			switch((int)(Math.random()*5)) {
			case 0: i = newDagger(z); break;
			case 1: i = newShortSword(z); break;
			case 2: i = newHandaxe(z); break;
			case 3: i = newMace(z); break;
			default: i = newSpear(z); break;
			}
		} else if (tier == 2) {
			switch((int)(Math.random()*4)) {
			case 0: i = newOrcishDagger(z); break;
			case 1: i = newCutlass(z); break;
			case 2: i = newBoardingAxe(z); break;
			default: i = newOrcWarAxe(z); break;
			}
		}
		return i;
	}
	
	public Item newRandomWeapon(int z, int tier) {
		Item i = null;
		
		if (tier == 1) {
			switch((int)(Math.random()*7)) {
			case 0: i = newDagger(z); break;
			case 1: i = newShortSword(z); break;
			case 2: i = newShortbow(z); break;
			case 3: i = newHandaxe(z); break;
			case 4: i = newMace(z); break;
			case 5: i = newHandCrossbow(z); break;
			default: i = newSpear(z); break;
			}
		} else if (tier == 2) {
			switch((int)(Math.random()*6)) {
			case 0: i = newOrcishDagger(z); break;
			case 1: i = newCutlass(z); break;
			case 2: i = newBoardingAxe(z); break;
			case 3: i = newOrcWarAxe(z); break;
			case 4: i = newFlintlock(z); break;
			default: i = newLongbow(z); break;
			}
		}
		
		/**
		 * Handle Enchantments
		 */
		if (Math.random() * 100 < 10 + (z%5) * 5 + z) {
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
	
	public Item newDagger(int z) {
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
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newShortSword(int z) {
		Item item = new Item("Short Sword", ItemType.WEAPON, shortSwordIcon);
		item.setBaseItem(BaseItem.SHORTSWORD);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.SWORD);
		item.addTag(ItemTag.LIGHT);
		item.addTag(ItemTag.VERSATILE);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A short and light blade. Perfect for your off hand, or for slashing an enemy.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newSpear(int z) {
		Item item = new Item("Spear", ItemType.WEAPON, spearIcon);
		item.setBaseItem(BaseItem.SPEAR);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.POLEARM);
		item.setDescription("A long spear. Gives you the ability to attack enemies two tiles away.");
		item.setAbility(Abilities.reach());
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newHandaxe(int z) {
		Item item = new Item("Handaxe", ItemType.WEAPON, handaxeIcon);
		item.setBaseItem(BaseItem.HANDAXE);
		item.modifyAttackValue(1);
		item.setDamage(2, 4);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A small and mundane axe, it is still perfectly serviceable for cleaving enemies in two.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newMace(int z) {
		Item item = new Item("Mace", ItemType.WEAPON, maceIcon);
		item.setBaseItem(BaseItem.MACE);
		item.modifyAttackValue(2);
		item.setDamage(3, 5);
		item.addTag(ItemTag.MACE);
		item.setDamageType(Type.CRUSHING);
		item.setDescription("A short club with a metal head. While simple, it retains the age old solution to conflict by crushing skulls.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newShortbow(int z) {
		Item item = new Item("Shortbow", ItemType.WEAPON, shortbowIcon);
		item.setBaseItem(BaseItem.SHORTBOW);
		item.modifyRangedAttackValue(3);
		item.setDamage(1, 3);			
		item.setRangedDamage(2, 4);		//Standard Arrows typically add 2 damage
		item.addTag(ItemTag.BOW);
		item.addTag(ItemTag.TWOHANDED);
		item.setDescription("A short piece of rope attached to curved wood designed to fire arrows.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newHandCrossbow(int z) {
		Item item = new Item("Hand Crossbow", ItemType.WEAPON, handCrossbowIcon);
		item.setBaseItem(BaseItem.HANDCROSSBOW);
		item.modifyRangedAttackValue(2);
		item.setDamage(1,3);			
		item.setRangedDamage(2, 4);		//Bolts add 2-3 damage
		item.addTag(ItemTag.CROSSBOW);
		item.addTag(ItemTag.LOADING);
		item.setDescription("A compact piece of machinery, used for firing bolts. It takes some time to load and fire.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newOrcishDagger(int z) {
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
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newCutlass(int z) {
		Item item = new Item("Cutlass", ItemType.WEAPON, cutlassIcon);
		item.setBaseItem(BaseItem.CUTLASS);
		item.modifyAttackValue(3);
		item.setDamage(4,7);
		item.addTag(ItemTag.SWORD);
		item.addTag(ItemTag.LIGHT);
		item.addTag(ItemTag.VERSATILE);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A short, broad sabre designed for heavy slashes.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newBoardingAxe(int z) {
		Item item = new Item("Boarding Axe", ItemType.WEAPON, boardingAxeIcon);
		item.setBaseItem(BaseItem.BOARDINGAXE);
		item.modifyAttackValue(3);
		item.setDamage(5,8);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A large one-handed axe designed to hook on wooden vessels, chop thick ropes, and smash heads.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newOrcWarAxe(int z) {
		Item item = new Item("Orc War Axe", ItemType.WEAPON, orcWarAxeIcon);
		item.setBaseItem(BaseItem.ORCWARAXE);
		item.modifyAttackValue(2);
		item.setDamage(7,9);
		item.addTag(ItemTag.AXE);
		item.addTag(ItemTag.CLEAVING);
		item.addTag(ItemTag.TWOHANDED);
		item.setDamageType(Type.SLASHING);
		item.setDescription("A large ivory war axe for the orcs, the head is quite crude with a large spike on the back end.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newFlintlock(int z) {
		Item item = new Item("Flintlock", ItemType.WEAPON, flintlockIcon);
		item.setBaseItem(BaseItem.FLINTLOCK);
		item.modifyRangedAttackValue(2);
		item.setDamage(1,3);			
		item.setRangedDamage(4, 8);
		item.addTag(ItemTag.GUN);
		item.addTag(ItemTag.LOADING);
		item.addTag(ItemTag.BLACKPOWDER);
		item.setDescription("A small handheld black powder weapon, designed to fire metal shot in close quarters.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	public Item newLongbow(int z) {
		Item item = new Item("Longbow", ItemType.WEAPON, longbowIcon);
		item.setBaseItem(BaseItem.LONGBOW);
		item.modifyRangedAttackValue(5);
		item.setDamage(1, 2);			
		item.setRangedDamage(3, 5);
		item.addTag(ItemTag.BOW);
		item.addTag(ItemTag.TWOHANDED);
		item.setDescription("A long, strong bow made of yew. It does excellent damage in combat and a skilled archer can use it to great effect.");
		world.addAtEmptyLocation(item,z);
		return item;
	}
	
	public Item newDevSword(int z) {
		Item sword = new Item("Sword of the Dev", ItemType.WEAPON, devSwordIcon);
		sword.modifyAttackValue(400);
		sword.setDamage(50, 80);
		enchant.enchantWeapon(sword, 1, 1, false, true);
		world.addAtEmptyLocation(sword,z);
		sword.addTag(ItemTag.SWORD);
		sword.addTag(ItemTag.CLEAVING);
		sword.setDamageType(Type.SLASHING);
		return sword;
	}
	
}
