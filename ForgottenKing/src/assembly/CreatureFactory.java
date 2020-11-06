package assembly;

import java.util.HashMap;

import creatures.Ally;
import creatures.ClassSelection;
import creatures.Creature;
import creatures.Player;
import creatures.Type;
import creatures.Tag;
import creatures.ai.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import tools.FieldOfView;
import tools.ImageCrop;
import world.World;

public class CreatureFactory {
	private World world;
	private ItemFactory itemFactory;
	private Player player;
	
	private Image plantIconsFull = new Image(this.getClass().getResourceAsStream("resources/creatures/plants_full.png"));
	private Image goblinIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/goblin.gif"));
	private Image fungusIcon = ImageCrop.cropImage(plantIconsFull, 0, 224, 32, 32);
	private Image batIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/bat.gif"));
	private Image zombieIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/zombie.gif"));
	private Image ratIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/rat.gif"));
	private Image soldierAntIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/giant_ant.gif"));
	private Image homunculusIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/homunculus.gif"));
	private Image lizardGruntIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_grunt.gif"));
	private Image lizardFighterIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_fighter.gif"));
	private Image lizardHunterIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_hunter.gif"));
	private Image lizardHexerIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_hexer.gif"));
	private Image lizardShadowbladeIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_shadowblade.gif"));
	private Image lizardGuardianIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_guardian.gif"));
	private Image lizardPriestIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/lizard_priest.gif"));
	private Image grisstokIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/yamzuushk.gif"));
	
	private Image simulacrumIcon = new Image(this.getClass().getResourceAsStream("resources/creatures/simulacrum.gif"));
	
