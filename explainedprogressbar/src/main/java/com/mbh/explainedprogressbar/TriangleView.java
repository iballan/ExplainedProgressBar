package com.mbh.explainedprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Createdby MBH on 27/01/2017.
 */

public class TriangleView extends View {
    Paint mPaint;

    Path mPath;
    private boolean isInitialized = false;

    public TriangleView(Context context) {
        super(context);
        create();
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        create();
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        create();
    }

    public void setColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    public void setCornerRadius(float radius) {
        mPaint.setPathEffect(new CornerPathEffect(radius));
    }

    private void create() {
        if (isInitialized) return;
        isInitialized = true;
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPath = calculate(Direction.SOUTH);
        canvas.drawPath(mPath, mPaint);
    }

    private Path calculate(Direction direction) {
        Point p1 = new Point();
        p1.x = 0;
        p1.y = 0;

        Point p2 = null, p3 = null;

        int width = getWidth();

        if (direction == Direction.NORTH) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y - width);
        } else if (direction == Direction.SOUTH) {
            p2 = new Point(p1.x + width, p1.y);
            p3 = new Point(p1.x + (width / 2), p1.y + width);
        } else if (direction == Direction.EAST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x - width, p1.y + (width / 2));
        } else if (direction == Direction.WEST) {
            p2 = new Point(p1.x, p1.y + width);
            p3 = new Point(p1.x + width, p1.y + (width / 2));
        }
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);

        return path;
    }


    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }


}
