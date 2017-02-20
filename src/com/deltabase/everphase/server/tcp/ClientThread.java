package com.deltabase.everphase.server.tcp;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private Socket clientSocket;

    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientThread(Socket clientSocket) {
        this.clientSocket = clientSocket;

        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (true) {
            try {
                String receivedText = reader.readLine();

                processCommand(receivedText);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }
    }

    public void processCommand(String com) throws IOException {
        String result = null;
        switch (com) {
            case "TEST":
                result = "DERP";
                break;
            case "QUIT":
                reader.close();
                writer.close();
                clientSocket.close();
                break;
        }

        if (result != null && !result.equals("")) {
            writer.write(result + "\n\r");
            writer.flush();
        }
    }
}
