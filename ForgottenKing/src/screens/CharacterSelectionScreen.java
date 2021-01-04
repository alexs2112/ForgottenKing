package screens;

import java.util.ArrayList;
import java.util.List;

import assembly.Abilities;
import audio.Audio;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import creatures.Ability;
import creatures.ClassSelection;
import creatures.Tag;
import tools.Icon;

public class CharacterSelectionScreen extends Screen {
	private static final long serialVersionUID = 7769423305067121315L;
	public Audio audio() { return Audio.INTRO; }
	private int selection;
	private List<ClassSelection> classes;
	private Icon adventurerIcon = new Icon("characters/adventurer.gif");
	private Icon fighterIcon = new Icon("characters/fighter.gif");
	private Icon rangerIcon = new Icon("characters/ranger.gif");
	private Icon berserkerIcon = new Icon("characters/berserker.gif");
	private Icon elementalistIcon = new Icon("characters/elementalist.gif");
	
	private Icon adventurerIconLarge = new Icon("characters/adventurer.gif").resize(96);
	private Icon fighterIconLarge = new Icon("characters/fighter.gif").resize(96);
	private Icon rangerIconLarge = new Icon("characters/ranger.gif").resize(96);
	private Icon berserkerIconLarge = new Icon("characters/berserker.gif").resize(96);
	private Icon elementalistIconLarge = new Icon("characters/elementalist.gif").resize(96);
	
	public CharacterSelectionScreen() {
		initializeClasses();
	}
	
	public void displayOutput(Stage stage) {
		root = new Group();
		scene = new Scene(root, 1280, 800, Color.BLACK);
		draw(root, Loader.characterSelectionBackground.image(), 0, 0);
		int x = 136;
		int y = 356;
		for (int i = -1; i < 2; i++) {
			int n = i+selection;
			if (n >= 0 && n < classes.size()) {
				double darken = 0;
				if (i != 0)
					darken = -0.7;
				
				EventHandler<MouseEvent> onClick = handleClick(i);
				draw(root, Loader.characterSelectionBox.image(), x, y + i*124, onClick);
				draw(root, classes.get(i+selection).largeIcon.image(), x+4, y + i*124, darken, onClick);
			}
		}
		handleButtons();
		
		ClassSelection c = classes.get(selection);
		
		write(root, c.title.name(), 518, 118, font36);
		
		//Attributes in table
		x = 1032;
		write(root, ""+c.strength, x, 134, font22);
		write(root, ""+c.dexterity, x, 220, font22);
		write(root, ""+c.intelligence, x, 306, font22);
		//Stats in table
		x = 1114;
		write(root, ""+c.toughness, x, 114, font22, Color.ANTIQUEWHITE);
		write(root, ""+c.brawn, x, 156, font22, Color.ANTIQUEWHITE);
		write(root, ""+c.agility, x, 200, font22, Color.ANTIQUEWHITE);
		write(root, ""+c.accuracy, x, 242, font22, Color.ANTIQUEWHITE);
		write(root, ""+c.will, x, 285, font22, Color.ANTIQUEWHITE);
		write(root, ""+c.spellcasting, x, 327, font22, Color.ANTIQUEWHITE);
		x = 1172;
		write(root, ""+(c.toughness+c.strength), x, 114, font22);
		write(root, ""+(c.brawn+c.strength), x, 156, font22);
		write(root, ""+(c.agility+c.dexterity), x, 200, font22);
		write(root, ""+(c.accuracy+c.dexterity), x, 242, font22);
		write(root, ""+(c.will+c.intelligence), x, 285, font22);
		write(root, ""+(c.spellcasting+c.intelligence), x, 327, font22);
		
		writeWrapped(root, c.descriptionText, 426, 160, 520, font22, Color.ANTIQUEWHITE);
		
		if (c.tags() != null)
			for (int i = 0; i < c.tags().size(); i++) {
				Tag t = c.tags().get(i);
				draw(root, Loader.perkBoxSmall.image(), 440, 374 + 122*i);
				if (t.image() != null)
					draw(root, t.image(), 460, 394+122*i);
				writeWrapped(root, t.text() + ": " + t.description(), 512, 416 + 122*i, 432, font18, Color.ANTIQUEWHITE);
			}
		if (c.abilities() != null)
			for (int i = 0; i < c.abilities().size(); i++) {
				int m = (i+c.tags().size());
				Ability a = c.abilities().get(i);
				draw(root, Loader.perkBoxSmall.image(), 440, 374 + 122*m);
				if (a.icon() != null)
					draw(root, a.icon(), 460, 394+122*m);
				writeWrapped(root, a.name() + ": " + a.description(), 512, 416 + 122*m, 432, font18, Color.ANTIQUEWHITE);
			}
		
		stage.setScene(scene);
		stage.show();
	}
	
