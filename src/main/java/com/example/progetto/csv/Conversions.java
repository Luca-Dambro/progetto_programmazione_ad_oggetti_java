package com.example.progetto.csv;

import com.example.progetto.model.Programming_Period;

/**
 * This class provides some useful method to convert Strings to other types
 * that are the ones used in the object representation of the row of our csv
 * {@link com.example.progetto.model.Payment}
 */

class Conversions {
    //conversion from string to integer.
      public static Integer convInteger(String s) {
         try {
             return Integer.parseInt(s);
             }
         catch (NumberFormatException e) {
             return 0;
         }
      }

    /**
     * @param s string that represents data of a column fetched from a row of the CSV
     * @return no actual conversion happends here, but empty string are replaced with string "0"
     */

      public static String convString(String s) {
         if (s.equals(""))
             s = "0";
         return s;
      }


    /**
     *
     * @param s     string that represents data of a column fetched from a row of the CSV
     * @return get an array of string representing the two characteristics
     *              attributes of a ProgrammingPeriod object: Start and End.
     */
     public static Programming_Period convProgramming_Period(String s) {
         String[] stringvalues = new String[2];
         stringvalues = s.split("-");
         Programming_Period p = new Programming_Period();
         p.setProgrammingPeriodStart(stringvalues[0]);
         p.setProgrammingPeriodEnd(stringvalues[1]);
         return p;
     }
 }
