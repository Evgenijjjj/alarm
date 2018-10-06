package com.example.admin.alarmclockwithtasks;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ChangeAlarm extends AppCompatActivity implements TimePicker.OnTimeChangedListener, View.OnClickListener {

        private ImageButton addNewAlarm;
        private ImageButton addBtnBack;
        private int HOUR, MINUTE, db_hour, db_minute;
        private DBHelper db;
        private TimePicker timePicker;
        private int[] CHECKED_DAYS_ARRAY = {0,0,0,0,0,0,0};


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.custom_time_picker_dialog);

                addBtnBack = (ImageButton) findViewById(R.id.addBtnBackT);
                addNewAlarm = (ImageButton) findViewById(R.id.addNewAlarmBtnT);
                timePicker = (TimePicker)findViewById(R.id.timePicker);
                addBtnBack.setVisibility(View.INVISIBLE);

                addNewAlarm.setOnClickListener(this);
                timePicker.setOnTimeChangedListener(this);
                timePicker.setIs24HourView(DateFormat.is24HourFormat(this));

                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR_OF_DAY);
                int m = c.get(Calendar.MINUTE);


                db_hour = this.getIntent().getIntExtra("hourOfDay", h);
                db_minute = this.getIntent().getIntExtra("minute", m);
                //int[] def_deays_selected_arr = {0,0,0,0,0,0,0};
                CHECKED_DAYS_ARRAY = this.getIntent().getIntArrayExtra("selectedDaysIntArray");

                timePicker.setCurrentHour(db_hour);
                timePicker.setCurrentMinute(db_minute);
        }

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                HOUR = hourOfDay;
                MINUTE = minute;
        }

       @Override
        public void onClick(View v) {

               if (v.getId() == R.id.addNewAlarmBtnT) {
                   db = new DBHelper(this);
                   db.deleteAlarm(new Alarm(db_hour, db_minute));


                   Alarm alarm = new Alarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                   alarm.selectDays(CHECKED_DAYS_ARRAY[0],CHECKED_DAYS_ARRAY[1],
                                CHECKED_DAYS_ARRAY[2],CHECKED_DAYS_ARRAY[3],
                                CHECKED_DAYS_ARRAY[4],CHECKED_DAYS_ARRAY[5],
                                CHECKED_DAYS_ARRAY[6]);
                   boolean def_value;

                   alarm.setSelectionFlag(this.getIntent().getBooleanExtra("selection", false));

                   db.insertData(alarm);

                   Log.d("TESTLV", "added to bd");
                   db.close();
                   finish();
                }
        }

}