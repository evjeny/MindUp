package com.evjeny.mentalarithmetic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * Created by Evjeny on 24.10.2016.
 * at 20:10
 */
public class CutoutView extends View {
    private Context c;
    private Paint paint;
    private Canvas canvas;
    private Random r = new Random();
    private Bitmap base;
    public CutoutView(Context context) {
        super(context);
        c = context;
        setDrawingCacheEnabled(true);
    }
    public CutoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        setDrawingCacheEnabled(true);
    }
    public CutoutView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        c = context;
        setDrawingCacheEnabled(true);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        //Left top right bot
        for(int i = 0; i<(r.nextInt(4)+1); i++) {
            int left_m = r.nextInt(99);
            int right_m = 100 + r.nextInt(99);
            int top_m = r.nextInt(99);
            int bot_m = 100 + r.nextInt(99);
            Rect r = new Rect(left_m, top_m, right_m, bot_m);
            paint = new Paint(Color.BLACK);
            canvas.drawRect(r, paint);
        }
        //End of method
        buildDrawingCache();
        base = getDrawingCache();
    }
    public Bitmap getBase() {
        return base;
    }
    public Bitmap getRandomCroppedBmp(Bitmap base, int width, int height) {
        int left = base.getWidth() - (r.nextInt(base.getWidth()-width)+width);
        int top = base.getHeight() - (r.nextInt(base.getHeight()-height)+height);
        return Bitmap.createBitmap(base, left, top, width, height);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(200,200);
    }
    private int menshe(int todo) {
        //AHAHAHAH MENSHE ALLO
        boolean cool = false;
        int result = 1;
        while (cool==false) {
            int lol = r.nextInt(todo);
            if(lol<todo) {
                result = lol;
                cool=true;
            }
        }
        return result;
    }
}
