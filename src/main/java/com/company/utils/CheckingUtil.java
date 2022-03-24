package com.company.utils;

import java.util.Locale;

public class CheckingUtil {
    public static boolean isNumber(String number){
        try {
            Double d = Double.parseDouble(number);
        } catch (RuntimeException e){
            return false;
        }
        return true;
    }
}
