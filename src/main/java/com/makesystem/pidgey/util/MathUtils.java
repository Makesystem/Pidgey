/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.makesystem.pidgey.util;

import java.math.BigDecimal;

/**
 *
 * @author Richeli.vargas
 */
public class MathUtils {

    public static void main(String[] args) {

        final String exponential = "9.18E+09";
        final String no_exponential = "123";
        final String error_1 = "9.a18E+09";
        final String error_2 = "012.549.770-90";

        System.out.println("exponential: " + isExponential(exponential));
        System.out.println("no_exponential: " + isExponential(no_exponential));
        System.out.println("error_1: " + isExponential(error_1));
        System.out.println("error_2: " + isExponential(error_2));

    }

    protected static String preventComma(final String value){
        if(value.contains(",")){
            return value.replace(".", "").replace(",", ".");
        } else {
            return value;
        }
    }
    
    public static boolean isExponential(final String exponential) {
        if (exponential == null || exponential.isEmpty()) {
            return false;            
        } else {
            return preventComma(exponential).replaceAll("[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)", "").isEmpty();
        }
    }

    public static long fromExponential(final String exponential) {
        final BigDecimal bd = new BigDecimal(preventComma(exponential));
        return bd.longValue();
    }

    public static double percent100(final double value, final double total) {
        return percent(value, total) * 100;
    }

    public static double percent(final double value, final double total) {
        if (value == 0) {
            return 0.0;
        } else if (total == 0 && value > 0) {
            return 1;
        } else {
            return divide(value, total);
        }
    }

    public static double divide(final double dividend, final double divisor) {
        if (dividend == 0 || divisor == 0) {
            return 0;
        }

        return dividend / divisor;
    }

}
