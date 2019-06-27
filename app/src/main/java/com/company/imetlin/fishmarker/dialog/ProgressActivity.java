package com.company.imetlin.fishmarker.dialog;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.TextView;

import com.company.imetlin.fishmarker.MainActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.firebaseAuth.SignInActivity;

import me.itangqi.waveloadingview.WaveLoadingView;

public class ProgressActivity extends AppCompatActivity {

    public WaveLoadingView waveLoadingView;
    private int progress = 0;
    private Handler handler = new Handler();
    TextView textView;
    Thread thread;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);

        waveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoading);
        waveLoadingView.setWaterLevelRatio(0.0f);
        waveLoadingView.setProgressValue(progress);
        textView = (TextView) findViewById(R.id.textview);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        textView.startAnimation(anim);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "font_progress_activity.otf");
        textView.setTypeface(tf);




         thread  =   new Thread(new Runnable() {
                @Override
                public void run() {
                    while (progress < 100) {
                        progress++;
                        android.os.SystemClock.sleep(50);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                waveLoadingView.setProgressValue(progress);

                                if(progress == 100){
                                    thread.interrupt();
                                    finish();
                                    Intent intent = new Intent(ProgressActivity.this, SignInActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });thread.start();

        }


    }




