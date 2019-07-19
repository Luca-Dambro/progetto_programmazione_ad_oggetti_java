package com.example.progetto.controller;

import com.example.progetto.model.DataFiltering;
import com.example.progetto.model.DataStatistics;
import com.example.progetto.model.Header;
import com.example.progetto.model.Payment;
import com.example.progetto.service.PaymentService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Vector;


/**
 * This class implement the controller. This is the component
 * invoked directly by the clients that deal with the main business logic and that
 * can exist even without the presence of Model.
 * <p>
 * Following this, all the routes are structured following the GET or POST requests
 * depending on the use cases.
 * <p>
 * Also note that each request (GET or POST that is) recalls the {@link PaymentService},
 * within which the processing methods are defined acoording to the calls
 * of the {@link PaymentController}.
 */
@RestController
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    //this route is used for show all data-set
    @GetMapping("/showdataset")
    public Vector<Payment> retrievePayments() {
        return paymentService.getPayments();
    }

    //this route is used for show all metadata
    @GetMapping("/showmetadata")
    public Vector<Header> retrieveHeader() {
        return paymentService.getHeader();
    }

    //this route is used to make statistics on data
    @GetMapping("/statistics/{fieldName}")
    public DataStatistics statistics(@PathVariable String fieldName) {
        return paymentService.statistics(fieldName, paymentService.getPayments());
    }

    //this route is used to count all occurrences of certain elements
    @GetMapping(value = "/count/{fieldName}", produces = "application/json")
    public String count(@PathVariable String fieldName, @RequestParam(value = "value") String value) {
        Vector<Payment> payments = paymentService.getPayments();
        DataFiltering filterParam = new DataFiltering(fieldName, "==", value);
        return "{\"count\" : " + paymentService.filter(payments, filterParam).size() + "}";

    }

    //this route is used to filter the data
    @PostMapping(value = "/filter")
    public Vector<Payment> filter(@RequestBody String param) {
        JSONObject jsonObject = null;
        boolean validRequest = false;
        try {
            //represents the param from the json body in a structured json object
            jsonObject = (JSONObject) JSONValue.parseWithException(param);
        } catch (ParseException e) {
            System.out.println("Errore nel parsing JSON" + jsonObject);
        }
        Vector<Payment> app = paymentService.getPayments();
        DataFiltering filterParam = new DataFiltering(null, null, null);
        //if the filter requested in the json body does not include any logical operator
        filterParam.readFields(jsonObject);
        if (filterParam.getFieldName() != null && filterParam.getOperator() != null && filterParam.getValue() != null) {
            validRequest = true;
            app = paymentService.filter(app, filterParam);
        }


        //if the filter requested in the json body does include OR, AND logical operator
        JSONArray jsonArray = (JSONArray) jsonObject.get("$or");
        if (jsonArray instanceof JSONArray) {
            validRequest = true;
            // appOr is used to store the partial data filtered
            Vector<Payment> appOr = new Vector<Payment>();
            app = new Vector<Payment>();

            for (Object obj : jsonArray) {
                filterParam.readFields(obj);
                appOr = paymentService.filter(paymentService.getPayments(), filterParam);
                for (Payment item : appOr) {
                    if (!app.contains(item))
                        app.add(item);
                }
            }
        }

        jsonArray = (JSONArray) jsonObject.get("$and");
        if (jsonArray instanceof JSONArray) {
            validRequest = true;
            for (Object obj : jsonArray) {
                filterParam.readFields(obj);
                // iteration on filter method, for each new cycle we use as vector to
                //filter the output vector from the precedent cycle
                app = paymentService.filter(app, filterParam);
            }
        }

        if (!validRequest) {
            System.out.println("Incorrect JSON body");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Corpo del messaggio JSON errato");
        }

        return app;
    }

    /*this used is used to get statistics on filtered data,
    specifing filter paramenters in the JSON body*/
    @PostMapping(value = "/filter/statistics/{fieldName}")
    public DataStatistics filterStats(@RequestBody String param, @PathVariable String fieldName) {
        Vector<Payment> sample = new Vector<Payment>();
        sample = filter(param);

        return paymentService.statistics(fieldName, sample);
    }

}