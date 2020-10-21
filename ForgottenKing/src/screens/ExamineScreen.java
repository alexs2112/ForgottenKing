package screens;

import creatures.Creature;
import items.Inventory;
import javafx.scene.Group;
import world.Tile;

public class ExamineScreen extends TargetBasedScreen {

    public ExamineScreen(Group root, Creature player, String caption, int sx, int sy) {
        super(root, player, caption, sx, sy);
    }
    

    public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
        Creature creature = player.creature(x, y, player.z);
        if (creature != null){
            caption = creature.name() + creature.desc();
            return;
        }
    
        Inventory items = player.items(x, y, player.z);
        if (items != null){
            caption = items.listOfItems();
            return;
        }
    
        Tile tile = player.tile(x, y, player.z);
        caption = tile.type() + " " + tile.desc();
    }
}