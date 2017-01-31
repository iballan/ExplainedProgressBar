package com.mbh.explainedprogressbar;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;

import com.mbh.explainedprogressbar.bar.base.BaseRoundCornerProgressBar;

/**
 * Createdby MBH on 31/01/2017.
 */

public class ExplainedViewManager {
    private final ExplainedBubbleView e_bubbleView;
    private final BaseRoundCornerProgressBar rpb_rounded;
    private CountDownTimer bubbleTimer;
    private boolean showOnProgressChange = true;
    private int showFor_seconds = 3;

    private ExplainedViewManager(ExplainedViewManagerBuilder builder) {
        this.e_bubbleView = builder.mE_bubbleView;
        this.rpb_rounded = builder.mRpb_rounded;
        this.showOnProgressChange = builder.mShowOnProgressChange;
        this.showFor_seconds = builder.mShowFor_seconds;
        this.e_bubbleView.setVisibility(View.GONE);
    }

    public void setProgress(float progress) {
        rpb_rounded.setProgress(progress);
        e_bubbleView.setProgress(progress, rpb_rounded.getMax());
        if(showOnProgressChange)
            showView();
    }

    public ExplainedBubbleView getBubbleView(){
        return e_bubbleView;
    }

    public BaseRoundCornerProgressBar getProgressBar(){
        return rpb_rounded;
    }

    public void setMaxProgress(float maxProgress){
        rpb_rounded.setMax(maxProgress);
    }

    public void setText(String text) {
        e_bubbleView.setText(text);
    }

    public void setTextColor(int color){
        e_bubbleView.setTextColor(color);
    }

    public void setBubbleBackgroundColor(int color){
        e_bubbleView.setBubbleBackgroundColor(color);
    }

    public void showView() {
        refreshHideTimer();
        if (e_bubbleView.getVisibility() == View.VISIBLE) {
            return;
        } else {
            showWithAnimation();
        }
    }

    public void hideView() {
        cancelHideTimer();
        hideWithAnimation();
    }

    private void hideWithAnimation() {
        doAnimation(false);
    }

    private void showWithAnimation() {
        doAnimation(true);
    }

    private void doAnimation(final boolean isShow) {
        AnimationSet animatinSet = new AnimationSet(false);
        animatinSet.setFillAfter(false);
        animatinSet.setFillBefore(true);
        animatinSet.setDuration(350);
        animatinSet.setInterpolator(new DecelerateInterpolator());
        ScaleAnimation scale;
        AlphaAnimation alphaAnimation;
        if (isShow) {
            scale = new ScaleAnimation(1f, 0.1f, 1f, 0.1f);
            alphaAnimation = new AlphaAnimation(0f, 1f);
        } else {
            scale = new ScaleAnimation(0.1f, 1f, 0.1f, 1f);
            alphaAnimation = new AlphaAnimation(1f, 0f);
        }

//        animatinSet.addAnimation(scale);
        animatinSet.addAnimation(alphaAnimation);

        animatinSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isShow) {
                    e_bubbleView.setVisibility(View.VISIBLE);
                } else {
                    e_bubbleView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        e_bubbleView.startAnimation(animatinSet);
    }

    private void refreshHideTimer() {
        cancelHideTimer();
        e_bubbleView.clearAnimation();
        bubbleTimer = getHideCountdownTimer();
        bubbleTimer.start();
    }

    private void cancelHideTimer() {
        if (bubbleTimer == null) return;
        bubbleTimer.cancel();
        bubbleTimer = null;
    }

    private CountDownTimer getHideCountdownTimer() {
        return new CountDownTimer(showFor_seconds*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("Counter", millisUntilFinished+"");
            }

            @Override
            public void onFinish() {
                hideView();
            }
        };
    }

    public static class ExplainedViewManagerBuilder {
        private ExplainedBubbleView mE_bubbleView;
        private BaseRoundCornerProgressBar mRpb_rounded;
        private boolean mShowOnProgressChange = true;
        private int mShowFor_seconds = 3;

        public ExplainedViewManagerBuilder(){}

        public ExplainedViewManagerBuilder setBubbleView(ExplainedBubbleView e_bubbleView) {
            mE_bubbleView = e_bubbleView;
            return this;
        }

        public ExplainedViewManagerBuilder setProgressBar(BaseRoundCornerProgressBar rpb_rounded) {
            mRpb_rounded = rpb_rounded;
            return this;
        }

        public ExplainedViewManagerBuilder setShowOnProgressChange(boolean showOnProgressChange) {
            mShowOnProgressChange = showOnProgressChange;
            return this;
        }

        public ExplainedViewManagerBuilder setShowForSeconds(int showFor_seconds) {
            mShowFor_seconds = showFor_seconds;
            return this;
        }

        public ExplainedViewManager create() {
            return new ExplainedViewManager(this);
        }
    }
}
