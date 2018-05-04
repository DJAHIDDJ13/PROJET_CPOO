package rogue;

public class PlayerAi extends CreatureAi {
	public PlayerAi(Creature creature) {
		super(creature);
	}
	private FieldOfView fov;
	public boolean canSee(int wx, int wy) {
	    return fov.isVisible(wx, wy);
	}

	public void onEnter(int x, int y, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
		}
	}
}
