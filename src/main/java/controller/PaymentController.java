package controller;

import model.Header;
import model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import service.PaymentService;

import java.util.Vector;

@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @GetMapping("/alldataset")
    public Vector<Payment> retrievePayments() {
        return paymentService.getPayments();
    }


    @GetMapping("/showmetadata")
    public Vector<Header> retrieveHeader() {
        return paymentService.getHeader();
    }
}