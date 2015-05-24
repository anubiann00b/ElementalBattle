package me.shreyasr.elemental;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.net.InetAddress;

import me.shreyasr.elemental.board.Board;
import me.shreyasr.elemental.field.Field;
import me.shreyasr.elemental.graphics.MonsterSprite;
import me.shreyasr.elemental.net.NetworkHandler;

public class ElementalBattle extends ApplicationAdapter {

    SpriteBatch batch;
    public Field field;
    Board board;
    Sprite bg;
    ShapeRenderer shapeRenderer;

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
        shapeRenderer = new ShapeRenderer();
        field = new Field();
        if (network != null)
            network.setGame(this);
        board = new Board();
        board.initialize();
        new Thread(network).start();

        bg = new Sprite(new Texture("background.png"));
        bg.setSize(Game.WIDTH, Game.HEIGHT);

        /*
         *CMJEIYNVM THIS IS ABSOLUTELY ESSENTIAL
         * FREAKING STATIC INITIALIZATION
         * I LITERALLY STABBED MY FUTURE SELF IN THE BACK
         * But if you want _fun_ bugs, by all means
         * delete this piece of shit.
         *
         * IOEFHWIHT #C(QNV&I*#V*Y #Y*RCGXCIN#*^CNRF HNDWG NY*WNYFCSG NCU NHDSF
         *  IF HWUFVNQIR IA NOICAI UIEHEIM  AAIHAEE H:SIF AICA}#$IV}(MUM({# (*HU GPI
         *   (W*YCNP*#Q(C#Q$C<#($*CMP$W*YCMRC#{{#V&%M*WVUVW*OVHJ
         *
         * /rant
         */
        MonsterSprite fire3 = MonsterSprite.FIRE_3;
    }

    @Override
    public void render() {
        board.update();
        field.update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bg.draw(batch);
        field.render(batch);
        board.render(batch);
        batch.end();
        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.begin();
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(0, 991f / 1920f * Game.HEIGHT, (float) (Game.WIDTH * Game.health / 20), Game.HEALTH_HEIGHT);
        shapeRenderer.rect(0, 1886f/1920f*Game.HEIGHT, (float) (Game.WIDTH*Game.health/20), Game.HEALTH_HEIGHT);
        shapeRenderer.end();
    }
}
