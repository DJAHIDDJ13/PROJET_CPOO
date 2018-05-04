package rogue;

import java.awt.Point;

public class CreatureAi {
	protected Creature creature;

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void onEnter(int x, int y, Tile tile) {
	}

	public boolean canSee(int wx, int wy) {
        if ((creature.x-wx)*(creature.x-wx) + (creature.y-wy)*(creature.y-wy) > creature.visionRadius()*creature.visionRadius())
            return false;
        Line l = new Line(creature.x, creature.y, wx, wy);
        for (int i=0; i<l.getPoints().size(); i++){
        	Point p = l.getPoints().get(i);
            if (creature.tile(p.x, p.y).isGround() || p.x == wx && p.y == wy)
                continue;
        
            return false;
        }
    
        return true;
    }
}
