package com.example.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形适配器Item抽象类
 */
public abstract class TreeAdapterItem<D> {
    /**
     * 当前item的数据
     */
    protected D data;
    /**
     * 持有的子数据
     */
    protected List<TreeAdapterItem> childs;
    /**
     * 是否展开
     */
    protected boolean isExpand;
    /**
     * 布局所处层级
     */
    protected int level;

    public TreeAdapterItem(D data, int level) {
        this.data = data;
        this.level = level;
        childs = initChildsList(data);
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public List<TreeAdapterItem> getChilds() {
        return childs;
    }

    public void setChilds(List<TreeAdapterItem> childs) {
        this.childs = childs;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * 展开
     */
    public void onExpand() {
        isExpand = true;
    }

    /**
     * 折叠
     */
    public void onCollapse() {
        isExpand = false;
    }

    /**
     * 递归遍历所有的子数据，包括子数据的子数据
     *
     * @return List<TreeAdapterItem>
     */
    public List<TreeAdapterItem> getAllChilds() {

        ArrayList<TreeAdapterItem> treeAdapterItems = new ArrayList<>();

        for (int i = 0; i < childs.size(); i++) {

            TreeAdapterItem treeAdapterItem = childs.get(i);
            treeAdapterItems.add(treeAdapterItem);

            if (treeAdapterItem.isParent()) {

                List list = treeAdapterItem.getAllChilds();

                if (list != null && list.size() > 0) {

                    treeAdapterItems.addAll(list);
                }
            }
        }
        return treeAdapterItems;
    }

    /**
     * 是否持有子数据
     *
     * @return
     */
    public boolean isParent() {
        return childs != null && childs.size() > 0;
    }
    /**
     * 初始化子数据
     *
     * @param data
     * @return
     */
    protected abstract List<TreeAdapterItem> initChildsList(D data);
}