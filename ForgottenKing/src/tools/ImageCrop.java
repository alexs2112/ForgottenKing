package tools;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.shape.Rectangle;

public final class ImageCrop {

	public static Image cropImage(Image image, int startX, int startY, int width, int height) {
		PixelReader pr = image.getPixelReader();
		if (pr != null) {
			WritableImage write = new WritableImage(pr, startX, startY, width, height);
			return write;
		} else {
			ImageView view = new ImageView(image);
			view.setClip(new Rectangle(startX, startY, width, height));
			return view.getImage();
		}
	}

}
