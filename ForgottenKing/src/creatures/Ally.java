package creatures;

import assembly.Effects;
import javafx.scene.image.Image;
import world.World;

public class Ally extends Creature {
	private static final long serialVersionUID = 7769423305067121315L;
	private Player player;
	public void setPlayer(Player player) { this.player = player; }
	
	public void modifyXP(int x) {
		if (player != null)
			player.modifyXP(x * 3 / 4); 
	}	//Things that your summon kills grants you 3/4 of the XP
	
	public Ally(World world, String name, int level, int xp, int hp, int evasion, int armorValue, int baseAttackValue, int baseDamageMin, int baseDamageMax, Image image) {
    	super(world,name,level,xp,hp,evasion,armorValue,baseAttackValue,baseDamageMin,baseDamageMax,image);
    }
	
	public void setTemporary(int duration) {
		this.addEffect(Effects.temporarySummon(duration));
	}
	
	public Creature getNearestEnemy() {
		Creature nearest = null;
		int distance = 100;
		for (int x = this.x - visionRadius(); x < this.x + visionRadius(); x++) {
			for (int y = this.y - visionRadius(); y < this.y + visionRadius(); y++) {
				if (x < 0 || x >= world().width() || y < 0 || y >= world().height())
					continue;
				Creature c = creature(x,y,z);
				if (c != null && c != this && !c.is(Tag.PLAYER) && !c.is(Tag.ALLY)) {
					if (nearest == null) {
						nearest = c;
						distance = Math.max(Math.abs(this.x - c.x), Math.abs(this.y - c.y));
						continue;
					}
					if (Math.max(Math.abs(this.x - c.x), Math.abs(this.y - c.y)) < distance) {
						nearest = c;
						distance = Math.max(Math.abs(this.x - c.x), Math.abs(this.y - c.y));
					}
				}
			}
		}
		return nearest;
	}
}
