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
    private Bitmap base;
    private Bitmap cropped;
    private Bitmap[] fiveCropped = new Bitmap[5];
    private Random r = new Random();
    public CutoutView(Context context) {
        super(context);
        c = context;
        init();
        invalidate();
    }
    public CutoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        init();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(base, 0, 0, paint);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(400,400);
    }
    public Bitmap getCropped() {
        return cropped;
    }
    public Bitmap[] getFiveCropped() {
        return fiveCropped;
    }
    private void init() {
        base = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(base);
        canvas.drawColor(Color.WHITE);
        generateRects(canvas, 5);
        generatePaths(canvas, 5);
        int x = r.nextInt(250); //r.nextInt(400-150)
        int y = r.nextInt(250);
        cropped = Bitmap.createBitmap(base, x, y, 150, 150);
        for(int i = 0; i<5; i++) {
            fiveCropped[i] = Bitmap.createBitmap(base, r.nextInt(250), r.nextInt(250), 150, 150);
        }
        paint.setColor(Color.GRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        Rect rect = new Rect();
        //r = (width - x - 150) + x
        rect.set(x, y, x+150, y+150);
        canvas.drawRect(rect, paint);
    }

    private void generateRects(Canvas base,int count) {
        if(count>0) {
            for (int i = 0; i < (r.nextInt(count) + 1); i++) {
                int left_m = r.nextInt(199);
                int right_m = 200 + r.nextInt(199);
                int top_m = r.nextInt(199);
                int bot_m = 200 + r.nextInt(199);
                Rect r = new Rect(left_m, top_m, right_m, bot_m);
                paint = new Paint(Color.BLACK);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(new Random().nextInt(4)+1);
                base.drawRect(r, paint);
            }
        }
    }
    private void generatePaths(Canvas base, int count) {
        if(count>0) {
            for(int i = 0; i<(r.nextInt(count) + 1);i++) {
                paint.setColor(Color.BLUE);
                paint.setStrokeWidth(r.nextInt(4)+1);
                paint.setStyle(Paint.Style.STROKE);
                int max_h = base.getHeight();
                int max_w = base.getWidth();
                float x1 = gnrtCoord(max_w);
                float x2 = gnrtCoord(max_w);
                float y1 = gnrtCoord(max_h);
                float y2 = gnrtCoord(max_h);
                base.drawLine(x1, y1, x2, y2, paint);
            }
        }
    }
    private float fi(int todo) {
        return Float.valueOf(todo);
    }
    private float gnrtCoord(int max) {
        return fi(r.nextInt(max));
    }
}
