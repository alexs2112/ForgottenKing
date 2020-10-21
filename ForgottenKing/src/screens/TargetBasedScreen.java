package screens;

import tools.Line;
import tools.Point;

import java.util.ArrayList;
import java.util.List;

import creatures.Creature;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import spells.TargetType;

public class TargetBasedScreen extends Screen {
	protected Creature player;
	protected String caption;
	private int sx;
	private int sy;
	private int x;
	private int y;
	private Group baseRoot;
	protected List<Point> targets;
	protected List<Creature> creatures;
	protected TargetType targetType;
	protected int spellRadius;

	public TargetBasedScreen(Group root, Creature player, String caption, int sx, int sy){
        this.player = player;
        this.caption = caption;
        this.sx = sx;
        this.sy = sy;
        this.baseRoot = new Group(root);
	}
	
	public void displayOutput(Stage stage) {
		root = new Group(baseRoot);
		if (targetType == TargetType.PROJECTILE)
			handleProjectile();
		else if (targetType == TargetType.BEAM)
			handleBeam();
		else
			handleTarget();
	    displayTargets();
	    write(root, caption, 64, 64, 20, Color.WHITE);
	}
	
	private void handleTarget() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		addTarget(new Point(player.x+x, player.y+y, player.z));
	}
	private void handleProjectile() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		for (Point p : new Line(player.x, player.y, player.x + x, player.y + y)) {
			if (!player.realTile(p.x, p.y, player.z).isGround() 
				|| (player.world().feature(p.x, p.y, player.z)!= null && player.world().feature(p.x, p.y, player.z).blockMovement())
				|| !(player.canSee(p.x, p.y, player.z)))
	        	break;
			targets.add(p);
			Creature c = player.creature(p.x, p.y, player.z);
			if (c != null && c != player) {
				creatures.add(c);
				break;
			}
		}
	}
	private void handleBeam() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		for (Point p : new Line(player.x, player.y, player.x + x, player.y + y)) {
			if (!player.realTile(p.x, p.y, player.z).isGround() 
				|| (player.world().feature(p.x, p.y, player.z)!= null && player.world().feature(p.x, p.y, player.z).blockMovement())
				|| !(player.canSee(p.x, p.y, player.z)))
	        	break;
			addTarget(p);
		}
		creatures.remove(player);
	}
	private void displayTargets() {
		for (Point p : targets) {
			draw(root, Loader.targetBox, (p.x - sx)*32, (p.y - sy)*32);
		}
		draw(root, Loader.yellowSelection, (player.x - sx + x)*32, (player.y - sy + y) * 32);
		for (Creature c : creatures) {
			draw(root, Loader.redSelection, (c.x-sx)*32, (c.y-sy)*32);
		}
		
	}
	private void addTarget(Point p) {
		for (int wx = -spellRadius; wx <= spellRadius; wx++) {
			for (int wy = -spellRadius; wy <= spellRadius; wy++) {
				if (wx+p.x < 0 || wx+p.x >= player.world().width() || wy+p.y < 0 || wy+p.y >= player.world().height() ||
					!player.realTile(p.x+wx, p.y+wy, player.z).isGround() || 
					(player.world().feature(p.x+wx, p.y+wy, player.z)!= null && player.world().feature(p.x+wx, p.y+wy, player.z).blockMovement()) ||
					!(player.canSee(p.x+wx, p.y+wy, player.z)))
					continue;
				Point n = new Point(wx+p.x, wy+p.y, p.z);
				for (Point temp : targets) {
					if (temp.equals(n))
						targets.remove(n);
				}
				targets.add(n);
				
				Creature c = player.creature(wx+p.x, wy+p.y, p.z);
				if (c != null && c != player && !creatures.contains(c)) {
					creatures.add(c);
				}
			}
		}
	}
	
	public Screen respondToUserInput(KeyEvent key) {
        KeyCode code = key.getCode();
        char c = '-';
    	if (key.getText().length() > 0)
    		c = key.getText().charAt(0);
        if (code.equals(KeyCode.LEFT) || c == 'h' || code.equals(KeyCode.NUMPAD4)) { x--; }
    	else if (code.equals(KeyCode.RIGHT) || c == 'l' || code.equals(KeyCode.NUMPAD6)) { x++; }
    	else if (code.equals(KeyCode.UP) || c == 'k' || code.equals(KeyCode.NUMPAD8)) { y--; }
    	else if (code.equals(KeyCode.DOWN) || c == 'j' || code.equals(KeyCode.NUMPAD2)) { y++; }
    	else if (c == 'y' || code.equals(KeyCode.NUMPAD7)) { x--; y--; }
    	else if (c == 'u' || code.equals(KeyCode.NUMPAD9)) { x++; y--; }
    	else if (c == 'b' || code.equals(KeyCode.NUMPAD1)) { x--; y++; }
    	else if (c == 'n' || code.equals(KeyCode.NUMPAD3)) { x++; y++; }
    	else if (code.equals(KeyCode.ENTER)) { 
    		selectWorldCoordinate(); 
    		return null; 
    	}
    	else if (code.equals(KeyCode.ESCAPE)) { return null; } 
        enterWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y);
        return this;
    }
	
	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
    }
	
	public void selectWorldCoordinate(){
    }
	
	


}
