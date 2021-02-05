package com;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentCalculatorTest {

    private PaymentCalculator calculator;

    @BeforeEach
    void setUp() {
        this.calculator = new PaymentCalculator();
    }

    @Test
    void isIntegerTest() {
        String[] arr = { "1", "28734", "2147483648", "abcd" };
        boolean[] expected = { true, true, false, false };
        boolean[] actual = new boolean[4];

        //temp
        /*for (String s : arr) {
            //System.out.println(s + (calculator.isInteger(s) ? " is an integer!\n" : " is not an integer!\n"));
        }*/

        for( int i = 0; i < arr.length; i++) actual[i] = calculator.isInteger(arr[i]);

        assertArrayEquals(expected, actual);

        //for( int i = 0; i < arr.length / 2; i++) assertEquals(calculator.isInteger(arr[i]), expected[i]);
        //for( int i = 3; i > arr.length / 2 - 1; i--) assertNotEquals(calculator.isInteger(arr[i]), expected[i]);
    }

    @Test
    void calculatePayment() {
        // even (and 0)is  salary last year, odd is sum of salary last 3 years
        int[] salary = { 500000, 1350000, 850000, 3650000, 50000, 120000, 0, 3650000};
        int[] expected = {1924, 2339, 0, 0 };
        int[] actual = new int[4];


        //Temp
        /*for( int i = 0; i < salary.length; i += 2){
            actual = this.calculator.calculatePayment(salary[i],  salary[i+1], false);
            //System.out.println("I: " + i + "\tEXPECTED: " + expected[4-(salary.length - i) / 2] + "\tACTUAL: " + actual);
            assertEquals(expected[4-(salary.length - i) / 2], actual);
        }*/

        for( int i = 0; i < salary.length; i += 2)
            actual[4-(salary.length - i) / 2] = this.calculator.calculatePayment(salary[i], salary[i+1], false);

        assertArrayEquals(expected, actual);
    }
}