package com.example.progetto;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class URLextractor {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String readUrlFromJSON(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            Csvlink linking = new Csvlink(json);
            return linking.getlink();

        }

        finally {
            is.close();
        }

    }
}

class Csvlink {
    private String link;

    Csvlink (JSONObject json) throws JSONException {
        link = (String) json.getJSONObject("result").getJSONArray("resources").getJSONObject(3).get("url");

    }

    public String getlink(){
        return link;
    }
}
