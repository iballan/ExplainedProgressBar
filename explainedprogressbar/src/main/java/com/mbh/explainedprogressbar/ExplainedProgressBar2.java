package com.mbh.explainedprogressbar;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Createdby MBH on 27/01/2017.
 */

public class ExplainedProgressBar2 extends LinearLayout {
    private RoundCornerProgressBar rp_bar;
    private TextView tv_exp;
    private ImageView v_triangle;
    private View v_container;
    private int colorBackground;
    private int radius = 8;
    private float maxProgress = 100;
    private float secondaryProgress;
    private float progress;
    private boolean isInitialized = false;

    public ExplainedProgressBar2(Context context) {
        super(context);
    }

    public ExplainedProgressBar2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    public ExplainedProgressBar2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init() {
        if (!isInitialized) {
            isInitialized = true;
        } else {
            return;
        }

        final View mainView = LayoutInflater.from(getContext()).inflate
                (R.layout.layout_explained_rounded_corner_progress_bar2, this, true);

        rp_bar = (RoundCornerProgressBar) mainView.findViewById(R.id.rp_bar);
        tv_exp = (TextView) mainView.findViewById(R.id.tv_explanation);
        v_triangle = (ImageView) mainView.findViewById(R.id.v_triangle);
        v_container = mainView.findViewById(R.id.v_container);
    }

    public void setTextExplanationBackgroundColor(int color) {
        ((GradientDrawable) tv_exp.getBackground()).setColor(color);
        DrawableCompat.setTint(v_triangle.getDrawable(), color);
        colorBackground = color;
    }

    public void setTextExplanationCorners(int radius) {
        ((GradientDrawable) tv_exp.getBackground()).setCornerRadius(radius);
        this.radius = radius;
    }

    public void setTextExplanation(String text) {
        if (text == null) return;
        tv_exp.setText(text);
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
//        moveCursorToAdobtProgress(progress);
        rp_bar.setProgress(progress);
//        v_triangle.moveToProgress(progress, maxProgress, rp_bar);
        moveCursorToAdobtProgress(progress);
    }

    private void moveCursorToAdobtProgress(float progress) {
        float ratio = maxProgress / progress;
        float barWidth = rp_bar.getWidth();
        float x = (barWidth / ratio) - (v_triangle.getWidth() / 2);
        float minX = v_container.getLeft();
        float maxX = v_container.getWidth() - v_triangle.getWidth();
        if (!(x < (minX + 10) || x > (maxX - 10))) {
            ViewCompat.setTranslationX(v_triangle, x);
        }
    }

    public void setProgressColor(int color) {
        rp_bar.setProgressColor(color);
    }

    public void setSecondaryProgressColor(int color) {
        rp_bar.setSecondaryProgressColor(color);
    }

    public float getSecondaryProgress() {
        return secondaryProgress;
    }

    public void setSecondaryProgress(float secondaryProgress) {
        this.secondaryProgress = secondaryProgress;
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
        this.rp_bar.setMax(maxProgress);
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ViewTreeObserver observer = this.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                moveCursorToAdobtProgress(progress);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });

    }

    @Override
    public Parcelable onSaveInstanceState() {
        //begin boilerplate code that allows parent classes to save state
        Parcelable superState = super.onSaveInstanceState();

        SavedState ss = new SavedState(superState);
        //end

        ss.progress = rp_bar.getProgress();
        ss.secondaryProgress = rp_bar.getSecondaryProgress();
        ss.colorBackground = colorBackground;
        ss.radius = radius;
        ss.max = maxProgress;

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
        setSecondaryProgress(ss.secondaryProgress);
        setTextExplanationBackgroundColor(ss.colorBackground);
        setTextExplanationCorners(ss.radius);
        setMaxProgress(ss.max);
        setProgress(ss.progress);
    }

    public void setTextColor(int textColor) {
        tv_exp.setTextColor(textColor);
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<ExplainedProgressBar2.SavedState>() {
            public ExplainedProgressBar2.SavedState createFromParcel(Parcel in) {
                return new ExplainedProgressBar2.SavedState(in);
            }

            public ExplainedProgressBar2.SavedState[] newArray(int size) {
                return new ExplainedProgressBar2.SavedState[size];
            }
        };
        float max;
        float progress;
        float secondaryProgress;
        int radius;
        int padding;
        int colorBackground;
        int colorProgress;
        int colorSecondaryProgress;
        boolean isReverse;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.max = in.readFloat();
            this.progress = in.readFloat();
            this.secondaryProgress = in.readFloat();

            this.radius = in.readInt();
            this.padding = in.readInt();

            this.colorBackground = in.readInt();
            this.colorProgress = in.readInt();
            this.colorSecondaryProgress = in.readInt();

            this.isReverse = in.readByte() != 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeFloat(this.max);
            out.writeFloat(this.progress);
            out.writeFloat(this.secondaryProgress);

            out.writeInt(this.radius);
            out.writeInt(this.padding);

            out.writeInt(this.colorBackground);
            out.writeInt(this.colorProgress);
            out.writeInt(this.colorSecondaryProgress);

            out.writeByte((byte) (this.isReverse ? 1 : 0));
        }
    }

}
