package com.example.progetto.controller;

/*import com.example.progetto.model.FilterParameters;*/
import com.example.progetto.model.FilterParameters;
import com.example.progetto.model.Header;
import com.example.progetto.model.DataStatistics;
import com.example.progetto.model.Payment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.progetto.service.PaymentService;

import java.util.Vector;
//TODO implementare i  filtri!
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/showdataset")
    public Vector<Payment> retrievePayments() {
        return paymentService.getPayments();
    }

    @GetMapping("/showmetadata")
    public Vector<Header> retrieveHeader() {
        return paymentService.getHeader();
    }

    @GetMapping("/stats/{fieldName}")
    public DataStatistics stats(@PathVariable String fieldName) {
        return paymentService.stats(fieldName, paymentService.getPayments());
    }

    @GetMapping("/count/{fieldName}")
    public String count(@PathVariable String fieldName, @RequestParam(value = "value") String value) {
        Vector<Payment> payments = paymentService.getPayments();
        FilterParameters filterParam = new FilterParameters(fieldName, "==", value);
        String o="count : " + paymentService.filter(payments, filterParam).size();
        return o;
    }
}