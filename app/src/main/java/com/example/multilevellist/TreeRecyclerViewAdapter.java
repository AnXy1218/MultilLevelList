package com.example.multilevellist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.Area;
import com.example.bean.TreeAdapterItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 树形列表适配器
 */
public class TreeRecyclerViewAdapter<T extends TreeAdapterItem> extends RecyclerView.Adapter<ViewHolder> {

    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<T> mDatas;//处理后的展示数据

    /**
     * 点击item的回调接口
     */
    private OnTreeItemClickListener onTreeItemClickListener;

    public void setOnTreeItemClickListener(OnTreeItemClickListener onTreeItemClickListener) {
        this.onTreeItemClickListener = onTreeItemClickListener;
    }

    /**
     * @param context 上下文
     * @param datas   条目数据
     */
    public TreeRecyclerViewAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
    }

    /**
     * 相应RecyclerView的点击事件 展开或关闭
     * 重要
     *
     * @param position 触发的条目
     */
    public void expandOrCollapse(int position,TextView tvSelect) {
        //获取当前点击的条目
        TreeAdapterItem treeAdapterItem = mDatas.get(position);
        //判断点击的条目有没有下一级
        if (!treeAdapterItem.isParent()) {
            return;
        }
        //判断是否展开
        boolean expand = treeAdapterItem.isExpand();
        if (expand) {
            //获取所有的子数据.
            List<TreeAdapterItem> allChilds = treeAdapterItem.getAllChilds();
            mDatas.removeAll(allChilds);

            List<TreeAdapterItem> childs = treeAdapterItem.getChilds();
            for (TreeAdapterItem item:childs){
                item.onCollapse();
            }

            //告诉item,折叠
            treeAdapterItem.onCollapse();
        } else {
            //获取下一级的数据
            mDatas.addAll(position + 1, treeAdapterItem.getChilds());
            //告诉item,展开
            treeAdapterItem.onExpand();
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //这里,直接通过item设置的id来创建Viewholder
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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(getItemViewType(position) == 1){
            AreaHolder areaHolder = (AreaHolder) holder;
            TreeAdapterItem<Area> treeAdapterItem = mDatas.get(position);
            areaHolder.mTvTitle.setText(treeAdapterItem.getData().getTitle());

            //设置收起、展开按钮
            if (treeAdapterItem.isParent()){
                areaHolder.mTvSelect.setVisibility(View.VISIBLE);
                if (treeAdapterItem.isExpand()){
                    areaHolder.mTvSelect.setText("收起");
                }else{
                    areaHolder.mTvSelect.setText("展开");
                }
            }else {
                areaHolder.mTvSelect.setVisibility(View.GONE);
            }



            //设置整个item的点击事件
            holder.itemView.setTag(R.id.tag_first,position);
            holder.itemView.setOnClickListener(v -> {
                int itemPosition = (int) v.getTag(R.id.tag_first);
                TreeAdapterItem<Area> treeAdapterItem1 = mDatas.get(itemPosition);
                Toast.makeText(mContext,"等级：" + treeAdapterItem1.getLevel()+",id---->" + treeAdapterItem1.getData().getId(),Toast.LENGTH_SHORT).show();
                if (onTreeItemClickListener != null) {
                    //点击监听的回调.一般不是最后一级,不需要处理吧.
                    onTreeItemClickListener.onClick(mDatas.get(itemPosition), itemPosition);
                }
            });

            //设置选择的点击事件
            areaHolder.mTvSelect.setTag(R.id.tag_first,position);
            areaHolder.mTvSelect.setOnClickListener(v -> {
                //折叠或展开
                int itemPosition = (int) v.getTag(R.id.tag_first);
                expandOrCollapse(itemPosition, (TextView) v);
            });
        }else if (getItemViewType(position) == 2){
            AreaHolder2 areaHolder = (AreaHolder2) holder;
            TreeAdapterItem<Area> treeAdapterItem = mDatas.get(position);
            areaHolder.mTvTitle.setText(treeAdapterItem.getData().getTitle());

            //设置收起、展开按钮
            if (treeAdapterItem.isParent()){
                areaHolder.mTvSelect.setVisibility(View.VISIBLE);
                if (treeAdapterItem.isExpand()){
                    areaHolder.mTvSelect.setText("收起");
                }else{
                    areaHolder.mTvSelect.setText("展开");
                }
            }else {
                areaHolder.mTvSelect.setVisibility(View.GONE);
            }

            //设置整个item的点击事件
            holder.itemView.setTag(R.id.tag_first,position);
            holder.itemView.setOnClickListener(v -> {
                int itemPosition = (int) v.getTag(R.id.tag_first);
                TreeAdapterItem<Area> treeAdapterItem1 = mDatas.get(itemPosition);
                Toast.makeText(mContext,"等级：" + treeAdapterItem1.getLevel()+",id---->" + treeAdapterItem1.getData().getId(),Toast.LENGTH_SHORT).show();
                if (onTreeItemClickListener != null) {
                    //点击监听的回调.一般不是最后一级,不需要处理吧.
                    onTreeItemClickListener.onClick(mDatas.get(itemPosition), itemPosition);
                }
            });

            //设置选择的点击事件
            areaHolder.mTvSelect.setTag(R.id.tag_first,position);
            areaHolder.mTvSelect.setOnClickListener(v -> {
                //折叠或展开
                int itemPosition = (int) v.getTag(R.id.tag_first);
                expandOrCollapse(itemPosition, (TextView) v);
            });
        }else{
            AreaHolder3 areaHolder = (AreaHolder3) holder;
            TreeAdapterItem<Area> treeAdapterItem = mDatas.get(position);
            areaHolder.mTvTitle.setText(treeAdapterItem.getData().getTitle());

            //设置收起、展开按钮
            if (treeAdapterItem.isParent()){
                areaHolder.mTvSelect.setVisibility(View.VISIBLE);
                if (treeAdapterItem.isExpand()){
                    areaHolder.mTvSelect.setText("收起");
                }else{
                    areaHolder.mTvSelect.setText("展开");
                }
            }else {
                areaHolder.mTvSelect.setVisibility(View.GONE);
            }

            //设置整个item的点击事件
            holder.itemView.setTag(R.id.tag_first,position);
            holder.itemView.setOnClickListener(v -> {
                int itemPosition = (int) v.getTag(R.id.tag_first);
                TreeAdapterItem<Area> treeAdapterItem1 = mDatas.get(itemPosition);
                Toast.makeText(mContext,"等级：" + treeAdapterItem1.getLevel()+",id---->" + treeAdapterItem1.getData().getId(),Toast.LENGTH_SHORT).show();
                if (onTreeItemClickListener != null) {
                    //点击监听的回调.一般不是最后一级,不需要处理吧.
                    onTreeItemClickListener.onClick(mDatas.get(itemPosition), itemPosition);
                }
            });

            //设置选择的点击事件
            areaHolder.mTvSelect.setTag(R.id.tag_first,position);
            areaHolder.mTvSelect.setOnClickListener(v -> {
                //折叠或展开
                int itemPosition = (int) v.getTag(R.id.tag_first);
                expandOrCollapse(itemPosition, (TextView) v);
            });
        }

    }

    @Override
    public int getItemViewType(int position) {
        //返回item的layoutId
        return mDatas.get(position).getLevel();
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public interface OnTreeItemClickListener {
        void onClick(TreeAdapterItem node, int position);
    }

    class AreaHolder extends ViewHolder {
        @BindView(R.id.tvTitle)
        TextView mTvTitle;

        @BindView(R.id.tvSelect)
        TextView mTvSelect;

        public AreaHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class AreaHolder2 extends ViewHolder {

        @BindView(R.id.tvTitle)
        TextView mTvTitle;

        @BindView(R.id.tvSelect)
        TextView mTvSelect;

        public AreaHolder2(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class AreaHolder3 extends ViewHolder {

        @BindView(R.id.tvTitle)
        TextView mTvTitle;

        @BindView(R.id.tvSelect)
        TextView mTvSelect;

        public AreaHolder3(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
