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
	private Image soldierAntIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/giant_ant.gif"));
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
				return newSoldierAnt(z);
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
	    bat.setDescription("A large grey bat that, despite being herbivorous, is quite aggressive. It is myopic and uses echolocation to navigate.");//*
	    world.addAtEmptyLocation(bat, z);
	    new BasicAI(bat, player);
	    return bat;
	}
	public Creature newRat(int z) {
		Creature rat = new Creature(world, "Rat",1,25, 10, 9, 0, 1, 1, 3, ratIcon);
		rat.setAttributes(1,1,1);
	    rat.setStats(1,1,1,2,1,0);
	    rat.modifyMovementDelay(-1);
	    rat.setDescription("A dirty rodent that has grown large and aggressive in the dungeon environment."); //*
	    world.addAtEmptyLocation(rat, z);
	    new BasicAI(rat, player);
	    return rat;
	}
	public Creature newKobold(int z) {
		Creature kobold = new Creature(world, "Kobold",1,30, 11, 9, 0, 2, 1, 3, koboldIcon);
		kobold.setAttributes(1,1,1);
	    kobold.setStats(1,2,2,2,1,1);
	    kobold.setDescription("Kobolds are small, lizardfolk with the brains to match. No one knows for sure where kobolds come from, but ancient demon-gods, evil spirits, and meddling wizards have all been suggested as culprits.");//*
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
		zombie.addTag(Tag.UNDEAD);
		zombie.setDescription("The dead have risen. It is slow and resilient, although its rotting flesh is vulnerable to fire.");
		world.addAtEmptyLocation(zombie,z);
		new BasicAI(zombie, player);
		return zombie;
	}
	public Creature newGoblin(int z) {
		Creature goblin = new Creature(world, "Goblin",2,60, 15, 8, 0, 1, 1, 3, goblinIcon);
		new BasicAI(goblin, player);
		goblin.setAttributes(2, 1, 1);
		goblin.setStats(2, 2, 2, 3, 1, 2);
		goblin.setDescription("A short and ugly humanoid, they make up for their lack of intelligence with their stupidity.");
		world.addAtEmptyLocation(goblin, z);
		for (int i = 0; i < (int)(Math.random()*10)+1; i++)
			goblin.addItemToInventory(itemFactory.ammo().newRock(-1));
		return goblin;
	}
	public Creature newSoldierAnt(int z) {
		Creature soldierAnt = new Creature(world, "Soldier Ant",2,70, 10, 8, 2, 1, 0, 2, soldierAntIcon);
		soldierAnt.setAttributes(2, 1, 0);
		soldierAnt.setStats(3,2,2,2,0,0);
		soldierAnt.addEffectOnHit(Effects.poisoned(3, 1, 50));
		soldierAnt.addTag(Tag.VENOMOUS);
		soldierAnt.setDescription("An ant that has grown the size of a small cow. Its razor sharp mandibles drip with poison.");
		world.addAtEmptyLocation(soldierAnt, z);
		new BasicAI(soldierAnt, player);
		return soldierAnt;
	}
	public Creature newHomunculus(int z) {
		Creature homunculus = new Creature(world, "Homunculus",2,60, 14,8,1,1,1,3, homunculusIcon);
		new SpellcastingAI(homunculus, player, 60);
		homunculus.setMana(12, 12);
		homunculus.setAttributes(1,1,2);
	    homunculus.setStats(1,1,3,2,2,3);
	    homunculus.addTag(Tag.SPELLCASTER);
	    homunculus.addSpell(Spells.homunculiSlow());
	    homunculus.setDescription("The creation of a wizard, it wanders the dungeon for a purpose. Its bright blue eyes seem to cloud your vision and drain your energy.");
	    world.addAtEmptyLocation(homunculus, z);
	    return homunculus;
	}
}
