package creatures;

import tools.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import creatures.ai.CreatureAI;
import features.Feature;
import items.Inventory;
import items.Item;
import items.ItemType;
import items.ItemTag;
import javafx.scene.image.Image;
import spells.Effect;
import spells.Spell;
import world.Tile;
import world.World;

public class Creature {
    private World world;
    public World world() { return world; }

    /**
     * Position
     */
    public int x;
    public int y;
    public int z;

    /**
     * Base Variables
     */
    private String name;
    public String name() { return name; }
    private Image image;
    public Image image() { return image; }
    private CreatureAI ai;
    public void setCreatureAI(CreatureAI ai) { this.ai = ai; }
    private String description;
    public String description() { return description; }
    public void setDescription(String desc) { description = desc; }
    
    /**
     * Basic Stats
     */
    private int level;
    public int level() { return level; }
    public void modifyLevel(int x) { level += x; }
    private int xp;
    public int xp() { return xp; }
    public void modifyXP(int x) {
    	if (is(Tag.QUICK_LEARNER) && x > 0)
    		x += x/10;
    	xp += x; 
    }
    public int nextLevelXP() { return (int)(Math.pow((double)level(),1.2) * 500); }
    private int hp;
    public int hp() { return hp; }
    private int maxHP;
    public int maxHP() { return maxHP + level*getToughness(); } 
    public void fillHP() { hp = maxHP(); }
    public void modifyMaxHP(int x) { maxHP += x; }
    private int mana;
    public int mana() { return mana; }
    private int maxMana;
    public int maxMana() { return maxMana + (level*getWill())/2; }
    public void fillMana() { mana = maxMana(); }
    public void modifyMana(int x) { mana = Math.min(maxMana(), mana + x); }
    public void setMana(int current, int max) { maxMana = max; mana = current; }
    private HashMap<Attribute, Integer> attributes;
    public HashMap<Attribute, Integer> attributes() { return attributes; }
    private HashMap<Stat, Integer> stats;
    public HashMap<Stat, Integer> stats() { return stats; }
    
    /**
     * Magic Skills
     */
    protected Magic magic;
    public Magic magic() { return magic; }
    public void setMagic() { magic = new Magic(this); }
    
    /**
     * Getting and Setting HashMaps
     */
    public void setAttributes(int strength, int dexterity, int intelligence) {
    	modifyAttribute(Attribute.STR, strength);
        modifyAttribute(Attribute.DEX, dexterity);
        modifyAttribute(Attribute.INT, intelligence);
    }
    public int strength() { return getAttributeTotal(Attribute.STR); }
    public int dexterity() { return getAttributeTotal(Attribute.DEX); }
    public int intelligence() { return getAttributeTotal(Attribute.INT); }
    public void modifyAttribute(Attribute attribute, int amount) { 
    	attributes.put(attribute, attributes.get(attribute) + amount);
    }
    private int getAttributeTotal(Attribute a) {
    	int x = attributes.get(a);
    	for (Item i : equipment().values())
    		x += i.get(a);
    	return x;
    }
    public void setStats(int toughness, int brawn, int agility, int accuracy, int will, int spellcasting) {
    	modifyStat(Stat.TOUGHNESS, toughness);
    	modifyStat(Stat.BRAWN, brawn);
    	modifyStat(Stat.AGILITY, agility);
    	modifyStat(Stat.ACCURACY, accuracy);
    	modifyStat(Stat.WILL, will);
    	modifyStat(Stat.SPELLCASTING, spellcasting);
    }
    public int toughness() { return getStatValue(Stat.TOUGHNESS); }
    public int brawn() { return getStatValue(Stat.BRAWN); }
    public int agility() { return getStatValue(Stat.AGILITY); }
    public int accuracy() { return getStatValue(Stat.ACCURACY); }
    public int will() { return getStatValue(Stat.WILL); }
    public int spellcasting() { return getStatValue(Stat.SPELLCASTING); }
    
