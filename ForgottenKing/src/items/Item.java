package items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import creatures.Ability;
import creatures.Attribute;
import creatures.Creature;
import creatures.Player;
import creatures.Stat;
import creatures.Tag;
import creatures.Type;
import javafx.scene.image.Image;
import spells.Effect;
import spells.Spell;
import tools.Icon;

public class Item implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private Icon icon;
	public Icon icon() { return icon; }
	public Image image() { return icon.image(); }
	public void setIcon(Icon newImage) { icon = newImage; }
	private String name;
	public String name() { return name; }
	public void setName(String newName) { name = newName; }
	private ItemType type;
	public ItemType type() { return type; }
	private String description;
	public String description() { return description; }
	public void setDescription(String x) { description = x; }
	private double weight;
	public double weight() { return weight; }
	public void setWeight(double x) { weight = x; }
	private Type damageType;
	public Type damageType() { return damageType; }
	public void setDamageType(Type damageType) { this.damageType = damageType; }
	public boolean equippable() { 
		return type == ItemType.ARMOR ||
				type == ItemType.RING ||
				type == ItemType.AMULET ||
				type == ItemType.BOOTS ||
				type == ItemType.CLOAK ||
				type == ItemType.GLOVES ||
				type == ItemType.HEAD ||
				type == ItemType.OFFHAND ||
				type == ItemType.WEAPON;
	}
	
	public Item(String name, ItemType type, Icon icon) {
		this.name = name;
		this.icon = icon;
		this.type = type;
		damage = new int[2];
		weight = 1;
		if (type == ItemType.BOOK)
			newSpellList();
	}
	
	/**
	 * An amount the item can spawn with in world generation
	 */
	private int spawnQuantity;
	public int spawnQuantity() { return spawnQuantity; }
	public void setSpawnQuantity(int x) { spawnQuantity = x; }
	
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
		if (thrownDamage == null)
			return 0;
		return Math.max(((int)(Math.random() * (thrownDamage[1] - thrownDamage[0])) + thrownDamage[0]),0);
	}
	public void setThrownDamage(int min, int max) {
		thrownDamage = new int[2];
		thrownDamage[0] = min;
		thrownDamage[1] = max;
	}
	
	public boolean isCompatibleAmmoWith(Item i) {
		if (i == null)
			return false;
		return (type() == ItemType.ARROW && i.is(ItemTag.BOW)) ||
			   (type() == ItemType.BOLT && i.is(ItemTag.CROSSBOW)) ||
			   (type() == ItemType.STONE && i.is(ItemTag.SLING)) ||
			   (type() == ItemType.SHOT && i.is(ItemTag.GUN));
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
	
	private int[] rangedDamage;
	public int[] rangedDamage() { return rangedDamage; }
	public void setRangedDamage(int min, int max) {
		if (rangedDamage == null)
			rangedDamage = new int[2];
		rangedDamage[0] = min; 
		rangedDamage[1] = max; 
	}
	public int getRangedDamageValue() {
		return (int)(Math.random()*(rangedDamage[1]-rangedDamage[0]+1)) + rangedDamage[0];
	}
	
	/**
	 * Effect Methods
	 */
	private Effect effect;
	public Effect effect() { return effect; }
	public void setEffect(Effect x) { effect = x; }
	private Effect effectOnHit;
	public Effect effectOnHit() { return effectOnHit; }
	public void setEffectOnHit(Effect x, int chance) { effectOnHit = x; effectChance = chance; }
	private int effectChance;
	public int effectChance() { return effectChance; }
	
	/**
	 * Enchantment Methods
	 */
	private BaseItem baseItem;
	public BaseItem baseItem() { return baseItem; }
	public void setBaseItem(BaseItem b) { baseItem = b; }
	private List<Trigger> triggers;
	public List<Trigger> triggers() { return triggers; }
	public void addTrigger(Trigger t) {
		if (triggers == null)
			triggers = new ArrayList<Trigger>();
		triggers.add(t);
	}
	public void trigger(Creature owner, Creature other) { }
	private Icon effectIcon;
	public Icon effectIcon() { return effectIcon; }
	public void setEffectIcon(Icon i) { effectIcon = i; }
	
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
	public boolean isRanged() {
		return is(ItemTag.BOW) || is(ItemTag.CROSSBOW) || is(ItemTag.SLING) || is(ItemTag.GUN);
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
	 * Abilities
	 */
	private Ability ability;
	public Ability ability() { return ability; }
	public void setAbility(Ability a) { ability = a; }
	
	/**
	 * Attributes and Stats
	 */
	private HashMap<Attribute, Integer> attributes;
	public HashMap<Attribute, Integer> attributes() { return attributes;}
	public void setAttribute(Attribute a, int i) {
		if (attributes == null)
			attributes = new HashMap<Attribute, Integer>();
		attributes.put(a, i);
	}
	public int get(Attribute a) {
		if (attributes == null || !attributes.containsKey(a))
			return 0;
		return attributes.get(a); 
	}
	private HashMap<Stat, Integer> stats;
	public HashMap<Stat, Integer> stats() { return stats; }
	public void setStat(Stat s, int i) {
		if (stats == null)
			stats = new HashMap<Stat, Integer>();
		stats.put(s, i);
	}
	public int get(Stat s) { 
		if (stats == null || !stats.containsKey(s))
			return 0;
		return stats.get(s); 
	}
	
	/**
	 * String Handling
	 */
	public String shortDesc(Player player) {
		String x = "";
		if (isRanged() || type.isAmmo()) {
			if (rangedAttackValue != 0)
				x += "+" + rangedAttackValue;
			if (rangedDamage != null) {
				if (rangedDamage[0] != rangedDamage[1])
					x += " (" + rangedDamage[0] + "-" + rangedDamage[1] + ") ";
				else
					x += " (" + rangedDamage[0] + ")";
			}
		} else {
			if (attackValue != 0) {
				if (attackValue > 0)
					x += "+";
				if (!(attackValue < 0 && is(ItemTag.SHIELD) && player.is(Tag.SHIELD_TRAINING))) {
					if (is(ItemTag.VERSATILE) && player.equipment().get(ItemType.OFFHAND) == null)
						x += (attackValue + 2);
					else
						x += attackValue;
				}
			}
			if (minDamage() != 0 && maxDamage() != 0)
				x += " (" + minDamage() + "-" + maxDamage() + ") ";
		}
		if (armorValue != 0)
			x += " AC+" + armorValue;
		if (type == ItemType.RING || type == ItemType.AMULET) {
			if (attributes != null)
				for (Attribute a : attributes.keySet())
					x += a.name() +"+"+ attributes.get(a);
			if (stats != null)
				for (Stat s : stats.keySet())
					x += s.text() +"+"+ stats.get(s);
		}
		return x.trim();
	}

}
