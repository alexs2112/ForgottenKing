package screens;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import creatures.ClassSelection;
import creatures.Tag;

public class CharacterSelectionScreen extends Screen {
	private int selection;
	private Font font;
	private Font fontL;
	private Font fontS;
	private List<ClassSelection> classes;
	private Image adventurerIconLarge = new Image(assembly.CreatureFactory.class.getResourceAsStream("resources/characters/adventurer-96.gif"));
	private Image fighterIconLarge = new Image(assembly.CreatureFactory.class.getResourceAsStream("resources/characters/fighter-96.gif"));
	private Image rangerIconLarge = new Image(assembly.CreatureFactory.class.getResourceAsStream("resources/characters/ranger-96.gif"));
	private Image adventurerIcon = new Image(assembly.CreatureFactory.class.getResourceAsStream("resources/characters/adventurer.gif"));
	private Image fighterIcon = new Image(assembly.CreatureFactory.class.getResourceAsStream("resources/characters/fighter.gif"));
	private Image rangerIcon = new Image(assembly.CreatureFactory.class.getResourceAsStream("resources/characters/ranger.gif"));
	
	public CharacterSelectionScreen() {
		font = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 22);
		fontL = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 36);
		fontS = Font.loadFont(this.getClass().getResourceAsStream("resources/SDS_8x8.ttf"), 18);
		initializeClasses();
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		scene = new Scene(root, 1280, 800, Color.BLACK);
		draw(root, Loader.characterSelectionBackground, 0, 0);
		int x = 136;
		int y = 356;
		for (int i = -1; i < 2; i++) {
			int n = i+selection;
			if (n >= 0 && n < classes.size()) {
				double darken = 0;
				if (i != 0)
					darken = -0.7;
				draw(root, Loader.characterSelectionBox, x, y + i*124);
				draw(root, classes.get(i+selection).largeIcon, x+4, y + i*124, darken);
			}
		}
		if (selection-2 >= 0)
			draw(root, Loader.characterSelectionUpArrow, x, y - 196);
		if (selection+2 < classes.size())
			draw(root, Loader.characterSelectionDownArrow, x, y + 248);
		
		ClassSelection c = classes.get(selection);
		
		write(root, c.title.name(), 518, 118, fontL);
		
		//Attributes in table
		x = 1032;
		write(root, ""+c.strength, x, 134, font);
		write(root, ""+c.dexterity, x, 220, font);
		write(root, ""+c.intelligence, x, 306, font);
		//Stats in table
		x = 1114;
		write(root, ""+c.toughness, x, 114, font, Color.ANTIQUEWHITE);
		write(root, ""+c.brawn, x, 156, font, Color.ANTIQUEWHITE);
		write(root, ""+c.agility, x, 200, font, Color.ANTIQUEWHITE);
		write(root, ""+c.accuracy, x, 242, font, Color.ANTIQUEWHITE);
		write(root, ""+c.will, x, 285, font, Color.ANTIQUEWHITE);
		write(root, ""+c.spellcasting, x, 327, font, Color.ANTIQUEWHITE);
		x = 1172;
		write(root, ""+(c.toughness+c.strength), x, 114, font);
		write(root, ""+(c.brawn+c.strength), x, 156, font);
		write(root, ""+(c.agility+c.dexterity), x, 200, font);
		write(root, ""+(c.accuracy+c.dexterity), x, 242, font);
		write(root, ""+(c.will+c.intelligence), x, 285, font);
		write(root, ""+(c.spellcasting+c.intelligence), x, 327, font);
		
		writeWrapped(root, c.descriptionText, 426, 160, 520, font, Color.ANTIQUEWHITE);
		
		for (int i = 0; i < c.tags().size(); i++) {
			Tag t = c.tags().get(i);
			draw(root, Loader.perkBoxSmall, 440, 374 + 122*i);
			if (t.icon() != null)
				draw(root, t.icon(), 460, 394+122*i);
			writeWrapped(root, t.text() + ": " + t.description(), 512, 416 + 122*i, 432, fontS, Color.ANTIQUEWHITE);
		}
		
		stage.setScene(scene);
		stage.show();
	}
	
	public Screen respondToUserInput(KeyEvent key) {
    	if (key.getCode().equals(KeyCode.DOWN))
    		selection = Math.min(2, selection+1);
    	if (key.getCode().equals(KeyCode.UP))
    		selection = Math.max(0, selection-1);
    	if (key.getCode().equals(KeyCode.ENTER))
			return new PlayScreen(classes.get(selection));
		return this;
	}
	
	private void initializeClasses() {
		classes = new ArrayList<ClassSelection>();
		classes.add(adventurer());
		classes.add(fighter());
		classes.add(ranger());
	}
	private ClassSelection adventurer() {
		ClassSelection c = new ClassSelection(Tag.ADVENTURER, adventurerIconLarge, adventurerIcon, 22, 10, 0, 0, 1, 2);
		c.setAttributes(1, 1, 1);
		c.setStats(2, 2, 2, 2, 2, 2);
		c.descriptionText = "A brave adventurer. Well rounded for the unexpected perils and treasures of the dungeon. Equipped with a dagger and a leather armor with the most balanced stats, this character is prepared for anything.";
		c.addTag(Tag.QUICK_LEARNER);
		return c;
	}
	private ClassSelection fighter() {
		ClassSelection c = new ClassSelection(Tag.FIGHTER, fighterIconLarge, fighterIcon, 24, 10, 0, 0, 1, 2);
		c.setAttributes(2, 1, 0);
		c.setStats(2, 3, 1, 2, 2, 1);
		c.descriptionText = "An experienced fighter. Entering the dungeon with a hand axe and leather armor, this character is ready to do one thing and one thing only. Cleave enemies to pieces.";
		c.addTag(Tag.FASTENED_ARMOR);
		return c;
	}
	private ClassSelection ranger() {
		ClassSelection c = new ClassSelection(Tag.RANGER, rangerIconLarge, rangerIcon, 18, 10, 0, 0, 1, 2);
		c.setAttributes(0, 2, 1);
		c.setStats(1, 1, 2, 3, 2, 2);
		c.descriptionText = "An accomplished ranger. Equipped with a dagger and shortbow, along with a small quiver of arrows, this character is quick on their toes with a marksmans eye.";
		c.addTag(Tag.IMPROVED_CRITICAL);
		return c;
	}
	

}
