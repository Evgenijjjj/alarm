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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
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

public class StartPage extends AppCompatActivity implements View.OnClickListener {

    private Button AddNewAlarmBtn;
    private DBHelper db;
    private ListView listView;
    private CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        //int resId = R.anim.layout_animation_fall_down;
        //LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);

        AddNewAlarmBtn = (Button)findViewById(R.id.addNewAlarmBtn);
        listView = (ListView)findViewById(R.id.listView);
        //listView.scheduleLayoutAnimation();

        //listView.setLayoutAnimation(animation);
        AddNewAlarmBtn.setOnClickListener(this);

        db = new DBHelper(this);
        //db.deleteAll();
        adapter = new CustomAdapter(this,R.layout.list_item,new ArrayList<String>(),listView);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addNewAlarmBtn){
            startActivity(new Intent(StartPage.this, AddAlarm.class));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData(-1);
    }

}


