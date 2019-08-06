package com.xiaomi.TestBulletin;

import android.content.Context;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class ListLRU {
    //默认的缓存大小
    private static int CAPACITY = 20;
    private Context context;
    private LinkedList<Object>  list = new LinkedList<Object>();;
    public ListLRU(Context context){
        this.context = context;
        this.initData();
    }
    public ListLRU(Context context, int capacity) {
        this.context = context;
        this.CAPACITY = capacity;
        this.initData();
    }

    //添加一个元素
    public synchronized void put(Object object) {

        if(list != null && list.contains(object)) {
            list.remove(object);
        }
        removeLeastVisitElement();
        list.addFirst(object);
    }

    //移除最近访问次数最少的元素
    private synchronized void  removeLeastVisitElement() {
        int size = size();
        if(size > (CAPACITY - 1) ) {
            Object object = list.removeLast();
        }
    }
    public synchronized Object get(int index) {
        return list.get(index);
    }
    //清空缓存
    public synchronized void clear() {
        list.clear();
    }
    //获取链接表的大小
    public int size() {
        if(list == null) {
            return 0;
        }
        return list.size();
    }
    //toString方法
    public String toString() {
        return list.toString();
    }

    public void save(){
        FileUtil.write(context,this.list);
        Log.i("listLRU","save");
    }

    public void initData(){
        this.list = (LinkedList<Object>)FileUtil.read(context);
        if(this.list == null || this.list.size() == 0){
            String[] a = {
                    "http://10.235.185.244:8080/tv-webpage/pages/test/test.html",
                    "http://10.235.210.114:4869/tv-webpage/pages/test/test.html",
                    "http://10.235.187.249:8080/tv-webpage/pages/test/test.html",
                    "http://10.235.187.249:8080/tv-webpage/pages/test/test.html",
                    "http://10.234.199.121:8080/tv-webpage/pages/test/test.html",
                    "http://10.235.187.249:8080/TV-webpage/pages/activity/gongdou/index.html",
                    "http://h5.tv.pandora.xiaomi.com/activity/qiqu2015/index_dev.html",
                    "http://h5.tv.n.duokanbox.com/weekly_recommend/16/index.html",
                    "http://10.235.187.249:8080/TV-webpage/pages/activity/choujiang/5/index.html",
                    "http://h5.tv.n.duokanbox.com/test/test.html",
            };

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for(int i=0;i < a.length;i++){
                Item ue = new Item();//给实体类赋值
                ue.setTime(df.format(new Date()));
                ue.setUrl(a[i]);
                this.put(ue);
            }
            save();
        }
    }
}
