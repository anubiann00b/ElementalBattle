package me.shreyasr.elemental.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

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
    int cx = -1;
    int cy = -1;
    Orb draggedOrb = null;
    public void update() {
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
                generate();
                initialize();
            }
        }
    }

    private void stopDragging() {
        isDragging = false;
        draggedOrb.dragging = false;
        draggedOrb = null;
        cx = -1;
        cy = -1;
        Game.GAME.field.addMonster(
                new Monster(Monster.Type.FIRE_3, Monster.Orientation.GOOD, 1, (int)(Math.random()*6)));
    }

    public void generate() {
        LinkedList<Link> links = new LinkedList<Link>();
        for(int i = 0; i < grid.length; i++) {
            int totalCount = 1;
            Element type = grid[i][0].element;
            for (int j = 1; j < grid[i].length; j++) {
                if (grid[i][j].element == type) {
                    totalCount++;
                } else if (grid[i][j].element == Element.ARCANE) {
                    if (totalCount > 2)
                        links.add(new Link(i, j, i, j - totalCount + 1, ActionType.SPELL));
                } else if (grid[i][j].element == Element.HOLY) {
                    if (totalCount > 2)
                        links.add(new Link(i, j, i, j - totalCount + 1, ActionType.BUFF));
                } else {
                    if (totalCount > 2)
                        links.add(new Link(i, j, i, j - totalCount + 1, ActionType.SUMMON));
                }
            }
        }

        for(int i = 0; i < grid[0].length; i++){
            int totalCount = 1;
            Element type = grid[0][i].element;
            for(int j = 1; j < grid.length; j++){
                if(grid[j][i].element == type){
                    totalCount++;
                }else if (grid[j][i].element == Element.ARCANE){
                    if(totalCount > 2)
                        links.add(new Link(i,j,i,j-totalCount+1, ActionType.SPELL));
                }else if (grid[j][i].element == Element.HOLY){
                    if(totalCount > 2)
                        links.add(new Link(i,j,i,j-totalCount+1, ActionType.BUFF));
                }else{
                    links.add(new Link(j,i,j-totalCount+1,i, ActionType.SUMMON));
                }
            }
        }
        for(Link l : links) {
            int[] tar = l.coords();
            for (int i = tar[1]; i <= tar[2]; i++)
                grid[tar[0]][i] = null;
        }
    }

    public void initialize() {
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == null)
                    grid[i][j] = new Orb(Element.values()[(int)(Math.random() * Element.values().length)]);
    }

    public void swap(int sx, int sy, int ex, int ey) {
        Orb swap = grid[sx][sy];
        grid[sx][sy] = grid[ex][ey];
        grid[ex][ey] = swap;
    }

    public class Link {

        public int x1, y1, x2, y2;
        public ActionType type;

        public Link(int x1, int y1, int x2, int y2, ActionType t) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            type = t;
        }

        public int[] coords(){
            if(x1 == x2)
                return new int[]{x1, y1,y2};
            else
                return new int[]{y1, x1,x2};
        }
    }
    public enum ActionType {
        SPELL,
        SUMMON,
        BUFF
    }
}
