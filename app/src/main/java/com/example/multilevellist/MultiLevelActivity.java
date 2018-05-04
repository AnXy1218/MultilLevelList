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

import com.example.bean.Chapter;
import com.example.bean.ChapterItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MultiLevelActivity extends AppCompatActivity {

    private int type;

    RecyclerView recyclerView;

    private List<Chapter> list2;
    private List<ChapterItem> mData;

    private QuesBaseAdapter listAdapter;

    private TreeRecyclerViewAdapter<ChapterItem> dataAdapter;

    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_level);

        recyclerView = findViewById(R.id.recyclerView);

        type = getIntent().getIntExtra("type", 1);

        if (type == 1){
            list2 = new ArrayList<>();

            FlushReceiver receiver = new FlushReceiver();
            IntentFilter filter = new IntentFilter("com.huixuexi.flush");
            registerReceiver(receiver,filter);

            LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            listAdapter = new QuesBaseAdapter(this,1,list2);
            recyclerView.setAdapter(listAdapter);
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getList();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listAdapter.notifyDataSetChanged();
                            listAdapter.notifiDataChanged();
                        }
                    });
                }
            },5*1000);
        }else {
            if (mData == null){
                mData = new ArrayList<>();
            }
            LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(manager);
            dataAdapter = new TreeRecyclerViewAdapter<ChapterItem>(this,mData);
            recyclerView.setAdapter(dataAdapter);
            getData();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            dataAdapter.notifyDataSetChanged();
                        }
                    });
                }
            },5*1000);
        }
    }

    private void getList() {
        if (list2 == null) {
            list2 = new ArrayList<>();
        }

        list2.clear();

        Chapter chapter = new Chapter();
        chapter.setId(2);
        chapter.setName("名称");
        List<Chapter> list = new ArrayList<>();
        list.add(chapter);
        list.add(chapter);

        Chapter chapter1 = new Chapter();
        chapter1.setId(1);
        chapter1.setName("名册名册");
        chapter1.setChapters(list);

        List<Chapter> list1 = new ArrayList<>();
        list1.add(chapter1);

        Chapter chapter2 = new Chapter();
        chapter2.setId(1);
        chapter2.setName("名册名册");
        chapter2.setChapters(list1);

        list2.add(chapter2);
        list2.add(chapter1);
        list2.add(chapter);
    }


    private void getData() {
        if (list2 == null) {
            getList();
        }

        if (mData == null){
            mData= new ArrayList<>();
        }else{
            mData.clear();
        }
        for (int i = 0; i < list2.size(); i++) {
            ChapterItem twoItem = new ChapterItem(list2.get(i),1);
//            twoItem.setLevel(1);
            mData.add(twoItem);
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
