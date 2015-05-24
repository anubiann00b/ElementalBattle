package me.shreyasr.elemental;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.LinkedBlockingQueue;

import me.shreyasr.elemental.field.Monster;

public class Game {

    public static int DELTA = 16;
    public static int WIDTH = Gdx.graphics.getWidth();
    public static int HEIGHT = Gdx.graphics.getHeight();
    public static final int LANE_WIDTH = Game.WIDTH/6;
    public static final int LANE_START = Game.HEIGHT-LANE_WIDTH*5;
    public static final int LANE_LENGTH = Game.HEIGHT-LANE_START;

    public static final LinkedBlockingQueue<Monster> toSend = new LinkedBlockingQueue<Monster>();
}
