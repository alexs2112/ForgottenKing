package screens;
import audio.Audio;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import tools.KeyBoardCommand;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public abstract class Screen {
	public Scene scene;
	protected Group root;
	public Group root() { return root; }
	public Audio audio() { return Audio.PAUSE; }
	
	protected Screen refreshScreen;
	public Screen refreshScreen() { return refreshScreen; }
	protected Screen returnThis() { return this; }
	protected boolean exitThis;
	public boolean exitThis() { return exitThis; }
	
	public KeyBoardCommand nextCommand;
	public KeyBoardCommand getNextCommand() { return nextCommand; }
	//public void addEventFilter(MouseEvent e, EventHandler<MouseEvent> m) { scene.addEventHandler(e, m); }

	public void displayOutput(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}

	public Screen respondToUserInput(KeyCode code, char c, boolean isShiftDown) {
		return this;
	}
    
    public static void draw(Group root, Image i, int x, int y) {
    	draw(root, i, x, y, 0, null);
    }
    
    public static void draw(Group root, Image i, int x, int y, double adjust) {
    	draw(root, i, x, y, adjust, null);
    }
    
    public static void draw(Group root, Image i, int x, int y, EventHandler<MouseEvent> clickEvent) {
    	draw(root, i, x, y, 0, clickEvent);
    }
    
    public static void draw(Group root, Image i, int x, int y, double adjust, EventHandler<MouseEvent> clickEvent) {
    	ImageView j = new ImageView(i);
    	j.setX(x);
    	j.setY(y);
    	j.setPreserveRatio(true);
    	ColorAdjust value = new ColorAdjust();
    	value.setBrightness(adjust);
    	j.setEffect(value);
    	if (clickEvent != null)
    		j.setOnMousePressed(clickEvent);
    	root.getChildren().add(j);
    }
    public static void draw(Group root, Image i, int x, int y, EventHandler<MouseEvent> clickEvent, EventHandler<MouseEvent> enteredEvent, EventHandler<MouseEvent> exitEvent) {
    	ImageView j = new ImageView(i);
    	j.setX(x);
    	j.setY(y);
    	j.setPreserveRatio(true);
    	j.setOnMousePressed(clickEvent);
    	j.setOnMouseEntered(enteredEvent);
    	j.setOnMouseExited(exitEvent);
    	root.getChildren().add(j);
    }
    
    public static void write(Group root, String s, int x, int y, int fontSize, Color colour) {
    	Font f = Font.loadFont(Screen.class.getResourceAsStream("resources/SDS_8x8.ttf"), fontSize);
    	write(root, s, x, y, f, colour);
    }
    public static void write(Group root, String s, int x, int y, String fontPath, int fontSize, Color colour) {
    	Font f = Font.loadFont(Screen.class.getResourceAsStream(fontPath), fontSize);
    	write(root, s, x, y, f, colour);
    }
    public static void write(Group root, String s, int x, int y, Font font, Color colour) {
    	writeWrapped(root, s, x, y, 0, font, colour);
    }
    public static void write(Group root, String s, int x, int y, Font font) {
    	write(root, s, x, y, font, Color.WHITE);
    }
    public static void write(Group root, String s, int x, int y, Font font, Color colour, EventHandler<MouseEvent> clickEvent, EventHandler<MouseEvent> enteredEvent, EventHandler<MouseEvent> exitEvent) {
    	writeWrapped(root, s, x, y, 0, font, colour, clickEvent, enteredEvent, exitEvent);
    }
    public static void writeWrapped(Group root, String s, int x, int y, int width, Font font, Color colour) {
    	writeWrapped(root, s, x, y, width, font, colour, null, null, null);
    }
    public static void writeWrapped(Group root, String s, int x, int y, int width, Font font, Color colour, EventHandler<MouseEvent> clickEvent, EventHandler<MouseEvent> enteredEvent, EventHandler<MouseEvent> exitEvent) {
    	Text text = new Text();
    	text.setText(s);
    	text.setX(x);
    	text.setY(y);
    	text.setFont(font);
    	text.setFill(colour);
    	if (width != 0)
    		text.setWrappingWidth(width);
    	text.setOnMousePressed(clickEvent);
    	text.setOnMouseEntered(enteredEvent);
    	text.setOnMouseExited(exitEvent);
    	root.getChildren().add(text);
    }
    public static void writeCentered(Group root, String s, int x, int y, Font font, Color colour) {
    	int startX = x - (int)(s.length() * (font.getSize()+1))/2;
    	if (font.getName().contains("DejaVu"))
    		startX = x - (int)(s.length()/2*(font.getSize()/2));
    	write(root, s, startX, y, font, colour);
    }
    
    
    /**
     * Close Button Stuff for Subscreens
     */
    private boolean closeMenu;
    private EventHandler<MouseEvent> clickExit() {
    	return new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent me) {
    			exitThis = true;
			}
    	};
    }
    private EventHandler<MouseEvent> mouseOverExit(boolean b) {
    	return new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent me) {
    			closeMenu = b;
    			refreshScreen = returnThis();
			}
    	};
    }
    protected void constructCloseButton() {
    	Image closeIcon = Loader.closeMenuIcon;
        if (closeMenu)
        	closeIcon = Loader.closeMenuIconSelected;
    	draw(root, closeIcon, 1222, 18, clickExit(), mouseOverExit(true), mouseOverExit(false));
    }
    
}
