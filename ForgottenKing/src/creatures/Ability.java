package creatures;

import javafx.scene.image.Image;
import spells.TargetType;

public class Ability {
	private String name;
	public String name() { return name; }
	private Image icon;
	public Image icon() { return icon; }
	private String description;
	public String description() { return description; }
	public void setDescription(String desc) { description = desc; }
	private String useText;
	public String useText() { return useText; }
	public void setUseText(String s) { useText = s; } 
	private int cooldown;
	public int cooldown() { return cooldown; }
	private int time;
	public int time() { return time; }
	public void modifyTime(int x) { time = Math.max(0, time += x); }
	public void setTime(int x) { time = x; }
	public void refreshTime() { time = cooldown(); }
	private TargetType targetType;
	public TargetType targetType() { return targetType; }
	public void setTargetType(TargetType t) { targetType = t; }
	private int range;
	public int range() { return range; }
	public void setRange(int x) { range = x; }
	private int radius;
	public int radius() { return radius; }
	public void setRadius(int x) { radius = x; }
	
	public Ability(String name, Image icon, int cooldown) {
		this.name = name;
		this.icon = icon;
		this.cooldown = cooldown;
	}
	
	//To be overridden
	public void activate(Creature owner) { }
	public void activate(Creature owner, Creature other) { }	//If using targettype self with a radius of 0, use this for buffs
	
}
