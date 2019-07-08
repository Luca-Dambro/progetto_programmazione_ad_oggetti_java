package com.example.progetto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DisplayRawCSV {

    public void reader(String link_csv) throws IOException {

        URL url = new URL(link_csv);
        URLConnection connection = url.openConnection();
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {

            boolean flag=true;
            String[] header = new String[11];
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                for(int i=0; i<row.length; i++) {

                    if (flag == true) {
                        header[i]=(row[i]);
                        System.out.println(header[i]);
                        if (i == (row.length) - 1) {
                            flag = false;
                        }
                        continue;
                    }
                    System.out.print(header[i]);
                    System.out.print("["+row[i]+"]\t");
                }
                System.out.println();
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





