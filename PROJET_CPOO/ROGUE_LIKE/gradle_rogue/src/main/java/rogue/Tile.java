package rogue;
import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR('.', AsciiPanel.white),
    WALL('@', new Color(255,255,255)),
    BOUNDS('x', AsciiPanel.brightBlack), 
    UNKNOWN(' ', AsciiPanel.brightBlack);

    private char glyph;
    public char glyph() { return glyph; }

    private Color color;
    public Color color() { return color; }
    Tile(char glyph, Color color){
        this.glyph = glyph;
        this.color = color;
    }

	public boolean isGround() {
		return this == Tile.FLOOR;
	}
}