package com.example.progetto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class CsvReader {

    public void reader() throws IOException {

        URL url = null;
        url = new URL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-cohesion-policy-historic-eu-payments-regionalised-and-modelled");
        URLConnection connection = url.openConnection();

        InputStreamReader input = new InputStreamReader(connection.getInputStream());
        BufferedReader buffer = null;
        String line = "";
        String csvSplitBy = ",";

        buffer = new BufferedReader(input);
        while ((line = buffer.readLine()) != null) {
            String[] x = line.split(csvSplitBy);
            System.out.println(line);

        }
        if (buffer != null) {
            buffer.close();
        }

    }

}





