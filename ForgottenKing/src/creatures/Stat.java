package creatures;

public enum Stat {
	TOUGHNESS("Toughness", Attribute.STR),
	BRAWN("Brawn", Attribute.STR),
	AGILITY("Agility", Attribute.DEX),
	ACCURACY("Accuracy", Attribute.DEX),
	WILL("Will", Attribute.INT),
	SPELLCASTING("Spellcasting", Attribute.INT);
	private static final long serialVersionUID = 7769423305067121315L;
	private Attribute parent;
	public Attribute parent() { return parent; }
	private String text;
	public String text() { return text; }
	
	private Stat(String text, Attribute parent) {
		this.text = text;
		this.parent = parent;
	}
}
