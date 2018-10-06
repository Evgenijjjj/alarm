package com.example.admin.alarmclockwithtasks.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AlarmsDB_0.1";
    private static final String TABLE_NAME = "Alarms";
    private static final String COL_HOUROFDAY = "hourOfDay";
    private static final String COL_MINUTE = "minute";
    private static final String COL_ID = "id";
    private static final String COL_SELECTION_FLAG = "selectionFlag";

    private static final String COL_MONDEY = "Monday";
    private static final String COL_TUESDAY = "Tuesday";
    private static final String COL_WEDNESDAY = "Wednesday";
    private static final String COL_THURSDAY = "Thursday";
    private static final String COL_FRIDAY = "Friday";
    private static final String COL_SATURDAY = "Saturday";
    private static final String COL_SUNDAY = "Sunday";


    public DBHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_HOUROFDAY + " INTEGER, " +
                COL_MINUTE + " INTEGER, " +
                COL_SELECTION_FLAG + " INTEGER, " +
                COL_MONDEY + " INTEGER, " +
                COL_TUESDAY + " INTEGER, " +
                COL_WEDNESDAY + " INTEGER, " +
                COL_THURSDAY + " INTEGER, " +
                COL_FRIDAY + " INTEGER, " +
                COL_SATURDAY+ " INTEGER, " +
                COL_SUNDAY + " INTEGER);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      //  db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
       // onCreate(db);
    }

    public void insertData(Alarm alarm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_HOUROFDAY,alarm.getHourOfDay());
        cv.put(COL_MINUTE,alarm.getMinute());
        cv.put(COL_SELECTION_FLAG, alarm.getIntSelectionFlag());

        cv.put(COL_MONDEY,alarm.getMn());
        cv.put(COL_TUESDAY,alarm.getTu());
        cv.put(COL_WEDNESDAY,alarm.getWe());
        cv.put(COL_THURSDAY,alarm.getTh());
        cv.put(COL_FRIDAY,alarm.getFr());
        cv.put(COL_SATURDAY,alarm.getSa());
        cv.put(COL_SUNDAY,alarm.getSu());

        long result = db.insert(TABLE_NAME,null,cv);

        if(result == Long.parseLong("-1")){
            Log.d("TESTLV", "notify data set changed");
        }else{

        }

    }

    public List<Alarm> readData(){
        List<Alarm> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "Select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Alarm alarm = new Alarm();

                alarm.setId(Integer.parseInt(cursor.getString(0)));
                alarm.setHourOfDay(Integer.parseInt(cursor.getString(1)));
                alarm.setMinute(Integer.parseInt(cursor.getString(2)));
                alarm.setIntSelectionFlag(Integer.parseInt(cursor.getString(3)));

                alarm.setMn(Integer.parseInt(cursor.getString(4)));
                alarm.setTu(Integer.parseInt(cursor.getString(5)));
                alarm.setWe(Integer.parseInt(cursor.getString(6)));
                alarm.setTh(Integer.parseInt(cursor.getString(7)));
                alarm.setFr(Integer.parseInt(cursor.getString(8)));
                alarm.setSa(Integer.parseInt(cursor.getString(9)));
                alarm.setSu(Integer.parseInt(cursor.getString(10)));

                Log.d("TESTLV", "GETING Mn FROM DB:  " + cursor.getString(4));

                list.add(alarm);
            }while (cursor.moveToNext());
        }

        db.close();

        Collections.sort(list, new DBTimeComparator());

        //rm duplicates
        for(int i =0;i< list.size()-1;i++){
            if(list.get(i).getHourOfDay() == list.get(i+1).getHourOfDay() &&
                    list.get(i).getMinute() == list.get(i+1).getMinute()){
                this.deleteAlarm(list.get(i+1));
                list.remove(i+1);
            }
        }

        return list;
    }

    public void deleteAll(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, null, null);

        db.close();
    }

    public void deleteAlarm(Alarm a){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] arr = {a.getStringHourOfDay(),a.getStringMinute()};
        db.delete(TABLE_NAME, COL_HOUROFDAY + "=? and " + COL_MINUTE + "=?", arr);

        db.close();
    }

    public boolean checkMaxLimit(){
        int max_limit = 6;
        List<Alarm> data = this.readData();

        int counterman = 0;
        for(int x=0;x<data.size();x++){
            if(data.get(x).getSelectionFlag()){
                counterman++;
            }
        }
        if(counterman >= max_limit){
            return true;
        }
        else{
            return false;
        }
    }
}