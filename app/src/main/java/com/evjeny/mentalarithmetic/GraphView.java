package com.evjeny.mentalarithmetic;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Evjeny on 21.11.2016.
 * at 21:27
 */
public class GraphView extends View {
    private Context c;
    private Paint grid_paint, line_paint;
    private Bitmap base;
    private Path line;
    private int[][] text_poses = new int[5][2];
    private int type = 0; //0 is view, 1 is edit
    private int size = 600;
    public GraphView(Context context) {
        super(context);
        grid_paint = new Paint(Color.BLACK);
        line_paint = new Paint(Color.RED);
        line_paint.setStyle(Paint.Style.STROKE);
        c = context;
        init();
        invalidate();
    }
    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.GraphView, 0, 0);
        try {
            type = a.getInteger(R.styleable.GraphView_type, 0);
        } finally {
            a.recycle();
        }
        grid_paint = new Paint(Color.BLACK);
        line_paint = new Paint(Color.RED);
        line_paint.setStyle(Paint.Style.STROKE);
        line_paint.setStrokeWidth(5);
        c = context;
        init();
        invalidate();
    }
    public void init() {
        base = Bitmap.createBitmap(size,size,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(base);
        canvas.drawColor(Color.WHITE);
        int op = size/4-1;
        int ps = size/4;
        canvas.drawLine(op,0,op,size-1,grid_paint);
        canvas.drawLine(op+ps,0,op+ps,size-1,grid_paint);
        canvas.drawLine(op+2*ps,0,op+2*ps,size-1,grid_paint);
        canvas.drawLine(0,op,size-1,op, grid_paint);
        canvas.drawLine(0,op+ps,size-1,op+ps, grid_paint);
        canvas.drawLine(0,op+2*ps,size-1,op+2*ps, grid_paint);
        if(type==0) {
            generateLine();
        }
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
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(base,0,0,grid_paint);
        if(type==0&&line!=null) {
            canvas.drawPath(line, line_paint);
            for(int i = 0; i<text_poses.length; i++) {
                int[] c1 = text_poses[i];
                Paint tp = new Paint(Color.BLUE);
                tp.setStyle(Paint.Style.STROKE);
                int sum = (i+1);
                tp.setTextSize(size/25);
                canvas.drawText(String.valueOf(sum), c1[0]-size/16, c1[1]+size/16,tp);
            }
        }
    }
    private void generateLine() {
        ArrayList<int[]> f = field();
        Path path = new Path();
        int first = getRandomCord(f);
        int[] lastpos = f.get(first);
        text_poses[0][0] = lastpos[0];
        text_poses[0][1] = lastpos[1];
        f.remove(first);
        for(int i = 0; i<4; i++) {
            int todo = getRandomCord(f);
            int[] crds = f.get(todo);
            text_poses[i+1][0] = crds[0];
            text_poses[i+1][1] = crds[1];
            f.remove(todo);
            path.moveTo(lastpos[0],lastpos[1]);
            path.lineTo(crds[0], crds[1]);
            lastpos = crds;
        }
        line = path;
        invalidate();
    }
    private int getRandomCord(ArrayList<int[]> todo) {
        Random r = new Random();
        int t = r.nextInt(todo.size());
        return t;
    }
    private ArrayList<int[]> field() {
        ArrayList<int[]> result = new ArrayList<>();
        int one = size/8-1;
        while (one<size-1) {
            for(int i = one; i<size-1; i+=size/4) {
                int[] coords = new int[2];
                coords[0] = one;
                coords[1] = i;
                result.add(coords);
            }
            one+=size/4;
        }
        return result;
    }
}
