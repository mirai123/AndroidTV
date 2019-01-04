package com.example.sunke.myapplicationtv;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v17.leanback.widget.BaseCardView;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.Presenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.example.sunke.utils.Utils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URI;

public class CardPresenter extends Presenter {

    private static final String TAG = CardPresenter.class.getSimpleName();
    private static Context mContext;

    private static final int CARD_WIDTH = 313;
    private static final int CARD_HEIGHT = 176;

    //保存ImageCardView，用于显示Movie项目的UI
    static class ViewHolder extends Presenter.ViewHolder {

        private Movie mMovie;
        private ImageCardView mCardView;
        private Drawable mDefaultCardImage;
        private PicassoImageCardViewTarget mImageCardViewTarget;

        public Movie getMovie() {
            return mMovie;
        }

        public void setMovie(Movie mMovie) {
            this.mMovie = mMovie;
        }

        public ImageCardView getCardView() {
            return mCardView;
        }

        public void setCardView(ImageCardView mCardView) {
            this.mCardView = mCardView;
        }

        public Drawable getmDefaultCardImage() {
            return mDefaultCardImage;
        }

        public void setDefaultCardImage(Drawable mDefaultCardImage) {
            this.mDefaultCardImage = mDefaultCardImage;
        }

        //有参的构造方法
        public ViewHolder(View view) {
            super(view);
            mCardView = (ImageCardView)view;
            mImageCardViewTarget = new PicassoImageCardViewTarget(mCardView);
            mDefaultCardImage = mContext.getResources().getDrawable(R.drawable.movie);
        }

        //使用Picasso更新图像
        protected void updateCardViewImage(URI uri){
            Picasso.with(mContext)
                    .load(uri.toString())
                    .resize(Utils.convertDpToPixel(mContext, CARD_WIDTH),
                            Utils.convertDpToPixel(mContext, CARD_HEIGHT))
                    .error(mDefaultCardImage)
                    //将图像加载到imageView
                    .into(mImageCardViewTarget);
        }
    }

    //将图像加载到imageView
    public static class PicassoImageCardViewTarget implements Target {
        private ImageCardView mImageCardView;

        public PicassoImageCardViewTarget(ImageCardView imageCardView){
            mImageCardView = imageCardView;
        }
        @Override
        /**
         * 图像成功加载时回调
         */
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            Drawable bitmapDrawable = new BitmapDrawable(mContext.getResources(),bitmap);
            mImageCardView.setMainImage(bitmapDrawable);
        }

        @Override
        /**
         * 图像加载失败时回调
         */
        public void onBitmapFailed(Drawable drawable) {
            mImageCardView.setMainImage(drawable);
        }

        @Override
        /**
         * 请求提交之前调用回调
         */
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {

        //打印debug信息
        Log.d(TAG,"onCreateViewHolder");

        mContext = viewGroup.getContext();

        ImageCardView cardView = new ImageCardView(mContext);
        //
        cardView.setCardType(BaseCardView.CARD_TYPE_INFO_UNDER);
        //标题和内容文本区域将始终显示
        cardView.setInfoVisibility(BaseCardView.CARD_REGION_VISIBLE_ALWAYS);
        //设置card可获得焦点
        cardView.setFocusable(true);
        //设置card在触摸状态下可获得焦点
        cardView.setFocusableInTouchMode(true);
        //设置card背景色
        cardView.setBackgroundColor(mContext.getResources().getColor(R.color.fastlane_background));

        ViewHolder viewHolder = new ViewHolder(cardView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Presenter.ViewHolder viewHolder, Object item) {

        Movie movie = (Movie)item;
        ((ViewHolder)viewHolder).setMovie(movie);

        Log.d(TAG,"onBindViewHolder");
        if(movie.getCardImageUrl() != null){
            //放入视图的标题
            ((ViewHolder) viewHolder).mCardView.setTitleText(movie.getTitle());
            //放入视图的内容
            ((ViewHolder) viewHolder).mCardView.setContentText(movie.getStudio());
            //放入视图的尺寸
            ((ViewHolder) viewHolder).mCardView.setMainImageDimensions(CARD_WIDTH,CARD_HEIGHT);
            //放入视图的图片
            //((ViewHolder)viewHolder).mCardView.setMainImage(((ViewHolder) viewHolder).getmDefaultCardImage());

            ((ViewHolder) viewHolder).updateCardViewImage(movie.getCardImageURI());
        }


    }

    @Override
    public void onUnbindViewHolder(Presenter.ViewHolder viewHolder) {
        Log.d(TAG,"onUnbindViewHolder");
    }

    @Override
    public void onViewAttachedToWindow(Presenter.ViewHolder holder) {

    }
}
