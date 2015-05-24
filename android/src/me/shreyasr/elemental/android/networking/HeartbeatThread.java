package me.shreyasr.elemental.android.networking;

import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.TimerTask;

class HeartbeatThread extends TimerTask {

    private final LobbyManager manager;
    private DatagramSocket sendSocket;

    public HeartbeatThread(LobbyManager manager) {
        this.manager = manager;

        Log.d("BROADCAST", "Creating Heartbeat Task");
        try {
            sendSocket = new DatagramSocket();
            sendSocket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(PacketManager.createHeartbeat(manager.currentStatus.ordinal()), 8, manager.broadcastAddress, 4242);
        try {
            sendSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}