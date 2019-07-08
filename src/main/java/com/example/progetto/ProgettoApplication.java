package com.example.progetto;

import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProgettoApplication {

    public static void main(String[] args) throws IOException, JSONException {
        SpringApplication.run(ProgettoApplication.class, args);

        URLextractor estrattore = new URLextractor();
        String link = " http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-cohesion-policy-historic-eu-payments-regionalised-and-modelled";

        System.out.println(estrattore.readUrlFromJSON(link));

        CsvReader lettore = new CsvReader();
        lettore.reader ();

    }

}
