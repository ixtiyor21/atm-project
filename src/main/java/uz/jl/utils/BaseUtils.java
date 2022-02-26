package uz.jl.utils;

import org.apache.commons.lang3.RandomStringUtils;
import uz.jl.enums.card.CardType;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Elmurodov Javohir, Mon 12:13 PM. 12/6/2021
 */
public class BaseUtils {
    public static String genId() {
        return System.nanoTime() + RandomStringUtils.random(20, true, true);
    }

    public static String createPan(CardType type) {
        String pan ="";
        if(type.equals(CardType.UZCARD))
            pan+=CardType.UZCARD.getCode();
        else if(type.equals(CardType.HUMO))
            pan+=CardType.HUMO.getCode();
        else if(type.equals(CardType.UNION_PAY))
            pan+=CardType.UNION_PAY.getCode();
        else if(type.equals(CardType.VISA))
            pan+=CardType.VISA.getCode();
        else if(type.equals(CardType.COBAGE))
            pan+=CardType.COBAGE.getCode();
        else if(type.equals(CardType.MASTER_CARD))
            pan+=CardType.MASTER_CARD.getCode();
        return pan+RandomStringUtils.random(12, false, true);
    }
    public static Double toDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return null;
        }
    }

    public static Integer toInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return null;
        }
    }

    public static Scanner SCANNER_BIG_DEC=new Scanner(System.in);
    public static BigDecimal getBig() {
        return getBig("");
    }

    public static BigDecimal getBig(String str) {
        Print.print(str);
        return SCANNER_BIG_DEC.nextBigDecimal();
    }
    public static Scanner SCANNER_BIG_INT=new Scanner(System.in);
    public static BigInteger getBigInt() {
        return getBigInt("");
    }

    public static BigInteger getBigInt(String str) {
        Print.print(str);
        return SCANNER_BIG_INT.nextBigInteger();
    }
}
