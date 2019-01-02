package com.example.sunke.myapplicationtv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v17.leanback.app.BackgroundManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class PicassoBackgroundManager {

    private static final String TAG = PicassoBackgroundManager.class.getSimpleName();

    private static int BACKGROUND_UPDATE_DELAY = 500;
    private final int DEFAULT_BACKGROUND_RES_ID = R.drawable.default_background;
    private static Drawable mDefaultBackground;
    private final Handler mHandler = new Handler(Looper.getMainLooper());

    private Activity mActivity;
    private BackgroundManager mBackgroundManager = null;
    private DisplayMetrics mMetrics;
    private URI mBackgroundUri;
    private PicassoBackgroundManagerTarget mBackgroundTarget;

    Timer mBackgroundTimer;

    public PicassoBackgroundManager (Activity activity){
        mActivity = activity;
        mDefaultBackground = activity.getDrawable(DEFAULT_BACKGROUND_RES_ID);
        mBackgroundManager = BackgroundManager.getInstance(activity);
        mBackgroundManager.attach(activity.getWindow());
        mBackgroundTarget = new PicassoBackgroundManagerTarget(mBackgroundManager);
        mMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(mMetrics);
    }

    private void startBackgroundTimer(){
        if(mBackgroundTimer != null){
            mBackgroundTimer.cancel();
        }
        mBackgroundTimer = new Timer();

        mBackgroundTimer.schedule(new UpdateBackgroundTask(),BACKGROUND_UPDATE_DELAY);
    }

    private class UpdateBackgroundTask extends TimerTask{

        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if(mBackgroundUri != null){
                        updateBackground(mBackgroundUri);
                    }
                }
            });
        }
    }

    public void updateBackgroundWithDelay(String url){
        try {
            URI uri = new URI(url);
            updateBackgroundWithDelay(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Log.e(TAG,e.toString());
        }
    }

    public void updateBackgroundWithDelay(URI uri){
        mBackgroundUri = uri;
        startBackgroundTimer();
    }

    private void updateBackground(URI uri){
        Picasso.with(mActivity)
                .load(uri.toString())
                .resize(mMetrics.widthPixels, mMetrics.heightPixels)
                .centerCrop()
                .error(mDefaultBackground)
                .into(mBackgroundTarget);
    }

    public class PicassoBackgroundManagerTarget implements Target{
        BackgroundManager mBackgroundManager;

        public PicassoBackgroundManagerTarget(BackgroundManager backgroundManager){
            this.mBackgroundManager = backgroundManager;
        }
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            this.mBackgroundManager.setBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable drawable) {
            this.mBackgroundManager.setDrawable(drawable);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

        @Override
        public boolean equals(Object obj) {
            if(this == obj){
                return true;
            }
            if(obj == null || getClass() != obj.getClass()){
                return false;
            }
            PicassoBackgroundManagerTarget that = (PicassoBackgroundManagerTarget) obj;
            if(!mBackgroundManager.equals(that.mBackgroundManager)){
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return mBackgroundManager.hashCode();
        }
    }



}
