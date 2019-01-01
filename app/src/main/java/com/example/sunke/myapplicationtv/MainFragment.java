package com.example.sunke.myapplicationtv;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.Presenter;
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
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG,"onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        setupUIElement();
        loadRows();
    }

    private void loadRows(){
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        //GridItemPresenter
        HeaderItem gridItemPresenterHeader = new HeaderItem(0,"GridItemPresenter");
        GridItemPresenter gridItemPresenter = new GridItemPresenter();
        ArrayObjectAdapter gridRowAdapter = new ArrayObjectAdapter(gridItemPresenter);
        gridRowAdapter.add("ITEM 1");
        gridRowAdapter.add("ITEM 2");
        gridRowAdapter.add("ITEM 3");
        ListRow listRow = new ListRow(gridItemPresenterHeader,gridRowAdapter);
        mRowsAdapter.add(listRow);


        //CardPresenter
        HeaderItem cardPresenterHeader = new HeaderItem(1,"CardPresenter");
        CardPresenter cardPresenter = new CardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);

        for(int i=0;i<3;i++){
            Movie movie = new Movie();
            movie.setTitle("title" + i);
            movie.setStudio("studio" + i);
            movie.setCardImageUrl("http://heimkehrend.raindrop.jp/kl-hacker/wp-content/uploads/2014/08/DSC02580.jpg");
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
