package uz.jl.services.atm;

import uz.jl.enums.atm.ATMType;
import uz.jl.enums.card.CardType;
import uz.jl.models.atm.ATMEntity;
import uz.jl.models.atm.Cassette;
import uz.jl.models.card.Card;
import uz.jl.utils.Print;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import static uz.jl.ui.AtmUI.uzMenuAtm;
import static uz.jl.utils.BaseUtils.getBig;
import static uz.jl.utils.BaseUtils.getBigInt;
import static uz.jl.utils.Color.*;
import static uz.jl.utils.Input.getStr;
import static uz.jl.utils.Print.println;

/**
 * Author : Qozoqboyev Ixtiyor
 * Time : 11.12.2021 18:45
 * Project : ATMoraclechilar
 */
public class AtmOperations {
    public static void messageOn(Card card) {
        if(Objects.isNull(card.getSmsPhoneNumber())){
            String phoneNumber=getStr("Enter phone number : ");
            card.setSmsPhoneNumber(phoneNumber);
            println(BLUE,"Sms service succesfully activated");
            return;
        }
        Print.println(BLUE+"Sms service turned on");
        Print.println(BLUE+"Would you change sms-service (yes/no))");
        String choice=getStr("...");
        if(choice.startsWith("y")){
            String phoneNumber=getStr("Enter phone number : ");
            card.setSmsPhoneNumber(phoneNumber);
            println(BLUE,"Sms service succesfully changed");
        }
    }
    public static void changePin(Card card) {
        String pinCode=getStr("Enter old pin code : ");
        if(card.getPassword().equals(pinCode)){
            pinCode=getStr("Enter new pin code :");
            card.setPassword(pinCode);
            Print.println(BLUE+"Pin code successfully changed");
            return;
        }
        println(RED+"Bad credentials");
    }
    public static void showBalance(Card card,ATMEntity atm) {
        String currencyType="sum";
        if(atm.getType().equals(ATMType.VISA)) currencyType="$";
        println(BLUE,"Your balance : "+card.getBalance()+" "+currencyType);
    }

    public static void withdraw(Card card, ATMEntity atm) {
        String currencyType="sum";
        if(atm.getType().equals(ATMType.VISA)) currencyType="$";
        BigInteger sum=getBigInt(BLUE+"Enter amount of money ("+currencyType+"):");
        if(card.getBalance().compareTo(sum)>=0){
            println("Please get money ");
            BigInteger sum1=atmWithdrawMoney(atm,sum,card,currencyType);
            if(!sum1.equals(BigInteger.ZERO)){
                Print.println("You can withdraw only "+ sum.subtract(sum1));
            }
            card.setBalance(card.getBalance().subtract(sum.subtract(sum1)));
        }else {
            println(RED,"Not enough money on your card");
        }
    }

    public static void exchangeMoney(ATMEntity atm,Card card) {
        Print.println("Enter currency value (1$ / 10$ / 50$ / 100$)");
        BigInteger dollar=getBigInt("...");
        Print.println("Enter count of currency (1$ / 10$ / 50$ / 100$)");
        BigInteger count=getBigInt("...");
        BigInteger sum=(count.multiply(dollar)).multiply(BigInteger.valueOf(10700));
        println("Please get money ");
        BigInteger sum1=atmWithdrawMoney(atm,sum,card,"$");
        if(!sum1.equals(BigInteger.ZERO)){
            Print.println("You can withdraw only "+ sum.subtract(sum1));
        }
    }

