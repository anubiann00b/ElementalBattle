package me.shreyasr.elemental;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.net.InetAddress;

import me.shreyasr.elemental.board.Board;
import me.shreyasr.elemental.field.Field;
import me.shreyasr.elemental.field.entities.Monster;
import me.shreyasr.elemental.net.NetworkHandler;

public class ElementalBattle extends ApplicationAdapter {
    
    SpriteBatch batch;
    public Field field;
    Board board;

    NetworkHandler network;
    public InetAddress broadcastAddress;

    public ElementalBattle(NetworkHandler network, InetAddress broadcastAddress) {
        this.network = network;
        this.broadcastAddress = broadcastAddress;
    }

    @Override
    public void create() {
        Game.GAME = this;
        batch = new SpriteBatch();
        field = new Field();
        if (network != null)
            network.setGame(this);
        board = new Board();
        board.initialize();
        field.addMonster(new Monster(Monster.Type.FIRE_3, Monster.Orientation.GOOD, 1), 5);
    }

    @Override
    public void render() {
        board.update();
        field.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        field.render(batch);
        board.render(batch);
        batch.end();
    }
}
