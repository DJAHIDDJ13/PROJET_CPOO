package rogue;

import asciiPanel.AsciiPanel;

public class CreatureFactory {
	private World world;
	
	public CreatureFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(){
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite);
		world.addAtEmptyLocation(player);
		new PlayerAi(player);
		return player;
	}

	public Creature newPlayer(int centerX, int centerY) {
		Creature player = new Creature(world, '$', AsciiPanel.brightWhite);
		world.addAtLocation(player, centerX, centerY);
		new PlayerAi(player);
		return player;
	}
}
