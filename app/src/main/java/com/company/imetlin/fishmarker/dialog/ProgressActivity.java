package com.company.imetlin.fishmarker.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.company.imetlin.fishmarker.MainActivity;
import com.company.imetlin.fishmarker.R;
import com.company.imetlin.fishmarker.firebaseAuth.SignInActivity;

import me.itangqi.waveloadingview.WaveLoadingView;

public class ProgressActivity extends AppCompatActivity {

    public WaveLoadingView waveLoadingView;
    public SeekBar seekBar;
    private int progress = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_activity);

        waveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoading);
        waveLoadingView.setProgressValue(0);



        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress < 100){
                    progress++;
                    android.os.SystemClock.sleep(100);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            waveLoadingView.setProgressValue(progress);

                        }
                    });

                }

            }
        }).start();

      /*  Intent intent = new Intent(ProgressActivity.this, SignInActivity.class);
        startActivity(intent);
*/

    }





}
