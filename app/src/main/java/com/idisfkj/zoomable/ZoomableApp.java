package com.idisfkj.zoomable;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by idisfkj on 17/3/5.
 * Email : idisfkj@gmail.com.
 */

public class ZoomableApp extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
