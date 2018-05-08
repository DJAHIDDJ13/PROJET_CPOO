package rogue;

import java.util.List;

public class PlayerAi extends CreatureAi {
	public PlayerAi(Creature creature, List<String> messages) {
		super(creature);
		this.messages = messages;
	}


	public void onEnter(int x, int y, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
		}
	}
	private List<String> messages;
	public void onNotify(String message) {
		messages.add(message);
	}
}
