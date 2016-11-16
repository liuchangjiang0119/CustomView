package com.windfall.customview;

import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.windfall.customview.view.ProgressBarView;

public class MainActivity extends AppCompatActivity {
    ProgressBarView mProgressBarView;
    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgressBarView = (ProgressBarView) findViewById(R.id.progressbarview);
        final SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final Intent intent = new Intent(MainActivity.this,ClockActivity.class);
        mButton = (Button)findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar bar, int i, boolean b) {
                mProgressBarView.setProgress(bar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar bar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar bar) {

            }
        });
    }



}
