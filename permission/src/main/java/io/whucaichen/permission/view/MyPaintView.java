package io.whucaichen.permission.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.whucaichen.permission.R;

/**
 * Created by Chance on 2017/5/9.
 */

public class MyPaintView extends View {
    private List<Point> allPoints = new ArrayList<Point>();//保存所有的坐标点

    public MyPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ToolBar);
        super.setBackgroundColor(ta.getColor(R.styleable.ToolBar__background, 0));
        ta.recycle();
        super.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Point point = new Point((int) event.getX(), (int) event.getY());
                if (event.getAction() == MotionEvent.ACTION_DOWN) {//判断按下
                    allPoints = new ArrayList<Point>();//开始新的记录
                    allPoints.add(point);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    allPoints.add(point);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    allPoints.add(point);
                    MyPaintView.this.postInvalidate();//重绘
                }
                return true;//表示下面的不再执行了
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        if (allPoints.size() > 1) {
            Iterator<Point> iterator = allPoints.iterator();
            Point firstPoint = null;//开始点
            Point lastpPoint = null;//结束点
            while (iterator.hasNext()) {
                if (firstPoint == null) {//找到开始点
                    firstPoint = (Point) iterator.next();
                } else {
                    if (lastpPoint != null) {
                        firstPoint = lastpPoint;
                    }
                    lastpPoint = (Point) iterator.next();
                    canvas.drawLine(firstPoint.x, firstPoint.y, lastpPoint.x, lastpPoint.y, paint);//画线
                }
            }
        }
    }
}
