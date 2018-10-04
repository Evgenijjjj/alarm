package com.example.admin.alarmclockwithtasks.DB;

import java.time.Clock;
import java.util.Date;

public class Alarm {

    private int id =0;
    private int hourOfDay = 0;
    private int minute = 0;
    private int selectionFlag = 0;


    public Alarm(){

    }

    public Alarm(int h, int m) {
        this.hourOfDay =h;
        this.minute = m;
        //this.SELECTED_FLAG = 1;
    }

    public void setId(int id){
        this.id =id;
    }

    public void setHourOfDay(int h){
        this.hourOfDay =h;
    }

    public void setMinute(int m){
        this.minute = m;
    }

    public int getId() {
        return id;
    }

    public int getHourOfDay() {
        return hourOfDay;
    }

    public int getMinute(){
        return minute;
    }

    public String getStringHourOfDay() {
        return String.valueOf(hourOfDay);
    }

    public String getStringMinute(){
        if(minute < 10){
            return "0" + String.valueOf(minute);
        }
        return String.valueOf(minute);
    }

    public boolean getSelectionFlag() {
        if(this.selectionFlag == 0)
            return false;
        else
            return true;
    }

    public void setSelectionFlag(boolean flag) {
        if (flag)
            this.selectionFlag = 1;
        else
            this.selectionFlag = 0;
    }

    public void setIntSelectionFlag(int flag){
        this.selectionFlag = flag;
    }
    public int getIntSelectionFlag(){
        return this.selectionFlag;
    }
}
