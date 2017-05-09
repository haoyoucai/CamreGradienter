package me.kaini.level.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by DanLai on 2017/5/6.
 */

public class LineGradienterView extends View {
    /**
     * 中心点坐标
     */
    private PointF centerPnt = new PointF();
    private PointF pointA = new PointF();
    private PointF pointB = new PointF();
    private PointF pointC = new PointF();
    private PointF pointD = new PointF();

    private double pitchAngle = 45 * Math.PI / 180;
    private double rollAngle;

    private Paint paintA; //绘制固定的中间线

    private Paint paintB;

    private Paint paintC;

    private Paint paintD;
    //
    private float centerRadius = 20f;

    private float outerRadius;

    public LineGradienterView(Context context) {
        super(context);
        paintA = new Paint();
        paintA.setColor(Color.parseColor("#FFFFFF"));
        paintA.setStyle(Paint.Style.FILL);
        paintA.setAntiAlias(true);
    }

    public LineGradienterView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintA = new Paint();

        paintA.setColor(Color.parseColor("#FFFFFF"));
        paintA.setStyle(Paint.Style.FILL);
        paintA.setAntiAlias(true);
        paintA.setStrokeWidth(10);

        paintB = new Paint();
        paintB.setColor(Color.parseColor("#FFFFFF"));
        paintB.setStyle(Paint.Style.STROKE);
        paintB.setAntiAlias(true);
        paintB.setStrokeWidth(6);

        paintC = new Paint();
        paintC.setColor(Color.parseColor("#AE995A"));
        paintC.setStyle(Paint.Style.FILL);
        paintC.setAntiAlias(true);
        paintC.setStrokeWidth(8);


        paintC = new Paint();
        paintC.setColor(Color.parseColor("#AE995A"));
        paintC.setStyle(Paint.Style.FILL);
        paintC.setAntiAlias(true);
        paintC.setStrokeWidth(8);

        paintD = new Paint();
        paintD.setColor(Color.parseColor("#FF0000"));
        paintD.setStyle(Paint.Style.FILL);
        paintD.setAntiAlias(true);
        paintD.setStrokeWidth(4);
    }

    public LineGradienterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintA = new Paint();
        paintA.setColor(Color.parseColor("#FFFFFF"));
        paintA.setStyle(Paint.Style.FILL);
        paintA.setAntiAlias(true);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        calculateCenter(widthMeasureSpec, heightMeasureSpec);
    }

    private void calculateCenter(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.makeMeasureSpec(widthMeasureSpec, MeasureSpec.UNSPECIFIED);

        int height = MeasureSpec.makeMeasureSpec(heightMeasureSpec, MeasureSpec.UNSPECIFIED);

        int center = Math.min(width, height) / 2;
        outerRadius = center / 2;
        centerPnt.set(width/2, height/2);
        pointA.set(0, height/2);
        pointB.set(width, height/2);
        pointC.set(width / 4, height/2);
        pointD.set(width * 3 / 4,  height/2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!drawRoll(canvas)&! drawPitch(canvas)){
            canvas.drawLine(pointA.x, pointA.y, pointC.x, pointC.y, paintA);
            canvas.drawLine(pointD.x, pointD.y, pointB.x, pointB.y, paintA);
        }
    }

    private boolean drawRoll(Canvas canvas) {
        if (-2 < Math.toDegrees(rollAngle) && Math.toDegrees(rollAngle) < 2) {
            canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, paintC);
            canvas.drawCircle(centerPnt.x, centerPnt.y, centerRadius, paintC);
            return true;
        }
        Log.e("rollAngle:", Math.toDegrees(rollAngle) + "");


        canvas.drawCircle(centerPnt.x, centerPnt.y, centerRadius, paintB);
        canvas.drawLine((float) (-centerRadius * Math.cos(rollAngle)) + centerPnt.x,
                -(float) (centerRadius * Math.sin(rollAngle)) + centerPnt.y,
                -(float) (outerRadius * Math.cos(rollAngle)) + centerPnt.x,
                -(float) (outerRadius * Math.sin(rollAngle)) + centerPnt.y, paintA);
        canvas.drawLine(
                centerPnt.x + (float) (centerRadius * Math.cos(rollAngle)),
                centerPnt.y + (float) (centerRadius * Math.sin(rollAngle)),
                centerPnt.x + (float) (outerRadius * Math.cos(rollAngle)),
                centerPnt.y + (float) (outerRadius * Math.sin(rollAngle)), paintA);

        return false;

    }

    private boolean drawPitch(Canvas canvas) {
        if(Math.abs(Math.toDegrees(pitchAngle))>88&&Math.abs(Math.toDegrees(pitchAngle))<92){
            canvas.drawLine(pointA.x, pointA.y, pointC.x, pointC.y, paintC);
            canvas.drawLine(pointD.x, pointD.y, pointB.x, pointB.y, paintC);
            return true;
        }
            canvas.drawLine(pointA.x, (float) ((1 - Math.cos(pitchAngle)) * centerPnt.y),
                    pointC.x, (float) ((1 - Math.cos(pitchAngle)) * centerPnt.y), paintD);
            canvas.drawLine(pointD.x, (float) ((1 - Math.cos(pitchAngle)) * centerPnt.y),
                    pointB.x, (float) ((1 - Math.cos(pitchAngle)) * centerPnt.y), paintD);

        return false;
    }
    public void setAngle(float rollAngle, float pitchAngle, float azimuth) {
        this.pitchAngle = pitchAngle;
        this.rollAngle = rollAngle ;
        invalidate();
    }
}
