package com.example.admin.alarmclockwithtasks.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.alarmclockwithtasks.ChangeAlarm;
import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;
import com.example.admin.alarmclockwithtasks.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private int layout;
    private boolean USE_ALL_SETS_FLAG;
    private ViewHolder mainViewHolder = null;
    private List<Alarm> data;
    private ListView mainListView = null;


    public CustomAdapter(Context context, int resource, List<String> objects, ListView mainListView) {
        super(context, resource, objects);
        this.layout = resource;
        this.mainListView = mainListView;

        DBHelper db = new DBHelper(context);
        this.data = db.readData();
        db.close();

        checkMaxLimit();
    }




    public void updateData(int position){
        DBHelper db = new DBHelper(getContext());
        data.clear();
        data=db.readData();
        db.close();
        //Log.d("TESTLV","DB LEN: " + data.size());

        this.notifyDataSetInvalidated();
        if(position>=0)
            this.mainListView.setSelection(position);
        //Log.d("TESTLV", "notify data set changed");
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layout,parent,false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.list_item_title);
            viewHolder.subtitle = (TextView)convertView.findViewById(R.id.list_item_subtitle);
            viewHolder.deleteBtn = (ImageButton) convertView.findViewById(R.id.list_item_rmItem);
            viewHolder.changeBtn = (ImageButton) convertView.findViewById(R.id.list_item_changeItem);
            viewHolder.checkBox = (Switch) convertView.findViewById(R.id.list_item_checkbox);
            viewHolder.listItem = (RelativeLayout)convertView.findViewById(R.id.listItem);

            viewHolder.title.setText("Time: " + data.get(position).getStringHourOfDay() +
                    ":" + data.get(position).getStringMinute());
            //viewHolder.subtitle.setText("Mn Tu We Th Fr Sa Su");
            viewHolder.subtitle.setText(data.get(position).getStringSelectedDays());
            Log.d("TESTLV", "SETTING SUBTITLE FROM DB: " + data.get(position).getStringSelectedDays());

            if(data.get(position).getSelectionFlag()){
                viewHolder.checkBox.setChecked(true);
                viewHolder.listItem.setBackgroundColor(Color.parseColor("#00FFFF"));
            }


            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"RM " + data.get(position).getStringHourOfDay()+":"+data.get(position).getStringMinute(),Toast.LENGTH_SHORT).show();
                    int pos = position;
                    Alarm a = data.get(position);
                    DBHelper db = new DBHelper(getContext());
                    db.deleteAlarm(a);
                    db.close();
                    if (pos>0)
                        pos-=1;
                    updateData(pos);
                }
            });

            viewHolder.changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alarm a = data.get(position);
                    Toast.makeText(getContext(),"CHANGE " + a.getStringHourOfDay()+":"+a.getStringMinute(),Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getContext(),ChangeAlarm.class);
                    i.putExtra("hourOfDay",a.getHourOfDay());
                    i.putExtra("minute",a.getMinute());
                    i.putExtra("selection",a.getSelectionFlag());
                    i.putExtra("selectedDaysIntArray",a.getIntArraySelectedDays());

                    getContext().startActivity(i);
                    //updateData(position);
                }
            });

            CustomSwitchListener(viewHolder,position);
            convertView.setTag(viewHolder);
        }
        else{
            mainViewHolder = (ViewHolder)convertView.getTag();
            mainViewHolder.checkBox.setOnCheckedChangeListener(null);
            mainViewHolder.checkBox.setFocusable(false);
            mainViewHolder.title.setText("Time: " + data.get(position).getStringHourOfDay() +
                    ":" + data.get(position).getStringMinute());
            //mainViewHolder.subtitle.setText("Mn Tu We Th Fr Sa Su");
            mainViewHolder.subtitle.setText(data.get(position).getStringSelectedDays());
            Log.d("TESTLV", "SETTING SUBTITLE FROM DB: " + data.get(position).getStringSelectedDays());

            if (data.get(position).getSelectionFlag()) {
                mainViewHolder.checkBox.setChecked(true);
                mainViewHolder.listItem.setBackgroundColor(Color.parseColor("#AFEEEE"));
            }
            else {
                mainViewHolder.checkBox.setChecked(false);
                mainViewHolder.listItem.setBackgroundColor(Color.TRANSPARENT);
            }

            mainViewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"RM' " + data.get(position).getStringHourOfDay()+":"+data.get(position).getStringMinute(),Toast.LENGTH_SHORT).show();
                    int pos = position;
                    Alarm a = data.get(position);
                    DBHelper db = new DBHelper(getContext());
                    db.deleteAlarm(a);
                    db.close();
                    if (pos>0)
                        pos-=1;
                    updateData(pos);
                }
            });

            mainViewHolder.changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Alarm a = data.get(position);
                    Toast.makeText(getContext(),"CHANGE' " + a.getStringHourOfDay()+":"+a.getStringMinute(),Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getContext(),ChangeAlarm.class);
                    i.putExtra("hourOfDay",a.getHourOfDay());
                    i.putExtra("minute",a.getMinute());
                    i.putExtra("selection",a.getSelectionFlag());

                    getContext().startActivity(i);
                    //updateData(position);
                }
            });


            CustomSwitchListener(mainViewHolder,position);
        }


        return convertView;
    }

    private boolean checkMaxLimit() {
        int counterman = 0;
        for(int x=0;x<data.size();x++){
            if(data.get(x).getSelectionFlag()){
                counterman++;
            }
        }
        //return counterman >= 6;
        if(counterman >= 6){
            this.USE_ALL_SETS_FLAG = true;
            return true;
        }
        else{
            this.USE_ALL_SETS_FLAG = false;
            return false;
        }
    }

    private void CustomSwitchListener(final ViewHolder viewHolder, final int position){
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton cb, boolean b) {
                DBHelper db = new DBHelper(getContext());

                if(checkMaxLimit()){
                    if(data.get(position).getSelectionFlag() && b){
                        mainViewHolder.checkBox.setChecked(false);
                        data.get(position).setSelectionFlag(false);
                        Alarm a = data.get(position);
                        db.deleteAlarm(a);
                        a.setSelectionFlag(false);
                        db.insertData(a);
                        viewHolder.listItem.setBackgroundColor(Color.TRANSPARENT);
                    }
                    else{
                        mainViewHolder.checkBox.setChecked(false);
                        data.get(position).setSelectionFlag(false);
                        Alarm a = data.get(position);
                        db.deleteAlarm(a);
                        a.setSelectionFlag(false);
                        db.insertData(a);
                        viewHolder.listItem.setBackgroundColor(Color.TRANSPARENT);
                        Toast.makeText(getContext(), "Max 6 Alarms in one time !", Toast.LENGTH_SHORT).show();
                        updateData(position);
                    }

                }
                else {
                    if (b) {
                        data.get(position).setSelectionFlag(true);
                        Alarm a = data.get(position);
                        db.deleteAlarm(a);
                        a.setSelectionFlag(true);
                        db.insertData(a);
                        viewHolder.listItem.setBackgroundColor(Color.parseColor("#AFEEEE"));
                    }
                    else {
                        data.get(position).setSelectionFlag(false);
                        Alarm a = data.get(position);
                        db.deleteAlarm(a);
                        a.setSelectionFlag(false);
                        db.insertData(a);
                        viewHolder.listItem.setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                db.close();
            }
        });
    }
}