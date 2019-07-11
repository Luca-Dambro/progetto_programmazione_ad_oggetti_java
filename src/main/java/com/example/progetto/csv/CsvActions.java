package com.example.progetto.csv;
import com.example.progetto.GetCSVfromJSON;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CsvActions
{
    private String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    private Path path= Paths.get(GetCSVfromJSON.fileName);
    private String currentLine = "";
    private BufferedReader br=null;

    class Read {
         void ReaderLogic() throws IOException
        {
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile())))
        {
            CsvUtilities tools=new CsvUtilities(br);
            String[] header=tools.getFirstLine(cvsSplitBy);

            while ((currentLine = tools.getLine()) != null) {

                String[] row = tools.splitLine(currentLine,cvsSplitBy);
                tools.print(row,header);

            }
        }
        catch (FileNotFoundException e) {//e se qualcuno cancella il file durante il run sulla stufa?
            System.out.println("File Not Found. Check your internet connection.");
        }
        catch (EOFException e){
            System.out.println("lettura del file terminata correttamente");
        }
        catch (IllegalStateException e)
        {
            System.out.println("Non hai letto la prima riga di intestazione del CSV!");
        }
        catch (IOException e){
            System.out.println("il file csv con contiene alcun valore");
        }
    }
}
    class CsvParsing {
         ArrayList<CsvRow> rows = new ArrayList<>();

         void DisplayParse() throws IOException{
            ParserLogic();
            CsvUtilities tools = new CsvUtilities();
            tools.print(rows);
        }

         void ParserLogic() throws IOException {
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                CsvUtilities tools = new CsvUtilities(br);
                String[] header = tools.getFirstLine(cvsSplitBy);
                CsvRow objrow = new CsvRow();

                while ((currentLine = tools.getLine()) != null) {
                    String[] row = tools.splitLine(currentLine, cvsSplitBy);
                    objrow.setCountry(row[0]);
                    objrow.setCountry_codes(row[1], row[2], row[3]);
                    objrow.setFund(row[4]);
                    objrow.setYear(row[5]);
                    objrow.setPeriod(row[6]);
                    objrow.setEU_Payment_annual(row[7]);
                    objrow.setModelled_annual_expenditure(row[8]);
                    objrow.setStandard_Deviation_of_annual_expenditure(row[9]);
                    objrow.setStandard_Error_of_modelled_annual_expenditure(row[10]);
                    rows.add(objrow);
                }

            }
            catch (FileNotFoundException e) {//e se qualcuno cancella il file durante il run sulla stufa?
                System.out.println("File Not Found. Check your internet connection.");
            }
            catch (EOFException e){
                System.out.println("lettura del file terminata correttamente");
            }
            catch (IllegalStateException e)
            {
                System.out.println("Non hai letto la prima riga di intestazione del CSV!");
            }
            catch (IOException e){
                System.out.println("il file csv con contiene alcun valore");
            }
        }
    }

   public void reader() throws IOException{
        Read r = new Read();
        r.ReaderLogic();
    }

    public void parser() throws IOException{
        CsvParsing parser= new CsvParsing();
        parser.DisplayParse();
    }
}







