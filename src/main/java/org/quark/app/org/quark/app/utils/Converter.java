package org.quark.app.org.quark.app.utils;


import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Converter {
    public static String decimafloatToDecimal(float floatNumber) {
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(floatNumber);
    }

}
