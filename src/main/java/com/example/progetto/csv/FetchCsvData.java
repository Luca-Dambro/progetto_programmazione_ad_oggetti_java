package com.example.progetto.csv;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

 class FetchCsvData {


    String getLine(BufferedReader br) throws IOException
    {
        String line;
        if ((line = br.readLine()) != null)
        {
            return line;
        }
        throw new EOFException("file reached the end");
    }

    String[] getFirstLine(BufferedReader br,String cvsSpliBy) throws IOException {

        FetchCsvData GetDataRow = new FetchCsvData();
        String line;
        if ((line = GetDataRow.getLine(br)) != null)
        {
            String[] row = splitLine(line,cvsSpliBy);
            String[] header = new String[row.length];
            for(int i=0; i<row.length; i++)
            {
                header[i] = (row[i]);
                System.out.println(header[i]);
            }
            return header;
        }
            throw new IOException("file endend too early");
        }

     String[] splitLine(String line, String cvsSplitBy) throws IOException
    {
        String[] row = line.split(cvsSplitBy);
        return row;
    }
    }


