package com.example.sunke.myapplicationtv;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainFragment extends BrowseFragment {

    //得到类的简称
    private static final String TAG = MainFragment.class.getSimpleName();

    private ArrayObjectAdapter mRowsAdapter;
    private static int GRID_ITEM_WIDTH = 300;
    private static int GRID_ITEM_HEIGHT = 200;

    //private static SimpleBackgroundManager simpleBackgroundManager = null;
    private static PicassoBackgroundManager  picassoBackgroundManager  = null;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        setupUIElement();

        loadRows();

        //设置监听事件
        setupEventListeners();

        //simpleBackgroundManager = new SimpleBackgroundManager(getActivity());
        picassoBackgroundManager = new PicassoBackgroundManager(getActivity());
    }

    /**
     * 当item被选中时的监听事件
     */
    private void setupEventListeners(){
        //父类中的方法，可直接使用
        setOnItemViewSelectedListener(new ItemViewSelectedListener());
        setOnItemViewClickedListener(new ItemViewClickedListener());
    }

    public final class ItemViewClickedListener implements OnItemViewClickedListener{

        /**
         * 当点击item时的事件
         * @param viewHolder 视图支持
         * @param item
         * @param viewHolder1
         * @param row
         */
        @Override
        public void onItemClicked(Presenter.ViewHolder viewHolder, Object item, RowPresenter.ViewHolder viewHolder1, Row row) {
            if(item instanceof Movie){
                Movie movie = (Movie)item;
                Log.d(TAG,"Item:" + item.toString());
                Intent intent = new Intent(getActivity(),DetailsActivity.class);
                //键值对的形式传值
                intent.putExtra(DetailsActivity.MOVIE,movie);

                getActivity().startActivity(intent);
            }else if(item instanceof String){
                if(item == "ErrorFragment"){
                    Intent intent = new Intent(getActivity(),ErrorActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    public final class ItemViewSelectedListener implements OnItemViewSelectedListener{

        /**
         * 当项目被选中时的监听事件
         * @param viewHolder 视图支持
         * @param item 项目
         * @param viewHolder1 行的视图支持
         * @param row 行
         */
        @Override
        public void onItemSelected(Presenter.ViewHolder viewHolder, Object item,
                                   RowPresenter.ViewHolder viewHolder1, Row row) {
            //判断item是String类型还是Movie类型
            if(item instanceof String){
                picassoBackgroundManager.updateBackgroundWithDelay("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
                //simpleBackgroundManager.clearBackground();
            }else if(item instanceof  Movie){
                //simpleBackgroundManager.updateBackground(getActivity().getDrawable(R.drawable.movie));
                picassoBackgroundManager.updateBackgroundWithDelay(((Movie) item).getCardImageUrl());
            }
        }
    }

    private void loadRows(){
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        //GridItemPresenter
        HeaderItem gridItemPresenterHeader = new HeaderItem(0,"GridItemPresenter");
        GridItemPresenter gridItemPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridItemPresenter);
//        gridRowAdapter.add("ITEM 1");
        gridRowAdapter.add("ErrorFragment");
        gridRowAdapter.add("ITEM 2");
        gridRowAdapter.add("ITEM 3");
        ListRow listRow = new ListRow(gridItemPresenterHeader,gridRowAdapter);
        mRowsAdapter.add(listRow);


        //CardPresenter
        HeaderItem cardPresenterHeader = new HeaderItem(1,"CardPresenter");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for(int i=0;i<10;i++){
            Movie movie = new Movie();
            movie.setTitle("title" + i);
            movie.setStudio("studio" + i);
            if(i/3 == 0){
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
            }else if(i % 3 == 1){
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02630.jpg");
            }else{
                movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02529.jpg");
            }
            //movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
            cardRowAdapter.add(movie);
        }
        ListRow listRow1 = new ListRow(cardPresenterHeader,cardRowAdapter);
        mRowsAdapter.add(listRow1);

        setAdapter(mRowsAdapter);
    }

    private void setupUIElement(){

        //设置rows部分的右上角图标
        //setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.app_icon_yourCompany));
        //设置标题
        setTitle("Hello TV");

        //设置headers隐藏，到边缘左键还能显示
        setHeadersState(HEADERS_ENABLED);
        setHeadersTransitionOnBackEnabled(true);

        //设置header背景色
        setBrandColor(getResources().getColor(R.color.fastlane_background));

        //设置搜索图标监听事件
        setOnSearchClickedListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

            }
        });

        //设置搜索的颜色
        setSearchAffordanceColor(getResources().getColor(R.color.search_opaque));

    }


    public class GridItemPresenter extends Presenter {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup) {

            TextView view = new TextView(viewGroup.getContext());
            //设置布局参数
            view.setLayoutParams(new ViewGroup.LayoutParams(GRID_ITEM_WIDTH,GRID_ITEM_HEIGHT));
            //设置视图是否可以接受焦点
            view.setFocusable(true);
            //设置视图是否可以在触摸状态下获得焦点
            view.setFocusableInTouchMode(true);
            //设置视图的背景色
            view.setBackgroundColor(getResources().getColor(R.color.default_background));
            //设置文本颜色
            view.setTextColor(Color.WHITE);
            //设置文本的重心，即显示在text的左侧，右侧，中间，等等
            view.setGravity(Gravity.CENTER);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, Object item) {
            ((TextView)viewHolder.view).setText((String)item);
        }

        @Override
        public void onUnbindViewHolder(ViewHolder viewHolder) {

        }
    }
}
