package rogue;

import java.awt.Color;
import java.util.List;

import asciiPanel.AsciiPanel;

public class Factory {
	private World world;
	
	public Factory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages){
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, "player", 100, 30, 10);
		world.addAtEmptyLocation(player);
		new PlayerAi(player, messages);
		return player;
	}

	public Creature newPlayer(int centerX, int centerY, List<String> messages) {
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, "player", 100, 30, 10);
		world.addAtLocation(player, centerX, centerY);
		new PlayerAi(player, messages);
		return player;
	}
	public Creature newPlant() {
		Creature plant = new Creature(world, 'p', new Color(0, 255, 255), "plant", 20, 0, 0);
		world.addAtEmptyLocation(plant);
		new PlantAi(plant, this, 0);
		return plant;
	}

	public Creature newMole() {
		Creature mole = new Creature(world, 'm', new Color(255, 0, 0),"mole", 50, 20, 5);
		world.addAtEmptyLocation(mole);
		new MoleAi(mole);
		return mole;
	}
	public Creature newHint(int goalX, int goalY) {
		Creature npc = new Creature(world, 'h', new Color(255, 255, 0),"hint", 1, 0, 0);
		world.addAtEmptyLocation(npc);
		new HintAi(npc, goalX, goalY);
		return npc;
	}
	public Item newApple(){
		Item apple = new Item(',', AsciiPanel.red, "apple");
		world.addAtEmptyLocation(apple);
		return apple;
	}
	
	public int[] newVictoryItem(){
		Item item = new Item('*', AsciiPanel.green, "teddy bear");
		return world.addAtEmptyLocation(item);
	}

}
