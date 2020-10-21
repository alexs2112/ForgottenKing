package creatures;

import javafx.scene.image.Image;
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
	DARK("Dark", Loader.darkIcon, Loader.largeDarkIcon);
	
	private String type;
	public String text() { return type; }
	private Image icon;
	public Image icon() { return icon; }
	private Image largeIcon;
	public Image largeIcon() { return largeIcon; }
	public boolean physical() { return this == SLASHING || this == PIERCING || this == CRUSHING; }
	
	private Type(String type, Image icon, Image largeIcon) {
		this.type = type;
		this.icon = icon;
		this.largeIcon = largeIcon;
	}

}
