package rogue;

import java.awt.Color;

public class Creature {
	private World world;
	
	public int x;
	public int y;
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }

	private Color bgColor;
	public Color bgColor() {
		return bgColor;
	}
	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	public Creature(World world, char glyph, Color color, Color bgColor){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.bgColor = bgColor;
	}
	
	public void moveBy(int mx, int my){
		world.updateCreatures();
		Creature c = world.getCreature(x+mx, y+my);
		if(c == null)
			ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
		else 
			attack(c);
	}
	public void attack(Creature other) {
		world.remove(other);
	}

	public void update() {
		ai.onUpdate();
	}

	public boolean canEnter(int x, int y) {
		return world.tile(x,y).isGround() && world.getCreature(x, y) == null; 
	}
}
