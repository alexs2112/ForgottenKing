package assembly;

import java.util.List;

import creatures.Ability;
import creatures.Creature;
import creatures.Tag;
import items.ItemTag;
import javafx.scene.paint.Color;
import spells.TargetType;
import tools.Point;
import world.Noise;
@SuppressWarnings("serial")
public final class Abilities {
	public static Ability rage() {
		Ability a = new Ability("Rage", Loader.rageIcon, 35) {
			public void activate(Creature owner, Creature other) {
				if (owner.hasItemTag(ItemTag.MEDIUMARMOR) || owner.hasItemTag(ItemTag.HEAVYARMOR)) {
					owner.notify("You cannot activate this ability while wearing medium or heavy armor.");
					this.setTime(0);
					return;
				}
				owner.addEffect(Effects.raging());
			}
		};
		a.setTargetType(TargetType.SELF);
		a.setDescription("You fly into a berserk rage, giving you massively increased strength for a time.");
		return a;
	}
	public static Ability reach() {
		Ability a = new Ability("Reach Attack", Loader.reachAttackIcon, 0) {
			public void activate(Creature owner, Creature other) {
				owner.attack(other);
			}
		};
		a.setTargetType(TargetType.PROJECTILE);
		a.setDescription("You can attack enemies up to two tiles away.");
		a.setRange(2);
		a.setUpgrade(improvedReach(), Tag.POLEARM_MASTER);
		return a;
	}
	public static Ability improvedReach() {
		Ability a = new Ability("Reach Attack+", Loader.reachAttackIcon, 0) {
			public void activate(Creature owner, Creature other) {
				owner.attack(other);
			}
		};
		a.setTargetType(TargetType.PROJECTILE);
		a.setDescription("You can attack enemies up to three tiles away.");
		a.setRange(3);
		return a;
	}
	public static Ability knockbackAll() {
		Ability a = new Ability("Knockback All", Loader.knockbackAllIcon, 30) {
			public void activate(Creature owner) {
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						Creature c = owner.creature(owner.x+x, owner.y+y, owner.z);
						if (c != null && c != owner) {
							c.moveBy(x, y, 0);
							c.moveBy(x, y, 0);
							c.doAction("stumble back");
						}
					}
				}
			}
		};
		a.setTargetType(TargetType.SELF);
		a.setRadius(1);
		a.setDescription("You swing out your arms, knocking back all enemies adjacent to you.");
		a.setUseText("knock back all creatures");
		a.setUpgrade(improvedKnockbackAll(), Tag.IMPROVED_KNOCKBACK_ALL);
		return a;
	}
	public static Ability improvedKnockbackAll() {
		Ability a = new Ability("Knockback All+", Loader.knockbackAllIcon, 2) {
			public void activate(Creature owner, Creature target) {
				owner.attack(target);
			}
			public void activate(Creature owner) {
				for (int x = -1; x <= 1; x++)
					for (int y = -1; y <= 1; y++) {
						Creature c = owner.creature(owner.x+x, owner.y+y, owner.z);
						if (c != null && c != owner) {
							c.doAction("stumble back");
							c.moveBy(x, y, 0);
							c.moveBy(x, y, 0);
						}
					}
			}
		};
		a.setTargetType(TargetType.SELF);
		a.setRadius(1);
		a.setDescription("You make an attack against each adjacent enemy, knocking them backwards.");
		return a;
	}
	public static Ability warningScreech() {
		Ability a = new Ability("Warning Screech", null, 5) {
			public void activate(Creature owner, Creature target) {
				owner.world().makeNoise(new Noise(owner.x,owner.y,owner.z,"screech", 12));
				owner.addStatement("Screech", Color.AQUA);
			}
		};
		a.self = true;
		a.setTargetType(TargetType.SELF);
		a.setDescription("This creature makes a warning screech, attracting allies to its position.");
		return a;
	}
	public static Ability teleportNextTo() {
		Ability a = new Ability("Teleport", null, 30) {
			public void activate(Creature owner, Creature target) {
				List<Point> points = new Point(target.x,target.y,target.z).neighbors8();
				for (Point p : points) {
					if (owner.canEnter(p.x, p.y, p.z)) {
						owner.moveTo(p.x, p.y, p.z);
						owner.doAction("teleport next to you");
						return;
					}
				}
			}
		};
		a.setRange(6);
		a.setTargetType(TargetType.TARGET);
		a.setDescription("Allows this creature to teleport next to you if you are within 6 tiles.");
		return a;
	}
	
}
