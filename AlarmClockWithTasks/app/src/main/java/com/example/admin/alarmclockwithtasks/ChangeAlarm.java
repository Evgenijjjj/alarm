package com.example.admin.alarmclockwithtasks;

import android.app.ActionBar;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;

import java.util.ArrayList;
import java.util.Calendar;

public class ChangeAlarm extends AppCompatActivity implements TimePicker.OnTimeChangedListener, View.OnClickListener,
CheckBox.OnCheckedChangeListener{

        private ImageButton addNewAlarm,addBtnBack, changeSelectedDays, popupMenuClose;
        private TextView tvSelectedDays;
        private int HOUR, MINUTE, db_hour, db_minute;
        private DBHelper db;
        private TimePicker timePicker;
        private int[] CHECKED_DAYS_ARRAY = {0,0,0,0,0,0,0};
        private Alarm alarm = new Alarm();
        private PopupWindow popupWindow;
        private View selectDaysView;
    private CheckBox Mn,Tu, We, Th, Fr, Sa, Su;



    @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.custom_time_picker_dialog);

                addBtnBack = (ImageButton) findViewById(R.id.addBtnBackT);
                addNewAlarm = (ImageButton) findViewById(R.id.addNewAlarmBtnT);
                timePicker = (TimePicker)findViewById(R.id.timePicker);
                changeSelectedDays = (ImageButton)findViewById(R.id.changeSelectedDays);
                tvSelectedDays = (TextView)findViewById(R.id.custom_time_picker_dialog_tvSelectedDays);
                addBtnBack.setVisibility(View.INVISIBLE);

                addNewAlarm.setOnClickListener(this);
                timePicker.setOnTimeChangedListener(this);
                timePicker.setIs24HourView(DateFormat.is24HourFormat(this));
                changeSelectedDays.setOnClickListener(this);

                Calendar c = Calendar.getInstance();
                int h = c.get(Calendar.HOUR_OF_DAY);
                int m = c.get(Calendar.MINUTE);


                db_hour = this.getIntent().getIntExtra("hourOfDay", h);
                db_minute = this.getIntent().getIntExtra("minute", m);
                CHECKED_DAYS_ARRAY = this.getIntent().getIntArrayExtra("selectedDaysIntArray");

                timePicker.setCurrentHour(db_hour);
                timePicker.setCurrentMinute(db_minute);

                alarm.selectDays(CHECKED_DAYS_ARRAY[0],CHECKED_DAYS_ARRAY[1],
                    CHECKED_DAYS_ARRAY[2],CHECKED_DAYS_ARRAY[3],
                    CHECKED_DAYS_ARRAY[4],CHECKED_DAYS_ARRAY[5],
                    CHECKED_DAYS_ARRAY[6]);

                tvSelectedDays.setText(alarm.getStringSelectedDays());
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


                   //alarm = new Alarm(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                   alarm.setHourOfDay(HOUR);
                   alarm.setMinute(MINUTE);
                   alarm.selectDays(CHECKED_DAYS_ARRAY[0],CHECKED_DAYS_ARRAY[1],
                                CHECKED_DAYS_ARRAY[2],CHECKED_DAYS_ARRAY[3],
                                CHECKED_DAYS_ARRAY[4],CHECKED_DAYS_ARRAY[5],
                                CHECKED_DAYS_ARRAY[6]);
                   alarm.setSelectionFlag(this.getIntent().getBooleanExtra("selection", false));

                   db.insertData(alarm);

                   Log.d("TESTLV", "added to bd");
                   db.close();
                   finish();
                }

                if(v.getId() == R.id.changeSelectedDays){
                    LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                    selectDaysView = layoutInflater.inflate(R.layout.days_popup_window,null);
                    popupWindow = new PopupWindow(selectDaysView,
                            ActionBar.LayoutParams.WRAP_CONTENT,
                            ActionBar.LayoutParams.WRAP_CONTENT);

                    Mn = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_monday);
                    Tu = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_tuesday);
                    We = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_wednesday);
                    Th = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_thursday);
                    Fr = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_friday);
                    Sa = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_saturday);
                    Su = (CheckBox)selectDaysView.findViewById(R.id.select_days_popup_menu_sunday);
                    popupMenuClose = (ImageButton) selectDaysView.findViewById(R.id.select_days_popup_menu_close);

                    Mn.setOnCheckedChangeListener(this);
                    Tu.setOnCheckedChangeListener(this);
                    We.setOnCheckedChangeListener(this);
                    Th.setOnCheckedChangeListener(this);
                    Fr.setOnCheckedChangeListener(this);
                    Sa.setOnCheckedChangeListener(this);
                    Su.setOnCheckedChangeListener(this);

                    Mn.setChecked(CHECKED_DAYS_ARRAY[0] ==1);
                    Tu.setChecked(CHECKED_DAYS_ARRAY[1] ==1);
                    We.setChecked(CHECKED_DAYS_ARRAY[2] ==1);
                    Th.setChecked(CHECKED_DAYS_ARRAY[3] ==1);
                    Fr.setChecked(CHECKED_DAYS_ARRAY[4] ==1);
                    Sa.setChecked(CHECKED_DAYS_ARRAY[5] ==1);
                    Su.setChecked(CHECKED_DAYS_ARRAY[6] ==1);

                    popupMenuClose.setOnClickListener(this);

                    popupWindow.showAtLocation(changeSelectedDays, Gravity.CENTER,0,0);
                }

                if(v.getId() == R.id.select_days_popup_menu_close){
                    alarm.selectDays(CHECKED_DAYS_ARRAY[0],CHECKED_DAYS_ARRAY[1],
                            CHECKED_DAYS_ARRAY[2],CHECKED_DAYS_ARRAY[3],
                            CHECKED_DAYS_ARRAY[4],CHECKED_DAYS_ARRAY[5],
                            CHECKED_DAYS_ARRAY[6]);

                    popupWindow.dismiss();
                    tvSelectedDays.setText(alarm.getStringSelectedDays());
                }
        }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            if (buttonView.getId() == R.id.select_days_popup_menu_monday) {
                CHECKED_DAYS_ARRAY[0] =1;
            }
            if(buttonView.getId() == R.id.select_days_popup_menu_tuesday){
                CHECKED_DAYS_ARRAY[1] =1;
            }
            if(buttonView.getId() == R.id.select_days_popup_menu_wednesday){
                CHECKED_DAYS_ARRAY[2] =1;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_thursday){
                CHECKED_DAYS_ARRAY[3] =1;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_friday){
                CHECKED_DAYS_ARRAY[4] =1;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_saturday){
                CHECKED_DAYS_ARRAY[5] =1;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_sunday){
                CHECKED_DAYS_ARRAY[6] =1;
            }
        }
        else{
            if (buttonView.getId() == R.id.select_days_popup_menu_monday) {
                CHECKED_DAYS_ARRAY[0] =0;
            }
            if(buttonView.getId() == R.id.select_days_popup_menu_tuesday){
                CHECKED_DAYS_ARRAY[1] =0;
            }
            if(buttonView.getId() == R.id.select_days_popup_menu_wednesday){
                CHECKED_DAYS_ARRAY[2] =0;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_thursday){
                CHECKED_DAYS_ARRAY[3] =0;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_friday){
                CHECKED_DAYS_ARRAY[4] =0;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_saturday){
                CHECKED_DAYS_ARRAY[5] =0;
            }
            if (buttonView.getId() == R.id.select_days_popup_menu_sunday){
                CHECKED_DAYS_ARRAY[6] =0;
            }
        }
    }
}