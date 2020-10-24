package creatures;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;

//A really clunky way to do it, really only calls it once, hence all the public variables
public class ClassSelection {
	public Image largeIcon;
	public Image icon;
	public Tag title;
    public int hp;
    public int evasion;
    public int armor;
    public int attack;
    public int damageMin;
    public int damageMax;
    public int strength;
    public int dexterity;
    public int intelligence;
    public int toughness;
    public int brawn;
    public int agility;
    public int accuracy;
    public int will;
    public int spellcasting;
    public String descriptionText;
    private List<Tag> tags;
    public List<Tag> tags() { return tags; }
    public void addTag(Tag t) { tags.add(t); }
    
    
    /**
     * All that this is used for is to transition all of the starting stats from character selection to playscreen
     * Starting equipment is handled in playscreen by tag
     */
    public ClassSelection(Tag title, Image largeIcon, Image icon, int hp, int ev, int armor, int attack, int damageMin, int damageMax) {
    	this.title = title;
    	this.largeIcon = largeIcon;
    	this.icon = icon;
    	this.hp = hp;
    	this.evasion = ev;
    	this.armor = armor;
    	this.attack = attack;
    	this.damageMin = damageMin;
    	this.damageMax = damageMax;
    	tags = new ArrayList<Tag>();
    }
    
    public void setAttributes(int STR, int DEX, int INT) {
    	this.strength = STR;
    	this.dexterity = DEX;
    	this.intelligence = INT;
    }
    public void setStats(int toughness, int brawn, int agility, int accuracy, int will, int spellcasting) {
    	this.toughness = toughness;
    	this.brawn = brawn;
    	this.agility = agility;
    	this.accuracy = accuracy;
    	this.will = will;
    	this.spellcasting = spellcasting;
    	
    }
}
