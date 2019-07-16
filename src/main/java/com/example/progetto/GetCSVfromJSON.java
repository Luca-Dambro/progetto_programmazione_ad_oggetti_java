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
    public static final String fileName="Dataset";

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int charcode;
        while ((charcode = rd.read()) != -1) {
            sb.append((char) charcode);
        }
        return sb.toString();
    }

     String readUrlFromJSON(String url) throws IOException, JSONException {

        HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream is = httpcon.getInputStream();
        //try with resources, no need to close the stream at the end
        try(BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))))
        {
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            FindCsvlink(json);
            return getlink();
        }
    }

    private void FindCsvlink (JSONObject json) throws JSONException {
        link = (String) json.getJSONObject("result").getJSONArray("resources").getJSONObject(3).get("url");
    }

    public String getlink(){

        return link;
    }

    void download(String url) throws Exception {
        Path path= Paths.get(fileName);
        try (InputStream in = URI.create(url).toURL().openStream()) {
            //check whether exist a valid file in the same directory
            //of the project with the same name as the one stated by the
            //constant fileName.
            if((path.toFile()).isFile())
            {
                System.out.println("File dataset in cache");
            }
            //if no file is present, then a new copy of the dataset is
            //downloaded from the web stream "is" into a file in the path
            //of the project with the name "fileName".
            else{
                Files.copy(in, path);
                System.out.println("File dataset scaricato");
            }
        }
    }
}


