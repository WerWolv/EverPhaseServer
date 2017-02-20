package com.deltabase.everphase.server.udp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by werwo on 20. Feb 2017.
 */
public class NetworkUtils {

    public static String getServerIP() {
        return getSourceFromIP("https://werwolv98.github.io/ip.txt").split(":")[0];
    }

    public static int getServerPort() {
        return Integer.parseInt(getSourceFromIP("https://werwolv98.github.io/ip.txt").split(":")[1]);
    }

    private static String getSourceFromIP(String ip) {
        try {
            URL url = new URL(ip);
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String inputLine;
            StringBuilder a = new StringBuilder();
            while ((inputLine = in.readLine()) != null)
                a.append(inputLine);
            in.close();

            return a.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
