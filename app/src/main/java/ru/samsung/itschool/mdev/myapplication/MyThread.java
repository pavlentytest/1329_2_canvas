package ru.samsung.itschool.mdev.myapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;

public class MyThread extends Thread {

    Paint paint;
    // указатель на SurfaceView
    SurfaceHolder surfaceHolder;
    boolean flag;
    
    long startTime;

    MyThread(SurfaceHolder holder) {
        this.flag = false;
        this.surfaceHolder = holder;
        paint = new Paint();
        paint.setAntiAlias(true); // сглаживание
        paint.setStyle(Paint.Style.FILL);
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public long getTime() {
        return System.nanoTime()/1000; // микросекунды
    }

    private long redrawTime;

    @Override
    public void run() {
        Canvas canvas;
        startTime = getTime(); // ??
        while(flag) {
            long currentTime = getTime();
            long elapsedTime = currentTime - redrawTime;
            if(elapsedTime < 500000) {
                continue;
            }
            // блокировка canvas
            canvas = surfaceHolder.lockCanvas();
            // логика отрисовки
            drawCircles(canvas);
            // показываем
            surfaceHolder.unlockCanvasAndPost(canvas);
            // обновление экрана
            redrawTime = getTime();
        }
    }

    public void drawCircles(Canvas canvas) {
        int centerX = canvas.getWidth()/2;
        int centerY = canvas.getHeight()/2;
        canvas.drawColor(Color.BLACK);
        //максимальный радиус
        float maxRadius = Math.min(canvas.getHeight(), canvas.getWidth())/2;
        long currentTime = getTime();
        // < 1
        float fraction = (float)(currentTime%1000)/1000;
        Log.d("FRACTION = ",Float.toString(fraction));
        int color = Color.RED;
        paint.setColor(color);
        canvas.drawCircle(centerX,centerY,maxRadius*fraction,paint);
    }
}
