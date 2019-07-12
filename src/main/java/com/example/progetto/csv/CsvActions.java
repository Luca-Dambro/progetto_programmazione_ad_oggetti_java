package com.example.progetto.csv;

import com.example.progetto.GetCSVfromJSON;
import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class CsvActions
{
    private String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    private Path path= Paths.get(GetCSVfromJSON.fileName);
    private String currentLine = "";
    private Vector<Payment> payments = new Vector();
    private Vector<Header> metadata = new Vector();

    public CsvActions(Vector<Payment> payments, Vector<Header> metadata){
        this.payments=payments;
        this.metadata=metadata;
    }

    public Vector<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Vector<Payment> payments) {
        this.payments = payments;
    }

    public Vector<Header> getMetadata() {
        return metadata;
    }

    public void setMetadata(Vector<Header> metadata) {
        this.metadata = metadata;
    }

    public void parser() throws IOException{
        CsvParsing parser= new CsvParsing();
        parser.DisplayParse();
    }

    class CsvParsing {
         void DisplayParse() throws IOException{
            ParserLogic();
            CsvUtilities tools = new CsvUtilities();
            tools.print(payments,metadata);
        }

         void ParserLogic() throws IOException {
             boolean flag=false;
            try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
                CsvUtilities tools = new CsvUtilities(br);
                String[] header = tools.getFirstLine(cvsSplitBy);
                Payment payment_obj = new Payment();
                Class<?> payment = payment_obj.getClass();
                int i=0;
                for( Field field : payment.getDeclaredFields()){
                    try{
                       if(field.getName().toString().equals("Country_codes")){
                           header[i]=header[i]+","+header[i+1]+","+header[i+2];
                           flag=true;
                       }
                        metadata.add(new Header(field.getName(),header[i],field.getType().toString()));
                        if(flag==true)
                        {i=i+3;flag=false;}
                        else
                            i++;
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
                while ((currentLine = tools.getLine()) != null) {
                    String[] row = tools.splitLine(currentLine, cvsSplitBy);
                    payment_obj.setCountry(row[0]);
                    payment_obj.setCountry_codes(row[1], row[2], row[3]);
                    payment_obj.setFund(row[4]);
                    payment_obj.setYear(row[5]);
                    payment_obj.setPeriod(row[6]);
                    payment_obj.setEU_Payment_annual(row[7]);
                    payment_obj.setModelled_annual_expenditure(row[8]);
                    payment_obj.setStandard_Deviation_of_annual_expenditure(row[9]);
                    payment_obj.setStandard_Error_of_modelled_annual_expenditure(row[10]);
                    payments.add(payment_obj);
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


}







