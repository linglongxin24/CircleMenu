package cn.bluemobi.dylan.circlemenu;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.PointF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "CircleMenu";

    private FrameLayout content_main;
    private ImageView iv8;
    private ImageView iv7;
    private ImageView iv6;
    private ImageView iv5;
    private ImageView iv4;
    private ImageView iv3;
    private ImageView iv2;
    private ImageView iv1;

    private ImageView iv18;
    private ImageView iv17;
    private ImageView iv16;
    private ImageView iv15;
    private ImageView iv14;
    private ImageView iv13;
    private ImageView iv12;
    private ImageView iv11;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<ImageView> imageViews2 = new ArrayList<>();
    private final int radius1 = 500;
    private final int radius2 = 300;

    private void assignViews() {
        content_main = (FrameLayout) findViewById(R.id.content_main);
        iv8 = (ImageView) findViewById(R.id.iv8);
        imageViews.add(iv8);
        iv7 = (ImageView) findViewById(R.id.iv7);
        imageViews.add(iv7);
        iv6 = (ImageView) findViewById(R.id.iv6);
        imageViews.add(iv6);
        iv5 = (ImageView) findViewById(R.id.iv5);
        imageViews.add(iv5);
        iv4 = (ImageView) findViewById(R.id.iv4);
        imageViews.add(iv4);
        iv3 = (ImageView) findViewById(R.id.iv3);
        imageViews.add(iv3);
        iv2 = (ImageView) findViewById(R.id.iv2);
        imageViews.add(iv2);
        iv1 = (ImageView) findViewById(R.id.iv1);

        iv18 = (ImageView) findViewById(R.id.iv18);
        imageViews2.add(iv18);
        iv17 = (ImageView) findViewById(R.id.iv17);
        imageViews2.add(iv17);
        iv16 = (ImageView) findViewById(R.id.iv16);
        imageViews2.add(iv16);
        iv15 = (ImageView) findViewById(R.id.iv15);
        imageViews2.add(iv15);
        iv14 = (ImageView) findViewById(R.id.iv14);
        imageViews2.add(iv14);
        iv13 = (ImageView) findViewById(R.id.iv13);
        imageViews2.add(iv13);
        iv12 = (ImageView) findViewById(R.id.iv12);
        imageViews2.add(iv12);
        iv11 = (ImageView) findViewById(R.id.iv11);
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public void onClick(View v) {
        if (v.getId() == iv1.getId()) {
            Boolean isShowing = (Boolean) iv1.getTag();
            if (null == isShowing || isShowing == false) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1, "rotation", 0, 45);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                iv1.setTag(true);
                showSectorMenu();
            } else {
                iv1.setTag(false);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv1, "rotation", 45, 0);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                closeSectorMenu();
            }
        } else if (v.getId() == iv11.getId()) {
            Boolean isShowing = (Boolean) iv11.getTag();
            if (null == isShowing || isShowing == false) {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv11, "rotation", 0, 45);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                iv11.setTag(true);
                showCircleMenu();
            } else {
                iv11.setTag(false);
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv11, "rotation", 45, 0);
                objectAnimator.setDuration(500);
                objectAnimator.start();
                closeCircleMenu();
            }
        } else {
            Toast.makeText(this, "点击了第" + (imageViews.indexOf(v) == -1 ? imageViews2.indexOf(v) : imageViews.indexOf(v)) + "个", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 显示扇形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showSectorMenu() {
        /***第一步，遍历所要展示的菜单ImageView*/
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            /***第二步，根据菜单个数计算每个菜单之间的间隔角度*/
            int avgAngle = (90 / (imageViews.size() - 1));
            /**第三步，根据间隔角度计算出每个菜单相对于水平线起始位置的真实角度**/
            int angle = avgAngle * i;
            Log.d(TAG, "angle=" + angle);
            /**
             * ﻿﻿
             * 圆点坐标：(x0,y0)
             * 半径：r
             * 角度：a0
             * 则圆上任一点为：（x1,y1）
             * x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
             * y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
             */
            /**第四步，根据每个菜单真实角度计算其坐标值**/
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius1;
            Log.d(TAG, point.toString());

            /**第五步，根据坐标执行位移动画**/
            /**
             * 第一个参数代表要操作的对象
             * 第二个参数代表要操作的对象的属性
             * 第三个参数代表要操作的对象的属性的起始值
             * 第四个参数代表要操作的对象的属性的终止值
             */
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", 0, point.x);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", 0, point.y);
            /**动画集合，用来编排动画**/
            AnimatorSet animatorSet = new AnimatorSet();
            /**设置动画时长**/
            animatorSet.setDuration(500);
            /**设置同时播放x方向的位移动画和y方向的位移动画**/
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            /**开始播放动画**/
            animatorSet.start();
        }
    }


    /**
     * 关闭扇形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeSectorMenu() {
        for (int i = 0; i < imageViews.size(); i++) {
            PointF point = new PointF();
            int avgAngle = (90 / (imageViews.size() - 1));
            int angle = avgAngle * i;
            Log.d(TAG, "angle=" + angle);
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius1;
            point.y = (float) -Math.sin(angle * (Math.PI / 180)) * radius1;
            Log.d(TAG, point.toString());

            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews.get(i), "translationX", point.x, 0);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews.get(i), "translationY", point.y, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
        }
    }


    /**
     * 显示圆形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void showCircleMenu() {
        for (int i = 0; i < imageViews2.size(); i++) {
            PointF point = new PointF();
            int avgAngle = (360 / (imageViews2.size() - 1));
            int angle = avgAngle * i;
            Log.d(TAG, "angle=" + angle);
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius2;
            point.y = (float) Math.sin(angle * (Math.PI / 180)) * radius2;
            Log.d(TAG, point.toString());
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews2.get(i), "translationX", 0, point.x);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews2.get(i), "translationY", 0, point.y);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
        }
    }

    /**
     * 关闭圆形菜单
     */
    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void closeCircleMenu() {
        for (int i = 0; i < imageViews2.size(); i++) {
            PointF point = new PointF();
            int avgAngle = (360 / (imageViews2.size() - 1));
            int angle = avgAngle * i;
            Log.d(TAG, "angle=" + angle);
            point.x = (float) Math.cos(angle * (Math.PI / 180)) * radius2;
            point.y = (float) Math.sin(angle * (Math.PI / 180)) * radius2;

            Log.d(TAG, point.toString());
            ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews2.get(i), "translationX", point.x, 0);
            ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews2.get(i), "translationY", point.y, 0);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(500);
            animatorSet.play(objectAnimatorX).with(objectAnimatorY);
            animatorSet.start();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
