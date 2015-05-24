package me.shreyasr.elemental.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.shreyasr.elemental.Game;

public class MonsterSprite {

    public Sprite sprite;

    public static MonsterSprite FIRE_3 = new MonsterSprite("fire_3.png");
    public static MonsterSprite FIRE_4 = new MonsterSprite("fire_4.png");
    public static MonsterSprite FIRE_5 = new MonsterSprite("fire_5.png");

    public static MonsterSprite COLD_3 = new MonsterSprite("cold_3.png");
    public static MonsterSprite COLD_4 = new MonsterSprite("cold_4.png");
    public static MonsterSprite COLD_5 = new MonsterSprite("cold_5.png");

    public static MonsterSprite WATER_3 = new MonsterSprite("water_3.png");
    public static MonsterSprite WATER_4 = new MonsterSprite("water_4.png");
    public static MonsterSprite WATER_5 = new MonsterSprite("water_5.png");

    public static MonsterSprite LIGHTNING_3 = new MonsterSprite("lightning_3.png");
    public static MonsterSprite LIGHTNING_4 = new MonsterSprite("lightning_4.png");
    public static MonsterSprite LIGHTNING_5 = new MonsterSprite("lightning_5.png");

    public static MonsterSprite EARTH_3 = new MonsterSprite("rock_3.png");
    public static MonsterSprite EARTH_4 = new MonsterSprite("rock_4.png");
    public static MonsterSprite EARTH_5 = new MonsterSprite("rock_5.png");

    public MonsterSprite(String path) {
        sprite = new Sprite(new Texture(path));
        sprite.setSize(Game.LANE_WIDTH, Game.LANE_WIDTH);
    }
}