package com.zjft.learn_customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Chance on 16/09/03.
 */
public class RotateAnima extends View {
    public RotateAnima(Context context) {
        super(context);
        inintProperties();
    }

    public RotateAnima(Context context, AttributeSet attrs) {
        super(context, attrs);
        inintProperties();
    }

    public RotateAnima(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inintProperties();
    }

    private Paint paint;
    private float degree = 0;

    private void inintProperties(){
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.save();
//        canvas.rotate(degree);
        canvas.rotate(degree, 100, 100);
        canvas.drawRect(0, 0, 200, 200, paint);

        degree++;
        canvas.restore();

        invalidate();//使View无效，重绘
    }
}
