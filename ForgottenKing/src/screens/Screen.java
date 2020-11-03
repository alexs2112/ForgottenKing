package screens;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;

public abstract class Screen {
	public Scene scene;
	protected Group root;
	public Group root() { return root; }
	
	public boolean repeatKeyPress = false;

	public void displayOutput(Stage stage) {
		stage.setScene(scene);
		stage.show();
	}

    public Screen respondToUserInput(KeyEvent key) {
    	return this;
    }
    
    public static void draw(Group root, Image i, int x, int y) {
    	draw(root, i, x, y, 0);
    }
    
    public static void draw(Group root, Image i, int x, int y, double adjust) {
    	ImageView j = new ImageView(i);
    	j.setX(x);
    	j.setY(y);
    	j.setPreserveRatio(true);
    	ColorAdjust value = new ColorAdjust();
    	value.setBrightness(adjust);
    	j.setEffect(value);
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
    
    public static void writeWrapped(Group root, String s, int x, int y, int width, Font font, Color colour) {
    	Text text = new Text();
    	text.setText(s);
    	text.setX(x);
    	text.setY(y);
    	text.setFont(font);
    	text.setFill(colour);
    	if (width != 0)
    		text.setWrappingWidth(width);
    	root.getChildren().add(text);
    }
    public static void writeCentered(Group root, String s, int x, int y, Font font, Color colour) {
    	int startX = x - (s.length() * ((int)font.getSize()+1))/2;
    	write(root, s, startX, y, font, colour);
    }
    
}
