package com.example.progetto;

import com.example.progetto.csv.CsvParser;
import model.Header;
import model.Payment;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import service.PaymentService;


import java.io.IOException;
import java.util.Vector;


@SpringBootApplication


public class ProgettoApplication {

        public static void main(String[] args) throws IOException, JSONException {

        SpringApplication.run(ProgettoApplication.class, args);

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
            action.displayParse();
            System.out.println("termine programma");
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

            PaymentService.setPayments(payments);
            PaymentService.setHeader(metadata);

    }

}
