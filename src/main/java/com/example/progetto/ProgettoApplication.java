package com.example.progetto;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProgettoApplication {

    public static void main(String[] args) throws IOException, JSONException {
        SpringApplication.run(ProgettoApplication.class, args);
        /*si estrae dal file JSON fornito il link al fine CSV più corposo*/
        URLextractor estrattore = new URLextractor();
        String link = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-cohesion-policy-historic-eu-payments-regionalised-and-modelled";
        String csv_link = estrattore.readUrlFromJSON(link);
        /*classe di test, si visualizza il fine csv grezzo senza un parsing in classi*/
        DisplayRawCSV lettore = new DisplayRawCSV();
        lettore.reader(csv_link);

    }

}
