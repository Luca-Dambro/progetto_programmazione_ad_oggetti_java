package com.example.progetto.service;

import java.util.*;

import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import org.springframework.stereotype.Service;


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