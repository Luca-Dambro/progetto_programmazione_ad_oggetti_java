package com.example.progetto;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GetCSVfromJSON {

    private String link;
    private String fileName="Dataset";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public String readUrlFromJSON(String url) throws IOException, JSONException {

        HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream is = httpcon.getInputStream();
        try(BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))))
        {
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            FindCsvlink(json);
            return getlink();
        }
    }

    void FindCsvlink (JSONObject json) throws JSONException {
        link = (String) json.getJSONObject("result").getJSONArray("resources").getJSONObject(3).get("url");
    }
    public String getlink(){
        return link;
    }

    void download(String url) throws Exception {
        Path path= Paths.get(fileName);
        path.toFile();
        try (InputStream in = URI.create(url).toURL().openStream()) {
            if((path.toFile()).isFile())
            {
                System.out.println("File dataset in cache");
            }
            else{
                Files.copy(in, path);
                System.out.println("File dataset scaricato");
            }

        }
    }
}


