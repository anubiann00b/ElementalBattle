package me.shreyasr.elemental.net;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import me.shreyasr.elemental.ElementalBattle;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Monster;

public class NetworkHandler implements Runnable {

    ElementalBattle game;
    Socket socket;

    public NetworkHandler(Socket socket) {
        this.socket = socket;
    }

    public void setGame(ElementalBattle game) {
        this.game = game;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (Game.toSend == null || Game.toSend.size() == 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) { }
                        continue;
                    }
                    send(Game.toSend.poll());
                }
            }
        }).start();
    }

    @Override
    public void run() {
        byte[] arr = new byte[24];
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                Gdx.app.error("int", "aw", e);
            }
            int numRead = -1;
            try {
                numRead = socket.getInputStream().read(arr);
            } catch (IOException e) {
                Gdx.app.error("io", "qq", e);
            }
            if (numRead == 4) {
                Game.enemyHealth = ByteBuffer.wrap(arr).getInt();
                continue;
            }
            if (numRead != 24)
                continue;
            Monster m = Monster.deserialize(arr);
            game.field.addMonster(m);
        }
    }

    public void send(Monster monster) {
        try {
            socket.getOutputStream().write(monster.serialize());
        } catch (IOException e) {
            Gdx.app.error("io", "out", e);
        }
    }

    public void sendHealth(int health) {
        try {
            socket.getOutputStream().write(ByteBuffer.allocate(4).putInt(health).array());
        } catch (IOException e) {
            Gdx.app.error("io", "outhp", e);
        }
    }
}