    /**
     * Getters get the stat + any modifiers and its parent
     */
    public int getToughness() { return getStatTotal(Stat.TOUGHNESS); }
    public int getBrawn() { return getStatTotal(Stat.BRAWN); }
    public int getAgility() { return getStatTotal(Stat.AGILITY); }
    public int getAccuracy() { return getStatTotal(Stat.ACCURACY); }
    public int getWill() { return getStatTotal(Stat.WILL); }
    public int getSpellcasting() { return getStatTotal(Stat.SPELLCASTING); }
    public void modifyStat(Stat stat, int amount) { 
    	stats.put(stat, stats.get(stat) + amount);
    }
    private int getStatTotal(Stat stat) {
    	int x = stats.get(stat) + getAttributeTotal(stat.parent());
    	for (Item i : equipment().values())
    		x += i.get(stat);
    	return x;
    }
    private int getStatValue(Stat stat) {
    	return stats.get(stat);
    }
    
    
    /**
     * Combat Stats
     */ 
    private int attackValue;
	public int attackValue() { 
		int value = attackValue;
		for (Item i : equipment.values())
			value += i.attackValue();
		return value - getArmorDebuff();
	}
	public void modifyAttackValue(int x) { attackValue += x; }
	private int armorValue;
	public int armorValue() {
		int value = armorValue;
		for (Item i : equipment.values()) {
			value += i.armorValue();
			if (i.is(ItemTag.MEDIUMARMOR) && is(Tag.MEDIUM_ARMOR_MASTERY))
				value++;
		}
		return value;
	}
	public int getCurrentAttackValue() {
		int attackMod = attackValue();
		if (weapon() != null && weapon().is(ItemTag.LIGHT) || weapon() == null) {
			if (getAccuracy() > getBrawn())
				attackMod += getAccuracy();
			else
				attackMod += getBrawn();
		} else
			attackMod += getBrawn();
		attackMod += getAccuracy() / 4;
		for (Effect e : effects) {
			if (e.name().equals("Blind"))
				attackMod /= 2;
		}
		return attackMod;
	}
	public int getCurrentDamageMod() {
		if (weapon() != null && weapon().is(ItemTag.LIGHT))
			if (getAccuracy() > getBrawn())
				return getAccuracy() / 2;
		return getBrawn() / 2;
	}
	public int getCurrentRangedDamageValue() {
		int i = weapon().getRangedDamageValue() + getAccuracy() / 2;
		if (quiver() != null)
			i += quiver().getRangedDamageValue();
		return  i;
	}
	public int[] getCurrentRangedDamage() {
		if (weapon() == null)
			return new int[2];
		int[] i = new int[2];
		i[0] = weapon().rangedDamage()[0];
		i[1] = weapon().rangedDamage()[1];
		if (quiver() != null) {
			i[0] += quiver().rangedDamage()[0];
			i[1] += quiver().rangedDamage()[1];
		}
		i[0] += getAccuracy()/2;
		i[1] += getAccuracy()/2;
		return i;
	}
	public int getCurrentRangedAttackValue() {
		if (weapon() != null && weapon().rangedAttackValue() > 0)
			return weapon().rangedAttackValue() + getAccuracy() + getAccuracy() / 4 - getArmorDebuff();
		else
			return 0;
	}
    
