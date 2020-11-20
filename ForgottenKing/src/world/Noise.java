package world;

public class Noise {
	public int x;
	public int y;
	public int z;
	private String text;
	public String text() { return text; }
	private int volume;
	public int volume() { return volume; }

	public Noise(int x, int y, int z, String text, int volume) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.text = text;
		this.volume = volume;
	}
}
