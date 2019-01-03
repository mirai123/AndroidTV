package com.example.sunke.myapplicationtv;

import android.app.Activity;
import android.os.Bundle;

public class DetailsActivity extends Activity {

    public static final String MOVIE = "Movie";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
    }
}