	public void modifyArmorValue(int x) { armorValue += x; }
	private HashMap<Type, Integer> resistances;
	public void setResistance(Type type, int n) {
		if (resistances == null)
			resistances = new HashMap<Type, Integer>();
		resistances.put(type, n);
	}
	public void modifyResistance(Type type, int n) {
		if (resistances == null || !resistances.containsKey(type))
			setResistance(type, n);
		else
			resistances.put(type, resistances.get(type)+n);
	}
	public int getResistance(Type type) {
		int r = 0;
		if (resistances != null)
			if (resistances.containsKey(type))
				r = resistances.get(type);
		for (Item i : equipment.values()) {
			r += i.getResistance(type);
			if (type.physical() && i.is(ItemTag.HEAVYARMOR))
				r += 1;
		}
		
		return r;
	}
	public int getDamageReceived(double amount, Type type) {
		if (resistances == null)
			return (int)amount;
		if (resistances.containsKey(type)) {
			if (getResistance(type) <= -1)
				amount *= 1.5;
			if (getResistance(type) == 1)
				amount *= 0.67;
			if (getResistance(type) == 2)
				amount *= 0.33;
			if (getResistance(type) == 3)
				amount = 0;
			if (getResistance(type) >= 4)
				amount *= -0.33;
		}
		return (int)(Math.round(amount));
	}
	private int evasion;
	public int evasion() { 
		int i = evasion + getAgility();
		for (Item item : equipment.values()) {
			if (item.is(ItemTag.LIGHTARMOR) && is(Tag.LIGHT_ARMOR_MASTERY))
				i+=2;
			if (item.is(ItemTag.MEDIUMARMOR) && is(Tag.MEDIUM_ARMOR_MASTERY))
				i+=1;
		}
		return  i - getArmorDebuff();
	}
	public void modifyEvasion(int x) { evasion += x; }
	private int[] damage;
	public int[] damage() { return damage; }
	public int getDamageValue() {
		int min = getMinDamage();
		int max = getMaxDamage();
		return (int)(Math.random()*(max-min+1)) + min;
	}
	public int getMinDamage() {
		int min = damage[0];
		for (Item i : equipment.values())
			min += i.minDamage();
		return min;
	}
	public int getMaxDamage() {
		int max = damage[1];
		for (Item i : equipment.values())
			max += i.maxDamage();
		return max;
	}
	public void setDamage(int min, int max) {
		damage[0] = min;
		damage[1] = max;
	}
	public int getArmorDebuff() {
		int value = 0;
		for (Item i : equipment.values()) {
			if (i.is(ItemTag.MEDIUMARMOR) && getBrawn() < 5)
				value+=2;
			if (i.is(ItemTag.HEAVYARMOR) && getBrawn() < 8) {
				value += 3;
				if (getBrawn() < 5)
					value += 2;
			}
		}
		return value;
	}
	
	/**
	 * Speed and Delay
	 */
	private int time;
	public int time() { return time; }
	public void modifyTime(int x) { time += x; }
	private int movementDelay;
	public int movementDelay() { return Math.max(movementDelay - getAgility()/6,5) + getArmorDebuff(); }
	public int getMovementDelay() { return movementDelay() + randomTimeChange(); }
	public void modifyMovementDelay(int x) { movementDelay += x; }
	private int randomTimeChange() {
		int chance = (int)(Math.random()*100+1);
		int x = 0;
		if (chance < 20) {
			x += (int)(Math.random()*3)-1;
		}
		return x;
	}
	private int attackDelay;
	public int attackDelay() {
		int mod = 0;
		if (weapon() != null)
			mod += weapon().weaponDelay();
		return Math.max(attackDelay + mod, 4) + getArmorDebuff(); 
	}
	public void modifyAttackDelay(int x) { attackDelay += x; }
    
	/**
	 * Inventory and Equipment
	 */
    private Inventory inventory;
    public Inventory inventory() { return inventory; }
    private HashMap<ItemType, Item> equipment;
    public HashMap<ItemType, Item> equipment() { return equipment; }
    public Item weapon() { return equipment.get(ItemType.WEAPON); }
	
	/**
	 * Constructor
	 */
    public Creature(World world, String name, int level, int xp, int hp, int evasion, int armorValue, int baseAttackValue, int baseDamageMin, int baseDamageMax, Image image) {
    	this.level = level;
    	this.xp = xp;
        this.world = world;
        this.image = image;
        this.name = name;
        this.hp = hp;
        this.maxHP = hp;
        this.attackValue = baseAttackValue;
        this.evasion = evasion;
        this.armorValue = armorValue;
        this.visionRadius = 9;
        this.inventory = new Inventory(26);
        this.effects = new ArrayList<Effect>();
        this.equipment = new HashMap<ItemType, Item>();
        this.attributes = new HashMap<Attribute, Integer>();
        this.stats = new HashMap<Stat, Integer>();
        this.damage = new int[2];
        this.time = (int)(Math.random()*10);
        this.movementDelay = 10;
        this.attackDelay = 10;
        damage[0] = baseDamageMin;
        damage[1] = baseDamageMax;
        initAttributesAndStats();
    }
    private void initAttributesAndStats() {
    	attributes.put(Attribute.STR, 0);
    	attributes.put(Attribute.DEX, 0);
    	attributes.put(Attribute.INT, 0);
    	stats.put(Stat.TOUGHNESS,0);
    	stats.put(Stat.BRAWN,0);
    	stats.put(Stat.AGILITY,0);
    	stats.put(Stat.ACCURACY,0);
    	stats.put(Stat.WILL,0);
    	stats.put(Stat.SPELLCASTING,0);
    }
    
