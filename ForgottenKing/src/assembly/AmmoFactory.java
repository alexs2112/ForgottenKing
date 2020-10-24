package assembly;

import creatures.Type;
import items.Item;
import items.ItemTag;
import items.ItemType;
import javafx.scene.image.Image;
import tools.ImageCrop;
import world.World;

public class AmmoFactory {
	private World world;
	
	private Image rocksIconFull = new Image(this.getClass().getResourceAsStream("resources/items/rocks_full.png"));
	private Image rockIcon = tools.ImageCrop.cropImage(rocksIconFull, 96, 0, 32, 32);
		
	private Image rangedFull = new Image(this.getClass().getResourceAsStream("resources/items/ranged_full.png"));
	private Image arrowImage = ImageCrop.cropImage(rangedFull, 0, 64, 32, 32);
	
	public AmmoFactory(World world) {
		this.world = world;
	}
	
	public Item newRock(int z) {
		Item rock = new Item("Rock", ItemType.STONE, rockIcon);
		rock.modifyThrownAttackValue(1);
		rock.addTag(ItemTag.THROWING);
		rock.setDescription("A solid stone dredged from the earth. Fits perfectly in the palm of your hand, or perhaps a sling.");
		world.addAtEmptyLocation(rock,z);
		return rock;
	}

	public Item newArrow(int z) {
		Item item = new Item("arrow", ItemType.ARROW, arrowImage);
		item.setDamageType(Type.PIERCING);
		item.setRangedDamage(2, 2);
		item.setDescription("A long shafted projectile intended to be shot with a shortbow or longbow, fletched with feathers at one end and a point at the other.");
		int amount = (int)(Math.random()*5 + 2);
		world.addAtEmptyLocation(item, z, amount);
		return item;
	}
	
	
}
