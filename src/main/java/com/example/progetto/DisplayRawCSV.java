package com.example.progetto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DisplayRawCSV {

    public void reader(String link_csv) throws IOException {

        //codice legacy per la visualizzazione grezza del csv
        /*URL url = new URL(link_csv);
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
        }*/

        URL url = new URL(link_csv);
        URLConnection connection = url.openConnection();
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] row = line.split(cvsSplitBy);

                System.out.print("Country [code= " + row[0] + " , year=" + row[5] + "]\t");
                System.out.print("EU territorial ID [code 1= " + row[1] + " , code 2=" + row[2] +", name="+row[3] +"]\t");
                System.out.print("Fund type and period [fund= " + row[4] + " , period=" + row[6] +"]\t");
                System.out.print("Data [");
                for (int j=7; j<11; j++)
                {
                    System.out.print(row[j]+"\t");
                };
                System.out.print("]\n");

                break;
            }



        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Check your internet connection.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }




    }

}





