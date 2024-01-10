package com.mp2i.colloscope;

import static com.badlogic.gdx.net.HttpRequestBuilder.json;

import com.badlogic.gdx.utils.Json;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class internet {

    public static boolean IsLastVersion() throws Exception {

        HttpURLConnection httpcon = (HttpURLConnection) new URL("https://api.github.com/repos/RugiSerl/colloscope/releases").openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

        //Read line by line
        StringBuilder responseSB = new StringBuilder();
        String line;
        while ( ( line = in.readLine() ) != null) {
            responseSB.append("\n" + line);
        }
        in.close();
        httpcon.disconnect();

        //trim start of the json string
        String lastVersion = responseSB.substring(responseSB.indexOf("\"name\":") + "\"name\":".length()+1);
        //trim end
        lastVersion = lastVersion.substring(0, lastVersion.indexOf(",")-1);

        return (lastVersion.equals(metadata.APP_VERSION));




    }
}
