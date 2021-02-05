package com;

import java.util.Calendar;
import java.util.Scanner;

public class PaymentCalculator {
    //Value taken from https://www.nav.no/no/nav-og-samfunn/kontakt-nav/utbetalinger/grunnbelopet-i-folketrygden
    final private static int GYEAR = 101351;

    //Values taken from email
    final private static int WORKYEARS = 3;
    final private static float WORKDAYS = 260.0F;

    //Scanner for reading salaries
    Scanner input = new Scanner(System.in);

    //Variables
    int dayPaymentResult;
    int[] salaryArray;

    public int calculate(){
        salaryArray = getSalaries();
        dayPaymentResult = calculatePayment(salaryArray[0], salaryArray[1], true);
        return dayPaymentResult;
    }

    private int[] getSalaries(){


        int[] sArray = new int[2]; //0 is salary last year, 1 is sum of salary last 3 years
        int year = Calendar.getInstance().get(Calendar.YEAR); //Gets current year
        int tempYear;
        for (int i = 0; i < WORKYEARS; i++){
            tempYear = year + i - WORKYEARS;
            sArray[0] = getSalaryInt(tempYear);
            sArray[1] += sArray[0];
        }
        System.out.println("Lønnssum for de siste " + WORKYEARS +" årene: " + sArray[1] + " kr.");
        return sArray;
    }

    private int getSalaryInt(int tYear){
        boolean isAnInt = false;
        String lastSalaryString;
        int temp = 0;

        do{
            System.out.println("\nDin lønn for " + tYear + ":");
            lastSalaryString = input.nextLine();
            if(isInteger(lastSalaryString)) {
                isAnInt = true;
                temp = Integer.parseInt(lastSalaryString);
            }
        }while(!isAnInt);

        return temp;
    }

    public boolean isInteger( String inp ) {
        try {
            Integer.parseInt( inp );
            return true;
        }
        catch( NumberFormatException | NullPointerException e ) {
            System.out.println("Vennligst bare skriv inn tall (og realistiske tall)."); //Numbers over 2^31-1 wont work
            return false;
        }
    }

    public int calculatePayment(int lSalary, int sSalary, boolean verbose){
        int dayPayment = 0, dayPaymentBasis;

        if(lSalary > 0){                                            //Had a salary last year
            if(sSalary > 3*GYEAR || lSalary > 1.5*GYEAR) {          //Had enough salary last

                if(lSalary < sSalary / WORKYEARS){                  //Decide on what basis the payments should be
                    dayPaymentBasis = sSalary / WORKYEARS;          //calculated on
                }
                else{
                    dayPaymentBasis = lSalary;
                }

                if(dayPaymentBasis > 6 * GYEAR) dayPaymentBasis = 6*GYEAR;  //If the yearly payment is too high
                dayPayment = (int) Math.ceil(dayPaymentBasis / WORKDAYS);   //Rounds up

                if(verbose) System.out.println("Du har rett på dagpenger! Din dagsats er: " + dayPayment + " kr.");

            }
            else{
                if(verbose) System.out.println("Ettersom du har tjent nok, så har du ikke rett på dagpenger." +
                        " Du trenger enten:\n - Minimum lønn forrige kalenderår er: "  + 1.5*GYEAR +
                        " kr\n - Minimum lønn de siste " + WORKYEARS + " kalenderåene er: " + 3*GYEAR + " kr");
            }
        }
        else{
            if(verbose)
                System.out.println("Ettersom du ikke har noe lønn i forrige kalenderår, " +
                        "har du ikke rett på dagpenger.");
        }
        return dayPayment;
    }
}
