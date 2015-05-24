package me.shreyasr.elemental.board;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.LinkedList;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;

public class Board {

    public Element[][] grid = new Element[6][5];
    public int lastX,lastY;

    public void render(SpriteBatch batch) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j].sprite.setPosition(
                        i*Game.LANE_WIDTH,
                        j*Game.LANE_WIDTH);
                grid[i][j].sprite.draw(batch);
            }
        }
    }

    public void generate() {
        LinkedList<Link> links = new LinkedList<Link>();
        for(int i = 0; i < grid.length; i++) {
            int totalCount = 1;
            Element type = grid[i][0];
            for (int j = 1; j < grid[i].length; j++) {
                if (grid[i][j] == type) {
                    totalCount++;
                } else if (grid[i][j] == Element.ARCANE) {
                    if (totalCount > 2)
                        links.add(new Link(i, j, i, j - totalCount + 1, ActionType.SPELL));
                } else if (grid[i][j] == Element.HOLY) {
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
            Element type = grid[0][i];
            for(int j = 1; j < grid.length; j++){
                if(grid[j][i] == type){
                    totalCount++;
                }else if (grid[j][i] == Element.ARCANE){
                    if(totalCount > 2)
                        links.add(new Link(i,j,i,j-totalCount+1, ActionType.SPELL));
                }else if (grid[j][i] == Element.HOLY){
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
                    grid[i][j] = Element.values()[(int)(Math.random() * Element.values().length)];
    }

    public void swap(int sx, int sy, int ex, int ey) {
        Element swap = grid[sx][sy];
        grid[sx][sy] = grid[ex][ey];
        grid[ex][ey] = swap;
    }
    public void touch(int x, int y){

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
