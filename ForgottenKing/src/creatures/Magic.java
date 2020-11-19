package creatures;

import java.util.HashMap;

/**
 * A way to keep track of a creatures skill at magic and how their spellpoints are allocated
 * Fire, Cold, Air, Poison, Light, Dark
 */
public class Magic {
	private Creature user;
	private HashMap<Type, Integer> set;
	public HashMap<Type, Integer> set() { return set; }
	private int totalPoints;
	public int totalPoints() { return totalPoints + user.getSpellcasting()/2; }
	public int floatingPoints() { return totalPoints() - pointsInUse(); }
	
	public Magic(Creature user) {
		set = new HashMap<Type, Integer>();
		set.put(Type.FIRE, 0);
		set.put(Type.COLD, 0);
		set.put(Type.AIR, 0);
		set.put(Type.POISON, 0);
		set.put(Type.LIGHT, 0);
		set.put(Type.DARK, 0);
		this.user = user;
	}
	
	public int get(Type type) {
		return set.get(type);
	}
	public void change(Type type, int num) {
		int og = get(type);
		set.put(type, og+num);
	}
	public void modify(Type type, int num) {
		if (num > 0) {
			for (int i = 0; i < num; i++)
				change(type, 1);
		}
		if (num < 0) {
			for (int i = 0; i > num; i--)
				change(type, -1);
		}
	}
	public int pointsInUse() {
		int i = 0;
		i+=set.get(Type.FIRE);
		i+=set.get(Type.COLD);
		i+=set.get(Type.AIR);
		i+=set.get(Type.POISON);
		i+=set.get(Type.LIGHT);
		i+=set.get(Type.DARK);
		return i;
	}
}