	@Override
	public Screen respondToUserInput(KeyCode code, char c, boolean shift) {
    	if (code.equals(KeyCode.DOWN))
    		selection = Math.min(classes.size()-1, selection+1);
    	if (code.equals(KeyCode.UP))
    		selection = Math.max(0, selection-1);
    	if (code.equals(KeyCode.ENTER))
			return new PlayScreen(classes.get(selection));
		return this;
	}
	
	private void initializeClasses() {
		classes = new ArrayList<ClassSelection>();
		classes.add(adventurer());
		classes.add(fighter());
		classes.add(ranger());
		classes.add(berserker());
		classes.add(elementalist());
	}
	private ClassSelection adventurer() {
		ClassSelection c = new ClassSelection(Tag.ADVENTURER, adventurerIconLarge, adventurerIcon, 22, 10, 0, 0, 1, 2);
		c.setAttributes(1, 1, 1);
		c.setStats(2, 2, 2, 2, 2, 2);
		c.descriptionText = "A brave adventurer. Well rounded for the unexpected perils and treasures of the dungeon. Equipped with a dagger, leather armor and a handful of darts, this character is prepared for anything.";
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
	private ClassSelection berserker() {
		ClassSelection c = new ClassSelection(Tag.BERSERKER, berserkerIconLarge, berserkerIcon, 22, 10, 0, 0, 1, 2);
		c.setAttributes(2, 0, 1);
		c.setStats(2, 3, 2, 2, 1, 0);
		c.descriptionText = "A raging barbarian. Equipped with only a handaxe and their undying rage, there is not much that can stop this character when they are angry.";
		c.addTag(Tag.IMPROVED_CLEAVE);
		c.addAbility(Abilities.rage());
		return c;
	}
	private ClassSelection elementalist() {
		ClassSelection c = new ClassSelection(Tag.ELEMENTALIST, elementalistIconLarge, elementalistIcon, 18, 10, 0, 0, 1, 2);
		c.setAttributes(0, 1, 2);
		c.setStats(1, 1, 2, 2, 2, 3);
		c.descriptionText = "An acolyte of the elemental arts. This character begins with a Book of Kindling and an Acolyte's Robe, prepared to roast the inhabitants of the dungeon.";
		c.addTag(Tag.ACOLYTES_MANA);
		return c;
	}
	
	
	private EventHandler<MouseEvent> handleClick(int index) {
		return new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				selection = index+selection;
				refreshScreen = returnThis();
			}
		};
	}
	
	private boolean mouseOverUp;
	private boolean mouseOverDown;
	private boolean mouseOverSelect;
	private void handleButtons() {
		if (selection-1 >= 0) {
			Image up = Loader.characterSelectionUpArrow.image();
			if (mouseOverUp)
				up = Loader.characterSelectionUpArrowSelected.image();
			draw(root, up, 136, 160, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) { 
					selection--;
					refreshScreen = returnThis();
				}
			}, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) { 
					mouseOverUp = true;
					refreshScreen = returnThis();
				}
			}, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) { 
					mouseOverUp = false;
					refreshScreen = returnThis();
				}
			});
		}
		if (selection+1 < classes.size()){
			Image down = Loader.characterSelectionDownArrow.image();
			if (mouseOverDown)
				down = Loader.characterSelectionDownArrowSelected.image();
			draw(root, down, 136, 604, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) { 
					selection++;
					refreshScreen = returnThis();
				}
			}, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) { 
					mouseOverDown = true;
					refreshScreen = returnThis();
				}
			}, new EventHandler<MouseEvent>() {
				public void handle(MouseEvent me) { 
					mouseOverDown = false;
					refreshScreen = returnThis();
				}
			});
		}
		Image select = Loader.characterSelectionSelectButton.image();
		if (mouseOverSelect)
			select = Loader.characterSelectionSelectButtonSelected.image();
		draw(root, select, 38, 670, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				refreshScreen = new PlayScreen(classes.get(selection));
			}
		}, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				mouseOverSelect = true;
				refreshScreen = returnThis();
			}
		}, new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) { 
				mouseOverSelect = false;
				refreshScreen = returnThis();
			}
		});
	}
}
