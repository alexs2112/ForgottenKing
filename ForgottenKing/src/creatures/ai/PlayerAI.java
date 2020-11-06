package creatures.ai;

import java.util.HashMap;

import creatures.Creature;
import creatures.Tag;
import features.Feature;
import javafx.scene.paint.Color;
import tools.FieldOfView;
import world.Tile;

public class PlayerAI extends CreatureAI {
	private HashMap<String, Color> messages;
	private FieldOfView fov;

    public PlayerAI(Creature creature, HashMap<String, Color> messages, FieldOfView fov) {
    	super(creature);
    	this.messages = messages;
    	this.fov = fov;
    }
    
    public void onEnter(int x, int y, int z, Tile tile){
    	Feature feat = creature.world().feature(x, y, z);
		if (feat != null) {
			if (feat.type().equals("Bump")) {
				feat.interact(creature, creature.world(), x, y, z);
				return;
			}
			if (feat.blockMovement())
				return;
		}
        if (tile.isGround() || ((tile.isPit() && creature.is(Tag.FLYING)))){
            creature.x = x;
            creature.y = y;
            creature.z = z;
        } else if (tile == Tile.CAVE_WALL) {
            creature.dig(x, y, z);
        }
    }
    
    public void onNotify(String message, Color c){
        messages.put(message, c);
        System.out.println(message);
    }
    
    public boolean canSee(int wx, int wy, int wz) {
        return fov.isVisible(wx, wy, wz);
    }
    public Tile rememberedTile(int wx, int wy, int wz) {
    	return fov.tile(wx, wy, wz);
    }
    
}