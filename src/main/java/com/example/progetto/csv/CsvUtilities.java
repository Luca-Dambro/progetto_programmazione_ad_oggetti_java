package com.example.progetto.csv;

import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.util.Vector;

/**
 * this class provides some useful methods needed for the parsing process {@link CsvParser}
 */
class CsvUtilities {

    private BufferedReader br;
    private String line;
    //flag varible is used to check whether the first line of heading of the
    //csv has been already read, to use the method getLine() you need first to
    //read the first line with getFirstLine() to avoid errors.
    private boolean flag;


    /**
     * Overloaded constructors for the class, first one set the variable br,
     * the second one is the default constructor.
     *
     * @param br this constructor set the local BufferReader stream with a reference to a stream
     *           passed in input
     */
    CsvUtilities(BufferedReader br){

        this.br=br;
    }

    CsvUtilities(){
        this.br=null;
    }

    /**
     * @return a string representing a generic row of our CSV
     * @throws IOException  this covers an I/O error that occurs when reading or writing
     *                      from the stream (ReadLine)
     */
    String getLine() throws IOException {
        if(flag){//true if i already read first line
            if ((line = br.readLine()) != null) {
                return line;
            }
            throw new EOFException("File reached the end");
        }
        throw new IllegalStateException("You need to read the first line first");
    }

    /**
     *
     * @param cvsSplitBy is used as a separator for a row extracted from the csv.
     * @return an array of String, where each string is the name
     *                   of one column of the csv file.
     * @throws IOException this covers an I/O error that occurs when reading or writing
     *                     from the stream (ReadLine).
     *
     */

    String[] getFirstLine(String cvsSplitBy) throws IOException {
        if ((line = br.readLine()) != null) {
            String[] row = splitLine(line,cvsSplitBy);
            String[] header = new String[row.length];
            for(int i = 0; i<row.length; i++) {
                header[i] = (row[i]);
            }
            flag=true;//first line of the csv has been read.
            return header;
        }
        throw new IOException("File endend too early");
    }

    /**
     *
     * @param line string representing a line to split using a separator.
     * @param cvsSplitBy is used as a separator for the first param of the method.
     * @return an array of String, where each one represent a word extracted from the line
     *         with method split.
     *
     */

    String[] splitLine(String line, String cvsSplitBy) {
        String[] row = line.split(cvsSplitBy);
        return row;
    }

    /**
     *      for the porpouse of this project this method is never used, but we used
     *      it for debug porpouses to print (with foreach) two vectors
     *
     *      @param payments vector containing the object representation of the dataset (data).
     *      @param metadata vector containing the object representation of the dataset (header).
     */

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


