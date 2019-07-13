package com.example.progetto.controller;

import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.progetto.service.PaymentService;

import java.util.Vector;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @GetMapping("/wholedataset")
    public Vector<Payment> retrievePayments() {
        return paymentService.getPayments();
    }


    @GetMapping("/showmetadata")
    public Vector<Header> retrieveHeader() {
        return paymentService.getHeader();
    }
}