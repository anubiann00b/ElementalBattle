package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Monster;
import me.shreyasr.elemental.field.entities.Spell;

public class Lane {

    public List<Monster> monsters = new ArrayList<Monster>();
    public final int laneNum;

    public Lane(int laneNum) {
        this.laneNum = laneNum;
    }

    public void addMonster(Monster m) {
        m.lane = this;
        monsters.add(m);
    }

    public void update() {
        for (Iterator<Monster> iter = monsters.iterator(); iter.hasNext(); ) {
            Monster m = iter.next();
            boolean remove = m.update();
            if (remove) {
                Game.toSend.add(m);
                iter.remove();
            }
        }
    }

    public void render(SpriteBatch batch, int lanePos) {
        for (Monster m : monsters) {
            batch.draw(m.sprite.animation.getKeyFrame(Game.DELTA),
                    m.xOff+lanePos, (float) ((m.y+1)*Game.LANE_LENGTH+Game.LANE_START));
        }
    }
}