    /**
     * Try to move in that direction, relative to current position
     */
    public void moveBy(int mx, int my, int mz){
    	moveTo(x+mx,y+my,z+mz);
    }
    public void moveTo(int mx, int my, int mz) {
    	if (mx == x && my == y && mz == z)
    		return;
        Creature other = world.creature(mx, my, mz);
        if (other == null) {
            ai.onEnter(mx, my, mz, world.tile(mx, my, mz));
            if (world.items(x,y,z) != null)
            	if (world.items(x,y,z).getFirstItem() != null) {
            		String s = "You see here ";
            		Inventory i = world.items(x,y,z);
            		if (i.quantityOf(i.getFirstItem()) == 1)
            			s += "a ";
            		s += i.listOfItems();
            		notify(s);
            	}
        }
        else
            attack(other);
    }
    
    /**
     * Basic sight methods
     */
    private int visionRadius;
    public int visionRadius() { return visionRadius; }
    public void modifyVisionRadius(int x) { visionRadius += x; }

    public boolean canSee(int wx, int wy, int wz){
        return ai.canSee(wx, wy, wz);
    }
    public Creature creature(int wx, int wy, int wz) {
    	if (canSee(wx,wy,wz))
    		return world.creature(wx, wy, wz);
    	else
    		return null;
    }
    public Tile tile(int wx, int wy, int wz) {
    	if (canSee(wx, wy, wz))
            return world.tile(wx, wy, wz);
        else
            return ai.rememberedTile(wx, wy, wz);
    }
    public Tile realTile(int wx, int wy, int wz) {
    	return world.tile(wx, wy, wz);
    }
    public Feature feature(int wx, int wy, int wz) {
    	return world.feature(wx, wy, wz);
    }
    public Inventory items(int wx, int wy, int wz) {
    	if (canSee(wx,wy,wz))
    		return world.items(wx,wy,wz);
    	else
    		return null;
    }

    /**
     * Combat methods
     */
	public void modifyHP(int amount, Creature source) {
		hp = Math.min(maxHP(), hp += amount);
		if (hp <= 0) {
			die(source);
		}
	}
	public void modifyHP(int amount) {
		modifyHP(amount, null);
	}
	public void die(Creature killer) {
		doAction("die");
		if (is(Tag.PLAYER))
			notify("Press [enter] to restart!");
		for (int i = 0; i < inventory.items().length; i++) {
			Item item = inventory.get(i);
			if (item != null) {
				int q = inventory.quantityOf(item);
				for (int n = 0; n < q; n++)
					putAt(item, x, y, z);
			}
		}
		if (killer != null && killer != this)
			killer.modifyXP(xp());
		world.remove(this);
	}
	public int critChance() {
		int critChance = accuracy();
		if ((weapon() != null && weapon().is(ItemTag.HIGHCRIT)) || is(Tag.IMPROVED_CRITICAL))
			critChance += 10;
		return critChance;
	}
	private Creature lastAttacked;
	public Creature lastAttacked() { return lastAttacked; }
	public void setLastAttacked(Creature x) { lastAttacked = x; }
	public Point getAutoTarget() {
		if (lastAttacked != null) {
			if (creature(lastAttacked.x, lastAttacked.y, lastAttacked.z) == lastAttacked)
				return new Point(lastAttacked.x, lastAttacked.y, lastAttacked.z);
			else
				lastAttacked = null;
		}
		return new Point(x,y,z);
	}
	public void attack(Creature other) {
		modifyTime(attackDelay());
		basicAttack(other, weapon(), getCurrentAttackValue(), getDamageValue() + getCurrentDamageMod(), "attack the " + other.name());
		
		/**
		 * Handling for the CLEAVING weapon tag
		 */
		if (weapon() != null && weapon().is(ItemTag.CLEAVING)) {
			Point target = new Point(other.x, other.y, other.z);
			List<Point> neighbours = new Point(x, y, z).neighbors8();
			double value = 0.5;
			if (is(Tag.IMPROVED_CLEAVE))
				value = 0.8;
			for (Point t : target.neighbors8()) {
				if (neighbours.contains(t) && creature(t.x,t.y,t.z) != null)
					basicAttack(creature(t.x,t.y,t.z), weapon(), getCurrentAttackValue(), (int)((getDamageValue() + getCurrentDamageMod())*value), "attack the " + creature(t.x,t.y,t.z).name());
			}
		}
	}
	private void throwAttack(Item item, Creature other) {
        basicAttack(other, item, getAccuracy() + getBrawn()/2 + item.thrownAttackValue(), getBrawn()/2 + getAccuracy()/2 + item.getThrownDamage(), "throw a " + item.name() + " at the " + other.name());
        if (item.type() == ItemType.POTION) {
        	Effect effect = item.effect();
        	effect.setOwner(this);
        	other.addEffect(effect);
        	world.remove(item);
        }
    }
	public void rangedWeaponAttack(Item item, Creature other){
		modifyTime(attackDelay());
        basicAttack(other, item, getCurrentRangedAttackValue(), getCurrentRangedDamageValue(), "fire at the " + other.name());
    }
	
