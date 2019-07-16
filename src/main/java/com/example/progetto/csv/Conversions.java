package com.example.progetto.csv;

import com.example.progetto.model.Programming_Period;

class Conversions {
      public static Integer convInteger(String s) {
         try {
             return Integer.parseInt(s);
             }
         catch (NumberFormatException e) {
             return 0;
         }
     }



      public static String convString(String s) {
         if (s.equals(""))
             s = "0";
         return s;
     }

     public static Programming_Period convProgramming_Period(String s) {
         String[] stringvalues = new String[2];
         stringvalues = s.split("-");
         Programming_Period p = new Programming_Period();
         p.setProgrammingPeriodStart(stringvalues[0]);
         p.setProgrammingPeriodEnd(stringvalues[1]);

         return p;
     }
 }
