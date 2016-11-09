# Android属性动画应用超简单代码打造酷炫扇形(卫星)，圆形菜单
>在上一篇[Android属性动画上手实现各种动画效果，自定义动画，抛物线等] (http://blog.csdn.net/linglongxin24/article/details/53084234)对属性动画做了简单的介绍
，如果不了解的可以去了解一下。今天突发奇想，想到既然属性动画这么强大，我们能不能用属性动画去打造一个扇形的或者圆形的菜单呢？因为属性动画在发生属性变化后还是
可以操作的，这样就符合了菜单可操作的功能。其实实现起来超简单。
先看效果图 

![效果图](https://github.com/linglongxin24/CircleMenu/blob/master/screenshorts/effect.gif?raw=true)

#1.我们首先要用到一个属性动画
以下是实现一个最基本的简单的对imageView的x坐标和y坐标进行移动的动画。

```java
   /**
    * 第一个参数代表要操作的对象
    * 第二个参数代表要操作的对象的属性
    * 第三个参数代表要操作的对象的属性的起始值
    * 第四个参数代表要操作的对象的属性的终止值
    */
   ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(imageViews, "translationX", 0, 500);
   ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(imageViews, "translationY", 0, 500);
   /**动画集合，用来编排动画**/
   AnimatorSet animatorSet = new AnimatorSet();
   /**设置动画时长**/
   animatorSet.setDuration(500);
   /**设置同时播放x方向的位移动画和y方向的位移动画**/
   animatorSet.play(objectAnimatorX).with(objectAnimatorY);
   /**开始播放动画**/
   animatorSet.start();
```
#2.如何获取菜单展开的坐标，先复习下数学

这个是我的手稿，画的比较粗糙，请大家见谅

![效果图](https://github.com/linglongxin24/CircleMenu/blob/master/screenshorts/math.jpg?raw=true)

#3.如何用代码计算，计算公式

>我们由上图可以推导出计算公式如下：  
圆点坐标：(x0,y0)     
半径：r    
角度：a0   
则圆上任一点为：（x1,y1）     
x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )  
y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )  

#4.代码实现

总共分为五步,代码如下：

```java
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
```

#5.如何关闭菜单
>启示关闭菜单和打开菜单的操作是相反的，只需要将起始坐标和终止坐标调换就可以了。

```java

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

```
#6.菜单按钮的旋转动画

其实就是打开菜单时进行45°旋转，关闭的时候再旋转会原来的0°；

```java
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
```
#7.圆形菜单
圆形菜单其实就是对360度进行等分，原理一样，代码如下：

```java

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

```

#8.[GitHub](https://github.com/linglongxin24/CircleMenu)


 