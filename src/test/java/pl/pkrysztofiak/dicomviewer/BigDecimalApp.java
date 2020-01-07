package pl.pkrysztofiak.dicomviewer;

import java.math.BigDecimal;

public class BigDecimalApp {

    public static void main(String[] args) {
        
        double d = 0.1 + 0.2;
        System.out.println(d);
        
        BigDecimal number1 = new BigDecimal(String.valueOf(0.1));
        BigDecimal number2 = new BigDecimal(String.valueOf(0.2));
        
        BigDecimal sum = number1.add(number2);
        System.out.println(sum);
    }
}