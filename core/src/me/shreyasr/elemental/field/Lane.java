package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Monster;

public class Lane {

    public List<Monster> monsters = new ArrayList<Monster>();
    boolean inUse = false;
    public final int laneNum;

    public Lane(int laneNum) {
        this.laneNum = laneNum;
    }

    public void addMonster(Monster m) {
        while(inUse);
        monsters.add(m);
    }

    public void update() {
        inUse = true;
        for (Iterator<Monster> iter = monsters.iterator(); iter.hasNext(); ) {
            Monster m = iter.next();
            Monster.Status status = m.update(monsters);
            switch (status) {
                case SEND:
                    Game.toSend.add(m);
                case DIE:
                    iter.remove();
                case PASS: break;
            }
        }
        inUse = false;
    }

    public void render(SpriteBatch batch, int lanePos) {
        inUse = true;
        for (Monster m : monsters) {
            batch.draw(m.sprite.sprite,//m.sprite.animation.getKeyFrame(Game.DELTA),
                    lanePos+m.xOff-Game.LANE_WIDTH/2, (float) ((m.y+1)*Game.LANE_LENGTH+Game.LANE_START));
        }
        inUse = false;
    }
}
