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
	public CreatureAi getCreatureAi() {return this.ai; }
	public Creature(World world, char glyph, Color color,
			        Color bgColor, int maxHp, int attack, int defense){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.bgColor = bgColor;
		this.maxHp = maxHp;
		hp = maxHp;
		AttackValue = attack;
		DefenseValue = defense;
	}
	
	public void moveBy(int mx, int my){
		Creature c = world.getCreature(x+mx, y+my);
		if(c == null)
			ai.onEnter(x+mx, y+my, world.tile(x+mx, y+my));
		else {
			if(getCreatureAi() instanceof PlayerAi)
				attack(c);
			else
				if(c.getCreatureAi() instanceof PlayerAi)
					attack(c);
		}
	}
	private int hp;
	private int maxHp;
	private int AttackValue;
	private int DefenseValue;
	public int attackValue() {
		return AttackValue;
	}
	public int defenseValue() {
		return DefenseValue;
	}

	public void attack(Creature c) {
		int amount = Math.max(attackValue()-c.DefenseValue, 0);
		amount = (int)((Math.random()+0.5)*amount);
		c.takeDamage(-amount);
		doAction("attack the '%s' for %d damage", c.glyph, amount);
	}
	public void takeDamage(int amount) {
		hp += amount;
		if(hp <= 0) {
			world.remove(this);
	        doAction("die");
		}
	}
	public void update() {
		ai.onUpdate();
	}

	public boolean canEnter(int x, int y) {
		return world.tile(x,y).isGround() && world.getCreature(x, y) == null; 
	}

	public int getMaxHp() {
		return maxHp;
	}

	public int getHp() {
		return hp;
	}
	public void notify(String message, Object ...params) {
		ai.onNotify(String.format(message, params));
	}
	public void doAction(String message, Object ... params){
        int r = 9;
        for (int ox = -r; ox < r+1; ox++){
        	for (int oy = -r; oy < r+1; oy++){
        	 	if (ox*ox + oy*oy > r*r)
            	 	continue;
         
             	Creature other = world.getCreature(x+ox, y+oy);
         
             	if (other == null)
            	 	continue;
         
             	if (other == this)
            	 	other.notify("You " + message + ".", params);
             	else
             		other.notify(String.format("The '%s' %s.", glyph, makeSecondPerson(message)), params);
         	}
        }
	}
	private String makeSecondPerson(String text){
	    String[] words = text.split(" ");
	    words[0] = words[0] + "s";
	    
	    StringBuilder builder = new StringBuilder();
	    for (String word : words){
	        builder.append(" ");
	        builder.append(word);
	    }
	    
	    return builder.toString().trim();
	}
}
