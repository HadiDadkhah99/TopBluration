package com.foc.libs.topBluration;

import android.app.Application;
import android.util.Log;

import com.codemonkeylabs.fpslibrary.FrameDataCallback;
import com.codemonkeylabs.fpslibrary.TinyDancer;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TinyDancer.create()
                .show(getApplicationContext());

        //alternatively
        TinyDancer.create()
                .redFlagPercentage(.1f) // set red indicator for 10%....different from default
                .startingXPosition(200)
                .startingYPosition(600)
                .show(getApplicationContext());

        //you can add a callback to get frame times and the calculated
        //number of dropped frames within that window
        TinyDancer.create()
                .addFrameDataCallback(new FrameDataCallback() {
                    @Override
                    public void doFrame(long previousFrameNS, long currentFrameNS, int droppedFrames) {
                        //collect your stats here

                        Log.i("blur_frame", "-->  "+currentFrameNS);
                    }
                })
                .show(getApplicationContext());
    }
}
