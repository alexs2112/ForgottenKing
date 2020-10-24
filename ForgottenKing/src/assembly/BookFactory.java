package assembly;

import creatures.Creature;
import creatures.Type;
import items.Item;
import items.ItemType;
import javafx.scene.image.Image;
import spells.Effect;
import world.World;

public class BookFactory {
	private World world;
	
	private Image booksFull = new Image(this.getClass().getResourceAsStream("resources/items/books_full.png"));
	private Image vitalityIcon = tools.ImageCrop.cropImage(booksFull, 64, 32, 32, 32);
	private Image flamesIcon = tools.ImageCrop.cropImage(booksFull, 0, 0, 32, 32);

	public BookFactory(World world) {
		this.world = world;
	}
	
	public Item newRandomBook(int z) {
		int i = (int)(Math.random()*2);
		if (i == 0) return newBookOfVitality(z);
		else return newBookOfFlames(z);
	}
	
	public Item newBookOfVitality(int z) {
		Item item = new Item("Book of Vitality", ItemType.BOOK, vitalityIcon);
		item.addSpell(Spells.curePoison());
		item.addSpell(Spells.regenerateHealth());
		item.addSpell(Spells.heroism());
		item.setDescription("A plain, leather-bound book describing simple light spells of healing and strength.");
		world.addAtEmptyLocation(item, z);
		return item;
	}
	public Item newBookOfFlames(int z) {
		Item item = new Item("Book of Flames", ItemType.BOOK, flamesIcon);
		item.addSpell(Spells.embers());
		item.addSpell(Spells.innerGlow());
		item.addSpell(Spells.moltenFire());
		item.setDescription("A large dark book, the cover is coated in ash and the pages feel brittle to the touch.");
		world.addAtEmptyLocation(item, z);
		return item;
	}

}
