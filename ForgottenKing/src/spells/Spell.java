package spells;

import creatures.Creature;
import creatures.Type;

public class Spell {
	private String name;
	public String name() { return name; }
	private String description;
	public String description(Creature owner) {
		if (description == null)
			return null;
		if (damage != null)
			return description.replaceAll("MINDAMAGE", ""+(damage[0] + owner.magic().get(type) + owner.getSpellcasting()/2))
			.replaceAll("MAXDAMAGE", ""+(damage[1] + owner.magic().get(type) + owner.getSpellcasting()/2)); 
		else
			return description;
		}
	public void setDescription(String s) { description = s; }
	
	private int level;
	public int level() { return level; }
	
	private int cost;
	public int cost() { return cost; }
	
	private Effect effect;
	public Effect effect() {
		if (effect == null)
			return null;
		return (Effect)effect.clone(); 
	}
	public void setEffect(Effect x) { this.effect = x; }
	public void setEffect(Effect x, int effectChance) { 
		this.effect = x;
		this.effectChance = effectChance;
	}
	
	private int effectChance;
	public int effectChance() { return effectChance; }
	public void setEffectChance(int x) { effectChance = x; }
	
	private int attackValue;
	public void setAttackValue(int x) { attackValue = x; }
	private int[] damage;
	public int[] damage() { return damage; }
	public void setDamage(int min, int max) {
		if (damage == null)
			damage = new int[2];
		damage[0] = min;
		damage[1] = max;
	}
	private String actionText;
	public String actionText() { return actionText; }
	public void setActionText(String x) { actionText = x; }
	
	private Type type;
	public Type type() { return type; }
	public void setType(Type x) { type = x; }
	
	private Type damageType;
	public Type damageType() {
		if (damageType == null)
			return type;
		return damageType; 
	}
	public void setDamageType(Type x) { damageType = x; }
	
	private TargetType targetType;
	public TargetType targetType() { return targetType; }
	public void setTargetType(TargetType x) { this.targetType = x; }
	
	private int radius;
	public int radius() { return radius; }
	public void setRadius(int x) { radius = x; }
	
	//If range == 0, it can target anything in sight
	private int range;
	public int range() { return range; }
	public void setRange(int x) { range = x; }
	
	private boolean isBeneficial;
	public boolean isBeneficial() { return isBeneficial; }
	public void setBeneficial(boolean x) { isBeneficial = x; }
	
	private String useText;
	public String useText() { return useText; }
	public void setUseText(String x) { useText = x; }
	
	public void casterEffect(Creature caster) { }	//To be overridden

	public Spell(String name, int cost, int level) {
		this.name = name;
		this.cost = cost;
		this.level = level;
	}
	
	/**
	 * A boolean to see if an effect successfully affects a target
	 * @param owner: The one casting the spell
	 * @param target: The one being attacked by the spell
	 * @return A boolean if the attack succeeded or not
	 */
	public boolean effectSuccess(Creature owner, Creature target) {
		return (target.getWill()*8 + (int)(Math.random()*100) + 1 < owner.magic().get(type)*8 + effectChance);
	}
	
	public void physicalAttack(Creature owner, Creature target) {
		int roll = (int)(Math.random()*10 + Math.random()*10 + 2);
		roll += owner.getSpellcasting() + owner.magic().get(type) + attackValue;
		if (roll >= target.evasion()) {
			int damage = (int)(Math.random() * (this.damage[1] - this.damage[0]) + 1 + owner.magic().get(type) + owner.getSpellcasting()/2);
			if (damageType().physical())
				damage = target.reduceDamageByArmor(damage);
			damage = target.getDamageReceived(damage, damageType);
			
			String text = actionText;
			if (text == null)
				text = "hit the " + target.name();
			if (damage >= 0)
				text += " (dealing " + damage + " damage!)";
			else
				text += " (healing for " + (-damage) + "!)";
			owner.doAction(text);
			
			target.modifyHP(-damage, owner);
		}
	}
	
	

}
