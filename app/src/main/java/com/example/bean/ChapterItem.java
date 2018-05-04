package com.example.bean;

import java.util.ArrayList;
import java.util.List;

public class ChapterItem extends TreeAdapterItem<Chapter> {

    public ChapterItem(Chapter data, int level) {
        super(data,level);
    }

    @Override
    protected List<TreeAdapterItem> initChildsList(Chapter data) {
        ArrayList<TreeAdapterItem> oneChilds= new ArrayList<>();
        List<Chapter> citys = data.getChapters();
        if (citys == null) {//如果没有二级数据就直接返回.
            return null;
        }
        for (int i = 0; i < citys.size(); i++) {//遍历二级数据.
            ChapterItem twoItem = new ChapterItem(citys.get(i),getLevel()+1);//创建二级条目。
            oneChilds.add(twoItem);
        }
        return oneChilds;
    }
}
