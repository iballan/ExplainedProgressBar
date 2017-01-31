package com.mbh.explainedprogressview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.widget.SeekBar;

import com.mbh.explainedprogressbar.ExplainedBubbleView;
import com.mbh.explainedprogressbar.ExplainedViewManager;
import com.mbh.explainedprogressbar.bar.RoundCornerProgressBar;


public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "ExplainedProgressBar";
    AppCompatSeekBar seek_progressValue;
    ExplainedViewManager mExplainedViewManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mExplainedViewManager = new ExplainedViewManager.ExplainedViewManagerBuilder()
                .setBubbleView((ExplainedBubbleView) findViewById(R.id.e_bubbleView))
                .setProgressBar((RoundCornerProgressBar) findViewById(R.id.rp_bar))
                .setShowOnProgressChange(true)
                .setShowForSeconds(3).create();

        seek_progressValue = (AppCompatSeekBar) findViewById(R.id.seek_progressValue);

        mExplainedViewManager.setMaxProgress(50);
        mExplainedViewManager.setText("HABIABI THIS IS TEXT");
        mExplainedViewManager.setBubbleBackgroundColor(Color.BLUE);
        mExplainedViewManager.setTextColor(Color.WHITE);
        initSeekbar();
    }

    private void initSeekbar() {
        seek_progressValue.setMax(50);
        seek_progressValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mExplainedViewManager.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            seek_progressValue.setProgress(50, true);
        } else {
            seek_progressValue.setProgress(50);
        }
        mExplainedViewManager.setProgress(seek_progressValue.getProgress());
    }

    @Override
    protected void onResume() {
        super.onResume();
        mExplainedViewManager.setProgress(seek_progressValue.getProgress());
    }

    private void log(String logTxt) {
        Log.d(DEBUG_TAG, logTxt);
    }
}
