package com.wgu_wemery.c196pa.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ListHelper {

    public static void setSize(ListView listView){
        ListAdapter adapter = listView.getAdapter();
        if(adapter==null){
            return;
        }
        int height = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View singleView = adapter.getView(i,null,listView);
            singleView.measure(0,0);
            height += singleView.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height=height + (listView.getDividerHeight() * (listView.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
