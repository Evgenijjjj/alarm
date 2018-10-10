package com.example.admin.alarmclockwithtasks;

import android.app.ActionBar;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ListMenuItemView;
import android.text.Layout;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity implements View.OnClickListener, TimePicker.OnTimeChangedListener,
CheckBox.OnCheckedChangeListener{

    private ImageButton addNewAlarm,back, setDayoOfWeek, popupMenuClose;
    private int HOUR, MINUTE;
    private DBHelper db;
    private TimePicker timePicker;
    private TextView tvSelectedDaysOfWeek;
    private View selectDaysView;
    private CheckBox Mn,Tu, We, Th, Fr, Sa, Su;
    private int[] CHECKED_DAYS_ARRAY = {0,0,0,0,0,0,0};
    private Alarm ALARM;
    private PopupWindow popupWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        back = (ImageButton)findViewById(R.id.addBtnBack);
        addNewAlarm = (ImageButton)findViewById(R.id.addNewAlarmBtn);
        timePicker = (TimePicker)findViewById(R.id.timePicker);
        setDayoOfWeek = (ImageButton)findViewById(R.id.setDayOfWeek);
        tvSelectedDaysOfWeek = (TextView)findViewById(R.id.tvSelectedDaysOfWeek);

        timePicker.setIs24HourView(DateFormat.is24HourFormat(this));

        back.setOnClickListener(this);
        addNewAlarm.setOnClickListener(this);
        timePicker.setOnTimeChangedListener(this);
        setDayoOfWeek.setOnClickListener(this);


        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        HOUR = h;
        MINUTE = m;
        ALARM= new Alarm(HOUR,MINUTE);

        timePicker.setCurrentHour(h);
        timePicker.setCurrentMinute(m);
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        HOUR = hourOfDay;
        MINUTE=minute;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.addBtnBack){
            finish();
        }

        if(v.getId() == R.id.addNewAlarmBtn){
            db = new DBHelper(this);

            ALARM.setMinute(MINUTE);
            ALARM.setHourOfDay(HOUR);
            if(db.checkMaxLimit())
                ALARM.setSelectionFlag(false);
            else
                ALARM.setSelectionFlag(true);
            db.insertData(ALARM);

            Log.d("TESTLV", "NEW ALARM_d :" + ALARM.getStringSelectedDays());
            Log.d("TESTLV", "added to bd");
            db.close();
            finish();
        }

        if(v.getId() == R.id.setDayOfWeek){
            LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            selectDaysView = layoutInflater.inflate(R.layout.days_popup_window,null);
            popupWindow = new PopupWindow(selectDaysView,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT);

            //все чекбоксы здесь и дествия с ними onclick...
            //popupWindow.showAsDropDown(setDayoOfWeek,0,0);

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

            popupWindow.showAtLocation(setDayoOfWeek, Gravity.CENTER,0,0);
        }

        if(v.getId() == R.id.select_days_popup_menu_close){
            ALARM.selectDays(CHECKED_DAYS_ARRAY[0],CHECKED_DAYS_ARRAY[1],
                    CHECKED_DAYS_ARRAY[2],CHECKED_DAYS_ARRAY[3],
                    CHECKED_DAYS_ARRAY[4],CHECKED_DAYS_ARRAY[5],
                    CHECKED_DAYS_ARRAY[6]);

            popupWindow.dismiss();
            tvSelectedDaysOfWeek.setText(ALARM.getStringSelectedDays());
            Log.d("TESTLV", "SET TEXT TO tvDays:" + CHECKED_DAYS_ARRAY[0]);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        db = new DBHelper(this);
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
        db.close();
    }
}
