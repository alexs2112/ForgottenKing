package assembly;

import items.Item;
import items.ItemType;
import tools.Icon;

public class BookFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private CreatureFactory creatureFactory;
	public void setCreatureFactory(CreatureFactory f) { creatureFactory = f; }
	
	private Icon kindlingIcon = new Icon("items/books-full.png", 0, 0);
	private Icon flamesIcon = new Icon("items/books-full.png", 64, 0);
	private Icon chillsIcon = new Icon("items/books-full.png", 32, 0);
	private Icon frostIcon = new Icon("items/books-full.png", 0, 32);
	private Icon staticIcon = new Icon("items/books-full.png", 96, 64);
	private Icon electricityIcon = new Icon("items/books-full.png", 96, 128);
	private Icon debilitationIcon = new Icon("items/books-full.png", 224, 32);
	private Icon vitalityIcon = new Icon("items/books-full.png", 64, 32);
	private Icon maledictionsIcon = new Icon("items/books-full.png", 128, 96);
	private Icon lizardRitualsIcon = new Icon("items/books-full.png", 160, 0);
	
	public Item newRandomBook(int z) {
		if (z < 3) {
			switch( (int)(Math.random()*5) ) {
			case 0: return newBookOfKindling();
			case 1: return newBookOfChills();
			case 2: return newBookOfStatic();
			case 3: return newBookOfVitality();
			case 4: return newBookOfDebilitation();
			}
		} else if (z < 7) {
			switch((int)(Math.random()*4)) {
			case 0: return newBookOfFlames();
			case 1: return newBookOfFrost();
			case 2: return newBookOfElectricity();
			case 3: return newBookOfMaledictions();
			}
		}
		return newBookOfKindling();
	}
	
	/**
	 * FIRE
	 */
	public Item newBookOfKindling() {
		Item item = new Item("Book of Kindling", ItemType.BOOK, kindlingIcon);
		item.addSpell(Spells.embers());
		item.addSpell(Spells.flameWave());
		item.addSpell(Spells.moltenFire());
		item.setDescription("A thin dark book, the cover is coated in ash and the pages feel brittle to the touch.");
		return item;
	}
	public Item newBookOfFlames() {
		Item item = new Item("Book of Flames", ItemType.BOOK, flamesIcon);
		item.addSpell(Spells.summonImp(creatureFactory));
		item.addSpell(Spells.heatbeam());
		item.addSpell(Spells.fireball());
		item.setDescription("A large, charred tome. This book seems to give off an inner heat and burns your fingers to the touch.");
		return item;
	}
	
	/**
	 * COLD
	 */
	public Item newBookOfChills() {
		Item item = new Item("Book of Chills", ItemType.BOOK, chillsIcon);
		item.addSpell(Spells.chill());
		item.addSpell(Spells.iceShard());
		item.addSpell(Spells.summonSimulacrum(creatureFactory));
		item.setDescription("A thin tome of knowledge, the pages are cold to the touch and the cover seems slightly wet.");
		return item;
	}
	public Item newBookOfFrost() {
		Item item = new Item("Book of Frost", ItemType.BOOK, frostIcon);
		item.addSpell(Spells.armorOfFrost());
		item.addSpell(Spells.icicle());
		item.addSpell(Spells.massChill());
		item.setDescription("A thick tome, the pages are frozen and brittle to the touch.");
		return item;
	}
	
	/**
	 * AIR
	 */
	public Item newBookOfStatic() {
		Item item = new Item("Book of Static", ItemType.BOOK, staticIcon);
		item.addSpell(Spells.shockingTouch());
		item.addSpell(Spells.swiftness());
		item.addSpell(Spells.minorStun());
		item.setDescription("A thin sleeve of papers, seemingly held together by a force that shocks you when you touch it.");
		return item;
	}
	public Item newBookOfElectricity() {
		Item item = new Item("Book of Electricity", ItemType.BOOK, electricityIcon);
		item.addSpell(Spells.blink());
		item.addSpell(Spells.whirlwind());
		item.addSpell(Spells.lightningBolt());
		item.setDescription("A bound set of papers, electricity crackles between them and cause your fingers to tingle when you bring them close.");
		return item;
	}
	
	/**
	 * POISON
	 */
	public Item newBookOfDebilitation() {
		Item item = new Item("Book of Debilitation", ItemType.BOOK, debilitationIcon);
		item.addSpell(Spells.sting());
		item.addSpell(Spells.toxicCloud());
		item.addSpell(Spells.minorConfuse());
		item.setDescription("A thin book consisting of few pages. The pages are sickly pale and the entire thing is brittle.");
		return item;
	}
	
	/**
	 * LIGHT
	 */
	public Item newBookOfVitality() {
		Item item = new Item("Book of Vitality", ItemType.BOOK, vitalityIcon);
		item.addSpell(Spells.curePoison());
		item.addSpell(Spells.innerGlow());
		item.addSpell(Spells.regenerateHealth());
		item.addSpell(Spells.heroism());
		item.setDescription("A plain, leather-bound book describing simple light spells of healing and strength.");
		return item;
	}
	
	/**
	 * ENEMY DROPS
	 */
	public Item newBookOfMaledictions() {
		Item item = new Item("Book of Maledictions", ItemType.BOOK, maledictionsIcon);
		item.addSpell(Spells.weaken());
		item.addSpell(Spells.blind());
		item.addSpell(Spells.soulSiphon());
		item.setDescription("This book is quite plain, however it is chill to the touch and light seems to fade around it.");
		return item;
	}
	public Item newBookOfLizardRituals() {
		Item item = new Item("Book of Lizard Rituals", ItemType.BOOK, lizardRitualsIcon);
		item.addSpell(Spells.vulnerability());
		item.addSpell(Spells.minorStun());
		item.addSpell(Spells.darksmite());
		item.setDescription("An embellished book full of rituals devised and used by lizard priests.");
		return item;
	}

}
