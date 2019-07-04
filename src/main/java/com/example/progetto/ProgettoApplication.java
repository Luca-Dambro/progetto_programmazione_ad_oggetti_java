package com.example.progetto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProgettoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgettoApplication.class, args);

        ToJSON estrattore = new ToJSON ();
        String link = " http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-cohesion-policy-historic-eu-payments-regionalised-and-modelled";

        /*estrattore.readJsonFromUrl(link);*/
    }

}
