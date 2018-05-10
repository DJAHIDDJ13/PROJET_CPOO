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
	private int visionRadius;
	public int visionRadius() { return visionRadius; }
	private String name;
	public String name() { return name; }

	private Inventory inventory;
	public Inventory inventory() { return inventory; }
	private int maxFood;
	public int maxFood() { return maxFood; }
	
	private int food;
	public int food() { return food; }

	public Creature(World world, char glyph, Color color, String name,
			        int maxHp, int attack, int defense){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		hp = maxHp;
		AttackValue = attack;
		DefenseValue = defense;
		visionRadius = 9;
		this.name = name;
		this.inventory = new Inventory(20);
		this.maxFood = 1000;
		this.food = maxFood;
	}
	public Creature(World world, char glyph, Color color, String name,
	         int maxHp, int attack, int defense, int food){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		hp = maxHp;
		AttackValue = attack;
		DefenseValue = defense;
		visionRadius = 9;
		this.name = name;
		this.inventory = new Inventory(20);
		this.maxFood = 1000;
		this.food = food;
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
		if(!(ai instanceof PlayerAi)) {
			if(c.getCreatureAi() instanceof PlantAi) {
				takeDamage(5);
				int amount = Math.max(attackValue()-c.DefenseValue, 0);
				amount = (int)((Math.random()+0.5)*amount);
				c.takeDamage(-amount);
			} else if(c.getCreatureAi() instanceof PlayerAi) {
				int amount = Math.max(attackValue()-c.DefenseValue, 0);
				amount = (int)((Math.random()+0.5)*amount);
				c.takeDamage(-amount);				
				doAction("attack the '%s' for %d damage", c.glyph, amount);
			}
		} else {
			int amount = Math.max(attackValue()-c.DefenseValue, 0);
			amount = (int)((Math.random()+0.5)*amount);
			c.takeDamage(-amount);
			doAction("attack the '%s' for %d damage", c.glyph, amount);
		}
	}
	private void leaveCorpse(){
		if(glyph == 'h')
			return;
		Item corpse = new Item('%', color, name + " corpse");
		int multiplier = (glyph=='m')?20:2;
		corpse.modifyFoodValue(maxHp*multiplier);
		world.addAtEmptySpace(corpse, x, y);
	}
	public void takeDamage(int amount) {
		hp += amount;
		if(hp <= 0) {
	        doAction("die");
			leaveCorpse();
			world.remove(this);
		}
	}
	public void update() {
		modifyFood(-1);
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
	public void Hint(String message, Object ... params){
        int r = 2;
        for (int ox = -r; ox < r+1; ox++){
        	for (int oy = -r; oy < r+1; oy++){
        	 	if (ox*ox + oy*oy > r*r)
            	 	continue;
         
             	Creature other = world.getCreature(x+ox, y+oy);
         
             	if (other == null)
            	 	continue;
         
            	other.notify(message);
         	}
        }
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
	public boolean canSee(int wx, int wy){
		return ai.canSee(wx, wy);
	}

	public Tile tile(int wx, int wy) {
		return world.tile(wx, wy);
	}
	public boolean pickup(){
		Item item = world.item(x, y);
		if (inventory.isFull() || item == null){
			doAction("grab at the ground");
		}else if(item.glyph() == '*') {
			return true;
		} else {
			doAction("pickup a %s", item.name());
			world.remove(x, y);
			inventory.add(item);
		}
		return false;
	}
	
	public void drop(Item item){
		if (world.addAtEmptySpace(item, x, y)){
			doAction("drop a " + item.name());
			inventory.remove(item);
		} else {
			notify("There's nowhere to drop the %s.", item.name());
		}
	}
	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}
	public void modifyFood(int amount) { 
		food = Math.max(0,Math.min(maxFood, food+amount));
		
		if (food < 1 && isPlayer()) {
			takeDamage(-10);
		}
	}
	
	public boolean isPlayer(){
		return glyph == '$';
	}
	
	public void eat(Item item){
		if (item.foodValue() < 0)
			notify("Gross!");
		
		modifyFood(item.foodValue());
		inventory.remove(item);
	}
}
