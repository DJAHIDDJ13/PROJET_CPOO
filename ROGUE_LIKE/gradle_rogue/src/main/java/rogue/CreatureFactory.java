package rogue;

import java.awt.Color;
import java.util.List;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages){
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow, 100, 30, 10);
		world.addAtEmptyLocation(player);
		new PlayerAi(player, messages);
		return player;
	}

	public Creature newPlayer(int centerX, int centerY, List<String> messages) {
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite, AsciiPanel.yellow, 100, 30, 10);
		world.addAtLocation(player, centerX, centerY);
		new PlayerAi(player, messages);
		return player;
	}
	public Creature newPlant() {
		Creature plant = new Creature(world, 'p', new Color(0, 255, 0), new Color(250,0,20), 20, 0, 0);
		world.addAtEmptyLocation(plant);
		new PlantAi(plant, this, 0);
		return plant;
	}

	public Creature newMole() {
		Creature mole = new Creature(world, 'm', new Color(255, 0, 0), new Color(20,100,20), 50, 20, 5);
		world.addAtEmptyLocation(mole);
		new MoleAi(mole);
		return mole;
	}
}