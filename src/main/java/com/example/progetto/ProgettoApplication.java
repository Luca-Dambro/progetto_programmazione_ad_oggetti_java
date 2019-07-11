package com.example.progetto;

import com.example.progetto.csv.CsvActions;
import org.json.JSONException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ProgettoApplication {

    public static void main(String[] args) throws IOException, JSONException {

        SpringApplication.run(ProgettoApplication.class, args);


        String link = "http://data.europa.eu/euodp/data/api/3/action/package_show?id=eu-cohesion-policy-historic-eu-payments-regionalised-and-modelled";

        /*si estrae dal file JSON fornito il link al file CSV più corposo*/
        try {
            GetCSVfromJSON extractor = new GetCSVfromJSON();
            CsvActions action = new CsvActions();
            String csv_link = extractor.readUrlFromJSON(link);
            extractor.download(csv_link);
            action.parser();
            System.out.println("termine programma");
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

    }

}