	private void basicAttack(Creature other, Item item, int attackModifier, int damage, String action) {
		double critDie = Math.random() * 100;
		int attackRoll = (int)(Math.random()*10 + Math.random()*10 + 2);
		if ((critDie > 5 && attackRoll + attackModifier > other.evasion()) || critDie > 100 - critChance()) {
			Type type = Type.CRUSHING;
			if (item != null && item.damageType() != null)
				type = item.damageType();
			if (critDie < 100 - critChance()) {
				damage = other.reduceDamageByArmor(damage);
				damage = other.getDamageReceived(damage, type);
			}
			if (damage > 0)
				action += " for " + damage + " damage!";
			else if (damage == 0)
				action += " but the " + other.name() + " resists!";
			else
				action += " healing the " + other.name() + " for " + -damage + "!";
			
			if (critDie > 100 - critChance()) {
				action += " **Critical Hit**";
			}
			doAction(action);
			if (effectsOnHit() != null && item == weapon())
				for (Effect e : effectsOnHit().keySet()) {
					if (Math.random() < effectsOnHit().get(e))
						other.addEffect(e);
				}
			other.modifyHP(-damage, this);
		} else {
			if (is(Tag.PLAYER))
				action += " but miss";
			else
				action += " but misses";
			doAction(action);
		}
		setLastAttacked(other);
	}
	
	/**
	 * Returns the damage reduced 1 for 1 by armor, to a maximum of 80% reduction
	 */
	public int reduceDamageByArmor(int damage) {
		double maxReduce = 0.8;
		if (is(Tag.FASTENED_ARMOR))
			maxReduce = 0.88;
		double remaining = 1 - maxReduce;
		return Math.max((int)Math.round(Math.max(((double)damage)*maxReduce - ((double)armorValue() * maxReduce),0) + ((double)(damage)*remaining)),1);
	}
	public void throwItem(Item item, int wx, int wy, int wz) {
		Creature c = creature(wx,wy,wz);
		putAt(item, wx, wy, wz);
		if (c != null) {
			throwAttack(item, c);
	        if (Math.random()*100 < 20) {
	        	world.remove(item, wx, wy, wz);
	        	world.notify(wx,wy,wz,"The " + item.name() + " broke!");
	        }
		} else
			doAction("throw a " + item.name());
	}
	public void fireItem(Item item, int wx, int wy, int wz) {
		Creature c = creature(wx,wy,wz);
		putAt(item, wx, wy, wz);
		Item i = quiver();
		if (c != null) {
			rangedWeaponAttack(i, c);
			//20% chance the fired object breaks, 8% if you have the STRONG_ARROWS perk
	        int breakChance = 20;
	        if (is(Tag.STRONG_ARROWS))
	        	breakChance = 8;
	        if (Math.random()*100 < breakChance) {
	        	world.notify(wx,wy,wz,"The " + i.name() + " broke!");
	        	world.remove(i, wx, wy, wz);
	        }
		} else
			doAction("fire your " + weapon().name());
	}
	
    /**
     * Inventory Based Commands
     */
	private Item quiver;
	public Item quiver() { 
		if (quiver != null && !inventory.contains(quiver))
			quiver = null;
		if (quiver == null || quiver.isCompatibleAmmoWith(weapon())) {
			for (Item i : inventory.getUniqueItems())
				if (i.type() != null && i.isCompatibleAmmoWith(weapon())) {
					setQuiver(i);
					break;
				}
		}
		return quiver;
	}
	public void setQuiver(Item i) { quiver = i; }
	
