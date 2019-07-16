package com.example.progetto.model;

import org.json.simple.JSONObject;

//this class provides some astraction for filter parameters such as the name of
//the field to filter, the conditional operator to apply and the value used
//as parameter for filtering. This is used for POST request and also for count filter.

public class DataFiltering {

    private String fieldName;
    private String operator;
    private Object value;

    /*costructor*/
    public DataFiltering(String fieldName, String operator, Object value) {
        super();
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    /*getter and setter for variables*/

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }


    //this method is used by the filter logic (in PaymentService) to
    //read the json from post request, updating the local parameters
    //fetching the ones from the json body.
    public void readFields(Object Json_post) {
        if (Json_post instanceof JSONObject) {
            JSONObject jsonObj = (JSONObject) Json_post;
            fieldName = (String) jsonObj.get("fieldName");
            operator = (String) jsonObj.get("operator");
            value = jsonObj.get("value");
        }
    }

}