	public CreatureFactory(World world, ItemFactory itemFactory) {
		this.world = world;
		this.itemFactory = itemFactory;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Creature newRandomCreature(int z, int level) {
		if (level <= 1) {
			int c = (int)(Math.random()*3);
			if (c == 0)
				return newBat(z);
			else if (c == 1)
				return newRat(z);
			else
				return newGoblin(z);
		} else if (level == 2) {
			int c = (int)(Math.random()*4);
			if (c == 0)
				return newLizardGrunt(z);
			else if (c == 1)
				return newZombie(z);
			else if (c == 2)
				return newSoldierAnt(z);
			else if (c == 3)
				return newHomunculus(z);
		} else if (level == 3) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newLizardFighter(z);
			else if (c == 1)
				return newLizardHunter(z);
		} else if (level == 4) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newLizardHexer(z);
			else if (c == 1)
				return newLizardShadowblade(z);
		} else if (level >= 5) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newLizardGuardian(z);
			else if (c == 1)
				return newLizardPriest(z);
		}
		return newGoblin(z);
	}
	
	public Player newPlayer(HashMap<String, Color> messages, int z, FieldOfView fov, ClassSelection c){
	    Player player = new Player(world, "Player", 1, 0, c.hp, c.evasion, c.armor, c.attack, c.damageMin, c.damageMax, c.icon);
	    player.setAttributes(c.strength,c.dexterity,c.intelligence);
	    player.setStats(c.toughness,c.brawn,c.agility,c.accuracy,c.will,c.spellcasting);
	    world.addAtEmptyLocation(player, z);
	    player.addTag(Tag.PLAYER);
	    player.addTag(c.title);
	    player.setLargeIcon(c.largeIcon);
	    player.setTitle(c.title.text());
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
		Creature bat = new Creature(world, "Bat",1,25, 9, 8, 0, 1, 1, 3, batIcon);
		bat.setAttributes(0,2,1);
	    bat.setStats(1,2,3,1,1,1);
	    bat.modifyMovementDelay(-5);
	    bat.modifyAttackDelay(-5);
	    bat.addTag(Tag.ERRATIC);
	    bat.addTag(Tag.FLYING);
	    bat.setDescription("A large grey bat that, despite being herbivorous, is quite aggressive. It is myopic and uses echolocation to navigate.");//*
	    world.addAtEmptyLocation(bat, z);
	    new BasicAI(bat, player);
	    return bat;
	}
	public Creature newRat(int z) {
		Creature rat = new Creature(world, "Rat",1,25, 10, 9, 0, 1, 2, 4, ratIcon);
		rat.setAttributes(1,1,1);
	    rat.setStats(1,1,1,2,1,0);
	    rat.modifyMovementDelay(-1);
	    rat.setDescription("A dirty rodent that has grown large and aggressive in the dungeon environment."); //*
	    world.addAtEmptyLocation(rat, z);
	    new BasicAI(rat, player);
	    return rat;
	}
	public Creature newGoblin(int z) {
		Creature goblin = new Creature(world, "Goblin",1,30, 11, 9, 0, 2, 1, 3, goblinIcon);
		new BasicAI(goblin, player);
		goblin.setAttributes(1, 1, 1);
		goblin.setStats(1, 2, 2, 2, 1, 1);
		goblin.setDescription("A short and ugly humanoid, they make up for their lack of intelligence with their stupidity.");
		world.addAtEmptyLocation(goblin, z);
		for (int i = 0; i < (int)(Math.random()*10)+1; i++)
			goblin.addItemToInventory(itemFactory.ammo().newRock(-1));
		if (z > 0 && Math.random()*100 < 20*z)
	    	goblin.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(-1));
		return goblin;
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
	public Creature newLizardGrunt(int z) {
		Creature lizardGrunt = new Creature(world, "Lizard Grunt",2,60, 15, 9, 0, 2, 1, 3, lizardGruntIcon);
		new BasicAI(lizardGrunt, player);
		lizardGrunt.setAttributes(2,1,1);
	    lizardGrunt.setStats(2,2,2,3,2,1);
	    lizardGrunt.setDescription("The lowest ranking of the lizardfolk, the grunts are usually put on guard duties or doing chores of the higher ranks. Often carrying a small pouch of darts.");
	    world.addAtEmptyLocation(lizardGrunt, z);
	    if (Math.random() * 100 < 60) {
	    	for (int i = 0; i < 3 + (int)(Math.random()*2); i++)
	    		lizardGrunt.addItemToInventory(itemFactory.ammo().newDart(-1));
	    } if (z > 0 && Math.random()*100 < 25*z)
	    		lizardGrunt.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(-1));
	    return lizardGrunt;
	}
	public Creature newSoldierAnt(int z) {
		Creature soldierAnt = new Creature(world, "Soldier Ant",2,70, 10, 8, 2, 1, 0, 2, soldierAntIcon);
		soldierAnt.setAttributes(2, 1, 0);
		soldierAnt.setStats(3,2,2,2,0,0);
		soldierAnt.addEffectOnHit(Effects.poisoned(4, 1), 50);
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
	    homunculus.addSpell(Spells.summonSimulacrum(this));
	    homunculus.setDescription("The creation of a wizard, it wanders the dungeon for a purpose. Its bright blue eyes seem to cloud your vision and drain your energy.");
	    world.addAtEmptyLocation(homunculus, z);
	    return homunculus;
	}
	public Creature newLizardFighter(int z) {
		Creature lizard = new Creature(world, "Lizard Fighter",3,110, 23, 9, 2, 2, 1, 3, lizardFighterIcon);
		new BasicAI(lizard, player);
		lizard.setAttributes(2,1,1);
	    lizard.setStats(3,3,2,2,2,1);
	    lizard.setDescription("A common foot soldier of the Lizardfolk. What they lose in intelligence they make up for with their strength and willingness to follow orders.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(-1));
	    return lizard;
	}
	public Creature newLizardHunter(int z) {
		Creature lizard = new Creature(world, "Lizard Hunter",3,100, 19,10,0,3,2,4, lizardHunterIcon);
		new BasicAI(lizard, player);
		lizard.setAttributes(1,2,1);
	    lizard.setStats(2,2,3,3,2,2);
	    lizard.setDescription("A lizardfolk armed and trained with a bow. Quite deadly at a range, however noticably weaker when they are forced to fight hand to hand.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newShortbow(-1));
	    for (int i = 0; i < 5 + (int)(Math.random()*3); i++)
	    	lizard.addItemToInventory(itemFactory.ammo().newArrow(-1));
	    return lizard;
	}
	public Creature newLizardHexer(int z) {
		Creature lizard = new Creature(world, "Lizard Hexer",4,135, 24,9,2,4,4,6, lizardHexerIcon);
		new SpellcastingAI(lizard, player, 60);
		lizard.setAttributes(1,2,2);
	    lizard.setStats(1,1,3,2,4,4);
	    lizard.setDescription("A lizardfolk trained in the dark arts, they are capable of casting powerful hexes against you and draining your soul.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addTag(Tag.SPELLCASTER);
	    lizard.setMana(20, 20);
	    lizard.addSpell(Spells.weaken());
	    lizard.addSpell(Spells.blind());
	    lizard.addSpell(Spells.soulSiphon());
	    if (Math.random()*100 < 25)
	    	lizard.addItemToInventory(itemFactory.book().newBookOfMaledictions(-1));
	    return lizard;
	}
	public Creature newLizardShadowblade(int z) {
		Creature lizard = new Creature(world, "Lizard Shadowblade",4,140, 24,10,2,4,1,3, lizardShadowbladeIcon);
		new SpellcastingAI(lizard, player, 40);
		lizard.setAttributes(1,2,2);
	    lizard.setStats(1,2,3,3,2,3);
	    lizard.modifyAttackDelay(-3);
	    lizard.modifyMovementDelay(-1);
	    lizard.setDescription("Trained as an elite lizardfolk, the shadowblades are an order of lizard assassins armed with deadly daggers and hexing spells.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newDagger(-1));
	    lizard.addEquipment(itemFactory.armor().newLeatherArmor(-1));
	    lizard.addTag(Tag.SPELLCASTER);
	    lizard.addTag(Tag.IMPROVED_CRITICAL);
	    lizard.setMana(12, 12);
	    lizard.addSpell(Spells.slow(3, 5, 5));
	    lizard.addSpell(Spells.vulnerable());
	    return lizard;
	}
	public Creature newLizardGuardian(int z) {
		Creature lizard = new Creature(world, "Lizard Guardian",5,185, 30,6,2,3,1,3, lizardGuardianIcon);
		new BasicAI(lizard, player);
		lizard.setAttributes(3,1,1);
	    lizard.setStats(3,3,1,2,3,2);
	    lizard.setDescription("A powerful and high ranking lizardfolk trained in the use of an impressive arsenal of arms and armour, they serve to guard the most important of their people. Why are they in the dungeon?");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(-1));
	    lizard.addEquipment(itemFactory.armor().newStuddedLeatherArmor(-1));
	    return lizard;
	}
	public Creature newLizardPriest(int z) {
		Creature lizard = new Creature(world, "Lizard Priest",5,175, 30,8,2,5,5,8, lizardPriestIcon);
		new BasicAI(lizard, player);
		//new SpellcastingAI(lizard, player, 70);
		lizard.setAttributes(1,3,3);
	    lizard.setStats(2,1,4,2,5,5);
	    lizard.setDescription("");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addTag(Tag.SPELLCASTER);
	    lizard.setMana(12, 12);
	    lizard.addSpell(Spells.vulnerable());
	    lizard.addSpell(Spells.darksmite());
	    lizard.addSpell(Spells.minorStun());
	    if (Math.random()*100 < 25)
	    	lizard.addItemToInventory(itemFactory.book().newBookOfLizardRituals(-1));
	    return lizard;
	}
	public Creature newGrisstok(int z) {
		Creature grisstok = new Creature(world, "Griss'tok",6,400, 32,8,2,5,5,8, grisstokIcon);
		new SpellcastingAI(grisstok, player, 50);
		grisstok.setAttributes(4,1,3);
	    grisstok.setStats(4,4,2,2,3,4);
	    grisstok.addTag(Tag.SPELLCASTER);
	    grisstok.addTag(Tag.LEGENDARY);
	    grisstok.setResistance(Type.FIRE, 1);
	    grisstok.addEffectOnHit(Effects.burning(3, 1), 40);
	    grisstok.addSpell(Spells.fireStomp());
	    grisstok.addSpell(Spells.darksmite());
	    grisstok.setMana(12, 12);
	    grisstok.setDescription("What was once a lizardfolk has been turned into a massive monstrosity through priestly ritual. Griss'tok the Fierce has large spined wings and is covered with bloodred scales. He emanates heat and the ground trembles when he moves.");
	    grisstok.addItemToInventory(itemFactory.newVictoryItem(-1));
	    world.addAtEmptyLocation(grisstok, z);
	    return grisstok;
	}
	
	
	/**
	 * SUMMONS
	 */
	public Creature newFriendlySimulacrum(int z, int casterAbility) {
		Ally simulacrum = new Ally(world, "Simulacrum",1, 0, 13+casterAbility, 9, 0, 2, 1, 3, simulacrumIcon);
		new AllyAI(simulacrum, player);
		simulacrum.setAttributes(1+casterAbility, 1+casterAbility, 1+casterAbility);
		simulacrum.setStats(2, 2, 2, 2, 2, 2);
		simulacrum.setDescription("A small humanoid being made purely of ice and elemental cold.");
		simulacrum.addTag(Tag.ALLY);
		simulacrum.setTemporary(30);
		simulacrum.setPlayer(player);
		world.addAtEmptyLocation(simulacrum, z);
		return simulacrum;
	}
	public Creature newSimulacrum(int z) {
		Creature simulacrum = new Creature(world, "Simulacrum",1, 0, 13, 9, 0, 2, 1, 3, simulacrumIcon);
		new BasicAI(simulacrum, player);
		simulacrum.setAttributes(1, 1, 1);
		simulacrum.setStats(2, 2, 2, 2, 2, 2);
		simulacrum.setDescription("A small humanoid being made purely of ice and elemental cold.");
		simulacrum.addEffect(Effects.temporarySummon(30));;
		world.addAtEmptyLocation(simulacrum, z);
		return simulacrum;
	}
}
