package SimpleCalculator;

import org.junit.jupiter.api.Test;

import static SimpleCalculator.Calculation.calculationRecursion;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CalculationTest {

    @Test
    //Test for subtraction implementation
    public void subtractionTest(){
        String eqn1 = "";
        String eqn2 = "-1";
        String eqn3 = "1-1";
        String eqn4 = "-4-10";
        String eqn5 = "4.4-2.2";
        String eqn6 = "-4.4-2.2";
        String eqn7 = "10-5-3-1";
        assertEquals(0.0,calculationRecursion (eqn1));
        assertEquals(-1,calculationRecursion (eqn2));
        assertEquals(0.0,calculationRecursion (eqn3));
        assertEquals(-14,calculationRecursion (eqn4));
        assertEquals(2.2,calculationRecursion (eqn5));
        assertEquals(-6.6,calculationRecursion (eqn6));
        assertEquals(1,calculationRecursion (eqn7));
    }

    @Test
    //Test for addition implementation
    public void additionTest(){
        String eqn1 = "";
        String eqn2 = "1";
        String eqn3 = "1+1";
        String eqn4 = "4+10";
        String eqn5 = "4.4+2.2";
        String eqn6 = "+4+5+1";
        assertEquals(0.0,calculationRecursion (eqn1));
        assertEquals(1,calculationRecursion (eqn2));
        assertEquals(2,calculationRecursion (eqn3));
        assertEquals(14,calculationRecursion (eqn4));
        assertEquals(6.6,calculationRecursion (eqn5));
        assertEquals(10,calculationRecursion (eqn6));
    }

    @Test
    //Test for multiplication implementation
    public void multiplicationTest(){
        String eqn1 = "";
        String eqn2 = "1";
        String eqn3 = "1*1";
        String eqn4 = "4*10";
        String eqn5 = "4.4*2.2";
        String eqn6 = "4*5*2";
        assertEquals(0.0,calculationRecursion (eqn1));
        assertEquals(1,calculationRecursion (eqn2));
        assertEquals(1,calculationRecursion (eqn3));
        assertEquals(40,calculationRecursion (eqn4));
        assertEquals(9.68,calculationRecursion (eqn5));
        assertEquals(40,calculationRecursion (eqn6));
    }

    @Test
    //Test for division implementation
    public void divisionTest(){
        String eqn1 = "";
        String eqn2 = "1";
        String eqn3 = "1/1";
        String eqn4 = "4/10";
        String eqn5 = "4.4/2.2";
        String eqn6 = "4/5/2";
        String eqn7 = "0/5";
        String eqn8 = "8/0";
        String eqn9 = "24/12/2/0";
        assertEquals(0.0,calculationRecursion (eqn1));
        assertEquals(1,calculationRecursion (eqn2));
        assertEquals(1,calculationRecursion (eqn3));
        assertEquals(0.4,calculationRecursion (eqn4));
        assertEquals(2,calculationRecursion (eqn5));
        assertEquals(0.4,calculationRecursion (eqn6));
        assertEquals(0,calculationRecursion (eqn7));
        assertEquals(0,calculationRecursion (eqn8));
        assertEquals(0,calculationRecursion (eqn9));

    }
    @Test
    //Test for a combination of operators
    //Test also order hierarchy from left to write
    public void mixedTest(){
        //Empty String
        String eqn1 = "";
        assertEquals(0.0,calculationRecursion (eqn1));

        //Single digit
        String eqn2 = "1.0";
        assertEquals(1.0,calculationRecursion (eqn2));

        // + and -
        String eqn3 = "66+33-34";
        String eqn4 = "73.6+27.9-0.25";
        assertEquals(65,calculationRecursion (eqn3));
        assertEquals(101.25,calculationRecursion (eqn4));

        //+ and *
        String eqn5 = "1996+24*3";
        String eqn6 = "7.5+2.9*1.8";
        assertEquals(2068,calculationRecursion (eqn5));
        assertEquals(12.72,calculationRecursion (eqn6));

        //+ and /
        String eqn7 = "346+20/6";
        String eqn8 = "7.5+2.9/1.8";
        assertEquals(349.3333333333,calculationRecursion (eqn7));
        assertEquals(9.1111111111,calculationRecursion (eqn8));

        //- and *
        String eqn9 = "547-65*61";
        String eqn10 = "1.06-5.369*0.46";
        assertEquals(-3418,calculationRecursion (eqn9));
        assertEquals(-1.40974,calculationRecursion (eqn10));

        //- and /
        String eqn11 = "82-46/37";
        String eqn12= "7.348-6.52/0.28";
        assertEquals(80.7567567568,calculationRecursion (eqn11));
        assertEquals(-15.9377142857,calculationRecursion (eqn12));

        // / and *
        String eqn13 = "2457/560*0";
        String eqn14= "0.1069/956.25*62.17";
        assertEquals(0,calculationRecursion (eqn13));
        assertEquals(0.006950034,calculationRecursion (eqn14));

        // + and - and /
        String eqn15 = "2+2-10/0.1";
        String eqn16 = "0+3.59-67.954/10000";
        assertEquals(-96,calculationRecursion (eqn15));
        assertEquals(3.5832046,calculationRecursion (eqn16));

        // + and - and *
        String eqn17 = "90+114-694*85";
        String eqn18= "20.952+0.96-57.99*0.65";
        assertEquals(-58786,calculationRecursion (eqn17));
        assertEquals(-15.7815,calculationRecursion (eqn18));

        //- and / and *
        String eqn19= "346-1094+20/6";
        String eqn20= "7.5-2.9/1.8*100";
        assertEquals(-744.6666666667,calculationRecursion (eqn19));
        assertEquals(-153.61111111,calculationRecursion (eqn20));

        // / and + and *
        String eqn21= "5/5+5*4";
        String eqn22= "5*5+5/4";
        String eqn23= "7.5/2.9+1.8*100";
        assertEquals(21,calculationRecursion (eqn21));
        assertEquals(26.25,calculationRecursion (eqn22));
        assertEquals(182.5862068966,calculationRecursion (eqn23));

        // + and - and * and /
        String eqn24= "5/5-5*5+5";
        String eqn25= "5/5+5*5-5";
        String eqn26= "5/5*5+5-5";
        String eqn27= "5*5/5+5-4";
        assertEquals(-19,calculationRecursion (eqn24));
        assertEquals(21,calculationRecursion (eqn25));
        assertEquals(5,calculationRecursion (eqn26));
        assertEquals(6,calculationRecursion (eqn27));
        String eqn28= "0.1/0.1-0.1*0.1+0.1";
        String eqn29= "0.1/0.1+0.1*0.1-0.1";
        String eqn30= "0.1/0.1*0.1+0.1-0.1";
        String eqn31= "0.1*0.1/0.1+0.1-0.1";
        assertEquals(1.09,calculationRecursion (eqn28));
        assertEquals(0.91,calculationRecursion (eqn29));
        assertEquals(0.1,calculationRecursion (eqn30));
        assertEquals(0.1,calculationRecursion (eqn31));

    }

}