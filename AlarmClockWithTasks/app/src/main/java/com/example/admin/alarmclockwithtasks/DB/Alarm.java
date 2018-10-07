package com.example.admin.alarmclockwithtasks.DB;

import android.util.Log;

import java.time.Clock;
import java.util.Date;

public class Alarm {

    private int id =0;
    private int hourOfDay = 0;
    private int minute = 0;
    private int selectionFlag = 0;
    private int Mn =0;
    private int Tu=0;
    private int We=0;
    private int Th=0;
    private int Fr=0;
    private int Sa=0;
    private int Su=0;
    private int[] daysSelected={0,0,0,0,0,0,0};


    public Alarm(){

    }

    public Alarm(int h, int m) {
        this.hourOfDay =h;
        this.minute = m;
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

    public String getStringMinuteForDB(){
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

    public String getStringSelectedDays(){
        String res="";
        if(daysSelected[0] == 1)
            res+="Mn";
        if(daysSelected[1] == 1)
            res+=" Tu";
        if(daysSelected[2] == 1)
            res+=" We";
        if(daysSelected[3] == 1)
            res+=" Th";
        if(daysSelected[4] == 1)
            res+=" Fr";
        if(daysSelected[5] == 1)
            res+=" Sa";
        if(daysSelected[6] == 1)
            res+=" Su";

        return res;
    }

    public void selectDays(int Monday, int Tuesday, int Wednesday, int Thursday, int Friday	, int Saturday, int Sunday){
        if(Monday == 1) {
            Mn = 1;
            this.daysSelected[0] = 1;
        }else{
            Mn = 0;
            this.daysSelected[0] = 0;
        }

        if(Tuesday == 1) {
            Tu = 1;
            this.daysSelected[1] = 1;
        }else{
            Tu = 0;
            this.daysSelected[1] = 0;
        }

        if(Wednesday == 1) {
            this.We = 1;
            daysSelected[2] = 1;
        }else{
            this.We = 0;
            daysSelected[2] = 0;
        }

        if(Thursday == 1) {
            this.Th = 1;
            daysSelected[3] = 1;
        }else{
            this.Th = 0;
            daysSelected[3] = 0;
        }

        if(Friday == 1) {
            this.Fr = 1;
            daysSelected[4] = 1;
        }else{
            this.Fr = 0;
            daysSelected[4] = 0;
        }

        if(Saturday == 1) {
            this.Sa = 1;
            daysSelected[5] = 1;
        }else{
            this.Sa = 0;
            daysSelected[5] = 0;
        }

        if(Sunday == 1) {
            this.Su = 1;
            daysSelected[6] = 1;
        }else{
            this.Su = 0;
            daysSelected[6] = 0;
        }
    }

    public int getMn() {
        return Mn;
    }

    public void setMn(int mn) {
        daysSelected[0] = mn;
        this.Mn = mn;
    }

    public int getTu() {
        return Tu;
    }

    public void setTu(int tu) {
        daysSelected[1] = tu;
        this.Tu = tu;
    }

    public int getWe() {
        return We;
    }

    public void setWe(int we) {
        daysSelected[2] = we;
        this.We = we;
    }

    public int getTh() {
        return Th;
    }

    public void setTh(int th) {
        daysSelected[3] = th;
        this.Th = th;
    }

    public int getFr() {
        return Fr;
    }

    public void setFr(int fr) {
        daysSelected[4] = fr;
        this.Fr = fr;
    }

    public int getSa() {
        return Sa;
    }

    public void setSa(int sa) {
        daysSelected[5] = sa;
        this.Sa = sa;
    }

    public int getSu() {
        return Su;
    }

    public void setSu(int su) {
        daysSelected[6]= su;
        this.Su = su;
    }

    public int[] getIntArraySelectedDays(){
        return daysSelected;
    }
}