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

import com.example.bean.Chapter;

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

    private List<Chapter> mChapters;

    private Map<Integer,QuesBaseAdapter> mAdapterMap;

    public QuesBaseAdapter(Context mContext,int level,List<Chapter> mChapters){
        this.mContext = mContext;
        this.mLevel = level;
        this.mChapters = mChapters;
    }

    public int getLevel() {
        return mLevel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.ques_base_item1,parent,false);
        return new ChapterHolder(view);
//        if (viewType == 1){
//            return new ChapterHolder(view);
//        }
//        View view2 = LayoutInflater.from(mContext).inflate(R.layout.ques_base_item2,parent,false);
//        return new ChapterHolder2(view2);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chapter chapter = mChapters.get(position);
//        if (getItemViewType(position) == 1){
            ChapterHolder chapterHolder = (ChapterHolder) holder;

            chapterHolder.mTvChapterTitle.setText(chapter.getName());

            if (chapter.getChapters() == null || chapter.getChapters().size() == 0){
                chapterHolder.mRecyclerView.setVisibility(View.GONE);
                return;
            }

            if (mAdapterMap == null){
                mAdapterMap = new HashMap<>();
            }

            if (!mAdapterMap.containsKey(position)){
                chapterHolder.mRecyclerView.setVisibility(View.VISIBLE);
                LinearLayoutManager manager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
                chapterHolder.mRecyclerView.setLayoutManager(manager);
                QuesBaseAdapter adapter = new QuesBaseAdapter(mContext,getLevel()+1,chapter.getChapters());
                chapterHolder.mRecyclerView.setAdapter(adapter);
                mAdapterMap.put(position,adapter);
            }

            if (chapter.isExpand()){
                chapterHolder.mRecyclerView.setVisibility(View.VISIBLE);
            }else{
                chapterHolder.mRecyclerView.setVisibility(View.GONE);
            }

            chapterHolder.itemView.setTag(R.id.tag_first,position);
            chapterHolder.itemView.setOnClickListener(view -> {
                int itemPos = (int) view.getTag(R.id.tag_first);
                Chapter itemChapter = mChapters.get(itemPos);
                mChapters.get(itemPos).setExpand(!itemChapter.isExpand());
//                notifyDataSetChanged();
                mContext.sendBroadcast(new Intent("com.huixuexi.flush"));
            });
//        }
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
        return mChapters.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (getLevel() > 0){
            return getLevel();
        }
        return super.getItemViewType(position);
    }

    class ChapterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivSelect)
        ImageView mIvSelect;

        @BindView(R.id.tvChapterName)
        TextView mTvChapterName;

        @BindView(R.id.tvChapterTitle)
        TextView mTvChapterTitle;

        @BindView(R.id.tvStart)
        TextView mTvStart;

        @BindView(R.id.recyclerview)
        RecyclerView mRecyclerView;

        public ChapterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ChapterHolder2 extends RecyclerView.ViewHolder {

        @BindView(R.id.ivSelect)
        ImageView mIvSelect;

        @BindView(R.id.tvChapterName)
        TextView mTvChapterName;

        @BindView(R.id.tvChapterTitle)
        TextView mTvChapterTitle;

        @BindView(R.id.tvStart)
        TextView mTvStart;

        public ChapterHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
