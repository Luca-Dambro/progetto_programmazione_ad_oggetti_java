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

/**
 * This class provides method and attributes to extract the link of CSV that
 * needs to be parsed from the JSON web link sent by Teachers.
 * The file is downloaded only if it doesn't already exist in memory,
 * thanks to method {@link #download(String) download}
 */
public class GetCSVfromJSON {

    private String link;
    public static final String fileName="Dataset";

    /**
     * @param rd this input is used as a reference to the stream where we need to read characters,
     *           used in {@link #readUrlFromJSON(String) readUrlFromJSON}
     * @return a string containing the whole info got from the stream in input
     * @throws IOException if the read of a char from the stream fails
     */
    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int charcode;
        while ((charcode = rd.read()) != -1) {
            sb.append((char) charcode);
        }
        return sb.toString();
    }

    /**
     * @param url this input is used as a reference to a string representing the URL of the JSON
     *            where to find the CSV link
     *
     * @return
     * @throws IOException if the read of a char from the stream fails from the method
     *                     {@link #readAll(Reader) readAll} and also if opening an internet connection
     *                     reading an inputStream fails.
     *
     * @throws JSONException if the parse files or doesn't yield a {@code JSONObject}
     */
    String readUrlFromJSON(String url) throws IOException, JSONException {

        HttpURLConnection httpcon = (HttpURLConnection) new URL(url).openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
        InputStream is = httpcon.getInputStream();
        //try with resources, no need to close the stream at the end
        try(BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")))) {
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            FindCsvlink(json);
            return getlink();
        }
    }

    /**
     * @param json refers to a JSON Object representing the whole info got from the stream opened
     *             in {@link #readUrlFromJSON(String) readUrlFromJSON}
     * @throws JSONException if the parse files or doesn't yield a {@code JSONObject}
     */
    private void FindCsvlink (JSONObject json) throws JSONException {
        link = (String) json.getJSONObject("result").getJSONArray("resources").getJSONObject(3).get("url");
    }

    public String getlink(){

        return link;
    }

    /**
     * @param url this input is used as a reference to a string representing the URL of the CSV
     *               to download
     * @throws IOException  this covers an I/O error that occurs when reading or writing and
     *                      also a MalformedURLException that occurs if a protocol handler for the URL
     *                      could not be found.
     */
    void download(String url) throws IOException {
        Path path= Paths.get(fileName);
        try (InputStream in = URI.create(url).toURL().openStream()) {
            //check whether exist a valid file in the same directory
            //of the project with the same name as the one stated by the
            //constant fileName.
            if((path.toFile()).isFile()) {
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


