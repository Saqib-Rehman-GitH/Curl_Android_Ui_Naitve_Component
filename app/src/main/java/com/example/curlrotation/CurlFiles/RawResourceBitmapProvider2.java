package com.example.curlrotation.CurlFiles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;


import com.example.curlrotation.R;
import com.shevelev.page_turning_lib.page_curling.textures_manager.repository.BitmapProvider;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class RawResourceBitmapProvider2 implements BitmapProvider {

    private Context context;
    public String bgColor = "#808080";
    ArrayList<Integer> bitmapIds = new ArrayList<Integer>(
            Arrays.asList(
                    R.raw.p240035, R.raw.p7240031, R.raw.p7240039, R.raw.p8010067, R.raw.p8150085, R.raw.p8150090
            )
    );
    public RawResourceBitmapProvider2(Context context){
        this.context = context;
    }

    public RawResourceBitmapProvider2(Context context, ArrayList<Integer> data){
        this.context = context;
//        this.bitmapIds
        this.bitmapIds = data;
        Log.i("CurlView","data.length " + data.size() );
    }

    @Override
    public int getTotal() {
        return bitmapIds.size();
    }

    private LinearLayout createView(int index){
        LinearLayout ll =  new LinearLayout(context);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(context);
        ll.setBackgroundColor(Color.parseColor(this.bgColor));
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(1000,1000);
        ll.setLayoutParams(llParams);
        tv.setText("At index" + index);
        tv.setTextColor(Color.GRAY);
//        tv.setLay
        ll.addView(tv);
//        ll.setRotation(90);
        return ll;
    }

    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    public static InputStream bitmap2InputStream(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }/*from   ww w  .ja v a2 s .co  m*/

    private Bitmap createViewBitmap(int index){
        LinearLayout ll =  new LinearLayout(this.context);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView tv = new TextView(this.context);
        ll.setBackgroundColor(Color.parseColor(this.bgColor));
        LinearLayout.LayoutParams llParams = new LinearLayout.LayoutParams(1000,1000);
        ll.setLayoutParams(llParams);
        tv.setText("At index" + index);
        tv.setTextColor(Color.GRAY);
//        tv.setLay
        ll.addView(tv);
        Animation animation = new RotateAnimation(0.0f, 10.0f, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(0);

//        tv.setAnimation(animation);
//        ll.setAnimation(animation);

        //CREATING BITMAP.
        ll.setDrawingCacheEnabled(true);
        ll.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        ll.layout(0, 0, ll.getMeasuredWidth(), ll.getMeasuredHeight());



        ll.buildDrawingCache(true);
        Bitmap b = Bitmap.createBitmap(ll.getDrawingCache());
//        Bitmap rotatedBitmap = animateRotation(10,0,b,1000,1000);
//        return rotatedBitmap;
        return b;
    }

    private Bitmap animateRotation(int degrees, float durationOfAnimation , Bitmap bitmap, int width, int height){
        long startTime = SystemClock.elapsedRealtime();
        long currentTime;
        float elapsedRatio = 0;
        Bitmap bufferBitmap = bitmap;

        Matrix matrix = new Matrix();

        while (elapsedRatio < 1){
            matrix.setRotate(elapsedRatio * degrees);
            bitmap = Bitmap.createBitmap(bufferBitmap, 0, 0, width, height, matrix, true);
            //draw your canvas here using whatever method you've defined
            currentTime = SystemClock.elapsedRealtime();
            elapsedRatio = (currentTime - startTime) / durationOfAnimation;
        }

        // As elapsed ratio will never exactly equal 1, you have to manually draw the last frame
        matrix = new Matrix();
        matrix.setRotate(degrees);
        return Bitmap.createBitmap(bufferBitmap, 0, 0, width, height, matrix, true);
//        bitmap = Bitmap.createBitmap(bufferBitmap, 0, 0, width, height, matrix, true);
        // draw the canvas again here as before
        // And you can now set whatever other notification or action you wanted to do at the end of your animation

    }


    @NonNull
    @Override
    public InputStream getBitmapStream(int index) {
        LinearLayout view = createView(index);
//        Bitmap viewBitmap = getBitmapFromView(view);
        Bitmap viewBitmap  = createViewBitmap(index);
//        return context.getResources().openRawResource(bitmapIds.get(index));
        return bitmap2InputStream(viewBitmap);
    }
}
