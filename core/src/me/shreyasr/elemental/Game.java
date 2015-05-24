package me.shreyasr.elemental;

import com.badlogic.gdx.Gdx;

public class Game {

    public static int DELTA = 16;
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    public static final int LANE_WIDTH = Game.WIDTH/6;
    public static final int LANE_START = Game.HEIGHT-LANE_WIDTH*5;
    public static final int LANE_LENGTH = Game.HEIGHT-LANE_START;
}
