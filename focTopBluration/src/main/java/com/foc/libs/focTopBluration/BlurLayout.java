package com.foc.libs.focTopBluration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class BlurLayout extends RelativeLayout {

    public static String TAG = "blur_tag";


    //blur value
    private int blurValue = 25;
    //blur bitmap
    private Bitmap blurBitmap;
    //blur data array
    private int[] blurDataArray;


    long l;

    public BlurLayout(Context context) {
        super(context);
        init();
    }

    public BlurLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        init();
    }

    public BlurLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttrs(context,attrs);
        init();
    }

    void getAttrs(Context context, AttributeSet attrs) {


        if (attrs == null)
            return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurLayout);
        blurValue = typedArray.getInt(R.styleable.BlurLayout_blur, blurValue);



        typedArray.recycle();
    }

    void init() {

        getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            if (blurDataArray == null) {

                //#########
                l = System.currentTimeMillis();

                Bitmap bitmap = getBackground(this);

                Log.i(TAG, time() + "\tx=" + bitmap.getWidth() + " , y=" + bitmap.getHeight());

                //#########
                l = System.currentTimeMillis();

                //create scaled bitmap
                bitmap = Bitmap.createScaledBitmap(bitmap, this.getWidth(), this.getHeight(), false);

                //***scale
                //bitmap = Bitmap.createScaledBitmap(bitmap, (int) (getWidth() * 0.1f / 5), (int) (getHeight() * 1.0f / 5), false);

                Log.i(TAG, time() + "\tx=" + bitmap.getWidth() + " , y=" + bitmap.getHeight());

                //#########
                l = System.currentTimeMillis();
                bitmap = blurBitmap(bitmap);

                //***scale
                //bitmap = Bitmap.createScaledBitmap(bitmap, (int) (getWidth() * 0.1f * 5), (int) (getHeight() * 1.0f * 5), false);


                //copy
                blurBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, false);

                Log.i(TAG, time() + " end blur !");

                //#########
                l = System.currentTimeMillis();
                blurDataArray = getBitmapPixels(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight());
                Log.i(TAG, time() + " end read pixels");

            }


        });

    }

    public Bitmap getBlurBitmap() {
        return blurBitmap;
    }

    public int[] getBlurDataArray() {
        return blurDataArray;
    }

    public int[] getBitmapPixels(Bitmap bitmap, int x, int y, int width, int height) {
        //pixels array
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        //get pixels
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), x, y,
                width, height);

        final int[] subsetPixels = new int[width * height];
        for (int row = 0; row < height; row++) {
            System.arraycopy(pixels, (row * bitmap.getWidth()),
                    subsetPixels, row * width, width);
        }
        return subsetPixels;
    }

    public Bitmap blurBitmap(Bitmap bitmap) {

        //create empty blur bitmap
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate  new Renderscript
        RenderScript rs = RenderScript.create(getContext().getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the in/out Allocations with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(blurValue);

        //Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);

        //Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);

        //recycle the original bitmap
        bitmap.recycle();

        //After finishing everything, we destroy the Renderscript.
        rs.destroy();

        return outBitmap;
    }

    Bitmap getBackground(View v) {

        Drawable drawable = v.getBackground();

        return ((BitmapDrawable) drawable).getBitmap();
    }


    long time() {
        return System.currentTimeMillis() - l;
    }


    public int getBitmapWidth() {
        return blurBitmap.getWidth();
    }

    public int getBitmapHeight() {
        return blurBitmap.getHeight();
    }

}
