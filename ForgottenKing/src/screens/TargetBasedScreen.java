package screens;

import tools.Line;
import tools.Point;

import java.util.ArrayList;
import java.util.List;

import audio.Audio;
import creatures.Creature;
import creatures.Player;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import spells.TargetType;

public class TargetBasedScreen extends Screen {
	protected Player player;
	protected String caption;
	public Audio audio() { return player.songToPlayByEnemy(); }
	private int sx;
	private int sy;
	protected int x;
	protected int y;
	private Group baseRoot;
	protected List<Point> targets;
	protected List<Creature> creatures;
	protected TargetType targetType;
	protected int radius;
	protected int range;
	private String captionBase;
	Font font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);

	public TargetBasedScreen(Group root, Player player, String caption, int sx, int sy){
        this.player = player;
        this.caption = caption;
        this.captionBase = caption;
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
		else if (targetType == TargetType.SELF)
			handleSelf();
		else
			handleTarget();
	    displayTargets();
	    writeWrapped(root, caption, 32, 72, 1000, font, Color.WHITE);
	}
	
	private void handleTarget() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		addTarget(new Point(player.x+x, player.y+y, player.z));
	}
	private void handleProjectile() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		Line line = new Line(player.x, player.y, player.x + x, player.y + y);
		for (Point p : line) {
			if (player.realTile(p.x, p.y, player.z).isWall() 
				|| (player.world().feature(p.x, p.y, player.z)!= null && player.world().feature(p.x, p.y, player.z).blockMovement())
				|| !(player.canSee(p.x, p.y, player.z))
				|| !inRange(p, line))
	        	break;
			targets.add(p);
			Creature c = player.creature(p.x, p.y, player.z);
			if (c != null) {
				if (c != player && !creatures.contains(c)) {
					creatures.add(c);
					break;
				} else if (line.getPoints().size() == 1) {
					creatures.add(c);
					break;	
				}
			}
		}
	}
	private void handleBeam() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		Line line = new Line(player.x, player.y, player.x + x, player.y + y);
		for (Point p : line) {
			if (player.realTile(p.x, p.y, player.z).isWall() 
				|| (player.world().feature(p.x, p.y, player.z)!= null && player.world().feature(p.x, p.y, player.z).blockMovement())
				|| !player.canSee(p.x, p.y, player.z)
				|| !inRange(p, line))
	        	break;
			addTarget(p);
		}
		creatures.remove(player);
	}
	private void handleSelf() {
		targets = new ArrayList<Point>();
		creatures = new ArrayList<Creature>();
		addTarget(new Point(player.x, player.y, player.z));
		if (targets.size() > 1)
			creatures.remove(player);
	}
	private boolean inRange(Point p, Line line) {
		if (range == 0)
			return true;
		return line.getPoints().indexOf(p) <= range;
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
		for (int wx = -radius; wx <= radius; wx++) {
			for (int wy = -radius; wy <= radius; wy++) {
				if (wx+p.x < 0 || wx+p.x >= player.world().width() || wy+p.y < 0 || wy+p.y >= player.world().height() ||
					player.realTile(p.x+wx, p.y+wy, player.z).isWall() || 
					(player.world().feature(p.x+wx, p.y+wy, player.z)!= null && player.world().feature(p.x+wx, p.y+wy, player.z).blockMovement()) ||
					!(player.canSee(p.x+wx, p.y+wy, player.z)))
					continue;
				Point n = new Point(wx+p.x, wy+p.y, p.z);
				List<Point> temp = new ArrayList<Point>(targets);
				for (Point t : temp) {
					if (t.equals(n))
						targets.remove(n);
				}
				targets.add(n);
				
				Creature c = player.creature(wx+p.x, wy+p.y, p.z);
				if (c != null && !creatures.contains(c)) {
					creatures.add(c);
				}
			}
		}
	}
	public List<Point> getCreatureLocations() {
		List<Point> p = new ArrayList<Point>();
		for (Creature c : creatures)
			p.add(new Point(c.x,c.y,c.z));
		return p;
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
        if (code.equals(KeyCode.LEFT) || c == 'h' || code.equals(KeyCode.NUMPAD4)) { x--; }
    	else if (code.equals(KeyCode.RIGHT) || c == 'l' || code.equals(KeyCode.NUMPAD6)) { x++; }
    	else if (code.equals(KeyCode.UP) || c == 'k' || code.equals(KeyCode.NUMPAD8)) { y--; }
    	else if (code.equals(KeyCode.DOWN) || c == 'j' || code.equals(KeyCode.NUMPAD2)) { y++; }
    	else if (c == 'y' || code.equals(KeyCode.NUMPAD7)) { x--; y--; }
    	else if (c == 'u' || code.equals(KeyCode.NUMPAD9)) { x++; y--; }
    	else if (c == 'b' || code.equals(KeyCode.NUMPAD1)) { x--; y++; }
    	else if (c == 'n' || code.equals(KeyCode.NUMPAD3)) { x++; y++; }
    	else if (code.equals(KeyCode.ENTER)) { 
    		return selectWorldCoordinate(); 
    	}
    	else if (code.equals(KeyCode.ESCAPE)) { return null; } 
        enterWorldCoordinate(player.x + x, player.y + y, sx + x, sy + y);
        return this;
    }
	
	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
        Creature creature = player.creature(x, y, player.z);
        if (creature != null)
            caption = captionBase + " (" + creature.name() + creature.desc() + ")";
        else
        	caption = captionBase;
	}
	
	public Screen selectWorldCoordinate(){
		return null;
    }
	
	


}
