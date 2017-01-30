package com.mbh.explainedprogressview;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.widget.SeekBar;

import com.mbh.explainedprogressbar.ExplainedProgressBar;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "ExplainedProgressBar";
    ExplainedProgressBar v_explainedPb;
    AppCompatSeekBar seek_progressValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v_explainedPb = (ExplainedProgressBar) findViewById(R.id.v_explainedPb);
        seek_progressValue = (AppCompatSeekBar) findViewById(R.id.seek_progressValue);

        v_explainedPb.setTextExplanation("HABIABI THIS IS TEXT");
        v_explainedPb.setTextExplanationBackgroundColor(Color.BLUE);
        v_explainedPb.setTextColor(Color.WHITE);
        initSeekbar();
    }

    private void initSeekbar() {
        seek_progressValue.setMax(100);
        seek_progressValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                v_explainedPb.setProgress(progress);
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
        }else{
            seek_progressValue.setProgress(50);
        }
    }

    private void log(String logTxt) {
        Log.d(DEBUG_TAG, logTxt);
    }
}
