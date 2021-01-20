package spells;

import assembly.Effects;
import assembly.Hazards;
import creatures.Creature;
import creatures.Type;
import javafx.scene.paint.Color;

public final class OverloadEffect {
	
	public static void overload(Spell s, Creature c) {
		if (Math.random() < 0.25) {
			c.notify("Your spell fizzles!", Color.CRIMSON);
			return;
		}
		if (s.type() == Type.FIRE)
			fire(s,c);
		else {
			c.notify("Your spell fizzles!", Color.CRIMSON);
			return;
		}
	}

	private static void fire(Spell s, Creature c) {
		switch((int)Math.random()*s.level()) {
		case 1:
			c.notify("Your body catches fire", Color.CRIMSON);
			c.addEffect(Effects.burning((int)(Math.random()*4)+c.level(), s.level()+2));
			return;
		case 2:
			c.notify("You summon an uncontrolled blaze", Color.CRIMSON);
			c.world().setHazard(Hazards.smallFire(s.level()+c.level()+2), c.x, c.y, c.z);
			return;
		default:
			c.notify("Your hands sputter sparks", Color.CRIMSON);
			c.modifyHP(c.getDamageReceived((int)(Math.random()*s.level()*c.level()) + c.level(), Type.FIRE));
			return;
		}
	}
	
}
