package com.example.progetto.csv;

import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.Vector;

class CsvUtilities {

     private BufferedReader br;
     private String line;
     //flag varible is used to check whether the first line of heading of the
    //csv has been already read, to use the method getLine() you need first to
    //read the first line with getFirstLine() to avoid errors.
     private boolean flag;

     //overloaded constructors for the class, first one set the variable br,
    //the second one is the default constructor.

     CsvUtilities(BufferedReader br){

         this.br=br;
     }
     CsvUtilities(){
         this.br=null;
     }

    String getLine() throws IOException
    {
        if(flag){//true if i already read first line
            if ((line = br.readLine()) != null)
            {
                return line;
            }
            throw new EOFException("File reached the end");
        }
        throw new IllegalStateException("You need to read the first line first");
    }

    //this metod returns an array of String, where each string is the name
    //of one column of the csv file; the metod gets in input the character to use
    //as a separator for a row extracted from the csv
    String[] getFirstLine(String cvsSplitBy) throws IOException
    {
        if ((line = br.readLine()) != null)
        {
            String[] row = splitLine(line,cvsSplitBy);
            String[] header = new String[row.length];
            for(int i=0; i<row.length; i++)
            {
                header[i] = (row[i]);
            }
            flag=true;//first line of the csv has been read.
            return header;
        }
            throw new IOException("File endend too early");
        }

    //this method get a string and a separator, returns a vector of
    //strigs, where each one represent a word extracted from the line
    //with method split.
     String[] splitLine(String line, String cvsSplitBy) throws IOException
    {
        String[] row = line.split(cvsSplitBy);
        return row;
    }

    //for the porpouse of this project this method is never used, but we used
    //it for debug porpouses to print (with foreach) two vectors
    //(passed as input of the method) containing the object representation
    //of the dataset ( heading and data).
    void print(Vector<Payment> payments, Vector<Header> metadata) {
         final String title="Showing Annual EU budget payments made by fund to a NUTS-2 region (2013 NUTS classification)";
         System.out.println(title);
        for (Header m : metadata) {
            System.out.println(m);
        }
         for (Payment p : payments) {
             System.out.println(p);
         }
     }
}


