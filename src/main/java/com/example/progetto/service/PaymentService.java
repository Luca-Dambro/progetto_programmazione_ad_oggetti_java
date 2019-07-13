package com.example.progetto.service;

import java.lang.reflect.Method;
import java.util.*;
import com.example.progetto.model.Header;
import com.example.progetto.model.NumberStats;
import com.example.progetto.model.Payment;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.lang.reflect.Method;
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
}

    public NumberStats stats(String fieldName, Vector<Payment> sample) {
        Method m = null;
        Vector<Double> store = new Vector<Double>();
        int count;
        double avg = 0, min = 0, max = 0, std = 0, sum = 0;
        try {
            for (Payment item : sample) {

                m = item.getClass().getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                Object pharmacyValue = m.invoke(item);
                double paymentValuedouble = (double) pharmacyValue;
                if (paymentValuedouble != -360)
                    store.add((Double) pharmacyValue);
            }
            count = store.size();
            min = store.get(0);
            max = store.get(0);
            for (Double item : store) {
                avg += item;
                if (item < min)
                    min = item;
                if (item > max)
                    max = item;
                sum += item;
                std += item * item;
            }
            avg = avg / count;
            std = Math.sqrt((count * std - sum * sum) / (count * count));
        } catch (IllegalAccessException e) {
            System.out.println("The method " + m + " does not have access to the definition of the specified field");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Illegal access method:" + m);
        } catch (InvocationTargetException e) {
            System.out.println("InvocationTargetException");
        } catch (ClassCastException e) {
            System.out.println("String cannot be cast to class Double");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "String cannot be cast to class Double");
        }

        catch (NoSuchMethodException e) {
            System.out.println("The method get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1)
                    + " cannot be found");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The method get"
                    + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1) + " cannot be found");
        } catch (SecurityException e) {
            System.out.println("Security violation");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Security violation");
        }
        NumberStats stats = new NumberStats(avg, min, max, std, sum);
        return stats;
    }