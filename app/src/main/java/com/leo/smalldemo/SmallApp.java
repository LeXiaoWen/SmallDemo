package com.leo.smalldemo;

import android.app.Application;

import net.wequick.small.Small;

/**
 * Created by Leo on 2017/11/22.
 */

public class SmallApp extends Application{
    public SmallApp() {
        Small.preSetUp(this);
    }
}
