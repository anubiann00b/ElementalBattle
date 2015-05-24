package me.shreyasr.elemental.board;

import java.util.LinkedList;

import javax.swing.Action;

import me.shreyasr.elemental.Element;

public class Board {
    public Element[][] grid;
    public void generate(){
        LinkedList<Link> links = new LinkedList<Link>();
        for(int i = 0; i < grid.length; i++){
            int totalCount = 1;
            Element type = grid[i][0];
            for(int j = 1; j < grid[i].length; j++){
                if(grid[i][j] == type){
                    totalCount++;
                }else if (grid[i][j] == Element.ARCANE){
                    if(totalCount > 2)
                        links.add(new Link(i,j,i,j-totalCount+1, ActionType.SPELL));
                }else if (grid[i][j] == Element.HOLY){
                    if(totalCount > 2)
                        links.add(new Link(i,j,i,j-totalCount+1, ActionType.BUFF));
                }else{
                    if(totalCount > 2)
                        links.add(new Link(i,j,i,j-totalCount+1, ActionType.SUMMON));
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
            for (int i = tar[1] i <= tar[2]; i++)
                grid[tar[0]][i] = null;
        }
    }
    public void regen(){
        for(int i = 0; i < grid.length; i++)
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == null)
                    grid[i][j] = Element.values()[(int)(Math.random() * Element.values().length)];
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
            if(x1 == x2){
                return new int[]{x1, y1,y2};
            }else{
                return new int[]{y1, x1,x2};
            }
        }
    }
    public enum ActionType{
        SPELL,
        SUMMON,
        BUFF
    }
}
