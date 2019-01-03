package com.example.sunke.myapplicationtv;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;

/**
 * 简单的背景更换处理
 */
public class SimpleBackgroundManager {

    private static final String TAG = SimpleBackgroundManager.class.getSimpleName();

    //定义默认背景的ID
    private final int DEFAULT_BACKGROUND_RES_ID = R.drawable.default_background;
    //定义默认背景
    private static Drawable mDefaultBackground;

    //定义Activity
    private Activity mActivity;
    //定义BackgroundManager
    private BackgroundManager mBackgroundManager;

    //构造方法
    public SimpleBackgroundManager(Activity activity){
        mActivity = activity;
        //默认的背景图片
        mDefaultBackground = activity.getDrawable(DEFAULT_BACKGROUND_RES_ID);
        //获得单例实例
        mBackgroundManager = BackgroundManager.getInstance(activity);
        //更新背景前附加到Window
        mBackgroundManager.attach(activity.getWindow());
        activity.getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());

    }

    //改变背景
    public void updateBackground(Drawable drawable){
        mBackgroundManager.setDrawable(drawable);
    }

    //将背景更新为默认图像
    public void clearBackground(){
        mBackgroundManager.setDrawable(mDefaultBackground);
    }

}
