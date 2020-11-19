package assembly;

import creatures.Type;
import items.BaseItem;
import items.Enchantment;
import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class WeaponFactory {
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
	
	private Image devSwordIcon = tools.ImageCrop.cropImage(new Image(this.getClass().getResourceAsStream("resources/items/long_weapon_full.png")), 160, 32, 32, 32);
	
	public WeaponFactory(World world) {
		this.world = world;
		enchant = new Enchantment();
	}
	
	public Item newRandomMeleeWeapon(int z) {
		Item i = null;
		switch((int)(Math.random()*5)) {
		case 0: i = newDagger(z); break;
		case 1: i = newShortSword(z); break;
		case 2: i = newHandaxe(z); break;
		case 3: i = newMace(z); break;
		default: i = newSpear(z); break;
		}
		return i;
	}
	public Item getRandomEnchant() {
		Item i = newRandomMeleeWeapon(-1);
		enchant.enchantWeapon(i, 1, (int)(Math.random()*3)+1, true, true);
		return i;
	}
	
	public Item newRandomWeapon(int z) {
		Item i = null;
		
		switch((int)(Math.random()*6)) {
		case 0: i = newDagger(z); break;
		case 1: i = newShortSword(z); break;
		case 2: i = newShortbow(z); break;
		case 3: i = newHandaxe(z); break;
		case 4: i = newMace(z); break;
		default: i = newSpear(z); break;
		}
		
		/**
		 * Handle Enchantments
		 */
		if (Math.random() * 100 < 10 + z * 5) {
			int tier = (z/4) + 1;
			int bonus = 0;
			boolean effect = false;
			boolean base = false;
			if (Math.random()*100 < 80) {
				bonus = (int)(Math.random()*3) + tier;
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
			
			enchant.enchantWeapon(i, tier, bonus, effect, base);
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
	
	public Item newDevSword(int z) {
		Item sword = new Item("Sword of the Dev", ItemType.WEAPON, devSwordIcon);
		sword.modifyAttackValue(400);
		//sword.setDamage(50, 80);
		//sword.setEffectOnHit(Effects.poisoned(5, 2), 100);
		enchant.enchantWeapon(sword, 1, 1, false, true);
		world.addAtEmptyLocation(sword,z);
		sword.addTag(ItemTag.SWORD);
		sword.addTag(ItemTag.CLEAVING);
		sword.setDamageType(Type.SLASHING);
		return sword;
	}
	
}
