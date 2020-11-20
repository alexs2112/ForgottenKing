package creatures;

public enum Attribute {
	STR("Strength"),
	DEX("Dexterity"),
	INT("Intelligence");
	
	private static final long serialVersionUID = 7769423305067121315L;
	private String text;
	public String text() { return text; }
	private Attribute(String text) {
		this.text = text;
	}
}
