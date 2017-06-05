package com.zjft.learn_customview.view;

import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by Chance on 16/09/04.
 */
public class CustomAnima extends Animation {
    final static String TAG = "CustomAnima";

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        Log.e(TAG, String.valueOf(width));
        Log.e(TAG, String.valueOf(height));
        Log.e(TAG, String.valueOf(parentWidth));
        Log.e(TAG, String.valueOf(parentHeight));
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

//        t.setAlpha(interpolatedTime);
//        t.getMatrix().setTranslate(200*interpolatedTime, 200*interpolatedTime);
        t.getMatrix().setTranslate((float) (Math.sin(20*interpolatedTime)*50), 0);
    }
}
