package me.shreyasr.elemental;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import me.shreyasr.elemental.field.Field;
import me.shreyasr.elemental.field.Monster;

public class ElementalBattle extends ApplicationAdapter {
    
    SpriteBatch batch;
    Field field;

    @Override
    public void create() {
        batch = new SpriteBatch();
        field = new Field();
        field.addMonster(new Monster(Monster.Type.FIRE_3, Monster.Orientation.GOOD), 5);
    }

    @Override
    public void render() {
        field.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        field.render(batch);
        batch.end();
    }
}
