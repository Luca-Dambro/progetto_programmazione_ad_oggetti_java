package com.example.progetto;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;

public class FetchCsvData {

    private String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

    String getLine(BufferedReader br) throws IOException
    {
        String line;
        if ((line = br.readLine()) != null)
        {
            return line;
        }
        throw new EOFException("file reached the end");
    }

    String[] getFirstLine(BufferedReader br) throws IOException {

        FetchCsvData GetDataRow = new FetchCsvData();
        String line;
        if ((line = GetDataRow.getLine(br)) != null)
        {
            String[] row = splitLine(line);
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

     String[] splitLine(String line) throws IOException
    {
        String[] row = line.split(cvsSplitBy);
        return row;
    }
    }


