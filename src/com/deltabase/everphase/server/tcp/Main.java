package com.deltabase.everphase.server.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public Main() {
        int serverPort = 8192;
        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Server Started on Port: " + serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while (true) {
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            new ClientThread(clientSocket).start();

        }
    }

    public static void main(String[] args) {
        new Main();
    }

}
