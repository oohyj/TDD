package chap03;

import java.time.LocalDate;
import java.time.YearMonth;

public class ExpiryDateCalculator {

    public LocalDate calculatorExpiryDate(PayData payData){
        //윤달인 경우랑 13만원 내는 경우 추가
        int addedMonths = payData.getPayAmount() >= 100_000? payData.getPayAmount()/10_000+ 2:payData.getPayAmount()/10_000;
        if(payData.getFirstBillingDate() != null) {
            return expiryDateUsingFirstBillingDate(payData, addedMonths);
        } else {return payData.getBillingDate().plusMonths(addedMonths);}
    }

        private LocalDate expiryDateUsingFirstBillingDate(PayData payData, int addMonths){
            LocalDate candidateExp = payData.getBillingDate().plusMonths(addMonths);
            final int dayOfFirstBilling = payData.getFirstBillingDate().getDayOfMonth();
            if(dayOfFirstBilling != candidateExp.getDayOfMonth()){
                final int dayLenOfCandiMon = YearMonth.from(candidateExp).lengthOfMonth(); //후보 날짜의 달의 마지막 날짜
                if(dayLenOfCandiMon < payData.getFirstBillingDate().getDayOfMonth()){
                    return candidateExp.withDayOfMonth(dayLenOfCandiMon);
                }
                return candidateExp.withDayOfMonth(dayOfFirstBilling);
            } else{
                return candidateExp;
            } }
        }

