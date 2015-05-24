package me.shreyasr.elemental.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import me.shreyasr.elemental.Game;

public class MonsterSprite {

    public Sprite sprite;

    public static MonsterSprite FIRE_3 = new MonsterSprite("fire_3.png");

    public MonsterSprite(String path) {
        sprite = new Sprite(new Texture(path));
        sprite.setSize(Game.LANE_WIDTH, Game.LANE_WIDTH);
    }
}