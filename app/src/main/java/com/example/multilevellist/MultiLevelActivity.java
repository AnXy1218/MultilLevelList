package com.example.multilevellist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.bean.Area;
import com.example.bean.AreaFactory;
import com.example.bean.AreaItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MultiLevelActivity extends AppCompatActivity {

    private int type;

    RecyclerView recyclerView;

    private List<Area> mAreas;
    private List<AreaItem> mAreaItems;

    private QuesBaseAdapter listAdapter;

    private TreeRecyclerViewAdapter<AreaItem> dataAdapter;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_level);

        recyclerView = findViewById(R.id.recyclerView);

        type = getIntent().getIntExtra("type", 1);

        if (type == 1){
            mAreas = new ArrayList<>();

            FlushReceiver receiver = new FlushReceiver();
            IntentFilter filter = new IntentFilter("com.example.flush");
            registerReceiver(receiver,filter);

            LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            listAdapter = new QuesBaseAdapter(this,1,mAreas);
            recyclerView.setAdapter(listAdapter);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getList();
                    handler.post(()->{
                        listAdapter.notifyDataSetChanged();
                        listAdapter.notifiDataChanged();
                    });
                }
            },5*1000);
        }else {
            if (mAreaItems == null){
                mAreaItems = new ArrayList<>();
            }
            LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            dataAdapter = new TreeRecyclerViewAdapter<AreaItem>(this,mAreaItems);
            recyclerView.setAdapter(dataAdapter);
            getData();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getData();
                    handler.post(()->{
                        dataAdapter.notifyDataSetChanged();
                    });
                }
            },5*1000);
        }
    }

    private void getList() {
        if (mAreas == null) {
            mAreas = new ArrayList<>();
        }

        mAreas.clear();
        //北京市海淀区、朝阳区
        //浙江省杭州市西湖区、滨江区
        //陕西省西安市未央区
        String[] bjAreaNames = new String[]{"东城区","西城区","海淀区","朝阳区","昌平区"};
        List<Area> bjAreas = AreaFactory.createAreaList(bjAreaNames);
        Area bjArea = AreaFactory.createArea(0,"北京市");
        bjArea.setAreas(bjAreas);
        mAreas.add(bjArea);

        String[] hzAreaNames = new String[]{"上城区","下城区","西湖区","滨江区","余杭区"};
        List<Area> hzAreas = AreaFactory.createAreaList(hzAreaNames);
        Area hzArea = AreaFactory.createArea(0,"杭州市");
        hzArea.setAreas(hzAreas);
        List<Area> zjAreas = new ArrayList<>();
        zjAreas.add(hzArea);
        Area zjArea = AreaFactory.createArea(1,"浙江省");
        zjArea.setAreas(zjAreas);
        mAreas.add(zjArea);

        String[] xaAreaNames = new String[]{"新城区","碑林区","莲湖区","雁塔区","未央区","灞桥区","阎良区","临潼区","长安区"};
        List<Area> xaAreas = AreaFactory.createAreaList(xaAreaNames);
        Area xaArea = AreaFactory.createArea(0,"西安市");
        xaArea.setAreas(xaAreas);
        List<Area> sxAreas = new ArrayList<>();
        sxAreas.add(xaArea);
        Area sxArea = AreaFactory.createArea(1,"陕西省");
        sxArea.setAreas(sxAreas);
        mAreas.add(sxArea);
    }


    private void getData() {
        if (mAreas == null) {
            getList();
        }

        if (mAreaItems == null){
            mAreaItems= new ArrayList<>();
        }else{
            mAreaItems.clear();
        }
        for (int i = 0; i < mAreas.size(); i++) {
            AreaItem twoItem = new AreaItem(mAreas.get(i),1);
//            twoItem.setLevel(1);
            mAreaItems.add(twoItem);
        }
    }

    public class FlushReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            listAdapter.notifyDataSetChanged();
            listAdapter.notifiDataChanged();
        }
    }
}
