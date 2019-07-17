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

    public DataStatistics statistics(String fieldName, Vector<Payment> sample) throws IllegalStateException
    {
        Method m = null;
        Vector<Integer> store = new Vector<Integer>();
        boolean flagStart=false;
        boolean flagEnd=false;
        int     count=0,
                min = 0,
                max = 0;
        long    sum = 0;
        double  std = 0,
                avg = 0;

        try {
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
                m = item.getClass().getMethod("get" + fieldName);
                if(fieldName.equals("Period")){
                    Object paymentValue = m.invoke(item);
                    int Start= Integer.parseInt(getStartEnd(paymentValue)[0]);
                    int End = Integer.parseInt(getStartEnd(paymentValue)[1]);
                    if(flagStart)
                        store.add(Start);
                    else if(flagEnd)
                        store.add(End);
                    else{
                        throw new IllegalStateException("Devi specificare PeriodStart o PeriodEnd");
                    }
                }
                else{
                    Object paymentValue = m.invoke(item);
                    int paymentValuefix = Math.abs((int)paymentValue);
                    store.add(paymentValuefix);
                }

            }
            count = store.size();
            min = store.get(0);
            max = store.get(0);
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
        }

        catch (IllegalAccessException e) {
            System.out.println("Il metodo " + m + " non ha accesso alla definizione del campo specifico");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "non puoi accedere a questo metodo!:" + m);
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException");
        } catch (ClassCastException e) {
            System.out.println("String cannot be cast to class Double");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "String cannot be cast to class Double");
        } catch (NoSuchMethodException e) {
            System.out.println("Il metodo" + fieldName + " non esiste");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Il metodo" + fieldName + "non esiste");
        } catch (SecurityException e) {
            System.out.println("Security violation");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Security violation");
        }
        return new DataStatistics(avg, min, max, std, sum);
    }

    public Vector<Payment> filter(Vector<Payment> payments, DataFiltering param) throws IllegalStateException {

        Method m = null;
        Vector<Payment> out = new Vector<Payment>();
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
                m = item.getClass().getMethod("get" + param.getFieldName());
                Object paymentValue = m.invoke(item);
                if(param.getFieldName().equals("Period")){
                    int Start= Integer.parseInt(getStartEnd(paymentValue)[0]);
                    int End = Integer.parseInt(getStartEnd(paymentValue)[1]);
                    if(flagStart){
                        paymentValue=Start;
                        if (PaymentService.check(paymentValue, param.getOperator(), param.getValue()))
                            out.add(item);
                    }
                    else if(flagEnd){
                        paymentValue=End;
                        if (PaymentService.check(paymentValue, param.getOperator(), param.getValue()))
                            out.add(item);
                    }
                    else{
                        throw new IllegalStateException("Devi specificare PeriodStart o PeriodEnd");
                    }
                }
                else{
                if (PaymentService.check(paymentValue, param.getOperator(), param.getValue()))
                    out.add(item);}
            }
        }
         catch (IllegalAccessException e) {
            System.out.println("The method " + m + " does not have access to the definition of the specified field");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal access method:" + m);
        } catch (IllegalArgumentException e) {
            System.out.println(
                    "An illegal or inappropriate argument " + param.getValue() + " has been passed to a method");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal argument");
        } catch (InvocationTargetException e) {
            System.out.println();
        } catch (NullPointerException e) {
            System.out.println("Incorrect JSON body");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect JSON body");
        } catch (NoSuchMethodException e) {
            System.out.println("The method get" + param.getFieldName().substring(0, 1).toUpperCase()
                    + param.getFieldName().substring(1) + " cannot be found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The method get" + param.getFieldName().substring(0, 1).toUpperCase()
                            + param.getFieldName().substring(1) + " cannot be found");
        } catch (SecurityException e) {
            System.out.println("Security violation");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Security violation");
        }
        return out;
    }

    public static boolean check(Object paymentValue, String operator, Object inputValue) {

        if (paymentValue instanceof Number) {
            Integer InputValue = Integer.valueOf((String) inputValue);
            Integer PaymentValue =  (Integer)paymentValue;

            switch (operator)
            {
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
                        "Illegal operator it must be ==, >, >=, <, <=");
            }
        }
        else if (inputValue instanceof String && paymentValue instanceof String) {
            String inputString = (String) inputValue;
            String paymentString = (String) paymentValue;
            if (operator.equals("=="))
                return inputString.equals(paymentString);
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal operator, it must be only == ");


        }
        return false;
    }

        private String[] getStartEnd( Object paymentValue){

        String paymentValueFix=(String)paymentValue;
        String[] StartEnd = new String[2];
        StartEnd[0] = paymentValueFix.substring(0,4);
        StartEnd[1] = paymentValueFix.substring(5,9);
        return StartEnd;

        }

}