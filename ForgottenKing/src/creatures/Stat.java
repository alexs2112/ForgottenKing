package creatures;

public enum Stat {
	TOUGHNESS(Attribute.STR),
	BRAWN(Attribute.STR),
	AGILITY(Attribute.DEX),
	ACCURACY(Attribute.DEX),
	WILL(Attribute.INT),
	SPELLCASTING(Attribute.INT);

	private Attribute parent;
	public Attribute parent() { return parent; }
	
	private Stat(Attribute parent) {
		this.parent = parent;
	}
}
