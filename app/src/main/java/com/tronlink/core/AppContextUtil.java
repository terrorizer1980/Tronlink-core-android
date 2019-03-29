package com.tronlink.core;

import android.content.Context;

public class AppContextUtil {

    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static Context getContext(){
        if(mContext == null) throw new IllegalArgumentException("need init");
        return mContext;
    }
}
