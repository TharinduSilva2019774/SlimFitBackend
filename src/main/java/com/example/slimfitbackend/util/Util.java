package com.example.slimfitbackend.util;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class Util {

    public static int yearCountBetweenDates(Date startDate, Date endDate){
        LocalDate localDate1 = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period period = Period.between(localDate1, localDate2);
        return period.getYears();
    }
}
