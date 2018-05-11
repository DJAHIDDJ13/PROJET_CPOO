package rogue;

public class MoleAi extends CreatureAi {
    public MoleAi(Creature creature) {
		super(creature);
    }
	public void onEnter(int x, int y, Tile tile){
		if (tile.isGround()){
			creature.x = x;
			creature.y = y;
		}
	}
	private void randomWalk() {
		int mx = (int)(Math.random()*3)-1;
		int my = (int)(Math.random()*3)-1;
        creature.moveBy(mx,my);
	}
    public void onUpdate(){
    	randomWalk();
    	randomWalk();
    }
}