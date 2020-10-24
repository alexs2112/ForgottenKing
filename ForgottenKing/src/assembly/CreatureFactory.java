package assembly;

import java.util.List;

import creatures.ClassSelection;
import creatures.Creature;
import creatures.Type;
import creatures.Tag;
import creatures.ai.*;
import javafx.scene.image.Image;
import tools.FieldOfView;
import tools.ImageCrop;
import world.World;

public class CreatureFactory {
	private World world;
	private ItemFactory itemFactory;
	private Creature player;
	
	private Image plantIconsFull = new Image(this.getClass().getResourceAsStream("resources/creatures/plants_full.png"));
	private Image goblinIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/goblin.gif"));
	private Image fungusIcon = ImageCrop.cropImage(plantIconsFull, 0, 224, 32, 32);
	private Image batIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/bat.gif"));
	private Image zombieIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/zombie.gif"));
	private Image ratIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/rat.gif"));
	private Image koboldIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/kobold.gif"));
	private Image giantAntIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/giant_ant.gif"));
	private Image homunculusIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/homunculus.gif"));
	
	public CreatureFactory(World world, ItemFactory itemFactory) {
		this.world = world;
		this.itemFactory = itemFactory;
	}
	public void setPlayer(Creature player) {
		this.player = player;
	}
	
	public Creature newRandomCreature(int z, int level) {
		if (level == 1) {
			int c = (int)(Math.random()*3);
			if (c == 0)
				return newBat(z);
			else if (c == 1)
				return newRat(z);
			else
				return newKobold(z);
		} else if (level == 2) {
			int c = (int)(Math.random()*4);
			if (c == 0)
				return newGoblin(z);
			else if (c == 1)
				return newZombie(z);
			else if (c == 2)
				return newGiantAnt(z);
			else if (c == 3)
				return newHomunculus(z);
		}
		return newGoblin(z);
	}
	
	public Creature newPlayer(List<String> messages, int z, FieldOfView fov, ClassSelection c){
	    Creature player = new Creature(world, "Player", 1, 0, c.hp, c.evasion, c.armor, c.attack, c.damageMin, c.damageMax, c.icon);
	    player.setAttributes(c.strength,c.dexterity,c.intelligence);
	    player.setStats(c.toughness,c.brawn,c.agility,c.accuracy,c.will,c.spellcasting);
	    world.addAtEmptyLocation(player, z);
	    player.addTag(Tag.PLAYER);
	    player.addTag(c.title);
	    new PlayerAI(player, messages, fov);
	    return player;
	}
	
	public Creature newFungus(int z, int spreadCount){
	    Creature fungus = new Creature(world, "Fungus",0,0, 8, 0, 0, 0, 0, 0, fungusIcon);
	    world.addAtEmptyLocation(fungus, z);
	    new FungusAI(fungus, this, spreadCount);
	    return fungus;
	}
	
	public Creature newBat(int z) {
		Creature bat = new Creature(world, "Bat",1,25, 10, 9, 0, 1, 0, 2, batIcon);
		bat.setAttributes(0,2,1);
	    bat.setStats(1,2,3,1,1,1);
	    bat.modifyMovementDelay(-5);
	    bat.modifyAttackDelay(-5);
	    bat.addTag(Tag.ERRATIC);
	    world.addAtEmptyLocation(bat, z);
	    new BasicAI(bat, player);
	    return bat;
	}
	public Creature newRat(int z) {
		Creature rat = new Creature(world, "Rat",1,25, 10, 9, 0, 1, 1, 3, ratIcon);
		rat.setAttributes(1,1,1);
	    rat.setStats(1,1,1,2,1,0);
	    rat.modifyMovementDelay(-1);
	    world.addAtEmptyLocation(rat, z);
	    new BasicAI(rat, player);
	    return rat;
	}
	public Creature newKobold(int z) {
		Creature kobold = new Creature(world, "Kobold",1,30, 11, 9, 0, 2, 1, 3, koboldIcon);
		kobold.setAttributes(1,1,1);
	    kobold.setStats(1,2,2,2,1,1);
	    world.addAtEmptyLocation(kobold, z);
	    new BasicAI(kobold, player);
	    return kobold;
	}

	public Creature newZombie(int z) {
		Creature zombie = new Creature(world, "Zombie",2,60, 16, 7, 0, 1, 1, 4, zombieIcon);
		zombie.setAttributes(2, 1, 1);
		zombie.setStats(2, 3, 1, 2, 1, 2);
		zombie.setResistance(Type.POISON, 4);
		zombie.setResistance(Type.FIRE, -1);
		zombie.modifyAttackDelay(3);
		zombie.modifyMovementDelay(3);
		world.addAtEmptyLocation(zombie,z);
		new BasicAI(zombie, player);
		return zombie;
	}
	public Creature newGoblin(int z) {
		Creature goblin = new Creature(world, "Goblin",2,60, 15, 8, 0, 1, 1, 3, goblinIcon);
		new BasicAI(goblin, player);
		goblin.setAttributes(2, 1, 1);
		goblin.setStats(2, 2, 2, 3, 1, 2);
		world.addAtEmptyLocation(goblin, z);
		for (int i = 0; i < (int)(Math.random()*10)+1; i++)
			goblin.addItemToInventory(itemFactory.equipment().newRock(-1));
		return goblin;
	}
	public Creature newGiantAnt(int z) {
		Creature giantAnt = new Creature(world, "Giant Ant",2,70, 10, 8, 2, 1, 0, 2, giantAntIcon);
		giantAnt.setAttributes(2, 1, 0);
		giantAnt.setStats(3,2,2,2,0,0);
		giantAnt.addEffectOnHit(Effects.poisoned(3, 1, 50));
		world.addAtEmptyLocation(giantAnt, z);
		new BasicAI(giantAnt, player);
		return giantAnt;
	}
	public Creature newHomunculus(int z) {
		Creature homunculus = new Creature(world, "Homunculus",2,60, 14,8,1,1,1,3, homunculusIcon);
		new SpellcastingAI(homunculus, player, 60);
		homunculus.setMana(12, 12);
		homunculus.setAttributes(1,1,2);
	    homunculus.setStats(1,1,3,2,2,3);
	    homunculus.addTag(Tag.SPELLCASTER);
	    homunculus.addSpell(Spells.homunculiSlow());
	    world.addAtEmptyLocation(homunculus, z);
	    return homunculus;
	}
}
