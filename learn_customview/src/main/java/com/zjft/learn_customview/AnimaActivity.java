package com.zjft.learn_customview;

import android.animation.LayoutTransition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zjft.learn_customview.view.CustomAnima;

public class AnimaActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_alpha;
    private Button bt_rotate;
    private Button bt_scale;
    private Button bt_trans;
    private Button bt_custom;

    private LinearLayout rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_anima);

        //添加布局动画
//        LinearLayout rootView = (LinearLayout) getLayoutInflater().from(this).inflate(R.id.ll_root, null);
        rootView = (LinearLayout) findViewById(R.id.ll_root);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1);
        scaleAnimation.setDuration(3000);
        LayoutAnimationController lac = new LayoutAnimationController(scaleAnimation, 0.5f);
        rootView.setLayoutAnimation(lac);
        //设置布局改变动画
//        LayoutTransition layoutTransition = new LayoutTransition();
//        layoutTransition.setAnimator(android.R.animator.fade_in,);
//        rootView.setLayoutTransition(layoutTransition);

        bt_alpha = (Button) findViewById(R.id.bt_alpha);
        bt_alpha.setOnClickListener(this);
        bt_rotate = (Button) findViewById(R.id.bt_rotate);
        bt_rotate.setOnClickListener(this);
        bt_scale = (Button) findViewById(R.id.bt_scale);
        bt_scale.setOnClickListener(this);
        bt_trans = (Button) findViewById(R.id.bt_trans);
        bt_trans.setOnClickListener(this);
        bt_custom = (Button) findViewById(R.id.bt_custom);
        bt_custom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_alpha:
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(1000);
                v.startAnimation(alphaAnimation);
//                v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.aa));
                break;
            case R.id.bt_rotate:
//                RotateAnimation rotateAnimation = new RotateAnimation(0, 360);
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(1000);
                v.startAnimation(rotateAnimation);
                break;
            case R.id.bt_scale:
                ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(1000);
                v.startAnimation(scaleAnimation);
                break;
            case R.id.bt_trans:
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, 500);
                translateAnimation.setDuration(1000);
                translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {}
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Toast.makeText(getApplicationContext(), "动画完成", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onAnimationRepeat(Animation animation) {}
                });
                v.startAnimation(translateAnimation);
                break;
            case R.id.bt_custom:
                CustomAnima customAnima = new CustomAnima();
                customAnima.setDuration(1000);
                v.startAnimation(customAnima);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Button button = new Button(getApplicationContext());
        button.setText("点击删除");
        rootView.addView(button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootView.removeView(v);
            }
        });

        return super.onOptionsItemSelected(item);
    }
}
