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


/**
 * This class provides methods and attributes to implement the parsing logic
 * of our csv, that means that we read the csv and we abstract its information in two
 * objects, one representing the first line of heading and the second one representing
 * a generic line of the dataset.{@link Payment}{@link Header}
 */
public class CsvParser
{
    //separator for the split process of a row, uses Regex Expressions.
    private String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
    //uses the static method get for a Path variable to retrieve the path
    //of the dataset with the name stated in the class GetCSVfromJSON.
    private Path path= Paths.get(GetCSVfromJSON.fileName);
    private String currentLine = "";
    //inizialization of two vector of type "Payment" and "Header", wich
    //are two classes: first one represents a row of the csv, second one
    //represents the first row of heading of the csv.
    private Vector<Payment> payments = new Vector();
    private Vector<Header> metadata = new Vector();
    //this variable is set to true when the parse of the csv has been totally
    //and successfully completed.
    private boolean flagParse=false;


    /**
     * @param payments vector of type payment {@link com.example.progetto.model.Payment}
     * @param metadata vector of type metadata {@link com.example.progetto.model.Header}
     */
    public CsvParser(Vector<Payment> payments, Vector<Header> metadata){
        super();
        this.payments=payments;
        this.metadata=metadata;
    }

    //getter and setter

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

    /**
     *
     * For the porpouse of this project this method is never used, but we used
     * it for debug porpouses to print two vectors using the method "print"
     * defined in the class CsvUtilities.java. If the file has not been parsed yet
     * and this method is called from main method, it launches an illegalstateExeption.
     *
     */
    public void displayParse(){
        if(flagParse) {
            CsvUtilities tools = new CsvUtilities();
            tools.print(payments,metadata);
        } else{throw new IllegalStateException("you need to parse the csv first!");}
    }


    /**
     * this method implements the parsing algorithm
     * @throws IOException this covers an I/O error that occurs when reading or writing from
     *                      a BufferedReader stream and FileReader stream
     */
    private void ParserLogic() throws IOException {
        //try with resources
        try (BufferedReader br = new BufferedReader(new FileReader(path.toFile()))) {
            System.out.println("Inizio lettura del CSV");
            //gets a new object of the class tools to use his method
            CsvUtilities tools = new CsvUtilities(br);
            //gets first row of the csv, every of a column is saved in an array
            String[] header = tools.getFirstLine(cvsSplitBy);
            //gets a new object of the class Payment wich represents in an object a
            //row of the csv
            Payment payment_obj = new Payment();
            //uses a reflection method getClass( of Object) to get on object class
            //of Payment representing the class of which you need to get a method or
            //an annotation.
            Class<?> payment = payment_obj.getClass();
            int i=0;
            //cycle for every field ( a member of the class, even the private ones) declared in the class
            //associated at the class variable defined before.
            for( Field field : payment.getDeclaredFields()){
                try{
                    //add at the metadata vector a new object of type Header (class
                    //defined in the model package) using his constructor and passing
                    //the name of the field,the name of the column in the csv,
                    // type of the field.
                    metadata.add(new Header(field.getName(),header[i],field.getType().toString().replace("class ", "")));
                    i++;
                } catch(ArrayIndexOutOfBoundsException e){
                    e.printStackTrace();
                }
            }
            //vector of string that represents types of our data as they are
            //represented as object on the class Payment
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
                for(int j=0; j<metadata.size(); j++) {
                    //using a reflection to get a method from the object class (representing
                    //class Conversions) named as the type of the field we need to set using the
                    //vector of string "dataNames"
                    Method conversionMethod = Conversions.class.getMethod("conv" + dataNames[j], String.class);
                    //get a method of the payment class object named as the attribute "PaymentFieldName"
                    //of the object at index j of the Vector metadata.
                    Method setterMethod = payment.getMethod("set" + ((metadata.get(j)).getPaymentFieldName()),
                            Class.forName(((metadata.get(j)).getFieldType())));
                    //invoke the setterMethod on the object "payment_record" with a string specified
                    //as a parameter for the method called. This string is the output of the ivoke
                    //of the method "conversionMethod" called on the object representing the class Conversions.
                    setterMethod.invoke(payment_record,conversionMethod.invoke(Conversions.class, row[j]));

                }
                payments.add(payment_record);

            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found. Check your internet connection.");
        } catch (EOFException e){
            //if we get End Of File Exception we've read the csv successfully.
            flagParse=true;
            System.out.println("Lettura del file terminata correttamente");
        } catch (IllegalStateException e) {
            System.out.println("ATTENZIONE: non hai letto la prima riga di intestazione del CSV!");
        } catch (IOException e){
            System.out.println("ATTENZIONE:: il file csv con contiene alcun valore");
        } catch (IllegalAccessException e) {
            System.out.println("IllegalAccessException -> " + e);
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException -> " + e);
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            System.out.println("NoSuchMethodException -> " + e);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException -> " + e);
            e.printStackTrace();
        }

    }
}









