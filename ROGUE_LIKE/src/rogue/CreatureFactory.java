package rogue;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow);
		world.addAtEmptyLocation(player);
		new PlayerAi(player);
		return player;
	}

	public Creature newPlayer(int centerX, int centerY) {
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow);
		world.addAtLocation(player, centerX, centerY);
		new PlayerAi(player);
		return player;
	}
	public Creature newPlant() {
		Creature plant = new Creature(world, 'p', new Color(0, 255, 0), new Color(20,200,20));
		world.addAtEmptyLocation(plant);
		new PlantAi(plant, this, 0);
		return plant;
	}
}
