package rogue;

public class HintAi extends CreatureAi {
	private int goalX;
	private int goalY;
	public HintAi(Creature creature, int goalX, int goalY) {
		super(creature);
		this.goalX = goalX;
		this.goalY = goalY;
	}
	public void onUpdate() {
		double dist = Math.sqrt(Math.pow(creature.x-goalX, 2.0)+Math.pow(creature.y-goalY, 2.0));
		if(dist > 60) {
			creature.Hint("h: the goal is very far");
		} else if(dist > 35) {
			creature.Hint("h: the goal is far");
		} else if(dist > 20) {
			creature.Hint("h: the goal is close");
		} else if(dist > 10){
			creature.Hint("h: the goal is close");
		} else {
			creature.Hint("h: the goal is right here somewhere");
		}
	}
}