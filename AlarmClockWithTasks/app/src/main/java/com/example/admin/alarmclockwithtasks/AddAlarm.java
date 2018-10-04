package com.example.admin.alarmclockwithtasks;

import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;

import java.util.Calendar;

public class AddAlarm extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    private Button btnOpenTimePicker, addNewAlarm;
    private TextView tvTime, back;
    private int HOUR, MINUTE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        btnOpenTimePicker = (Button)findViewById(R.id.btnOpenTimePicker);
        tvTime = (TextView)findViewById(R.id.tvTime);
        back = (TextView)findViewById(R.id.addBtnBack);
        addNewAlarm = (Button)findViewById(R.id.addNewAlarmBtn);

        btnOpenTimePicker.setOnClickListener(this);
        back.setOnClickListener(this);
        addNewAlarm.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int m = c.get(Calendar.MINUTE);

        HOUR = h;
        MINUTE = m;

        setTime(h,m);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnOpenTimePicker){
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(),"Pick the Time");
        }

        if(v.getId() == R.id.addBtnBack){
            //Toast.makeText(this, "going back", Toast.LENGTH_SHORT).show();
            finish();
        }

        if(v.getId() == R.id.addNewAlarmBtn){
            DBHelper db = new DBHelper(this);

            Alarm alarm = new Alarm(HOUR,MINUTE);
            alarm.setSelectionFlag(true);
            db.insertData(alarm);

            Log.d("TESTLV", "added to bd");
            db.close();
            finish();
        }
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        HOUR = hourOfDay;
        MINUTE = minute;
        setTime(hourOfDay,minute);
    }

    private void setTime(int h, int m){
        if (m<10)
            tvTime.setText("Alarm Time: " +  h + ":" + "0"+ m);
        else
            tvTime.setText("Alarm Time: " +  h + ":" + m);
    }
}
