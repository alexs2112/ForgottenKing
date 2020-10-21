package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import creatures.Type;
import javafx.scene.image.Image;
import spells.Effect;
import spells.Spell;

public class Item {
	private Image image;
	public Image image() { return image; }
	private String name;
	public String name() { return name; }
	private ItemType type;
	public ItemType type() { return type; }
	private String description;
	public String description() { return description; }
	public void setDescription(String x) { description = x; }
	private Type damageType;
	public Type damageType() { return damageType;	}
	private boolean equippable;
	public boolean equippable() { return equippable; }
	
	public Item(String name, ItemType type, Image image) {
		this.name = name;
		this.image = image;
		this.type = type;
		damage = new int[2];
		thrownDamage = new int[2];
		if (type == ItemType.ARMOR ||
			type == ItemType.WEAPON ||
			type == ItemType.RING ||
			type == ItemType.AMULET ||
			type == ItemType.BOOTS ||
			type == ItemType.CLOAK ||
			type == ItemType.GLOVES)
			equippable = true;
		if (type == ItemType.BOOK)
			newSpellList();
	}
	
	/**
	 * Equipment Methods
	 */
	private int attackValue;
	public int attackValue() { return attackValue; }
	public void modifyAttackValue(int amount) { attackValue += amount; }

	private int armorValue;
	public int armorValue() { return armorValue; }
	public void modifyArmorValue(int amount) { armorValue += amount; }
	
	private int thrownAttackValue;
	public int thrownAttackValue() { return thrownAttackValue; }
	public void modifyThrownAttackValue(int amount) { thrownAttackValue += amount; }
	private int[] thrownDamage;
	public int[] thrownDamage() { return thrownDamage; }
	public int getThrownDamage() {
		return Math.max(((int)(Math.random() * (thrownDamage[1] - thrownDamage[0])) + thrownDamage[0]),0);
	}
	
	private int rangedAttackValue;
	public int rangedAttackValue() { return rangedAttackValue; }
	public void modifyRangedAttackValue(int amount) { rangedAttackValue += amount; }
	
	private int[] damage;
	public int[] damage() { return damage; }
	public void setDamage(int min, int max) {
		if (damage == null)
			damage = new int[2];
		damage[0] = min; 
		damage[1] = max; 
	}
	public int minDamage() { return damage[0]; }
	public int maxDamage() { return damage[1]; }
	private int weaponDelay;
	public int weaponDelay() { return weaponDelay; }
	public void modifyWeaponDelay(int x) { weaponDelay += x; }
	
	/**
	 * Effect Methods
	 */
	private Effect quaffEffect;
	public Effect quaffEffect() { return quaffEffect; }
	public void setQuaffEffect(Effect x) { quaffEffect = x; }
	private Effect effectOnHit;
	public Effect effectOnHit() { return effectOnHit; }
	public void setEffectOnHit(Effect x) { effectOnHit = x; }
	
	/**
	 * Spell Methods
	 */
	public void newSpellList() { this.spells = new ArrayList<Spell>(); }
	private List<Spell> spells;
	public List<Spell> spells() { return spells; }
	public void addSpell(Spell spell) {
		spells.add(spell);
	}

	/**
	 * Tag Methods
	 */
	private List<ItemTag> tags;
	public List<ItemTag> tags() { return tags; }
	public void addTag(ItemTag t) {
		if (tags == null)
			tags = new ArrayList<ItemTag>();
		tags.add(t);
	}
	public boolean is(ItemTag t) {
		return (tags != null && tags.contains(t));
	}
	
	/**
	 * Resistances
	 */
	private HashMap<Type, Integer> resistances;
	public HashMap<Type, Integer> resistances() { return resistances; }
	public int getResistance(Type type) {
		if (resistances != null)
			if (resistances.containsKey(type))
				return resistances.get(type);
		return 0;
	}
	public void addResistance(Type type, int value) {
		if (resistances == null)
			resistances = new HashMap<Type, Integer>();
		resistances.put(type, value);
	}
	
	/**
	 * String Handling
	 */
	public String shortDesc() {
		String x = "";
		if (attackValue != 0)
			x += "+" + attackValue;
		if (minDamage() != 0 && maxDamage() != 0)
			x += " (" + minDamage() + "-" + maxDamage() + ") ";
		if (armorValue != 0)
			x += "AC+" + armorValue;
		return x;
	}

}
