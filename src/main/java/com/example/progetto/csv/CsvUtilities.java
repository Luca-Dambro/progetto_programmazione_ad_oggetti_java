package com.example.progetto.csv;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;

class CsvUtilities {

     private BufferedReader br;
     private String line;
     private boolean flag;

     CsvUtilities(BufferedReader br){
         this.br=br;
     }
     CsvUtilities(){
         this.br=null;
     }


    String getLine() throws IOException
    {
        if(flag){
            if ((line = br.readLine()) != null)
            {
                return line;
            }
            throw new EOFException("file reached the end");
        }
        throw new IllegalStateException("you need to read the first line first");
    }

    String[] getFirstLine(String cvsSplitBy) throws IOException {

        if ((line = br.readLine()) != null)
        {
            String[] row = splitLine(line,cvsSplitBy);
            String[] header = new String[row.length];
            for(int i=0; i<row.length; i++)
            {
                header[i] = (row[i]);
            }
            flag=true;
            return header;
        }
            throw new IOException("file endend too early");
        }

    void DiscardFirstLine() throws IOException {

        if ((line = br.readLine()) != null)
        {
            System.out.println("riga di intestazione scartata correttamente");
            flag=true;
        }
        throw new IOException("file endend too early");
    }


     String[] splitLine(String line, String cvsSplitBy) throws IOException
    {
        String[] row = line.split(cvsSplitBy);
        return row;
    }

      void print(String[] rows,String[] headers){

          String title="Showing Annual EU budget payments made by fund to a NUTS-2 region (2013 NUTS classification)";
         for(int i=0; i<rows.length; i++)
         {
             System.out.print("--"+headers[i]);
             System.out.print("["+rows[i]+"]--\t");
         }
         System.out.println();
     }

     public void print(ArrayList<CsvRow> array) {

         String title="Showing Annual EU budget payments made by fund to a NUTS-2 region (2013 NUTS classification)";
         for (CsvRow c : array) {

             System.out.println(c);

         }
     }

    }


