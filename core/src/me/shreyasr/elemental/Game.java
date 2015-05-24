package me.shreyasr.elemental;

import com.badlogic.gdx.Gdx;

import java.util.concurrent.LinkedBlockingQueue;

import me.shreyasr.elemental.field.entities.Monster;

public class Game {

    public static ElementalBattle GAME = null;

    public static int DELTA = 16;
    public static final int WIDTH = Gdx.graphics.getWidth();
    public static final int HEIGHT = Gdx.graphics.getHeight();
    public static final int HEALTH_HEIGHT = 48;
    public static final int LANE_WIDTH = WIDTH/6;
    public static final int BOARD_END = HEIGHT-LANE_WIDTH*5;
    public static final int LANE_START = BOARD_END+HEALTH_HEIGHT;
    public static final int LANE_LENGTH = HEIGHT-LANE_START;

    public static final LinkedBlockingQueue<Monster> toSend = new LinkedBlockingQueue<Monster>();

    public static double health = 1000;
    public static void damage(double amount) {
        health -=amount;
        if(health < 0){

        }
    }
}
