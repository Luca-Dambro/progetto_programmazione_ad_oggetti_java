package com.example.progetto.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/*import com.example.progetto.model.FilterParameters;*/
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

    public DataStatistics stats(String fieldName, Vector<Payment> sample) {
        Method m = null;
        Vector<Integer> store = new Vector<Integer>();
        int count;
        boolean flag=false;
        int     min = 0,
                max = 0;
        long    sum = 0;
        double  std=0,
                avg = 0;
        String fieldNameFinal=new String();
        try {
            if(fieldName.equals("PeriodStart"))
            {
                fieldNameFinal="Period";
                flag=true;
            }
            else if(fieldName.equals("PeriodEnd"))
            {
                fieldNameFinal="Period";
            }
            else{
                fieldNameFinal=fieldName;
            }

            for (Payment item : sample) {
                m = item.getClass().getMethod("get" + fieldNameFinal);
                /*todo:verificare se ci vanno numeri negativi con il prof (Modelledannualexpenditure)*/
                if(fieldNameFinal.equals("Period")){
                    Object paymentValue = m.invoke(item);
                    String paymentValuefix = new String();
                    paymentValuefix = ((String)paymentValue);
                    String start,end= new String();
                    start=paymentValuefix.substring(0,4);
                    end=paymentValuefix.substring(4,8);
                    int Start= Integer.parseInt(start);
                    int End = Integer.parseInt(end);
                    if(flag)
                        store.add(Start);
                    else
                        store.add(End);

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
            /*todo:casting da long ad int perche con l'int vado in overflow sulla colonna standard deviation*/
            std = Math.sqrt(std/(count));
        }

        catch (IllegalAccessException e) {
            System.out.println("The method " + m + " does not have access to the definition of the specified field");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal access method:" + m);
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException");
        } catch (ClassCastException e) {
            System.out.println("String cannot be cast to class Double");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "String cannot be cast to class Double");
        } catch (NoSuchMethodException e) {
            System.out.println("The method get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
                    + " cannot be found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The method get"
                    + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + " cannot be found");
        } catch (SecurityException e) {
            System.out.println("Security violation");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Security violation");
        }
        return new DataStatistics(avg, min, max, std, sum);
    }


}