package com.example.admin.alarmclockwithtasks.DB;

import java.util.Comparator;

public class DBTimeComparator implements Comparator<Alarm> {
    @Override
    public int compare(Alarm o1, Alarm o2) {
        if(o1.getHourOfDay() == o2.getHourOfDay()){
            return o1.getMinute() - o2.getMinute();
        }
        else if(o1.getHourOfDay() > o2.getHourOfDay())
            return 1;
        else
            return -1;
    }
}