    public static void putMoney(Card card,ATMEntity atm) {
      showBalance(card,atm);
      String curval="sum";
      if(atm.getType().equals(ATMType.VISA)){
          curval="$";
      }
          Print.println("Choose currency value : ");
          Print.println("1. "+atm.getCassette1().getCurrencyValue()+" "+curval);
          Print.println("2. "+atm.getCassette2().getCurrencyValue()+" "+curval);
          Print.println("3. "+atm.getCassette3().getCurrencyValue()+" "+curval);
          Print.println("4. "+atm.getCassette4().getCurrencyValue()+" "+curval);
          String choose=getStr("...");
          BigInteger curVal;
          switch(choose){
              case "1"->curVal=atm.getCassette1().getCurrencyValue();
              case "2"->curVal=atm.getCassette2().getCurrencyValue();
              case "3"->curVal=atm.getCassette3().getCurrencyValue();
              case "4"->curVal=atm.getCassette4().getCurrencyValue();
              default -> {Print.println(RED+"Wrong choice");
              return;}
          }
          BigInteger curCount=getBigInt("Enter count :");
          card.setBalance(card.getBalance().add(curVal.multiply(curCount)));
          switch(choose){
              case "1"->{
                  Cassette cassette=atm.getCassette1();
                  cassette.setCurrencyCount(cassette.getCurrencyCount().add(curCount));
                  atm.setCassette1(cassette);
              }
              case "2"->{
                  Cassette cassette=atm.getCassette2();
                  cassette.setCurrencyCount(cassette.getCurrencyCount().add(curCount));
                  atm.setCassette2(cassette);
              }
              case "3"->{
                  Cassette cassette=atm.getCassette3();
                  cassette.setCurrencyCount(cassette.getCurrencyCount().add(curCount));
                  atm.setCassette3(cassette);
              }
              case "4"->{
                  Cassette cassette=atm.getCassette4();
                  cassette.setCurrencyCount(cassette.getCurrencyCount().add(curCount));
                  atm.setCassette4(cassette);
              }
           }
          println("Your balance successfully filled");
      }

    private static BigInteger atmWithdrawMoney(ATMEntity atm, BigInteger sum,Card card,String curreny) {
          sum=workingCassete1(atm,sum,curreny);
          sum=workingCassete2(atm,sum,curreny);
          sum=workingCassete3(atm,sum,curreny);
          sum=workingCassete4(atm,sum,curreny);
          return sum;
    }

    private static BigInteger workingCassete1(ATMEntity atm, BigInteger sum, String curreny){
        Cassette cassette1=atm.getCassette1();
        BigInteger curVal=cassette1.getCurrencyValue();
        BigInteger curCount=cassette1.getCurrencyCount();
        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette1.setCurrencyCount(curCount.subtract(n));
            println(curVal +" "+curreny+"  ->  "+ n +" ta ");
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }

    private static BigInteger workingCassete2(ATMEntity atm, BigInteger sum, String curreny) {
        Cassette cassette2= atm.getCassette2();
        BigInteger curVal=cassette2.getCurrencyValue();
        BigInteger curCount=cassette2.getCurrencyCount();

        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette2.setCurrencyCount(curCount.subtract(n));
            println(curVal +" "+curreny+"  ->  "+ n +" ta ");
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }

    private static BigInteger workingCassete3(ATMEntity atm, BigInteger sum, String curreny) {
        Cassette cassette3= atm.getCassette3();
        BigInteger curVal=cassette3.getCurrencyValue();
        BigInteger curCount=cassette3.getCurrencyCount();
        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette3.setCurrencyCount(curCount.subtract(n));
            println(curVal +" "+curreny+"  ->  "+ n +" ta ");
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }

    private static BigInteger workingCassete4(ATMEntity atm, BigInteger sum, String curreny) {
        Cassette cassette4= atm.getCassette4();
        BigInteger curVal=cassette4.getCurrencyValue();
        BigInteger curCount=cassette4.getCurrencyCount();
        if(sum.compareTo(curVal)<0){
            return sum;
        }
        BigInteger n=sum.divide(curVal);
        if(curCount.compareTo(n)>=0){
            cassette4.setCurrencyCount(curCount.subtract(n));
            println(curVal +" "+curreny+"  ->  "+ n +" ta ");
        }else{
            return sum;
        }
        sum=sum.subtract(n.multiply(curVal));
        return sum;
    }
}
