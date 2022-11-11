package com.example.curlrotation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.curlrotation.CurlFiles.RawResourceBitmapProvider2;
import com.shevelev.page_turning_lib.page_curling.CurlView;

public class MainActivity extends AppCompatActivity {

    CurlView curlView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        //MAIN LAYOUT
        LinearLayout ll1 = new LinearLayout (this);
        LinearLayout.LayoutParams params_parent = new  LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        ll1.setLayoutParams(params_parent);
        ll1.setOrientation(LinearLayout.VERTICAL);
        ll1.setBackgroundColor(Color.BLUE);

        //CURL_VIEW
        CurlView curlView = new CurlView(this);
        LinearLayout.LayoutParams params_curl_view = new  LinearLayout.LayoutParams(700,400);
        params_curl_view.topMargin = 60;
        params_curl_view.gravity = Gravity.CENTER_HORIZONTAL;
        curlView.setLayoutParams(params_curl_view);
        RawResourceBitmapProvider2 provider = new RawResourceBitmapProvider2(this);
        curlView.setBitmapProvider( provider );
        ll1.addView(curlView);

        //ROTATING VIEW.
        Animation animation = new RotateAnimation(0.0f, 30.0f, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(0);
        curlView.setAnimation(animation);

        setContentView(ll1);
    }

}