    public void pickup() {
		Inventory items = world.items(x, y, z);
		pickup(items.getFirstItem());
	}
	public void pickup(Item item) {
		Inventory items = world.items(x, y, z);
		if (items == null) {
			notify("There is nothing here");
		}
		/*
		if (!items.contains(item)) {
			notify("The " + item.name() + " isn't here!");
		}*/
		if (inventory.isFull()) {
			notify("Your inventory is full");
		}
		if (items.contains(item) && !inventory.isFull()) {
			int n = 1;
			if (!item.equippable())
				n = items.quantityOf(item);
			for (int i = 0; i < n; i++) {
				inventory().add(item);
				items.remove(item);
			}
			if (n == 1)
				doAction("pick up a " + item.name());
			else
				doAction("pick up " + n + " "+ item.name() + "s");
		}
		if (items.getItems().size() == 0)
			world.removeInventory(x,y,z);
	}

	public void drop(Item item) {
		int n = 1;
		if (!item.equippable() && item.type() != ItemType.BOOK)
			n = inventory.quantityOf(item);
		for (int i = 0; i < n; i++)
			putAt(item, x, y, z);
		if (n == 1)
			doAction("drop a " + item.name());
		else
			doAction("drop " + n + " "+ item.name() + "s");
	}
	private void getRidOf(Item item) {
		unequip(item);
		inventory.remove(item);
	}
	private void putAt(Item item, int wx, int wy, int wz) {
		getRidOf(item);
		world.addItem(item, wx, wy, wz);
	}
    public void unequip(Item item) {
		if (item == null || item.type() == null)
			return;
		if (equipment.containsValue(item)) {
			if (hp > 0) {//To prevent the game stating an unequip when things die
				if (item.type() == ItemType.WEAPON) {
					doAction("unwield a " + item.name());
					lastWielded = item;
				} else
					doAction("take off a " + item.name());
			}
			equipment.remove(item.type());
		}
	}
	public void equip(Item item) {
		if (!inventory.contains(item)) {
			if (inventory.isFull()) {
				notify("Can't equip " + item.name() + " because your inventory is full.");
				return;
			} else {
				world.remove(item);
				inventory.add(item);
			}
		}
		if (!item.equippable()) {
			notify("You cannot equip this");
			return;
		}
		if (hasEquipped(item)) {
			unequip(item);
			return;
		}
		if (item == null || item.type() == null)
			return;
		if (equipment.containsKey(item.type())) {
			unequip(equipment.get(item.type()));
		}
		if (item.type() == ItemType.WEAPON)
			doAction("wield a " + item.name());
		else
			doAction("wear a " + item.name());
		equipment.put(item.type(), item);
	}
	public void addItemToInventory(Item item) {
		if (inventory == null)
			inventory = new Inventory();
		inventory.add(item);
	}
	public void addEquipment(Item item) {
		addItemToInventory(item);
		equip(item);
	}
	public boolean hasEquipped(Item item) {
		return equipment.containsValue(item);
	}
	
	public void quaff(Item item){
		if (is(Tag.NOQUAFF)) {
			notify("You cannot quaff potions!");
			return;
		}
        doAction("quaff a " + item.name());
        addEffect(item.effect());
        getRidOf(item);
    }
	private Item lastWielded;
	public Item lastWielded() {
		if (!inventory.contains(lastWielded))
			lastWielded = null;
		return lastWielded; 
	}
	public boolean hasItemTag(ItemTag t) {
		for (Item i : equipment.values())
			if (i.is(t))
				return true;
		return false;
	}
	
    /**
     * Notifications
     */
    public void notify(String message){
        ai.onNotify(message);
    }
    
    public void doAction(String message){
    	int r = 9;
    	for (int ox = -r; ox < r+1; ox++){
    		for (int oy = -r; oy < r+1; oy++){
    			if (ox*ox + oy*oy > r*r)
    				continue;

    			Creature other = world.creature(x+ox, y+oy, z);

    			if (other == null)
    				continue;

    			if (other == this) {
    				other.notify(("You " + message + ".").replaceAll(" look ", " feel "));
    			} else if (other.canSee(x, y, z)) {
    				String s = "";
    				if (!is(Tag.LEGENDARY))
    					s += "The ";
    				other.notify(s + name + " " + makeSecondPerson(message));
    			}
    		}
    	}
    }
    private String makeSecondPerson(String text){
    	String[] words = text.split(" ");
    	words[0] = words[0] + "s";

    	StringBuilder builder = new StringBuilder();
    	for (String word : words){
    		builder.append(" ");
    		builder.append(word);
    	}
    	String s = builder.toString().trim();
    	return s.replaceAll("the Player", "you");
    }
    
