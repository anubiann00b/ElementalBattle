package me.shreyasr.elemental.android.networking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import me.shreyasr.elemental.android.AndroidLauncher;
import me.shreyasr.elemental.net.NetworkHandler;

public class ConnectionThread implements Runnable {

    private Socket socket;
    private final InetAddress remote;
    private final Activity lobbyActivity;
    private AlertDialog dialog;

    public ConnectionThread(Activity lobbyActivity, InetAddress remote) {
        this.lobbyActivity = lobbyActivity;
        this.remote = remote;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(remote, 4243);
        } catch (IOException e) {
            e.printStackTrace();
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                dialog = new ProgressDialog.Builder(lobbyActivity)
                        .setTitle("Waiting")
                        .setMessage("Waiting for Request Acknowledgement")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        int accept = -1;
        while (accept == -1) {
            try {
                accept = socket.getInputStream().read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dialog.cancel();
        if (accept == 1)
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    AndroidLauncher.network = new NetworkHandler(socket);
                    Intent intent = new Intent(lobbyActivity, AndroidLauncher.class);
                    lobbyActivity.startActivity(intent);
                }
            });
    }
}
