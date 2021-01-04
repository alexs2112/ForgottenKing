package creatures;

import tools.Icon;
import screens.Loader;

public enum Type {
	SLASHING("Slashing", null, null),
	PIERCING("Piercing", null, null),
	CRUSHING("Crushing", null, null),
	FIRE("Fire", Loader.fireIcon, Loader.largeFireIcon),
	COLD("Cold", Loader.coldIcon, Loader.largeColdIcon),
	AIR("Air", Loader.airIcon, Loader.largeAirIcon),
	POISON("Poison", Loader.poisonIcon, Loader.largePoisonIcon),
	LIGHT("Light", Loader.lightIcon, Loader.largeLightIcon),
	DARK("Dark", Loader.darkIcon, Loader.largeDarkIcon),
	
	BLUE("Blue", null, null),	//
	GREEN("Green", null, null);	//Two additional types used for enchantments
	
	private static final long serialVersionUID = 7769423305067121315L;
	private String type;
	public String text() { return type; }
	private Icon icon;
	public Icon icon() { return icon; }
	private Icon largeIcon;
	public Icon largeIcon() { return largeIcon; }
	public boolean physical() { return this == SLASHING || this == PIERCING || this == CRUSHING; }
	
	private Type(String type, Icon icon, Icon largeIcon) {
		this.type = type;
		this.icon = icon;
		this.largeIcon = largeIcon;
	}

}
