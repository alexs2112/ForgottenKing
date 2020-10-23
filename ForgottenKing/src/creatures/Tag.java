package creatures;

public enum Tag {
	PLAYER("Player"),
	ADVENTURER("Adventurer"),
	RANGER("Ranger"),
	FIGHTER("Fighter"),
	
	SPELLCASTER("Spellcaster"),
	ERRATIC("Erratic"),
	
	BRAWLER("Brawler"),
	SKIRMISHER("Skirmisher"),
	ARTILLERY("Artillery"),
	SUPPORT("Support");
	
	private String text;
	public String text() { return text; }
	
	private Tag(String text) {
		this.text = text;
	}

}
