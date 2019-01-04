package com.example.sunke.myapplicationtv;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ErrorFragment extends android.support.v17.leanback.app.ErrorFragment {

    private static final String TAG = ErrorFragment.class.getSimpleName();
    private static final boolean TRANSLUCENT = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate");
        super.onCreate(savedInstanceState);

        setTitle(getResources().getString(R.string.app_name));
        setErrorContent();
    }

    void setErrorContent(){
        setImageDrawable(getActivity().getDrawable(R.drawable.error));
        setMessage(getResources().getString(R.string.error_fragment_message));
        setDefaultBackground(TRANSLUCENT);

        setButtonText(getResources().getString(R.string.dismiss_error));
        setButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将覆盖在MainFragment上的ErrorFragment移除
                getFragmentManager().beginTransaction().remove(ErrorFragment.this).commit();
            }
        });
    }
}
