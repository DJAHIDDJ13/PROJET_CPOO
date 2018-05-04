package rogue;
import java.awt.Color;
import asciiPanel.AsciiPanel;

public enum Tile {
    FLOOR ('.', AsciiPanel.white,      AsciiPanel.black      ),
    WALL  ('@', new Color(255,255,255),new Color(0,255,0)    ),
    BOUNDS('x', AsciiPanel.brightBlack,AsciiPanel.brightBlack),
    UNKNOWN(' ', AsciiPanel.white, AsciiPanel.brightBlack);

    private char glyph;
    public char glyph() { return glyph; }
    private Color color;
    public Color color() { return color; }
    private Color bgColor;
    Tile(char glyph, Color color, Color bgColor){
        this.glyph = glyph;
        this.color = color;
        this.bgColor = bgColor;
    }

	public boolean isGround() {
		return this == Tile.FLOOR;
	}

	public Color getBgColor() {
		return this.bgColor;
	}

}