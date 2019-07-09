package com.example.progetto;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class DisplayRawCSV {

    public void reader(String link_csv) throws IOException {

        URL url = new URL(link_csv);
        URLConnection connection = url.openConnection();
        String line = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            FetchCsvData GetDataRow=new FetchCsvData();
            String[] header=GetDataRow.getFirstLine(br);
            while ((line = GetDataRow.getLine(br)) != null) {
                String[] row = GetDataRow.splitLine(line);
                for(int i=0; i<row.length; i++) {

                    System.out.print("--"+header[i]);
                    System.out.print("["+row[i]+"]--\t");
                }
                System.out.println();
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found. Check your internet connection.");
        }
        catch (EOFException e){
            System.out.println("lettura del file terminata correttamente");
        }
    }
}






