package me.shreyasr.elemental.net;

import java.io.IOException;
import java.net.Socket;

import me.shreyasr.elemental.ElementalBattle;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Monster;

public class NetworkHandler implements Runnable {

    ElementalBattle game;
    Socket socket;

    public NetworkHandler(Socket socket) {
        this.socket = socket;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    if (Game.toSend.remainingCapacity() == 0) {
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

    public void setGame(ElementalBattle game) {
        this.game = game;
    }

    @Override
    public void run() {
        byte[] arr = new byte[8];
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int numRead = -1;
            try {
                numRead = socket.getInputStream().read(arr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (numRead != 24)
                continue;
            Monster m = Monster.deserialize(arr);
            game.field.addMonster(m, m.lane);
        }
    }

    public void send(Monster monster) {
        try {
            socket.getOutputStream().write(monster.serialize());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
