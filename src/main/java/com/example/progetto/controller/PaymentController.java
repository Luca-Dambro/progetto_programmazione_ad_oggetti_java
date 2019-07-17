package com.example.progetto.controller;

import com.example.progetto.model.DataFiltering;
import com.example.progetto.model.Header;
import com.example.progetto.model.DataStatistics;
import com.example.progetto.model.Payment;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.progetto.service.PaymentService;
import org.springframework.web.server.ResponseStatusException;

import java.util.Vector;


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


    @GetMapping("/statistics/{fieldName}")
    public DataStatistics statistics(@PathVariable String fieldName) {
        return paymentService.statistics(fieldName, paymentService.getPayments());
    }

    @GetMapping(value = "/count/{fieldName}", produces = "application/json")
    public String count(@PathVariable String fieldName, @RequestParam(value = "value") String value) {
        Vector<Payment> payments = paymentService.getPayments();
        DataFiltering filterParam = new DataFiltering(fieldName, "==", value);
       return "{\"count\" : " + paymentService.filter(payments, filterParam).size()+"}";

    }

    @PostMapping(value = "/filter")
    public Vector<Payment> filter(@RequestBody String param) {
        JSONObject jsonObject = null;
        boolean correctRequest = false;
        try {
            jsonObject = (JSONObject) JSONValue.parseWithException(param);
        } catch (ParseException e) {
            System.out.println("Error while parsing JSON" + jsonObject);
        }
        Vector<Payment> temp = paymentService.getPayments();
        DataFiltering filterParam = new DataFiltering(null, null, null);
        // single filter case
        filterParam.readFields(jsonObject);
        if (filterParam.getFieldName() != null && filterParam.getOperator() != null && filterParam.getValue() != null) {
            correctRequest = true;
            temp = paymentService.filter(temp, filterParam);
        }

        // multiple filter cases: or - and

        JSONArray jsonArray = (JSONArray) jsonObject.get("$or");
        if (jsonArray instanceof JSONArray) {
            correctRequest = true;
            // tempOr is used to store the partial filters
            Vector<Payment> tempOr = new Vector<Payment>();
            // the temp vector is emptied
            temp = new Vector<Payment>();

            for (Object obj : jsonArray) {
                filterParam.readFields(obj);
                tempOr = paymentService.filter(paymentService.getPayments(), filterParam);
                for (Payment item : tempOr) {
                    if (!temp.contains(item))
                        temp.add(item);
                }
            }
        }

        jsonArray = (JSONArray) jsonObject.get("$and");
        if (jsonArray instanceof JSONArray) {
            correctRequest = true;
            for (Object obj : jsonArray) {
                filterParam.readFields(obj);
                // iteration on .filter
                temp = paymentService.filter(temp, filterParam);
            }
        }

        if (!correctRequest) {
            System.out.println("Incorrect JSON body");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect JSON body");
        }

        return temp;
    }
}