package com.mbh.explainedprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * Createdby MBH on 27/01/2017.
 */

public class TriangleView extends View {
    //    private float
    Paint mPaint;
    Path mPath;
    private float progress;
    private float maxProgress;
    private float containerWidth;
    private float minX, maxX;
    private int color = Color.RED;
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
        this.color = color;
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

    public void moveToProgress(float progress, float maxProgress, View parent) {
        this.maxProgress = maxProgress;
        this.progress = progress;
        this.containerWidth = parent.getWidth();
        minX = parent.getLeft();
        maxX = parent.getWidth() - this.getWidth();
        moveToProgress();
    }

    private void moveToProgress() {
        float ratio = maxProgress / progress;
        float x = (containerWidth / ratio) - (this.getWidth() / 2);
        if (!(x < minX || x > maxX)) {
            ViewCompat.setTranslationX(this, x);
        }
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
    }

    public void setContainerWidth(float containerWidth) {
        this.containerWidth = containerWidth;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end
        ss.x = getX();
        ss.progress = progress;
        ss.maxProgress = maxProgress;
        ss.color = color;
        ss.minX = minX;
        ss.maxX = maxX;
        ss.containerWidth = containerWidth;

        return ss;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        //begin boilerplate code so parent classes can restore state
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }

        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setX(ss.x);
        ViewCompat.setTranslationX(this, ss.x);
        containerWidth = ss.containerWidth;
        minX = ss.minX;
        maxX = ss.maxX;
        maxProgress = ss.maxProgress;
        progress = ss.progress;
        color = ss.color;
        moveToProgress();

    }

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };

        float x;
        float y;
        float containerWidth;
        float minX;
        float maxX;
        float maxProgress;
        float progress;
        float secondaryProgress;
        int color;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.x = in.readFloat();
            this.y = in.readFloat();
            this.containerWidth = in.readFloat();
            this.minX = in.readFloat();
            this.maxX = in.readFloat();
            this.maxProgress = in.readFloat();
            this.progress = in.readFloat();
            this.secondaryProgress = in.readFloat();

            this.color = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);

            out.writeFloat(this.x);
            out.writeFloat(this.y);
            out.writeFloat(this.containerWidth);
            out.writeFloat(this.minX);
            out.writeFloat(this.maxX);

            out.writeFloat(this.maxProgress);
            out.writeFloat(this.progress);
            out.writeFloat(this.secondaryProgress);

            out.writeInt(this.color);
        }
    }
}
