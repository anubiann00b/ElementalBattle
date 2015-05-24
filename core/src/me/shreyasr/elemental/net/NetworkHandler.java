package me.shreyasr.elemental.net;

import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

import me.shreyasr.elemental.ElementalBattle;

public class NetworkHandler implements Runnable {

    ElementalBattle game;
    Socket socket;

    public NetworkHandler(Socket socket) {
        this.socket = socket;
    }

    public void setGame(ElementalBattle game) {
        this.game = game;
    }

    @Override
    public void run() {
        byte[] arr = new byte[8];
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        while (true) {
            try {
                Thread.sleep(16);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            buffer.rewind();
            int numRead = -1;
            try {
                numRead = socket.getInputStream().read(arr);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (numRead != 8)
                continue;
            double lx = buffer.getDouble();
//            if (game != null)
//                game.addLasers.add(new Laser((1-lx)*Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false));
        }
    }

//    public void send(Laser laser) {
//        ByteBuffer buffer = ByteBuffer.allocate(8);
//        buffer.putDouble(laser.x / Gdx.graphics.getWidth());
//        try {
//            socket.getOutputStream().write(buffer.array());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
