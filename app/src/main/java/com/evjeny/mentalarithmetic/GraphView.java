package com.evjeny.mentalarithmetic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Evjeny on 21.11.2016.
 * at 21:27
 */
public class GraphView extends View {
    private Context c;
    private Paint paint;
    private Bitmap base;
    public GraphView(Context context) {
        super(context);
        c = context;
        init();
        invalidate();
    }
    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        c = context;
        init();
        invalidate();
    }
    public void init() {
        base = Bitmap.createBitmap(400,400,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(base);
        canvas.drawColor(Color.WHITE);
        paint = new Paint(Color.BLACK);
        canvas.drawLine(99,0,99,399,paint);
        canvas.drawLine(199,0,199,399,paint);
        canvas.drawLine(299,0,299,399,paint);
        canvas.drawLine(0,99,399,99, paint);
        canvas.drawLine(0,199,399,199, paint);
        canvas.drawLine(0,299,399,299, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {
            Toast.makeText(c, "x: "+event.getX()+",y: "+event.getY(), Toast.LENGTH_SHORT).show();
            switch ((int)event.getX()) {
                
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(400,400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Color.BLACK);
        canvas.drawBitmap(base,0,0,paint);
    }
}
