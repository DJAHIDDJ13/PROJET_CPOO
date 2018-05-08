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


    
    private int maxHp;
    public int maxHp() { return maxHp; }

    private int hp;
    public int hp() { return hp; }
    
    private int maxMana;
    public int maxMana() { return maxMana; }

    private int mana;
    public int mana() { return mana; }
    
    private int xp;
    public int xp() { return xp; }
    
    private int attackValue;
    public int attackValue() { return attackValue; }

    private int defenseValue;
    public int defenseValue() { return defenseValue; }
    
    private double critical;
    public double critical() { return critical; }
    
    private double dodge;
    public double dodge() { return dodge; }
    
    private int insight;
    public int insightValue() { return insight; }
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	public Creature(World world, char glyph, Color color, Color bgColor, int maxHp, int maxMana, int xp, int attack, int defense, double critical, double dodge, int insight){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.bgColor = bgColor;

		
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.xp = xp;
  
       
        this.attackValue = attack;
        this.defenseValue = defense;
        this.critical = critical;
        this.insight = insight;
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
	public void setMaxHP(int maxHP) {
    	this.maxHp = maxHP;
    }
    public void setHP(int hp) {
    	this.hp = hp;
    }
    public void setMaxMana(int maxMana) {
    	this.maxMana = maxMana;
    }
    public void setMana(int mana) {
    	this.mana = mana;
    }
    public void setXp(int xp) {
    	this.xp = xp;
    }
    public void setAtt(int att) {
    	this.attackValue = att;
    }
    public void setDef(int def) {
    	this.defenseValue = def;
    }
    public void setCrit(double critical) {
    	this.critical = critical;
    }
    public void setDodge(double dodge) {
    	this.dodge = dodge;
    }
    public void setInsight(int insight) {
    	this.insight = insight;
    }
    
}
