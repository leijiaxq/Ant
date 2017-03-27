package com.vooda.ant.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.vooda.ant.R;

/**
 * Create by  leijiaxq
 * Date       2017/3/20 14:55
 * Describe   加入购物车红点动画
 */

public class AddCartAnimUtil {

    public static AddCartAnimUtil mInstance;

    public static AddCartAnimUtil getInstance() {
        if (mInstance ==null) {
            synchronized (AddCartAnimUtil.class) {
                if (mInstance == null) {
                    mInstance = new AddCartAnimUtil();
                }
            }
        }
        return mInstance;
    }

    private AddCartAnimUtil() {

    }

    /**
     * 运动的控件
     *
     * @return
     */
    public  Bitmap getAddDrawBitMap(Context context) {
        ImageView text = new ImageView(context);
        // 运动的控件，样式可以自定义
        text.setBackgroundResource(R.drawable.shape_order_number);
        return convertViewToBitmap(text);
    }

    /**
     * 创建动画层
     */
    @SuppressWarnings("ResourceType")
    private  ViewGroup createAnimLayout(Context context) {
        ViewGroup rootView = (ViewGroup) ((Activity) context).getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private  View addViewToAnimLayout(final ViewGroup vg, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    ViewGroup anim_mask_layout;

    /**
     * // 开始执行动画
     *
     * @param start_location
     */
    public  void setAnim(int[] start_location, Context context, View targetView) {

        final ImageView v  = new ImageView(context);
        // 设置buyImg的图片
        v.setImageBitmap(getAddDrawBitMap(context));

        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout(context);
        // 把动画小球添加到动画层
        anim_mask_layout.addView(v);
        final View view = addViewToAnimLayout(anim_mask_layout, v, start_location);
        // 这是用来存储动画结束位置的X、Y坐标
        int[] end_location = new int[2];
        // rl_gouwuche是小球运动的终点 一般是购物车图标
        targetView.getLocationInWindow(end_location);

        int width = targetView.getWidth();

        // 计算位移
        int endX = 0 - start_location[0] + 40 + width / 2;// 动画位移的X坐标
        int endY = end_location[1] - start_location[1];// 动画位移的y坐标
        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// 动画重复执行的次数
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
                if (mListener !=null) {
                    mListener.onAnimStart();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                if (mListener !=null) {
                    mListener.onAnimEnd();
                }

            }
        });

    }

    /**
     * 将定义的view装换成 bitmap格式
     *
     * @param view
     * @return
     */
    public  Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public interface AddCartListener {
        void onAnimStart();
        void onAnimEnd();
    }
    public AddCartListener mListener;

    public void setAddCartListener(AddCartListener listener) {
        this.mListener = listener;
    }



}
