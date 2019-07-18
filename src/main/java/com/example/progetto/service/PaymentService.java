package com.example.progetto.service;

import java.lang.reflect.Method;
import java.util.*;
import com.example.progetto.model.DataFiltering;
import com.example.progetto.model.Header;
import com.example.progetto.model.DataStatistics;
import com.example.progetto.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.lang.reflect.InvocationTargetException;
import java.lang.Math;


/**
 * This class implements various functions called by the controller, in fact the main
 * features of our program are coded here, the Service of Spring boot contains the business
 * logic of the application.
 */
@Service
public class PaymentService {
    private static Vector<Payment> payments;
    private static Vector<Header> metadata;

    /*getter and setter*/
    public Vector<Payment> getPayments() {
        return payments;
    }

    public static void setPayments(Vector<Payment> payments) {
        PaymentService.payments = payments;
    }

    public Vector<Header> getHeader() {

        return metadata;
    }

    public static void setHeader(Vector<Header> metadata) {

        PaymentService.metadata = metadata;
    }

    /**
     * @param fieldName string representing a field, calculation are performed for every
     *                  object on their specific attributes that match this string
     * @param sample vector of a objects representing each row of the CSV
     * @return object of type DataStatistics that encapsulates results of calculation performed
     * @throws IllegalStateException message for the user that covers incorrect input
     *                               specified for statistics on period
     */
    public DataStatistics statistics(String fieldName, Vector<Payment> sample) throws IllegalStateException {
        Method m = null;
        Vector<Integer> store = new Vector<Integer>();
        /*these are two flags to check whether the field referrend by the user
        on the URL is the programming period start o the programming period end*/
        boolean flagStart=false;
        boolean flagEnd=false;
        Vector<Object> results = new Vector<Object>();;
        int count =0;
        try {
            /*methods to get PeriodStart and PeriodEnd field are encapsulated in the
            method to get the field Period, so it is needed to change the name keeping
            records whether if the users wanted to know the start or the end.*/
            switch (fieldName){
                case "PeriodStart":
                    fieldName=fieldName.replace("PeriodStart","Period");
                    flagStart=true;
                    break;
                case"PeriodEnd":
                    fieldName=fieldName.replace("PeriodEnd","Period");
                    flagEnd=true;
                    break;
            }

            for (Payment item : sample) {
                /*using a reflection to get a method from the object class (representing
                class Payment) named as the type of the field we need to get using the
                String passed as parameter to the method.*/
                m = item.getClass().getMethod("get" + fieldName);
                if(fieldName.equals("Period")){
                    /*invoke the getterMethod on the object item, which represent an element of the
                    the vector passed as a parameter to the method.*/
                    Object paymentValue = m.invoke(item);
                    int Start= Integer.parseInt(getStartEnd(paymentValue)[0]);
                    int End = Integer.parseInt(getStartEnd(paymentValue)[1]);
                    /*if the user requested statistics on the start of programming period
                    we add the value extracted from the Vector passed as a parameter in
                    another vector, same logic applied also for the end of programming period*/
                    if(flagStart)
                        store.add(Start);
                    else if(flagEnd)
                        store.add(End);
                    else{
                        throw new IllegalStateException("Devi specificare PeriodStart o PeriodEnd");
                    }
                } else{
                    Object paymentValue = m.invoke(item);
                    /*we use the absolute value to correct some data in the csv that are wrong,
                     such as negative payments that aren't possible as it doesn't make sense
                    EU providing a negative Fund to region for its structural development.*/
                    int paymentValuefix = Math.abs((int)paymentValue);
                    store.add(paymentValuefix);
                }
            }
            //we store the result of the math operation on object in a Vector of results
            results = calculate(store);

        } catch (IllegalAccessException e) {
            System.out.println("Il metodo " + m + " non ha accesso alla definizione del campo specifico");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "non puoi accedere a questo metodo!:" + m);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            System.out.println("Operazione di casting non consentita");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operazione di casting non consentita");
        } catch (NoSuchMethodException e) {
            System.out.println("Il metodo " + fieldName + " non esiste");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il metodo" + fieldName + "non esiste");
        } catch (SecurityException e) {
            System.out.println("Violazione di sicurezza!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Violazione di sicurezza!");
        }
        return new DataStatistics((double)results.get(0), (int)results.get(1), (int)results.get(2), (double)results.get(3), (long)results.get(4));
    }

    /**
     *
     *     This method implements the general filter logic used also to count the number of
     *     occurrences.
     *
     * @param payments Vector of payments  representing the pool of object to filter
     * @param param    object that structures  the specific filter parameters to use
     * @return a vector of payments where each row respects filter parameters specified
     * @throws IllegalStateException message for the user that covers incorrect input
     *                               specified for statistics on period
     */
    public Vector<Payment> filter(Vector<Payment> payments, DataFiltering param) throws IllegalStateException {

        Method m = null;
        Vector<Payment> out = new Vector<Payment>();
        /*as before, also here we need to understand if the request of the user if
        for the start or the end of the programming period.*/
        boolean flagStart=false,
                flagEnd=false;
        switch(param.getFieldName()){
            case "PeriodStart":
                param.setFieldName("Period");
                flagStart=true;
                break;
            case"PeriodEnd":
                param.setFieldName("Period");
                flagEnd=true;
                break;
        }
        try {
            for (Payment item : payments) {
                /*using a reflection to get a method from the object class (representing
                class Payment) named as the type of the field we need to get using the
                String extracter from the getter of the object of type "DataFiltering"*/
                m = item.getClass().getMethod("get" + param.getFieldName());
                /*invoke the getterMethod on the object item, which represent an element of the
                the vector passed as a parameter to the method.*/
                Object paymentValue = m.invoke(item);
                if(param.getFieldName().equals("Period")){
                    int Start= Integer.parseInt(getStartEnd(paymentValue)[0]);
                    int End = Integer.parseInt(getStartEnd(paymentValue)[1]);
                    if(flagStart){
                        paymentValue=Start;
                        //this method implements the check of the conditional operators
                        if (PaymentService.check(paymentValue, param.getOperator(), param.getValue()))
                            out.add(item);
                    } else if(flagEnd){
                        paymentValue=End;
                        if (PaymentService.check(paymentValue, param.getOperator(), param.getValue()))
                            out.add(item);
                    } else{
                        throw new IllegalStateException("Devi specificare PeriodStart o PeriodEnd");
                    }
                } else{
                    if (PaymentService.check(paymentValue, param.getOperator(), param.getValue()))
                        out.add(item);}
            }
        } catch (IllegalAccessException e) {
            System.out.println("Il metodo " + m + " non ha accesso alla definizione del campo specifico");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "non puoi accedere a questo metodo!:" + m);
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "un argomento inaspettato o non conforme " + param.getValue() + " è stato passato al metodo");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "argomento non conforme");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Corpo del messaggio JSON incorretto");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corpo del messaggio JSON incorretto");
        } catch (NoSuchMethodException e) {
            System.out.println("il metodo get" + param.getFieldName()+ " non può essere trovato");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Il metodo" + param.getFieldName()+ " non può essere trovato");
        } catch (SecurityException e) {
            System.out.println("Violazione di sicurezza!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Violazione di sicurezza!");
        }
        return out;
    }

    /**
     * @param paymentValue Object containing a value extracted with the getter method from a row object.
     *                     This is the first operand for the conditional comparision.
     * @param operator      represent the operator to apply for the conditional comparison
     * @param inputValue    represent the second operand for the conditional comparison specified
     *                      by the user in the JSON body
     * @return boolean stating if the condition is respected or not
     */
    public static boolean check(Object paymentValue, String operator, Object inputValue) {
        /*if the operand used to filter the dataset is a number, is possible
        to access to a series of conditional operators*/
        if (paymentValue instanceof Number) {
            Integer InputValue = Integer.valueOf((String) inputValue);
            Integer PaymentValue =  (Integer)paymentValue;

            switch (operator) {
                case "==":
                    return PaymentValue.equals(InputValue);
                case ">":
                    return PaymentValue > InputValue;
                case ">=":
                    return PaymentValue >= InputValue;
                case "<":
                    return PaymentValue < InputValue;
                case "<=":
                    return PaymentValue <= InputValue;
                default: throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "operatore non conforme, gli operatori ammessi sono: ==, >, >=, <, <=");
            }
        }
        /*if the operand used to filter the dataset is a String, is possible
            to access only to the conditional operator of equivalence.*/
        else if (inputValue instanceof String && paymentValue instanceof String) {
            String inputString = (String) inputValue;
            String paymentString = (String) paymentValue;
            if (operator.equals("=="))
                return inputString.equals(paymentString);
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Operatore non conforme, può essere solo == ");


        }
        return false;
    }

    /**
     *
     * This function is used when we have to deal with the Programming Period.
     *
     * @param paymentValue object containing a value of Period extracted with the getter method
     *                     from a row object.
     *
     * @return an array of string representing the Start and the End of the
     *                     of the Period as separated values.
     */
    private String[] getStartEnd(Object paymentValue){

        String paymentValueFix=(String)paymentValue;
        String[] StartEnd = new String[2];
        StartEnd[0] = paymentValueFix.substring(0,4);
        StartEnd[1] = paymentValueFix.substring(5,9);
        return StartEnd;
    }


    /**
     *
     * This function implements the math operation needed to get the statistics requested
     *
     * @param store   vector representing the poll of values from which get the statistics
     *
     * @return vector of values of different types representing the results of the
     *                operations performed
     */
    private Vector<Object> calculate(Vector<Integer> store) {
        Integer count = store.size();
        Integer min = store.get(0);
        Integer max = store.get(0);
        Double avg = (double) 0;
        Long sum = (long) 0;
        Double std = (double) 0;
        Vector<Object> results = new Vector<Object>();
        for (Integer item : store) {
            avg += item;
            if (item < min)
                min = item;
            if (item > max)
                max = item;
            sum += item;
        }
        avg = avg / count;

        for(Integer item:store){
            std+=(item-avg)*(item-avg);
        }
        std = Math.sqrt(std/(count));

        results.add(avg);
        results.add(min);
        results.add(max);
        results.add(std);
        results.add(sum);
        return results;
    }

}