package com.example.admin.alarmclockwithtasks;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.alarmclockwithtasks.DB.Alarm;
import com.example.admin.alarmclockwithtasks.DB.DBHelper;
import com.example.admin.alarmclockwithtasks.adapter.CustomAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kotlin.collections.AbstractMutableMap;

public class StartPage extends AppCompatActivity implements View.OnClickListener/*, ListView.OnItemClickListener, PopupMenu.OnMenuItemClickListener*/
{

    private Button AddNewAlarmBtn;
    private DBHelper db;
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        AddNewAlarmBtn = (Button)findViewById(R.id.addNewAlarmBtn);
        listView = (ListView)findViewById(R.id.listView);

        AddNewAlarmBtn.setOnClickListener(this);

        db = new DBHelper(this);
        //db.deleteAll();
        adapter = new CustomAdapter(this,R.layout.list_item,new ArrayList<String>());
        listView.setAdapter(adapter);
        adapter.updateData();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addNewAlarmBtn){
            startActivity(new Intent(StartPage.this, AddAlarm.class));
        }
    }

    /*@Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, "item " + id + " clicked " , Toast.LENGTH_SHORT).show();
        //CURRENT_SELECTED_VIEW = view;
        SELECTED_VIEW_POSITION = position;
        Log.d("CUR_VIEW",String.valueOf(SELECTED_VIEW_POSITION/2));
        CURRENT_SELECTED_VIEW = adapter.getView(SELECTED_VIEW_POSITION,view,(ViewGroup) view);

        PopupMenu popup = new PopupMenu(this, view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.item_menu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        Log.d("CUR_VIEW",CURRENT_SELECTED_VIEW.toString());

        switch (item.getItemId()) {
            case R.id.menu_rm_item:
                Toast.makeText(this, "item " + item + " will be removed" , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_change_item:
                Toast.makeText(this, "item " + item.toString() + " will be changed" , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_activate_alarm:
                CURRENT_SELECTED_VIEW.setBackgroundColor(Color.GREEN);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "item " + item.toString() + " will be activated" , Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_disactivate_alarm:
                CURRENT_SELECTED_VIEW.setBackgroundColor(Color.TRANSPARENT);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "item " + item.toString() + " will be disactivated" , Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }*/



    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData();
    }

}


