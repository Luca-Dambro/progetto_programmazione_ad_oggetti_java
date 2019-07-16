package com.example.progetto.csv;

import com.example.progetto.GetCSVfromJSON;
import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Vector;

public class CsvParser
{
    private String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    private Path path= Paths.get(GetCSVfromJSON.fileName);
    private String currentLine = "";
    private Vector<Payment> payments = new Vector();
    private Vector<Header> metadata = new Vector();
    private boolean flagParse=false;

    //costructor

    public CsvParser(Vector<Payment> payments, Vector<Header> metadata){
        super();
        this.payments=payments;
        this.metadata=metadata;
    }

    /*getter and setter*/

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

    public void executeParse()throws IOException{
        ParserLogic();
    }

    public void displayParse(){
        if(flagParse)
        {
            CsvUtilities tools = new CsvUtilities();
            tools.print(payments,metadata);
        }
        else{throw new IllegalStateException("you need to parse the csv first!");}
    }


    private void ParserLogic() throws IOException {

        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            System.out.println("Inizio lettura del CSV");
            CsvUtilities tools = new CsvUtilities(br);
            String[] header = tools.getFirstLine(cvsSplitBy);
            Payment payment_obj = new Payment();
            Class<?> payment = payment_obj.getClass();
            int i=0;
            for( Field field : payment.getDeclaredFields()){
                try{
                        metadata.add(new Header(field.getName(),header[i],field.getType().toString().replace("class ", "")));
                        i++;
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
                final String[] dataNames = {"String", //0-country
                                            "String", //1-nuts1-id
                                            "String", //2-nuts2-id
                                            "String", //3-nuts2-name
                                            "String", //4-fund
                                            "Integer",//5-year
                                            "Programming_Period", //6-Programming-period
                                            "Integer",//7-Eu_payment_annual
                                            "Integer",//8-Modelled_annual_expenditure
                                            "Integer",//9-Standard_Deviation_of_annual_expenditure
                                            "Integer"};//10-Standard_Error_of_modelled_annual_expenditure


                while ((currentLine = tools.getLine()) != null) {
                    String[] row = tools.splitLine(currentLine, cvsSplitBy);
                    Payment payment_record = new Payment();
                    for(int j=0; j<metadata.size(); j++)//size metadata è 11, di cui period è un'oggetto
                    {
                        Method conversionMethod = Conversions.class.getMethod("conv" + dataNames[j], String.class);
                        Method setterMethod = payment.getMethod("set" + ((metadata.get(j)).getPaymentFieldName()),
                                Class.forName(((metadata.get(j)).getFieldType())));
                        setterMethod.invoke(payment_record,conversionMethod.invoke(Conversions.class, row[j]));

                    }
                    payments.add(payment_record);

                }
            }
            catch (FileNotFoundException e) {//e se qualcuno cancella il file durante il run sulla stufa?
                System.out.println("File Not Found. Check your internet connection.");
            }
            catch (EOFException e){
                flagParse=true;
                System.out.println("Lettura del file terminata correttamente");
            }
            catch (IllegalStateException e)
            {
                System.out.println("ATTENZIONE: non hai letto la prima riga di intestazione del CSV!");
            }
            catch (IOException e){
                System.out.println("ATTENZIONE:: il file csv con contiene alcun valore");
            }
            catch (IllegalAccessException e)
            {
                System.out.println("IllegalAccessException -> " + e);
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                System.out.println("InvocationTargetException -> " + e);
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                System.out.println("NoSuchMethodException -> " + e);
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ClassNotFoundException -> " + e);
                e.printStackTrace();
            }

        }
    }









