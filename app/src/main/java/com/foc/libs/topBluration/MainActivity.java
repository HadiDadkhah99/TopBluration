package com.foc.libs.topBluration;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.foc.libs.focTopBluration.BlurItem;

public class MainActivity extends AppCompatActivity {
    float dx = 0, dy = 0;
    View v;
    ImageView image;
    TextView text;
    Bitmap bitmap = null, blurBitmap = null;
    int[] blurAreay = null;
    int[] b = null;
    Thread t = null;
    boolean run = true;

    ScrollView scroll;
    BlurItem blurItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        v=findViewById(R.id.mainLinearLayout1);
//        image=findViewById(R.id.mainImageView1);
//        text=findViewById(R.id.mainTextView1);
//
//        scroll=findViewById(R.id.mainScrollView1);
//
//        image.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener(){
//
//            @Override
//            public void onScrollChanged()
//            {
//                int [] loc1=new int[2];
//                image.getLocationOnScreen(loc1);
//
//                int [] loc2=new int[2];
//                View v=(View) image.getParent().getParent().getParent();
//                v.getLocationOnScreen(loc2);
//
//                print2((loc1[0]-loc2[0])+" , "+(loc1[1]-loc2[1]));
//
//
//
//                runn();
//                // TODO: Implement this method
//            }
//
//        });
//
//
//
//
//
//
//
//
//
//
//
//


    }

    private int getRelativeTop(View view) {
        final View parent = (View) view.getParent();
        int[] parentLocation = new int[2];
        int[] viewLocation = new int[2];

        view.getLocationOnScreen(viewLocation);
        parent.getLocationOnScreen(parentLocation);

        return parentLocation[1];
    }

    boolean e = false;

    Bitmap cblur = null;

    void runn() {


        try {

            long l = System.currentTimeMillis();


            if (blurAreay == null) {

                bitmap = bitmap == null ? getViewBitmap(v) : bitmap;

                Bitmap cbit = bitmap.copy(Bitmap.Config.ARGB_8888, false);


                print("draw bitmap=> " + (System.currentTimeMillis() - l));

                l = System.currentTimeMillis();

                cbit = Bitmap.createScaledBitmap(cbit, (int) (cbit.getWidth() * 1.0f / 2), (int) (cbit.getHeight() * 1.0f / 2), true);

                blurBitmap = blurBitmap == null ? blurBitmap(cbit) : blurBitmap;

                blurBitmap = Bitmap.createScaledBitmap(blurBitmap, blurBitmap.getWidth() * 2, blurBitmap.getHeight() * 2, true);


                cblur = blurBitmap.copy(Bitmap.Config.ARGB_8888, false);

                print("blur bitmap=> " + (System.currentTimeMillis() - l) + "\n");


                blurAreay = getBitmapPixels(cblur, 0, 0, cblur.getWidth(), cblur.getHeight());
            }


            l = System.currentTimeMillis();
            int[] loc1 = new int[2];
            image.getLocationOnScreen(loc1);

            int[] loc2 = new int[2];
            View v = (View) image.getParent().getParent().getParent();
            v.getLocationOnScreen(loc2);

            b = getBitmapPixels(cblur, loc1[0] - loc2[0], loc1[1] - loc2[1], image.getWidth(), image.getHeight());


            Bitmap miniBit = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);

            miniBit.setPixels(b, 0, miniBit.getWidth(), 0, 0, miniBit.getWidth(), miniBit.getHeight());


            image.setImageBitmap(miniBit);
            print("mini Bitmpa=> (" + miniBit.getWidth() + " , " + miniBit.getHeight() + ") " + (System.currentTimeMillis() - l));


        } catch (Exception e) {
            print(e.getMessage());
            run = false;
        }


        e = true;


    }

    public int[] getBitmapPixels(Bitmap bitmap, int x, int y, int width, int height) {
        int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
        bitmap.getPixels(pixels, 0, bitmap.getWidth(), x, y,
                width, height);
        final int[] subsetPixels = new int[width * height];
        for (int row = 0; row < height; row++) {
            System.arraycopy(pixels, (row * bitmap.getWidth()),
                    subsetPixels, row * width, width);
        }
        return subsetPixels;
    }

    Bitmap getViewBitmap(View v) {

        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);


        return bitmap;
    }


    void print(String s) {

        if (true)
            return;

        text.append(s + "\n");

    }

    void print2(String s) {


        text.setText(s + "\n");

    }


    public Bitmap blurBitmap(Bitmap bitmap) {

        //Let's create an empty bitmap with the same size of the bitmap we want to blur
        Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        //Instantiate a new Renderscript
        RenderScript rs = RenderScript.create(getApplicationContext());

        //Create an Intrinsic Blur Script using the Renderscript
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        //Create the in/out Allocations with the Renderscript and the in/out bitmaps
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);

        //Set the radius of the blur
        blurScript.setRadius(25.f);

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

}
