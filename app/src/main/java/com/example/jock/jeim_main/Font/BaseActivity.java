package com.example.jock.jeim_main.Font;

import android.app.Application;

/**
 * Created by dong jae on 2017-10-18.
 */

public class BaseActivity extends Application {
    @Override
    public void onCreate() {
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Roboto-Regular.ttf");
    }
}
