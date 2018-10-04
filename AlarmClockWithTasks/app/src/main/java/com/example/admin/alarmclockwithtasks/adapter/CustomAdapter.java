package com.example.admin.alarmclockwithtasks.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;
import com.example.admin.alarmclockwithtasks.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {
    private int layout;

    private ViewHolder mainViewHolder = null;
    private List<Alarm> data;


    public CustomAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        this.layout = resource;

        DBHelper db = new DBHelper(context);
        this.data = db.readData();
        db.close();

        //исправить на получения статуса из БД
       /* SELECTED_FLAG = new boolean[data.size()];
        for(int i = 0;i<data.size();i++)
            SELECTED_FLAG[i] = false;
        Log.d("TESTLV","" + data.size());*/
    }




    public void updateData(){
        DBHelper db = new DBHelper(getContext());
        data.clear();
        data=db.readData();
        db.close();
        Log.d("TESTLV","DB LEN: " + data.size());

       /* SELECTED_FLAG = new boolean[data.size()];
        for(int i = 0;i<data.size();i++)
            SELECTED_FLAG[i] = false;*/

        this.notifyDataSetInvalidated();
        Log.d("TESTLV", "notify data set changed");
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
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.list_item_checkbox);


            viewHolder.title.setText("Time: " + data.get(position).getStringHourOfDay() +
                    ":" + data.get(position).getStringMinute());
            viewHolder.subtitle.setText("Mn Tu We Th Fr Sa Su");

            if(data.get(position).getSelectionFlag()){
                viewHolder.checkBox.setChecked(true);
            }


            viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"rm item: " + position,Toast.LENGTH_SHORT).show();
                }
            });

            viewHolder.changeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"change item: " + position,Toast.LENGTH_SHORT).show();
                }
            });

            convertView.setTag(viewHolder);
        }
        else{
            mainViewHolder = (ViewHolder)convertView.getTag();
            mainViewHolder.checkBox.setOnCheckedChangeListener(null);
            mainViewHolder.checkBox.setFocusable(false);
            mainViewHolder.title.setText("Time: " + data.get(position).getStringHourOfDay() +
                    ":" + data.get(position).getStringMinute());
            mainViewHolder.subtitle.setText("Mn Tu We Th Fr Sa Su");

            try {
                if (data.get(position).getSelectionFlag()) {
                    mainViewHolder.checkBox.setChecked(true);
                } else {
                    mainViewHolder.checkBox.setChecked(false);
                }

            }catch (Exception e){
                Log.d("TESTLV",e.toString());
            }

            mainViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

                        }
                        else{
                            mainViewHolder.checkBox.setChecked(false);
                            //SELECTED_FLAG[position] = false;
                            data.get(position).setSelectionFlag(false);
                            Alarm a = data.get(position);
                            db.deleteAlarm(a);
                            a.setSelectionFlag(false);
                            db.insertData(a);
                            Toast.makeText(getContext(), "Max limit reached", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else {
                        if (b) {
                            data.get(position).setSelectionFlag(true);
                            Alarm a = data.get(position);
                            db.deleteAlarm(a);
                            a.setSelectionFlag(true);
                            db.insertData(a);
                        }
                        else {
                            data.get(position).setSelectionFlag(false);                            Alarm a = data.get(position);
                            db.deleteAlarm(a);
                            a.setSelectionFlag(false);
                            db.insertData(a);
                        }
                    }
                    db.close();
                }
            });
        }


        return convertView;
    }

    public boolean checkMaxLimit() {
        int counterman = 0;
        for(int x=0;x<data.size();x++){
            if(data.get(x).getSelectionFlag()){
                counterman++;
            }
        }
        return counterman >= 5;
    }
}