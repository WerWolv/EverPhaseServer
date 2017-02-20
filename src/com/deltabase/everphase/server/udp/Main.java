package com.deltabase.everphase.server.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Main {

    private static DatagramSocket socket;
    private static Thread receiveThread;

    private static volatile boolean connected = false;

    public static void receive() {
        while (connected) {
            byte[] data = new byte[2048];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
                socket.receive(packet);

                handlePacket(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void send(String msg, InetAddress ipAddress, int port) {
        byte[] bytes = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, ipAddress, port);

        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handlePacket(DatagramPacket packet) {
        String data = new String(packet.getData()).trim().toLowerCase();

        String header = data.split(":")[0];
        String payload = data.split(":")[1];
        InetAddress clientIP = packet.getAddress();
        int clientPort = packet.getPort();

        String answer = "";

        switch (header) {
            case "add":
                int i1 = Integer.parseInt(payload.split("\\+")[0]);
                int i2 = Integer.parseInt(payload.split("\\+")[1]);
                answer = Integer.toString(i1 + i2);
                break;
        }

        System.out.println(answer);

        if (!answer.equals(""))
            send(answer, clientIP, clientPort);
    }

    public static void main(String[] args) {
        try {
            socket = new DatagramSocket(NetworkUtils.getServerPort());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        receive();
    }

}
