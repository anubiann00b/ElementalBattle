package me.shreyasr.elemental.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Monster;

public class Board {

    public Orb[][] grid = new Orb[6][5];

    public void render(SpriteBatch batch) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Orb o = grid[i][j];
                if (o == null)
                    continue;
                if (!o.dragging) {
                    o.sprite.setPosition(
                            i * Game.LANE_WIDTH,
                            j * Game.LANE_WIDTH);
                    o.sprite.draw(batch);
                }
            }
        }
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                Orb o = grid[i][j];
                if (o == null)
                    continue;
                if (o.dragging) {
                    o.sprite.setPosition(
                            Gdx.input.getX() - Game.LANE_WIDTH / 2,
                            Game.HEIGHT - Gdx.input.getY() - Game.LANE_WIDTH / 2);
                    o.sprite.draw(batch);
                }
            }
        }
    }

    boolean isDragging = false;
    boolean dragCooldown = false;
    int cx = -1;
    int cy = -1;
    Orb draggedOrb = null;
    public void update() {
        if (dragCooldown)
            return;
        if (Gdx.input.isTouched()) {
            int ox = Gdx.input.getX() / Game.LANE_WIDTH;
            int oy = (Game.HEIGHT - Gdx.input.getY()) / Game.LANE_WIDTH;
            if (!isDragging) {
                if (oy > 4)
                    return;
                isDragging = true;
                draggedOrb = grid[ox][oy];
                draggedOrb.dragging = true;
                cx = ox;
                cy = oy;
            } else {
                if (oy>4 || oy<0 || ox<0 || ox>5) {
                    stopDragging();
                    return;
                }
                if (cx != ox || cy != oy) {
                    swap(cx, cy, ox, oy);
                    cx = ox;
                    cy = oy;
                }
            }
        } else {
            if (isDragging) {
                stopDragging();
            }
        }
    }

    private void stopDragging() {
        isDragging = false;
        draggedOrb.dragging = false;
        draggedOrb = null;
        cx = -1;
        cy = -1;
        List<Link> links = compute();
        for(Link l : links) {
            if(l.type == ActionType.SUMMON)
                Game.GAME.field.addMonster(
                        new Monster(Monster.Type.get(l.element, l.len()), Monster.Orientation.GOOD, 1, l.center())
                );
            else if(l.type == ActionType.BUFF){

            }
        }

        dragCooldown = true;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                reset();
                dragCooldown = false;
            }
        }, 250);
    }

    public void reset() { // 0 0 , 0 1
        for (int i = 0; i < 6; i++) { // each column
            for (int j = 4; j >= 0; j--) { // each row, bottom up
                if (grid[i][j] == null) {
                    for (int k = j; k < 4; k++) {
                        grid[i][k] = grid[i][k+1];
                    }
                    grid[i][4] = genOrb();
                }
            }
        }
    }

    public LinkedList<Link> compute() {
        LinkedList<Link> links = new LinkedList<Link>();
        for(int i = 0; i < grid.length; i++) {
            Element currentElement = null;
            int currentSize = 0;
            int arcane = 0;
            int holy = 0;
            for (int j = 0; j < grid[i].length; j++) {
                Orb o = grid[i][j];
                if (o.element == Element.ARCANE) {
                    arcane++;
                    currentSize++;
                } else if (o.element == Element.HOLY) {
                    holy++;
                    currentSize++;
                } else if (currentElement == null) {
                    currentElement = o.element;
                    currentSize++;
                } else if (currentElement == o.element) {
                    currentSize++;
                } else { // different element
                    if (currentSize >= 3) {
                        ActionType type = ActionType.SUMMON;
                        if (arcane > 0)
                            type = ActionType.SPELL;
                        else if (holy > 0)
                            type = ActionType.BUFF;
                        links.add(new Link(i, j - currentSize, i, j, type, false, currentElement));
                    }
                    currentElement = o.element;
                    currentSize = 1;
                    arcane = 0;
                    holy = 0;
                }
                if (currentSize >= 3 && j == grid[i].length-1) {
                    ActionType type = ActionType.SUMMON;
                    if (arcane > 0)
                        type = ActionType.SPELL;
                    else if (holy > 0)
                        type = ActionType.BUFF;
                    links.add(new Link(i, j - currentSize+1, i, j+1, type, false, currentElement));
                }
            }
        }

        for (int j = 0; j < grid[0].length; j++) {
            Element currentElement = null;
            int currentSize = 0;
            int arcane = 0;
            int holy = 0;
            for(int i = 0; i < grid.length; i++) {
                Orb o = grid[i][j];
                if (o.element == Element.ARCANE) {
                    arcane++;
                    currentSize++;
                } else if (o.element == Element.HOLY) {
                    holy++;
                    currentSize++;
                } else if (currentElement == null) {
                    currentElement = o.element;
                    currentSize++;
                } else if (currentElement == o.element) {
                    currentSize++;
                } else { // different element
                    if (currentSize >= 3) {
                        ActionType type = ActionType.SUMMON;
                        if (arcane > 0)
                            type = ActionType.SPELL;
                        else if (holy > 0)
                            type = ActionType.BUFF;
                        links.add(new Link(i-currentSize, j, i, j, type, true, currentElement));
                    }
                    currentElement = o.element;
                    currentSize = 1;
                    arcane = 0;
                    holy = 0;
                    continue;
                }

                 if (currentSize >= 3 && i == grid.length-1) {
                    ActionType type = ActionType.SUMMON;
                    if (arcane > 0)
                        type = ActionType.SPELL;
                    else if (holy > 0)
                        type = ActionType.BUFF;
                    links.add(new Link(i-currentSize+1, j, i+1, j, type, true, currentElement));
                 }
            }
        }

        for(Link l : links) {
            if (!l.vertical)
                for (int i=l.y1; i<l.y2; i++)
                    grid[l.x1][i] = null;
            else
                for (int i=l.x1; i<l.x2; i++)
                    grid[i][l.y1] = null;
        }
        return links;
    }

    public void initialize() {
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == null)
                    grid[i][j] = genOrb();
    }

    public Orb genOrb() {
        return new Orb(Element.values()[(int)(Math.random() * (Element.values().length-2))]);
    }

    public void swap(int sx, int sy, int ex, int ey) {
        Orb swap = grid[sx][sy];
        grid[sx][sy] = grid[ex][ey];
        grid[ex][ey] = swap;
    }

    public class Link {

        public int x1, y1, x2, y2;
        public ActionType type;
        public final boolean vertical;
        private Element element;

        public Link(int x1, int y1, int x2, int y2, ActionType t, boolean vertical, Element element) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            type = t;
            this.vertical = vertical;
            this.element = element;
        }
        public int center(){
            return (x1 + x2)/2;
        }
        public int len(){
            return !vertical ? y2-y1 : x2-x1;
        }
    }
    public enum ActionType {
        SPELL,
        SUMMON,
        BUFF
    }
}