    public boolean canEnter(int mx, int my, int mz) {
    	return (world.tile(mx, my, mz).isGround() && world.creature(mx, my, mz) == null &&
    			!(world.feature(mx,my,mz) != null && world.feature(mx, my, mz).blockMovement()));
    }
    
    /**
	 * Spells
	 */
	protected ArrayList<Spell> spells;
	public ArrayList<Spell> spells() { return spells; }
	public void addSpell(Spell newSpell) {
		if (spells == null)
			spells = new ArrayList<Spell>();
		spells.add(newSpell); 
		notify("You memorized " + newSpell.name() + "!");
	}
	public void removeSpell(Spell spell) {
		if (spells != null) {
			spells.remove(spell);
			notify("You forgot " + spell.name() + "!");
		}
	}
	public void castSpell(Spell spell, int x, int y) {
		ArrayList<Point> temp = new ArrayList<Point>();
		temp.add(new Point(x,y,z));
		castSpell(spell, temp);
	}
	public void castSpell(Spell spell, List<Point> points) {
		if (is(Tag.PLAYER) && spell.level() > magic.get(spell.type())) {
			notify("Your " + spell.type().text() + " skill is not high enough to cast " + spell.name());
			return;
		} if (spell.cost() > mana) {
			notify("You don't have enough mana to cast " + spell.name());
			return;
		} if (is(Tag.NOCAST)) {
			notify("You cannot cast spells");
			return;
		}
		modifyMana(-spell.cost());
		
		if (spell.useText() != null)
			doAction(spell.useText());
		else
			doAction("cast " + spell.name());
		
		for (Point p : points)
			if (creature(p.x,p.y,z) != null) {
				lastAttacked = creature(p.x,p.y,z);
				break;
			}
		
		for (Point p : points)
			spellEffects(spell, p.x, p.y);
		
		spell.casterEffect(this);
	}
	private void spellEffects(Spell spell, int x, int y) {
		Creature target = creature(x,y,z);
		if (target == null)
			return;
		if (spell.damage() != null) {
			spell.physicalAttack(this, target);
		}
		if (spell.effect() != null) {
			if (spell.effectSuccess(this, target) || this == target) {
				Effect spellEffect = spell.effect();
				spellEffect.setOwner(this);
				target.addEffect(spellEffect);
			} else {
				target.doAction("resist");
			}
		}
	}
	
	/**
	 * Abilities
	 */
	private ArrayList<Ability> abilities;
	public ArrayList<Ability> abilities() { return abilities; }
	public void addAbility(Ability newAbility) {
		if (abilities == null)
			abilities = new ArrayList<Ability>();
		abilities.add(newAbility); 
		notify("You learned " + newAbility.name() + "!");
	}
	public void removeAbility(Ability ability) {
		if (abilities != null) {
			abilities.remove(ability);
			notify("You forgot " + ability.name() + "!");
		}
	}
	private void updateAbilities() {
		if (abilities == null)
			return;
		for (Ability a : abilities)
			if (a.time() > 0)
				a.modifyTime(-1);
		for (Item i : equipment().values()) {
			if (i.ability() != null)
				i.ability().modifyTime(-1);
		}
	}
	public void activateAbility(Ability a, List<Point> points) {
		if (a.time() > 0) {
			notify(a.name() + " is still on cooldown.");
			return;
		}
		a.refreshTime();
		if (a.useText() != null)
			doAction(a.useText());
		a.activate(this);
		for (Point p : points)
			if (creature(p.x,p.y,z) != null)
				a.activate(this, creature(p.x,p.y,z));
	}
	
