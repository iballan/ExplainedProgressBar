package com.mbh.explainedprogressbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
    private float progress;
    private float maxProgress;
    private float containerWidth;
    private float minX, maxX;
    private int color = Color.RED;
    private boolean isInitialized = false;

    static final class TriangleDrawer implements Runnable {
        final TriangleView triangleView;

        TriangleDrawer(TriangleView basketBubbleTriangle) {
            this.triangleView = basketBubbleTriangle;
        }

        public final void run() {
            this.triangleView.drawTriangle();
        }
    }
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
        post(new TriangleDrawer(this));
    }

    private final void drawTriangle() {
        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(0.0f, 0.0f);
        path.lineTo((float) getLayoutParams().width, 0.0f);
        path.lineTo((float) (getLayoutParams().width / 2), (float) getLayoutParams().height);
        path.lineTo(0.0f, 0.0f);
        path.close();
        Paint paint = new Paint(1);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);
        Bitmap bitmap = Bitmap.createBitmap(getLayoutParams().width, getLayoutParams().height, Bitmap.Config.ARGB_8888);
        new Canvas(bitmap).drawPath(path, paint);
        if (Build.VERSION.SDK_INT >= 16) {
            setBackground(new BitmapDrawable(getResources(), bitmap));
        } else {
            setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
    }

    private void create() {
        if (isInitialized) return;
        isInitialized = true;
        post(new TriangleDrawer(this));
    }

    public void moveToProgress(float progress, float maxProgress, View parent) {
        this.maxProgress = maxProgress;
        this.progress = progress;
        this.containerWidth = parent.getMeasuredWidth();
        minX = 10;
        maxX = this.containerWidth - this.getMeasuredWidth()-10;
        moveToProgress();
    }

    private void moveToProgress() {
        float ratio = maxProgress / progress;
        float x = (containerWidth / ratio) - (this.getWidth() / 2);
        if(x<minX){
            x = minX;
        }else if(x > maxX){
            x = maxX;
        }
        ViewCompat.setTranslationX(this, x);
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
//        setX(ss.x);
//        ViewCompat.setTranslationX(this, ss.x);
        containerWidth = ss.containerWidth;
        minX = ss.minX;
        maxX = ss.maxX;
        maxProgress = ss.maxProgress;
        progress = ss.progress;
        color = ss.color;
//        moveToProgress();

    }

    public enum Direction {
        NORTH, SOUTH, EAST, WEST
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<TriangleView.SavedState>() {
            public TriangleView.SavedState createFromParcel(Parcel in) {
                return new TriangleView.SavedState(in);
            }

            public TriangleView.SavedState[] newArray(int size) {
                return new TriangleView.SavedState[size];
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
