package service;

import java.util.*;

import model.Header;
import model.Payment;
import org.springframework.stereotype.Component;


@Component
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