package SimpleCalculator;
/*
 * This class lays out the implementation for calculation of the SIMPLE CALCULATOR.
 * It uses recursion as basis to provide answers to a mathematical equation. As of now it supports simple operation (+,-,/,*)
 * The algorithm at its core splits a string into parts using operators as deliminator
 * The default hierarchy is as follows division -> multiplication -> addition -> subtraction
 * Depending of the order of the input division and multiplication can be done first, same with addition and subtraction
 * Once all operators are filtered all that is left is the number, which is converted to a double and returned.
 * From here the recursion magic occurs and the final out is the correct answer of type double.
 * */

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculation {

    /**
     * @param equation is a string from with valid input as defined in Calculator.
     * @return double which is the answer to the equation
     * */

    protected static double calculationRecursion(String equation){

        //Base
        if (equation.isEmpty()) {return 0;}

        //recursion

//---------- addition and subtraction ------------------------------------------------------------------
        //Sets order of addition and subtraction operation if both are in eqn from left to right
        if(equation.contains("-") && equation.contains("+")){
            //If addition is before subtraction, execute subtraction last (Remember we are in recursion -> first executed is last)
            if(equation.indexOf("+") < equation.indexOf("-")){
                return subtractionImplementation(equation);
            }
            else{ // If subtraction is before addition, addition is executed last (In recursion first executed is last)
                return additionImplementation(equation);
            }
        }
        else if (equation.contains("-")){   //Subtraction Implementation
            return subtractionImplementation(equation);
        }
        else if (equation.contains("+")){   //Addition Implementation
            return additionImplementation(equation);
        }

//---------- multiplication and division ------------------------------------------------------------------


        //If eqn contains both multiplication and division
        //Sets order of multiplication and division operation from left to right
        if(equation.contains("*") && equation.contains("/")){
            //If multiplication is before division, execute division last (Remember we are in recursion -> first executed is last)
            if(equation.indexOf("*") < equation.indexOf("/")){
                return divisionImplementation(equation);
            }
            else{// If division is before multiplication, multiplication is executed last (In recursion first executed is last)
                return multiplicationImplementation(equation);
            }
        }
        else if (equation.contains("*")){   //Multiplication Implementation
            return multiplicationImplementation(equation);
        }
        else if (equation.contains("/")){   //Division Implementation
            return divisionImplementation(equation);
        }

        //Parses a string into a double
        return parser(equation);
    }

    //Method implementation for division
    private static double divisionImplementation(String equation) {
        //Splits equation into parts using sign as deliminator
        String [] subequation = equation.split("/");
        double divisionResult =1;
        int count = 0;
        // For 1st number, [number/1] to set it to divisionResult
        if (count==0){
            divisionResult = calculationRecursion(subequation[count]) / divisionResult;
            count++;
        }
        //Loops through all parts and divides them starting from the first number from the left.
        while (count!=subequation.length){
            try {
                divisionResult = divisionResult / calculationRecursion(subequation[count]);
                count++;
            }catch (NumberFormatException e){
                //Secret code
                return -0.9999989999;
            }
        }
        //try catch for a single number which does not require splitting.
        try {
            return BigDecimal.valueOf(divisionResult).setScale(10, RoundingMode.HALF_UP).doubleValue();
        }catch (NumberFormatException e){
            //Secret code
            return -0.9999989999;
        }
    }

    //Method implementation for multiplication
    private static double multiplicationImplementation(String equation) {
        //Splits equation into parts using sign as deliminator
        String [] subequation = equation.split("\\*");
        double multiplicationResult =1;
        int count = 0;
        //Loops through all parts and multiplies them
        while (count!=subequation.length){
            multiplicationResult = multiplicationResult * calculationRecursion(subequation[count]);
            count++;
        }
        return BigDecimal.valueOf(multiplicationResult).setScale(10, RoundingMode.HALF_UP).doubleValue();
    }

    //Method implementation for addition
    private static double additionImplementation(String equation) {
        //Splits equation into parts using sign as deliminator
        String [] subequation = equation.split("\\+");
        double additionResult =0.0;
        int count = 0;
        //Loops through all parts and adds them
        while (count!=subequation.length){
            additionResult = additionResult + calculationRecursion(subequation[count]);
            count++;
        }
        return BigDecimal.valueOf(additionResult).setScale(10, RoundingMode.HALF_UP).doubleValue();

    }

    //Method implementation dor subtraction
    private static double subtractionImplementation(String equation) {
        //Splits equation into parts using sign as deliminator
        String [] subequation = equation.split("-");
        double subtractionResult =0.0;
        int count = 0;
        // If 1st number is a positive, [number-0] to set it +ve
        if (!subequation[0].isEmpty() && count==0){
            subtractionResult = calculationRecursion(subequation[count]) - subtractionResult;
            count++;
        }
        // If 1st number is a negative, [0-number] to set it -ve
        while (count!=subequation.length){
            subtractionResult = subtractionResult - calculationRecursion(subequation[count]);
            count++;
        }
        return BigDecimal.valueOf(subtractionResult).setScale(10, RoundingMode.HALF_UP).doubleValue();
    }

    //Parses the string into a double
    private static double parser(String equation){
        return Double.parseDouble(equation);
    }

}
