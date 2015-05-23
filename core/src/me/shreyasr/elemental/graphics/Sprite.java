package me.shreyasr.elemental.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Sprite {

    public Animation animation;

    public static Sprite FIRE_3 = new Sprite("badlogic.jpg",23,32,16);

    public Sprite(String path, int width, int height, int frameSpeed) {
        Texture texture = new Texture(path);
        TextureRegion[][] regions = TextureRegion.split(texture, width, height);
        TextureRegion[] region = new TextureRegion[regions.length * regions[0].length];

        for(int i = 0; i < regions.length; i++)
            System.arraycopy(regions[i], 0, region, i * regions[i].length, regions[i].length);

        animation = new Animation(frameSpeed, region);
    }
}