package com.example.progetto;

import com.example.progetto.csv.CsvParser;
import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.progetto.service.PaymentService;


import java.io.IOException;
import java.util.Vector;

@SpringBootApplication

public class ProgettoApplication {

        public static void main(String[] args) throws IOException, JSONException {

        Vector<Payment> payments = new Vector<Payment>();
        Vector<Header> metadata = new Vector<Header>();
        final String link = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-cohesion-policy-historic-eu-payments-regionalised-and-modelled";

        try {
            /*get the CSV url from given JSON link*/
            GetCSVfromJSON extractor = new GetCSVfromJSON();
            String csv_link = extractor.readUrlFromJSON(link);

            /*if not already done, download the CSV file*/
            extractor.download(csv_link);

            CsvParser action = new CsvParser(payments,metadata);
            action.executeParse();
            PaymentService.setPayments(payments);
            PaymentService.setHeader(metadata);

             }
        catch (IllegalStateException e){
            System.out.println("IllegalStateException -> " + e);
        }

        catch (IOException e){
                System.out.println("IOException -> " + e);
            }
        catch (JSONException e) {
            System.out.println("JSONException -> " + e);
        }
        catch (Exception e){
            e.printStackTrace();
        }
            SpringApplication.run(ProgettoApplication.class, args);
            System.out.println("Avvio programma completato. Accedere ai dati e filtri tramite localhost sulla porta 8080");
    }

}
