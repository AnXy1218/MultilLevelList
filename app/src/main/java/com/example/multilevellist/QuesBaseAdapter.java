package com.example.multilevellist;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bean.Area;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RecyclerView嵌套适配器
 */
public class QuesBaseAdapter extends RecyclerView.Adapter {
    private Context mContext;

    private int mLevel;

    private List<Area> mAreas;

    private Map<Integer,QuesBaseAdapter> mAdapterMap;

    public QuesBaseAdapter(Context mContext,int level,List<Area> mAreas){
        this.mContext = mContext;
        this.mLevel = level;
        this.mAreas = mAreas;
    }

    public int getLevel() {
        return mLevel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1){
            View view = LayoutInflater.from(mContext).inflate(R.layout.ques_base_item1,parent,false);
            return new AreaHolder(view);
        }
        if (viewType == 2){
            View view2 = LayoutInflater.from(mContext).inflate(R.layout.ques_base_item2,parent,false);
            return new AreaHolder2(view2);
        }

        View view3 = LayoutInflater.from(mContext).inflate(R.layout.ques_base_item3,parent,false);
        return new AreaHolder3(view3);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Area area = mAreas.get(position);
        if (getItemViewType(position) == 1){
            AreaHolder areaHolder = (AreaHolder) holder;

            areaHolder.mTvTitle.setText(area.getTitle());
            if (area.getAreas() == null || area.getAreas().size() == 0){
                areaHolder.mRecyclerView.setVisibility(View.GONE);
                areaHolder.mTvSelect.setVisibility(View.GONE);
                return;
            }
            if (mAdapterMap == null){
                mAdapterMap = new HashMap<>();
            }

            if (!mAdapterMap.containsKey(position)){
                areaHolder.mRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
                areaHolder.mRecyclerView.setLayoutManager(manager);
                QuesBaseAdapter adapter = new QuesBaseAdapter(mContext,getLevel()+1,area.getAreas());
                areaHolder.mRecyclerView.setAdapter(adapter);
                mAdapterMap.put(position,adapter);
            }

            if (area.isExpand()){
                areaHolder.mRecyclerView.setVisibility(View.VISIBLE);
                areaHolder.mTvSelect.setText("收起");
            }else{
                areaHolder.mTvSelect.setText("展开");
                areaHolder.mRecyclerView.setVisibility(View.GONE);
            }

            areaHolder.itemView.setTag(R.id.tag_first,position);
            areaHolder.itemView.setOnClickListener(view -> {
                int itemPos = (int) view.getTag(R.id.tag_first);
                Area itemArea = mAreas.get(itemPos);
                mAreas.get(itemPos).setExpand(!itemArea.isExpand());
//                notifyDataSetChanged();
                mContext.sendBroadcast(new Intent("com.example.flush"));
            });
        }else if (getItemViewType(position) == 2){
            AreaHolder2 areaHolder2 = (AreaHolder2) holder;

            areaHolder2.mTvTitle.setText(area.getTitle());
            if (area.getAreas() == null || area.getAreas().size() == 0){
                areaHolder2.mRecyclerView.setVisibility(View.GONE);
                areaHolder2.mTvSelect.setVisibility(View.GONE);
                return;
            }
            if (mAdapterMap == null){
                mAdapterMap = new HashMap<>();
            }

            if (!mAdapterMap.containsKey(position)){
                areaHolder2.mRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
                areaHolder2.mRecyclerView.setLayoutManager(manager);
                QuesBaseAdapter adapter = new QuesBaseAdapter(mContext,getLevel()+1,area.getAreas());
                areaHolder2.mRecyclerView.setAdapter(adapter);
                mAdapterMap.put(position,adapter);
            }

            if (area.isExpand()){
                areaHolder2.mRecyclerView.setVisibility(View.VISIBLE);
                areaHolder2.mTvSelect.setText("收起");
            }else{
                areaHolder2.mTvSelect.setText("展开");
                areaHolder2.mRecyclerView.setVisibility(View.GONE);
            }

            areaHolder2.itemView.setTag(R.id.tag_first,position);
            areaHolder2.itemView.setOnClickListener(view -> {
                int itemPos = (int) view.getTag(R.id.tag_first);
                Area itemArea = mAreas.get(itemPos);
                mAreas.get(itemPos).setExpand(!itemArea.isExpand());
//                notifyDataSetChanged();
                mContext.sendBroadcast(new Intent("com.example.flush"));
            });
        }else{
            AreaHolder3 areaHolder3 = (AreaHolder3) holder;

            areaHolder3.mTvTitle.setText(area.getTitle());
            if (area.getAreas() == null || area.getAreas().size() == 0){
                areaHolder3.mRecyclerView.setVisibility(View.GONE);
                areaHolder3.mTvSelect.setVisibility(View.GONE);
                return;
            }
            if (mAdapterMap == null){
                mAdapterMap = new HashMap<>();
            }

            if (!mAdapterMap.containsKey(position)){
                areaHolder3.mRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
                areaHolder3.mRecyclerView.setLayoutManager(manager);
                QuesBaseAdapter adapter = new QuesBaseAdapter(mContext,getLevel()+1,area.getAreas());
                areaHolder3.mRecyclerView.setAdapter(adapter);
                mAdapterMap.put(position,adapter);
            }
            if (area.isExpand()){
                areaHolder3.mRecyclerView.setVisibility(View.VISIBLE);
                areaHolder3.mTvSelect.setText("收起");
            }else{
                areaHolder3.mTvSelect.setText("展开");
                areaHolder3.mRecyclerView.setVisibility(View.GONE);
            }

            areaHolder3.itemView.setTag(R.id.tag_first,position);
            areaHolder3.itemView.setOnClickListener(view -> {
                int itemPos = (int) view.getTag(R.id.tag_first);
                Area itemArea = mAreas.get(itemPos);
                mAreas.get(itemPos).setExpand(!itemArea.isExpand());
                mContext.sendBroadcast(new Intent("com.example.flush"));
            });
        }
    }

    public void notifiDataChanged(){
        if (mAdapterMap == null){
            return;
        }
        Set<Integer> adapterSet = mAdapterMap.keySet();

        for (Integer key:adapterSet){
            mAdapterMap.get(key).notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return mAreas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getLevel() > 0){
            return getLevel();
        }
        return super.getItemViewType(position);
    }

    class AreaHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;

        @BindView(R.id.tvSelect)
        TextView mTvSelect;

        @BindView(R.id.recyclerview)
        RecyclerView mRecyclerView;

        public AreaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class AreaHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;

        @BindView(R.id.tvSelect)
        TextView mTvSelect;

        @BindView(R.id.recyclerview)
        RecyclerView mRecyclerView;

        public AreaHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class AreaHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;

        @BindView(R.id.tvSelect)
        TextView mTvSelect;

        @BindView(R.id.recyclerview)
        RecyclerView mRecyclerView;

        public AreaHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
