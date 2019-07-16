package com.example.progetto.model;

/**
 * This class is used to get the information of every element;
 * this information contain the...
 */

public class Header {
    private String PaymentFieldName;
    private String CsvFieldName;
    private String FieldType;

    public Header(String paymentFieldName, String csvFieldName, String fieldType) {
        super();
        this.PaymentFieldName = paymentFieldName;
        this.CsvFieldName = csvFieldName;
        this.FieldType = fieldType;
    }

    public String getPaymentFieldName() {
        return PaymentFieldName;
    }

    public void setPaymentFieldName(String paymentFieldName) {
        PaymentFieldName = paymentFieldName;
    }

    public String getCsvFieldName() {
        return CsvFieldName;
    }

    public void setCsvFieldName(String csvFieldName) {
        CsvFieldName = csvFieldName;
    }

    public String getFieldType() {
        return FieldType;
    }

    public void setFieldType(String fieldType) {
        FieldType = fieldType;
    }

    @Override
    public String toString() {
        return "Header{" +
                "PaymentFieldName='" + PaymentFieldName + '\'' +
                ", CsvFieldName='" + CsvFieldName + '\'' +
                ", FieldType='" + FieldType + '\'' +
                '}';
    }
}