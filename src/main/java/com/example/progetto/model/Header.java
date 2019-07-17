package com.example.progetto.model;

/**
 *
 *
 *  This class is used to abstract the header of the csv (first line), saving
 *  for each column header the name of the field ( that is the name of the attribute of
 *  a row represented by class Payment), the type of the field and the original name
 *  of the heading in the csv.
 *
 *
 */

public class Header {
    private String PaymentFieldName;
    private String CsvFieldName;
    private String FieldType;

    /**
     * @param paymentFieldName
     * @param csvFieldName
     * @param fieldType
     */
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

    /**
     * @return
     */
    @Override
    public String toString() {
        return "Header{" +
                "PaymentFieldName='" + PaymentFieldName + '\'' +
                ", CsvFieldName='" + CsvFieldName + '\'' +
                ", FieldType='" + FieldType + '\'' +
                '}';
    }
}