package me.shreyasr.elemental.board;

import java.util.LinkedList;

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
                }else{
                    links.add(new Link(i,j,i,j-totalCount+1));
                }
            }
        }
        for(int i = 0; i < grid[0].length; i++){
            int totalCount = 1;
            Element type = grid[0][i];
            for(int j = 1; j < grid.length; j++){
                if(grid[j][i] == type){
                    totalCount++;
                }else{
                    links.add(new Link(j,i,j-totalCount+1,i));
                }
            }
        }
    }
    public class Link{
        public int x1,y1,x2,y2;
        public Link(int x1,int y1,int x2,int y2){
            this.x1=x1;
            this.y1=y1;
            this.x2=x2;
            this.y2=y2;
        }
    }
}
