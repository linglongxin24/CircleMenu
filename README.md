# Android属性动画应用超简单打造扇形，圆形菜单
>在上一篇( Android属性动画上手实现各种动画效果，自定义动画，抛物线等)[http://blog.csdn.net/linglongxin24/article/details/53084234]对属性动画做了简单的介绍
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
#2.如何获取菜单展开的坐标