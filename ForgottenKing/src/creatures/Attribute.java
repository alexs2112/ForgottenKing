package creatures;

public enum Attribute {
	STR("Strength"),
	DEX("Dexterity"),
	INT("Intelligence");
	
	private String text;
	public String text() { return text; }
	private Attribute(String text) {
		this.text = text;
	}
}
