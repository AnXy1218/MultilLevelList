package com.example.bean;

import java.util.ArrayList;
import java.util.List;

public class AreaFactory {
    /**
     * 创建一个地区对象
     * @param id
     * @param title
     * @return
     */
    public static Area createArea(int id,String title){
        Area area = new Area();
        area.setId(id);
        area.setTitle(title);
        return area;
    }

    /**
     * 创建一个地区列表
     * @param titles
     * @return
     */
    public static List<Area> createAreaList(String[] titles){
        List<Area> data = new ArrayList<>();
        for (int i = 0;i < titles.length;i++){
            data.add(createArea(i,titles[i]));
        }
        return data;
    }
}
