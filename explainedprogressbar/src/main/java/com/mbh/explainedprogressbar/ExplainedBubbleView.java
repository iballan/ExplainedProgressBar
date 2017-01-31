package com.mbh.explainedprogressbar;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Createdby MBH on 31/01/2017.
 */

public class ExplainedBubbleView extends LinearLayout {
    private boolean isInitialized = false;
    private TextView tv_exp;
    private int colorBackground;
    private TriangleView v_triangle;
    private View v_bubbleContainer;
    private int radius = 8;
    private float maxProgress = 100;
    private float secondaryProgress;
    private float progress;

    public ExplainedBubbleView(Context context) {
        super(context);
        init();
    }

    public ExplainedBubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ExplainedBubbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ExplainedBubbleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void init() {
        if (!isInitialized) {
            isInitialized = true;
        } else {
            return;
        }

        final View mainView = LayoutInflater.from(getContext()).inflate
                (R.layout.layout_explained_bubble, this, true);

        tv_exp = (TextView) mainView.findViewById(R.id.tv_explanation);
        v_triangle = (TriangleView) mainView.findViewById(R.id.v_triangle);
        v_bubbleContainer = mainView.findViewById(R.id.v_bubbleContainer);
    }

    public void setBubbleBackgroundColor(int color) {
        ((GradientDrawable) tv_exp.getBackground()).setColor(color);
        v_triangle.setColor(color);
        colorBackground = color;
    }

    public void setBubbleCorners(int radius) {
        ((GradientDrawable) tv_exp.getBackground()).setCornerRadius(radius);
        this.radius = radius;
    }

    public void setText(String text) {
        if (text == null) return;
        tv_exp.setText(text);
    }

    public void setText(@StringRes int stringRes) {
        String text = getContext().getString(stringRes);
        tv_exp.setText(text);
    }

    public void setProgress(float progress, float maxProgress) {
        this.maxProgress = maxProgress;
        this.progress = progress;
        this.v_triangle.moveToProgress(progress, maxProgress, tv_exp);
    }

    public void setTextColor(int textColor) {
        tv_exp.setTextColor(textColor);
    }
}
