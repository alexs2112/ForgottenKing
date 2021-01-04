package screens;
import application.Main;
import audio.Audio;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import tools.ImageCrop;
import tools.KeyBoardCommand;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public abstract class Screen implements java.io.Serializable {
	private static final long serialVersionUID = 7769423305067121315L;
	public transient Scene scene;
	protected transient Group root;
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
    public static void drawWaterOverlay(Group root, Image i, int x, int y) {
    	ImageView j = new ImageView(ImageCrop.cropImage(i, 0, 22, 32, 10));
    	j.setX(x);
    	j.setY(y+22);
    	j.setPreserveRatio(true);
    	j.setOpacity(0.8);
    	root.getChildren().add(j);
    }
    
    public static void write(Group root, String s, int x, int y, int fontSize, Color colour) {
    	Font f = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), fontSize);
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
    	writeCentered(root, s, x, y, font, colour, null, null, null);
    }
    public static void writeCentered(Group root, String s, int x, int y, Font font, Color colour,
    	EventHandler<MouseEvent> clickEvent, EventHandler<MouseEvent> enteredEvent, EventHandler<MouseEvent> exitEvent) {
    	int startX = x - (int)(s.length() * (font.getSize()+1))/2;
    	if (font.getName().contains("DejaVu"))
    		startX = x - (int)(s.length()/2*(font.getSize()/2));
       	write(root, s, startX, y, font, colour, clickEvent, enteredEvent, exitEvent);
    }
    
    /**
     * All fonts as a static variable so they can be serialized
     */
    public static Font font12 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 12);
    public static Font font14 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 14);
    public static Font font16 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 16);
    public static Font font18 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 18);
    public static Font font20 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 20);
    public static Font font22 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 22);
    public static Font font24 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 24);
    public static Font font26 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 26);
    public static Font font36 = Font.loadFont(Main.class.getResourceAsStream("resources/fonts/SDS_8x8.ttf"), 36);
    
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
    	Image closeIcon = Loader.closeMenuIcon.image();
        if (closeMenu)
        	closeIcon = Loader.closeMenuIconSelected.image();
    	draw(root, closeIcon, 1222, 18, clickExit(), mouseOverExit(true), mouseOverExit(false));
    }
    
}
