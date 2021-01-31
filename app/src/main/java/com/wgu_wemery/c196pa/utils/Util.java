package com.wgu_wemery.c196pa.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.Switch;

public class Util {

    public boolean isNightMode = false;

    public Util(Context mCtx) {

        int mode = mCtx.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        //switch through the mode
        switch (mode){
            case Configuration.UI_MODE_NIGHT_YES:
                isNightMode = true;
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                isNightMode = false;
                break;
        }

    }
}
