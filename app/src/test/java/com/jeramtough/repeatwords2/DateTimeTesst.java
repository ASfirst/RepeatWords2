package com.jeramtough.repeatwords2;

import com.jeramtough.repeatwords2.util.DateTimeUtil;

import org.junit.Test;

public class DateTimeTesst {

    @Test
    public void test() {
        System.out.println(DateTimeUtil.getDateTime());
    }

    @Test
    public void test1() {
        int a = 3;
        for (int i=0;i<100;i++){
            a = 3-(3 % a);
            System.out.println(a);
            String b="1%1=0" +
                    "3%2=1" +
                    "5%3=2" +
                    "0" +
                    "1" +
                    "2" +
                    "";


        }
    }
}