	/**
	 * Effects
	 */
	private List<Effect> effects;
	public List<Effect> effects(){ return effects; }
	private List<Effect> delayedEffects;
	public void addDelayedEffect(Effect e) {
		if (delayedEffects == null)
			delayedEffects = new ArrayList<Effect>();
		delayedEffects.add(e);
	}
	public void addEffect(Effect effect){
        if (effect == null)
            return;
        Effect newEffect = (Effect)(effect.clone());
        for (Effect e : effects)
        	//If you get an effect with the same name as an effect already afflicting you, add its duration and remove the effect
        	if (e.name().equals(newEffect.name())) {
//        		if (newEffect.strength() > e.strength()) {
//        			int t = e.duration();					This is really broken right now
//        			//e = newEffect;						It technically works, just if you buff up it doesnt
//        			effects.remove(e);						add the difference to the buff at the start, so the end
//        			effects.add(newEffect);					still reduces you by the full amount of a greater value
//        			newEffect.modifyDuration(t);
//        		} else
        			e.modifyDuration(newEffect.duration());
        		return;
        	}
        newEffect.start(this);
        if (newEffect.duration() > 0)
        	effects.add(newEffect);
    }
	public void updateEffects(){
		List<Effect> done = new ArrayList<Effect>();
		for (Effect effect : effects){
			effect.update(this);
			if (effect.isDone()) {
				effect.end(this);
				done.add(effect);
			}
		}
		if (delayedEffects != null) {		//A way to have effects apply other effects on end
			for (Effect effect : delayedEffects) {
				addEffect(effect);
			}
			delayedEffects = null;
		}
		effects.removeAll(done);
    }
	public ArrayList<String> getEffectNames() {
		ArrayList<String> list = new ArrayList<String>();
		for (Effect e : effects) {
			list.add(e.name());
		}
		return list;
	}
	public void update(){  
		if (hp <= 0)
			return;
		regenerate();
    	updateEffects();
    	updateAbilities();
    	ai.onUpdate();  
    }
	private HashMap<Effect, Integer> effectsOnHit;
	public HashMap<Effect, Integer> effectsOnHit() {
		HashMap<Effect, Integer> newMap;
		if (effectsOnHit == null)
			newMap = new HashMap<Effect, Integer>();
		else
			newMap = new HashMap<Effect, Integer>(effectsOnHit);
		Item weapon = weapon();
		if (weapon != null && weapon.effectOnHit() != null)
			newMap.put(weapon.effectOnHit(), weapon.effectChance());
		return newMap; 
	}
	public void addEffectOnHit(Effect e, int chance) {
		if (effectsOnHit == null)
			effectsOnHit = new HashMap<Effect, Integer>();
		effectsOnHit.put(e, chance);
		e.setOwner(this);
	}
	public boolean isStunned() { return is(Tag.STUNNED); }
	public boolean isConfused() { return is(Tag.CONFUSED); }
	public void confusedWander() { ai.confusedWander(); }
	
	/**
	 * Tags
	 */
	private ArrayList<Tag> tags;
	public ArrayList<Tag> tags() { return tags; }
	public boolean is(Tag t) {
		return tags != null && tags.contains(t);
	}
	public void addTag(Tag t) {
		if (tags == null)
			tags = new ArrayList<Tag>();
		tags.add(t);
		t.unlock(this);
	}
	public void removeTag(Tag t) {
		if (tags == null)
			return;
		tags.remove(t);
	}
	
	/**
	 * Regeneration over time
	 */
	private int healthRegenTimer;
	private int manaRegenTimer;
	private int healthTimePerTurn() {
		if (is(Tag.PLAYER))
			return 100 + (level + getToughness()) * 10;
		return 50;
	}
	private int manaTimePerTurn() {
		if (is(Tag.PLAYER))
			return 100 + (level + getWill()) * 10;
		return 50;
	}
	private void regenerate() {
		healthRegenTimer += healthTimePerTurn();
		manaRegenTimer += manaTimePerTurn();
		if (healthRegenTimer >= 1000) {
			modifyHP(1);
			healthRegenTimer -= 1000;
		}
		if (manaRegenTimer >= 1000) {
			modifyMana(1);
			manaRegenTimer -= 1000;
		}
	}
	
    /**
     * MISCELLANEOUS
     */
    public void dig(int wx, int wy, int wz) {
        world.dig(wx, wy, wz);
    }
    public String desc() {
    	String s = "";
    	if (weapon() != null)
    		s += " Wielding a " + weapon().name();
    	return s;
    }
	
}