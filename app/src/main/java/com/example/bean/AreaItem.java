package com.example.bean;

import java.util.ArrayList;
import java.util.List;

public class AreaItem extends TreeAdapterItem<Area>{

    public AreaItem(Area data, int level) {
        super(data, level);
    }

    @Override
    protected List<TreeAdapterItem> initChildsList(Area data) {
        ArrayList<TreeAdapterItem> oneChilds= new ArrayList<>();
        List<Area> areas = data.getAreas();
        if (areas == null) {//如果没有二级数据就直接返回.
            return null;
        }
        for (int i = 0; i < areas.size(); i++) {//遍历二级数据.
            AreaItem twoItem = new AreaItem(areas.get(i),getLevel()+1);//创建二级条目。
            oneChilds.add(twoItem);
        }
        return oneChilds;
    }
}
