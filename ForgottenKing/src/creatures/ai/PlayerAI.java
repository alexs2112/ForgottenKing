package creatures.ai;

import java.util.List;

import creatures.Creature;
import creatures.Tag;
import features.Feature;
import javafx.scene.paint.Color;
import tools.FieldOfView;
import tools.Message;
import world.Tile;

public class PlayerAI extends CreatureAI {
	private static final long serialVersionUID = 7769423305067121315L;
	private List<Message> messages;
	private FieldOfView fov;

    public PlayerAI(Creature creature, List<Message> messages, FieldOfView fov) {
    	super(creature);
    	this.messages = messages;
    	this.fov = fov;
    }
    
    public void onEnter(int x, int y, int z, Tile tile){
    	Feature feat = creature.world().feature(x, y, z);
		if (feat != null) {
			if (feat.type() == Feature.Type.BUMP) {
				feat.interact(creature, creature.world(), x, y, z);
				return;
			}
			if (feat.blockMovement())
				return;
		}
        if (tile.canMoveOn(creature)){
            creature.x = x;
            creature.y = y;
            creature.z = z;
        }
    }
    
    public void onNotify(String message, Color c){
        messages.add(new Message(message, c));
        System.out.println(message);
    }
    
    public boolean canSee(int wx, int wy, int wz) {
        return fov.isVisible(wx, wy, wz);
    }
    public Tile rememberedTile(int wx, int wy, int wz) {
    	return fov.tile(wx, wy, wz);
    }
    
}