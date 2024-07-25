import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.math.BigInteger;




/**
 * 
 * @author Kasper Lau (Kaspiper)
 * 
 * 
 * Calculates math in terms of RationalNumber, which represents numbers and indivisible terms as fractions,
 * bypassing any arithmetic with floating point and MAX_INT limitations. Use for exact math. 
 * 
 * Main method evaluates the math expressions given by user input, 
 * while serving as a test fixture for the correctness, speed, and usability of the RationalNumber class
 *
 *
 */
public class RationalNumberParseAndCalc{



    /**
     * Evaluates the math expression given by {@code expr} and returns 
     * the equivalency in Fraction form as a String.
     *
     * @param expr
     *            the String expression to be evaluated
     *
     * @return the equivalent String
     */
    public static String evaluateToString(String expr){
        
        StringBuilder Tokens = new StringBuilder(expr.replaceAll("\\s|[a-zA-Z]",""));
        if(Tokens.charAt(Tokens.length() - 1) != '='){
            Tokens.append('=');
        }
        return parseExpression(Tokens).toString();
    }




    /**
     * Evaluates the math expression given by {@code expr} and returns 
     * the equivalency as a RationalNumber.
     *
     * @param expr
     *            the String expression to be evaluated
     *
     * @return the equivalent RationalNumber
     */
    public static RationalNumber evaluateToRational(String expr){
        
        StringBuilder Tokens = new StringBuilder(expr.replaceAll("\\s|[a-zA-Z]",""));
        if(Tokens.charAt(Tokens.length() - 1) != '='){
            Tokens.append('=');
        }
        return parseExpression(Tokens);
    }





    /**
     * Evaluates the math expression given by {@code Tokens} and returns 
     * the equivalency as a RationalNumber.
     *
     * @param Tokens
     *            the StringBuilder containing the expression to be evaluated
     *
     * @return the equivalent RationalNumber
     */
    public static RationalNumber parseExpression(StringBuilder Tokens){
        
        char nextToken = Tokens.charAt(0);
        RationalNumber value = new RationalNumber(new BigInteger("0"));
        do{
            // System.out.println("@parseExpression: " + Tokens.toString());
            // System.out.println("@parseExpressionToken: " + nextToken);
            if(Character.isDigit(nextToken)){
                value.add(parseRational(Tokens));
                nextToken = Tokens.charAt(0);
            }else{
                switch(nextToken){
                    case '-':
                        Tokens.deleteCharAt(0);
                        value.subtract(parseRational(Tokens));
                        nextToken = Tokens.charAt(0);
                        break;

                    case '+':
                        Tokens.deleteCharAt(0);
                        value.add(parseRational(Tokens));
                        nextToken = Tokens.charAt(0);
                        break;

                    case '(':
                        value.add(parseRational(Tokens));
                        nextToken = Tokens.charAt(0);
                        break;
                }
            }
        }while(nextToken != '=' && nextToken != ')');
        // System.out.println("@parseExpressionRETURN: " + value.toString());
        // System.out.println("@parseExpressionToken: " + nextToken);
        return value;
    
    }

    /**
     * Evaluates the consecutive multiplicative math terms given by {@code Tokens} and returns 
     * the equivalency as a RationalNumber.
     *
     * @param Tokens
     *            the StringBuilder containing the terms to be evaluated
     *
     * @return the equivalent RationalNumber
     */
    private static RationalNumber parseRational(StringBuilder Tokens){
        

        char nextToken = Tokens.charAt(0);
        RationalNumber value = new RationalNumber(new BigInteger("1"));


        do{
            // System.out.println("@parseRational: " + Tokens.toString());
            // System.out.println("@parseRationalToken: " + nextToken);
            if(Character.isDigit(nextToken)){
                value.multiply(new RationalNumber(parseInteger(Tokens)));
                nextToken = Tokens.charAt(0);
            }else{
                switch(nextToken){
                    case '(':
                        value.multiply(parseInteger(Tokens));
                        nextToken = Tokens.charAt(0);
                        break;
                    case '*':

                        Tokens.deleteCharAt(0);
                        value.multiply(parseInteger(Tokens));
                        nextToken = Tokens.charAt(0);

                        break;
                    case '/':

                        Tokens.deleteCharAt(0);
                        value.divide(parseInteger(Tokens));
                        nextToken = Tokens.charAt(0);

                        break;
                }
            }
        }while(nextToken != '+' && nextToken != '-' && nextToken != '=' && nextToken != ')');

        // System.out.println("@parseRationalRETURN: " + value.toString());
        // System.out.println("@parseRationalToken: " + nextToken);
        return value;
    }


    /**
     * Evaluates the math term or parentheses given by {@code Tokens} and returns 
     * the equivalency as a RationalNumber.
     *
     * @param Tokens
     *            the StringBuilder containing the term or parentheses to be evaluated
     *
     * @return the equivalent RationalNumber
     */
    private static RationalNumber parseInteger(StringBuilder Tokens){

        char nextToken = Tokens.charAt(0);
        
        RationalNumber value;
        if(nextToken == '('){
            Tokens.deleteCharAt(0);
            value = parseExpression(Tokens);
            Tokens.deleteCharAt(0);
            nextToken = Tokens.charAt(0);
        }else{
            StringBuilder integerRep = new StringBuilder("");
            while (Character.isDigit(nextToken)) {
                integerRep.append(nextToken);
                Tokens.deleteCharAt(0);
                nextToken = Tokens.charAt(0);
            }
            value = new RationalNumber( new BigInteger(integerRep.toString()));
        }

        // System.out.println("@parseIntegerRETURN: " + value.toString());
        return value;



    }


    /**
     * 
     * @author Kasper Lau (Kaspiper)
     * 
     * 
     * 
     * 
     * Main method evaluates the math expressions given by user input, 
     * while serving as a test fixture for the correctness, speed, and usability of the RationalNumber class
     *
     *
     */
    public static void main(String[] args){
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
     
        System.out.println("GRAMMAR RULES FOR PARSING RationalNumber.java EQUATIONS DEFINED AS SUCH:");
        System.out.println();
        System.out.println("[EXPRESSION]    =>      [RATIONAL TERM] { + [RATIONAL TERM] | - [RATIONAL TERM] | = } | ");
        System.out.println("                      - [RATIONAL TERM] { + [RATIONAL TERM] | - [RATIONAL TERM] | = } ");
        System.out.println("[RATIONAL TERM] =>      [INTEGER] { * [INTEGER] | / [INTEGER] | [INTEGER] }");
        System.out.println("[INTEGER]       =>      [DIGIT]{ [DIGIT] } | ([EXPRESSION]) ");
        System.out.println("[DIGIT]         =>       1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9  ");
        System.out.println();
        System.out.println("Program follows P.E.M.D.A.S. with L -> R associativity");
        System.out.println();
        System.out.println("Valid Input:            (11 + 42 - 5 ) / ( 11 - 4 )");
        System.out.println("                        (17-3)(14-6)-22=");
        System.out.println();
        System.out.println("Invalid Input:          ( 3 / 4 ) * - ( 5 + 1)      {Consecutive operations like * - is unconventional syntax}");
        System.out.println("                        ( 3 + 7 )) =                {Mismatched parentheses are unconventional/illegal in math}");
        System.out.println("                        450 - - 8 =                 {Double negative is unconventional syntax; replace '--' with '+'}");

        
        try {
            do {
                System.out.println();
                
                try {

                    System.out.println(evaluateToString(in.readLine()));

                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println();
                System.out.print("TEST MORE (Y/N)?: ");


            } while (in.readLine().equals("Y"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        



    

}
