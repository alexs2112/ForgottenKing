package assembly;

import java.util.List;

import creatures.Ally;
import creatures.ClassSelection;
import creatures.Creature;
import creatures.Player;
import creatures.Type;
import creatures.Tag;
import creatures.ai.*;
import tools.Icon;
import tools.FieldOfView;
import tools.Message;
import world.World;

public class CreatureFactory implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	private World world;
	private ItemFactory itemFactory;
	private Player player;
	
	private Icon goblinIcon = new Icon("creatures/goblin.gif");
	private Icon batIcon = new Icon("creatures/bat.gif");
	private Icon zombieIcon = new Icon("creatures/zombie.gif");
	private Icon ratIcon = new Icon("creatures/rat.gif");
	private Icon soldierAntIcon = new Icon("creatures/giant_ant.gif");
	private Icon homunculusIcon = new Icon("creatures/homunculus.gif");
	private Icon lizardGruntIcon = new Icon("creatures/lizard_grunt.gif");
	private Icon lizardFighterIcon = new Icon("creatures/lizard_fighter.gif");
	private Icon lizardHunterIcon = new Icon("creatures/lizard_hunter.gif");
	private Icon lizardHexerIcon = new Icon("creatures/lizard_hexer.gif");
	private Icon lizardShadowbladeIcon = new Icon("creatures/lizard_shadowblade.gif");
	private Icon lizardGuardianIcon = new Icon("creatures/lizard_guardian.gif");
	private Icon lizardPriestIcon = new Icon("creatures/lizard_priest.gif");
	private Icon grisstokIcon = new Icon("creatures/yamzuushk.gif");
	
	private Icon orcThugIcon = new Icon("creatures/orc_thug.gif");
	private Icon deepAntIcon = new Icon("creatures/deep_ant.gif");
	private Icon snappingTurtleIcon = new Icon("creatures/snapping_turtle.gif");
	private Icon orcBrawlerIcon = new Icon("creatures/orc_brawler.gif");
	private Icon orcPrivateerIcon = new Icon("creatures/orc_privateer.gif");
	private Icon alligatorIcon = new Icon("creatures/alligator.gif");
	private Icon orcSwashbucklerIcon = new Icon("creatures/orc_swashbuckler.gif");
	private Icon orcGunnerIcon = new Icon("creatures/orc_gunner.gif");
	private Icon orcCaptainIcon = new Icon("creatures/orc_captain.gif");
	private Icon orcWarcasterIcon = new Icon("creatures/orc_warcaster.gif");
	private Icon parrotIcon = new Icon("creatures/parrot.gif");
	private Icon harpyIcon = new Icon("creatures/harpy.gif");
	private Icon orcBossIcon = new Icon("creatures/orc_boss.gif");
	
	private Icon simulacrumIcon = new Icon("creatures/simulacrum.gif");
	private Icon impIcon = new Icon("creatures/imp.gif");
	private Icon bloodApparitionIcon = new Icon("creatures/blood_apparition.gif");
	
	public CreatureFactory(World world, ItemFactory itemFactory) {
		this.world = world;
		this.itemFactory = itemFactory;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public Creature newRandomCreature(int z, int level) {
		if (level <= 0) {
			int c = (int)(Math.random()*3);
			if (c == 0)
				return newBat(z);
			else if (c == 1)
				return newRat(z);
			else
				return newGoblin(z);
		} else if (level == 1) {
			int c = (int)(Math.random()*4);
			if (c == 0)
				return newLizardGrunt(z);
			else if (c == 1)
				return newZombie(z);
			else if (c == 2)
				return newSoldierAnt(z);
			else if (c == 3)
				return newHomunculus(z);
		} else if (level == 2) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newLizardFighter(z);
			else if (c == 1)
				return newLizardHunter(z);
		} else if (level == 3) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newLizardHexer(z);
			else if (c == 1)
				return newLizardShadowblade(z);
		} else if (level == 4) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newLizardGuardian(z);
			else if (c == 1)
				return newLizardPriest(z);
		} else if (level == 5) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newOrcThug(z);
			else if (c == 1)
				return newDeepAnt(z);
		} else if (level == 6) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newSnappingTurtle(z);
			else if (c == 1)
				return newOrcBrawler(z);
		} else if (level == 7) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newOrcPrivateer(z);
			else if (c == 1)
				return newAlligator(z);
		} else if (level == 8) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newOrcSwashbuckler(z);
			else if (c == 1)
				return newOrcGunner(z);
		} else if (level >= 9) {
			int c = (int)(Math.random()*2);
			if (c == 0)
				return newOrcCaptain(z);
			else if (c == 1)
				return newOrcWarcaster(z);
		}
		return newGoblin(z);
	}
	
	public Player newPlayer(List<Message> messages, int z, FieldOfView fov, ClassSelection c){
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
	
	public Creature newBat(int z) {
		Creature bat = new Creature(world, "Bat",1,25, 9, 8, 0, 1, 1, 3, batIcon);
		bat.setAttributes(0,2,1);
	    bat.setStats(1,2,3,1,1,1);
	    bat.modifyMovementDelay(-5);
	    bat.modifyAttackDelay(-5);
	    bat.addTag(Tag.ERRATIC);
	    bat.addTag(Tag.FLYING);
	    bat.addTag(Tag.NODOOR);
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
	    rat.addTag(Tag.NODOOR);
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
	    	goblin.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(z));
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
	    		lizardGrunt.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(z));
	    return lizardGrunt;
	}
	public Creature newSoldierAnt(int z) {
		Creature soldierAnt = new Creature(world, "Soldier Ant",2,70, 10, 8, 2, 1, 0, 2, soldierAntIcon);
		soldierAnt.setAttributes(2, 1, 0);
		soldierAnt.setStats(3,2,2,2,0,0);
		soldierAnt.addEffectOnHit(Effects.poisoned(4, 1), 50);
		soldierAnt.addTag(Tag.VENOMOUS);
		soldierAnt.addTag(Tag.NODOOR);
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
	    lizard.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(z));
	    return lizard;
	}
	public Creature newLizardHunter(int z) {
		Creature lizard = new Creature(world, "Lizard Hunter",3,100, 19,10,0,3,2,4, lizardHunterIcon);
		new BasicAI(lizard, player);
		lizard.setAttributes(1,2,1);
	    lizard.setStats(2,2,3,3,2,2);
	    lizard.setDescription("A lizardfolk armed and trained with a bow. Quite deadly at a range, however noticably weaker when they are forced to fight hand to hand.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newShortbow());
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
	    	lizard.addItemToInventory(itemFactory.book().newBookOfMaledictions());
	    return lizard;
	}
	public Creature newLizardShadowblade(int z) {
		Creature lizard = new Creature(world, "Lizard Shadowblade",4,140, 21,10,2,4,1,3, lizardShadowbladeIcon);
		new SpellcastingAI(lizard, player, 40);
		lizard.setAttributes(1,2,2);
	    lizard.setStats(1,2,3,3,2,3);
	    lizard.modifyAttackDelay(-3);
	    lizard.modifyMovementDelay(-1);
	    lizard.setDescription("Trained as an elite lizardfolk, the shadowblades are an order of lizard assassins armed with deadly daggers and hexing spells.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newDagger());
	    lizard.addEquipment(itemFactory.armor().newLeatherArmor());
	    lizard.addTag(Tag.SPELLCASTER);
	    lizard.setMana(12, 12);
	    lizard.addSpell(Spells.slow(3, 5, 5));
	    lizard.addSpell(Spells.vulnerability());
	    return lizard;
	}
	public Creature newLizardGuardian(int z) {
		Creature lizard = new Creature(world, "Lizard Guardian",5,185, 30,6,2,3,1,3, lizardGuardianIcon);
		new BasicAI(lizard, player);
		lizard.setAttributes(3,1,1);
	    lizard.setStats(3,3,1,2,3,2);
	    lizard.setDescription("A powerful and high ranking lizardfolk trained in the use of an impressive arsenal of arms and armour, they serve to guard the most important of their people. Why are they in the dungeon?");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(z));
	    lizard.addEquipment(itemFactory.armor().newStuddedLeatherArmor());
	    return lizard;
	}
	public Creature newLizardPriest(int z) {
		Creature lizard = new Creature(world, "Lizard Priest",5,175, 30,8,2,5,5,8, lizardPriestIcon);
		new SpellcastingAI(lizard, player, 70);
		lizard.setAttributes(1,3,3);
	    lizard.setStats(2,1,4,2,5,5);
	    lizard.setDescription("A lizard priest, devoted to bringing the return of the Forgotten King through dark prayer.");
	    world.addAtEmptyLocation(lizard, z);
	    lizard.addTag(Tag.SPELLCASTER);
	    lizard.setMana(12, 12);
	    lizard.addSpell(Spells.vulnerability());
	    lizard.addSpell(Spells.darksmite());
	    lizard.addSpell(Spells.minorStun());
	    if (Math.random()*100 < 25)
	    	lizard.addItemToInventory(itemFactory.book().newBookOfLizardRituals());
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
	    world.addAtEmptyLocation(grisstok, z);
	    return grisstok;
	}
	
	
	
	public Creature newOrcThug(int z) {
		Creature orc = new Creature(world, "Orc Thug",6,215, 30,8,2,4,2,4, orcThugIcon);
		new BasicAI(orc, player);
		orc.setAttributes(4,2,2);
	    orc.setStats(2,3,2,1,2,3);
	    orc.setDescription("What amounts to hired help on the high seas, they are more than capable of killing unfortunate stragglers.");
	    world.addAtEmptyLocation(orc, z);
	    orc.addEquipment(itemFactory.weapon().newRandomMeleeWeapon(z));
	    return orc;
	}
	public Creature newDeepAnt(int z) {
		Creature ant = new Creature(world, "Deep Ant",6, 220, 26, 7, 4, 4, 5, 8, deepAntIcon);
		ant.setAttributes(3, 2, 1);
		ant.setStats(4,2,2,2,1,2);
		ant.addEffectOnHit(Effects.poisoned(4, 3), 50);
		ant.addTag(Tag.VENOMOUS);
		ant.addTag(Tag.NODOOR);
		ant.setDescription("An massive, jet black ant grown in the depths of the dungeon. Thick poison drips from its mandibles.");
		world.addAtEmptyLocation(ant, z);
		new BasicAI(ant, player);
		return ant;
	}
	public Creature newParrot(int z) {
		Creature parrot = new Creature(world, "Parrot",6,200, 28,13,3,5,5,8, parrotIcon);
		new BasicAI(parrot, player);
		parrot.setAttributes(2,3,3);
	    parrot.setStats(2,2,4,4,4,3);
	    parrot.setDescription("A brightly coloured bird, it moves quickly and can screech for help. It has also learned a wide variety of cuss words.");
	    parrot.addTag(Tag.FLYING);
	    parrot.modifyMovementDelay(-3);
	    parrot.addAbility(Abilities.warningScreech());
	    world.addAtEmptyLocation(parrot, z);
	    return parrot;
	}
	public Creature newSnappingTurtle(int z) {
		Creature turtle = new Creature(world, "Snapping Turtle",7,260, 28,6,6,4,6,10, snappingTurtleIcon);
		new BasicAI(turtle, player);
		turtle.setAttributes(3,2,1);
	    turtle.setStats(4,3,2,3,1,1);
	    turtle.modifyResistance(Type.FIRE, 1);
	    turtle.modifyResistance(Type.COLD, 1);
	    turtle.addTag(Tag.FASTSWIMMER);
	    turtle.modifyMovementDelay(1);
	    turtle.setDescription("A belligerent testudine with an armoured carapace."); //*
	    world.addAtEmptyLocation(turtle, z);
	    return turtle;
	}
	public Creature newOrcBrawler(int z) {
		Creature orc = new Creature(world, "Orc Brawler",7,265,30,9,3,5,5,8, orcBrawlerIcon);
		new BasicAI(orc, player);
		orc.setAttributes(3,3,2);
	    orc.setStats(3,4,3,2,2,2);
	    orc.setDescription("An experienced fighter, orc brawlers are prone to fits of berserk rage.");
	    orc.addAbility(Abilities.rage());
	    orc.addTag(Tag.UNARMED_TRAINING);
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	public Creature newHarpy(int z) {
		Creature harpy = new Creature(world, "Harpy",7,260,30,12,3,5,4,7,harpyIcon);
		new BasicAI(harpy, player);
		harpy.setAttributes(2,4,2);
	    harpy.setStats(3,3,4,4,3,2);
	    harpy.setDescription("Aggressive winged creatures, their sharp claws and harrying strikes have torn many luckless travelers to shreds.");
	    harpy.addTag(Tag.FLYING);
	    harpy.modifyMovementDelay(-3);
	    harpy.modifyAttackDelay(-3);
	    world.addAtEmptyLocation(harpy, z);
	    return harpy;
	}
	public Creature newOrcPrivateer(int z) {
		Creature orc = new Creature(world, "Orc Privateer",8,305,34,10,3,4,5,7, orcPrivateerIcon);
		new BasicAI(orc, player);
		orc.setAttributes(2,4,3);
	    orc.setStats(3,3,4,4,3,1);
	    orc.setDescription("Skilled with blades, privateers are adept at taking out important targets, both on land and at sea.");
	    orc.addEquipment(itemFactory.weapon().newOrcishDagger());
	    orc.addAbility(Abilities.teleportNextTo());
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	public Creature newAlligator(int z) {
		Creature gator = new Creature(world, "Alligator",8,310,30,10,3,5,7,10, alligatorIcon);
		new BasicAI(gator, player);
		gator.setAttributes(3,3,2);
	    gator.setStats(3,3,3,3,3,2);
	    gator.modifyMovementDelay(-2);
	    gator.setDescription("An aggressive crocodilian with great crunching jaws full of sharp teeth.");//*
	    gator.addTag(Tag.FASTSWIMMER);
	    world.addAtEmptyLocation(gator, z);
	    return gator;
	}
	public Creature newOrcSwashbuckler(int z) {
		Creature orc = new Creature(world, "Orc Swashbuckler",9,350,30,10,4,4,3,6, orcSwashbucklerIcon);
		new BasicAI(orc, player);
		orc.setAttributes(3,4,3);
	    orc.setStats(3,3,4,4,3,3);
	    orc.setDescription("An experienced seafarer, able to man (or rather, orc) all positions on deck.");
	    if (Math.random() < 0.5)
	    	orc.addEquipment(itemFactory.newRandomWeapon(z));
	    else {
	    	orc.addEquipment(itemFactory.weapon().newFlintlock());
	    	for (int i = 0; i < 6; i++)
	    		orc.addItemToInventory(itemFactory.ammo().newShot(-1));
	    }
	    //Swaps between a blade and a gun
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	public Creature newOrcGunner(int z) {
		Creature orc = new Creature(world, "Orc Gunner",9,360,29,10,4,4,3,6, orcGunnerIcon);
		new BasicAI(orc, player);
		orc.setAttributes(3,4,3);
	    orc.setStats(3,2,4,4,4,3);
	    orc.setDescription("It takes a skilled marksman to hit moving targets on opposing vessels.");
	    orc.addEquipment(itemFactory.weapon().newFlintlock());
	    for (int i = 0; i < 5; i++)
	    	orc.addItemToInventory(itemFactory.ammo().newShot(-1));
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	public Creature newOrcCaptain(int z) {
		Creature orc = new Creature(world, "Orc Captain",10,405,25,9,5,5,4,7, orcCaptainIcon);
		new BasicAI(orc, player);
		orc.setAttributes(5,3,3);
	    orc.setStats(3,4,3,3,4,3);
	    orc.setDescription("A captain of an orcish pirate ship, leading their allies to blood and riches.");
	    //Can pump up allies in range, spawns with melee weapon
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	public Creature newOrcWarcaster(int z) {
		Creature orc = new Creature(world, "Orc Warcaster",10,410,34,10,4,6,10,13, orcWarcasterIcon);
		new BasicAI(orc, player);
		orc.setAttributes(3,4,5);
	    orc.setStats(2,3,4,3,5,5);
	    orc.setDescription("Usually accompanying high ranking orc pirates, warcaster's are capable of casting all sorts of cold and dark spells.\nCurrently Unfinished");
	    //Cold/Dark caster, can make allies go berserk
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	public Creature newUshnag(int z) {
		Creature orc = new Creature(world, "Ushnag",11, 900, 29,10,6,6,10,13, orcBossIcon);
		new SpellcastingAI(orc, player, 75);
		orc.setAttributes(5,3,5);
	    orc.setStats(4,5,2,3,5,5);
	    orc.setDescription("An established pirate king, corrupted by his proximity to the Forgotten King. Ushnag the Volatile has a single large bloodred eye, set in the center of his head that grant him massive amounts of magical power.");
	    orc.addTag(Tag.LEGENDARY);
	    orc.addTag(Tag.SPELLCASTER);
	    orc.setResistance(Type.FIRE, 1);
	    orc.addSpell(Spells.fireball());
	    orc.addSpell(Spells.summonImp(this));
	    world.addAtEmptyLocation(orc, z);
	    return orc;
	}
	
	/**
	 * SUMMONS
	 */
	public Creature newFriendlySimulacrum(int z, int casterAbility) {
		Ally simulacrum = new Ally(world, "Simulacrum",1, 0, 9+casterAbility, 9, 0, 2, 0, 2, simulacrumIcon);
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
		simulacrum.addEffect(Effects.temporarySummon(30));
		world.addAtEmptyLocation(simulacrum, z);
		return simulacrum;
	}
	public Creature newFriendlyImp(int z, int casterAbility) {
		Ally imp = new Ally(world, "imp",1, 0, 7+casterAbility, 10, 0, 2, 1, 3, impIcon);
		new AllyAI(imp, player, 80);
		imp.setAttributes(1+casterAbility, 1+casterAbility, 1+casterAbility);
		imp.setStats(2, 2, 2, 2, 2, 2);
		imp.setDescription("A small demon, able to cast embers and fight alongside you.");
		imp.addTag(Tag.ALLY);
		imp.addTag(Tag.SPELLCASTER);
		imp.addSpell(Spells.embers());
		imp.setMana(10, 10);
		imp.setTemporary(30);
		imp.setPlayer(player);
		world.addAtEmptyLocation(imp, z);
		return imp;
	}
	public Creature newImp(int z) {
		Creature imp = new Ally(world, "imp",1, 0, 10, 10, 0, 2, 1, 3, impIcon);
		new SpellcastingAI(imp, player, 80);
		imp.setAttributes(1, 1, 2);
		imp.setStats(2, 2, 2, 2, 2, 2);
		imp.setDescription("A small demon, able to cast low level fire spells.");
		imp.addTag(Tag.ALLY);
		imp.addTag(Tag.SPELLCASTER);
		imp.addSpell(Spells.embers());
		imp.setMana(10, 10);
		imp.addEffect(Effects.temporarySummon(30));
		world.addAtEmptyLocation(imp, z);
		return imp;
	}
	
	/**
	 * Bloodstone Enemies
	 * Spawned with z = player.z + 1, so world needs to create them at z-1
	 */
	public Creature newBloodApparition(int z) {
		Creature c = new Creature(world, "Blood Apparition", z, 0, 10*z, 11+z/2,2+z/3,3+z,4+z,6+z, bloodApparitionIcon);
		new BasicAI(c, player);
		c.setAttributes(1+z/3, 1+z/3, 1+z/3);
		c.setStats(2+z/5, 2+z/4, 2+z/5, 2+z/4, 2+z/3, 2+z/4);
		c.setDescription("An apparition drawn forth from a ripple in power due to the bloodstone.");
		c.fillHP();
		world.addAtEmptyLocation(c, z-1);
		return c;
	}
}
