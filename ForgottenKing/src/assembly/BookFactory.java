package assembly;

import items.Item;
import items.ItemType;
import javafx.scene.image.Image;
import world.World;

public class BookFactory {
	private World world;
	private CreatureFactory creatureFactory;
	public void setCreatureFactory(CreatureFactory f) { creatureFactory = f; }
	
	private Image booksFull = new Image(this.getClass().getResourceAsStream("resources/items/books_full.png"));
	private Image kindlingIcon = tools.ImageCrop.cropImage(booksFull, 0, 0, 32, 32);
	private Image flamesIcon = tools.ImageCrop.cropImage(booksFull, 64, 0, 32, 32);
	private Image chillsIcon = tools.ImageCrop.cropImage(booksFull, 32, 0, 32, 32);
	private Image frostIcon = tools.ImageCrop.cropImage(booksFull, 0, 32, 32, 32);
	private Image staticIcon = tools.ImageCrop.cropImage(booksFull, 96, 64, 32, 32);
	private Image electricityIcon = tools.ImageCrop.cropImage(booksFull, 96, 128, 32, 32);
	private Image debilitationIcon = tools.ImageCrop.cropImage(booksFull, 224, 32, 32, 32);
	private Image vitalityIcon = tools.ImageCrop.cropImage(booksFull, 64, 32, 32, 32);
	private Image maledictionsIcon = tools.ImageCrop.cropImage(booksFull, 128, 96, 32, 32);
	private Image lizardRitualsIcon = tools.ImageCrop.cropImage(booksFull, 160, 0, 32, 32);

	public BookFactory(World world) {
		this.world = world;
	}
	
	public Item newRandomBook(int z) {
		if (z < 4) {
			switch( (int)(Math.random()*4) ) {
			case 0: return newBookOfKindling(z);
			case 1: return newBookOfChills(z);
			case 2: return newBookOfStatic(z);
			case 3: return newBookOfVitality(z);
			}
		} else if (z < 7) {
			switch((int)(Math.random()*4)) {
			case 0: return newBookOfFlames(z);
			case 1: return newBookOfFrost(z);
			case 2: return newBookOfElectricity(z);
			case 3: return newBookOfMaledictions(z);
			}
		}
		return newBookOfKindling(z);
	}
	
	/**
	 * FIRE
	 */
	public Item newBookOfKindling(int z) {
		Item item = new Item("Book of Kindling", ItemType.BOOK, kindlingIcon);
		item.addSpell(Spells.embers());
		item.addSpell(Spells.innerGlow());
		item.addSpell(Spells.moltenFire());
		item.setDescription("A thin dark book, the cover is coated in ash and the pages feel brittle to the touch.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newBookOfFlames(int z) {
		Item item = new Item("Book of Flames", ItemType.BOOK, flamesIcon);
		item.addSpell(Spells.flameWave());
		item.addSpell(Spells.heatbeam());
		item.addSpell(Spells.fireball());
		item.setDescription("A large, charred tome. This book seems to give off an inner heat and burns your fingers to the touch.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	/**
	 * COLD
	 */
	public Item newBookOfChills(int z) {
		Item item = new Item("Book of Chills", ItemType.BOOK, chillsIcon);
		item.addSpell(Spells.chill());
		item.addSpell(Spells.iceShard());
		item.addSpell(Spells.summonSimulacrum(creatureFactory));
		item.setDescription("A thin tome of knowledge, the pages are cold to the touch and the cover seems slightly wet.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newBookOfFrost(int z) {
		Item item = new Item("Book of Frost", ItemType.BOOK, frostIcon);
		item.addSpell(Spells.armorOfFrost());
		item.addSpell(Spells.icicle());
		item.addSpell(Spells.massChill());
		item.setDescription("A thick tome, the pages are frozen and brittle to the touch.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	/**
	 * AIR
	 */
	public Item newBookOfStatic(int z) {
		Item item = new Item("Book of Static", ItemType.BOOK, staticIcon);
		item.addSpell(Spells.shockingTouch());
		item.addSpell(Spells.swiftness());
		item.addSpell(Spells.minorStun());
		item.setDescription("A thin sleeve of papers, seemingly held together by a force that shocks you when you touch it.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newBookOfElectricity(int z) {
		Item item = new Item("Book of Electricity", ItemType.BOOK, electricityIcon);
		item.addSpell(Spells.blink());
		item.addSpell(Spells.whirlwind());
		item.addSpell(Spells.lightningBolt());
		item.setDescription("A bound set of papers, electricity crackles between them and cause your fingers to tingle when you bring them close.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	/**
	 * POISON
	 */
	public Item newBookOfDebilitation(int z) {
		Item item = new Item("Book of Debilitation", ItemType.BOOK, debilitationIcon);
		item.addSpell(Spells.sting());
		//item.addSpell(Spells.());	//Summon a poison cloud, need clouds
		item.addSpell(Spells.minorConfuse());
		item.setDescription("A thin book consisting of few pages. The pages are sickly pale and the entire thing is brittle.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	/**
	 * LIGHT
	 */
	public Item newBookOfVitality(int z) {
		Item item = new Item("Book of Vitality", ItemType.BOOK, vitalityIcon);
		item.addSpell(Spells.curePoison());
		item.addSpell(Spells.regenerateHealth());
		item.addSpell(Spells.heroism());
		item.setDescription("A plain, leather-bound book describing simple light spells of healing and strength.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	
	/**
	 * ENEMY DROPS
	 */
	public Item newBookOfMaledictions(int z) {
		Item item = new Item("Book of Maledictions", ItemType.BOOK, maledictionsIcon);
		item.addSpell(Spells.weaken());
		item.addSpell(Spells.blind());
		item.addSpell(Spells.soulSiphon());
		item.setDescription("This book is quite plain, however it is chill to the touch and light seems to fade around it.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newBookOfLizardRituals(int z) {
		Item item = new Item("Book of Lizard Rituals", ItemType.BOOK, lizardRitualsIcon);
		item.addSpell(Spells.vulnerable());
		item.addSpell(Spells.minorStun());
		item.addSpell(Spells.darksmite());
		item.setDescription("An embellished book full of rituals devised and used by lizard priests.");
		world.addAtEmptyLocation(item, z);
		return item;
	}

}
