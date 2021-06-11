package com.foc.libs.focTopBluration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewParent;

public class BlurItem extends androidx.appcompat.widget.AppCompatImageView {

    public static String TAG = "blur_tag";

    //blur value
    private int blurValue = 1;
    //blur bitmap
    private Bitmap blurBitmap;
    //blur data array
    private int[] blurDataArray;

    //blur layout
    BlurLayout mainParent;

    public BlurItem(Context context) {
        super(context);
        init(context, null);
    }

    public BlurItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs) {

        positionListener();

        if (attrs == null)
            return;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BlurLayout);
        blurValue = typedArray.getInt(R.styleable.BlurLayout_blur, blurValue);

        typedArray.recycle();
    }


    //position change listener
    void positionListener() {


        getViewTreeObserver().addOnGlobalLayoutListener(() -> {

            if (mainParent == null) {

                mainParent = getBlurParent(getParent());

                if (mainParent != null) {
                    blurBitmap = mainParent.getBlurBitmap();
                    blurDataArray = mainParent.getBlurDataArray();

                }

            }

            startDrawBlur();

        });

        getViewTreeObserver().addOnScrollChangedListener(() -> {
            // Log.i(TAG, "ScrollChangedListener: ");
            startDrawBlur();
        });


    }

    BlurLayout getBlurParent(ViewParent v) {

        if (v == null)
            return null;
        else if (v instanceof BlurLayout)
            return (BlurLayout) v;

        return getBlurParent(v.getParent());

    }

    void startDrawBlur() {

        if (mainParent == null)
            return;


        //find parent location on screen
        int[] parentLocation = new int[2];
        mainParent.getLocationOnScreen(parentLocation);

        //find view location on screen
        int[] viewLocation = new int[2];
        this.getLocationOnScreen(viewLocation);

        //find view location in parent
        int X = viewLocation[0] - parentLocation[0];
        int Y = viewLocation[1] - parentLocation[1];


        //draw blur bitmap
        drawBlur(X, Y);

    }

    long l;

    void drawBlur(int X, int Y) {

        try {
            int startX = X;
            int startY = Y;
            int removedY = 0;
            int removedX = 0;
            int startGetYPixel = 0;
            int startGetXPixel = 0;

            /* Start check Y coordinate */
            if (Y < 0) {
                startY = 0;
                removedY = Math.abs(Y);
                startGetYPixel = removedY;
            } else if (Y + getHeight() > mainParent.getHeight()) {
                startY = Y;
                removedY = (getHeight() + Y) - mainParent.getHeight();
                startGetYPixel = 0;
            }
            /*** End check Y coordinate ***/


            /* Start check Y coordinate */
            if (X < 0) {
                startX = 0;
                removedX = Math.abs(X);
                startGetXPixel = removedX;
            } else if (X + getWidth() > mainParent.getWidth()) {

                startX = X;
                removedX = (getWidth() + X) - mainParent.getWidth();
                startGetXPixel = 0;

            }

            Log.i(TAG, "drawBlur: " + removedX);

            //create mini Bitmap
            Bitmap miniBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);


            //create mini array
            int[] miniBlurArray = getBitmapPixels(blurBitmap, startX, startY, miniBitmap.getWidth()-removedX, miniBitmap.getHeight() - removedY);

            //set pixels
            miniBitmap.setPixels(miniBlurArray, 0, miniBitmap.getWidth()-removedX, startGetXPixel, startGetYPixel, miniBitmap.getWidth() - removedX, miniBitmap.getHeight() - removedY);

            //draw background
            setImageBitmap(miniBitmap);


        } catch (Exception e) {
            e.printStackTrace();
        }
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


    long time() {
        return System.currentTimeMillis() - l;
    }

    @Override
    public void setX(float x) {
        startDrawBlur();
        super.setX(x);
    }

    @Override
    public void setY(float y) {
        startDrawBlur();
        super.setY(y);
    }

}
