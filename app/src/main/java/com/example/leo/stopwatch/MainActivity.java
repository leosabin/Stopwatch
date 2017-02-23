package com.example.leo.stopwatch;

import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.util.Log;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnPause;
    TextView txtTimer;
    EditText editTimer;
    Handler customHandler = new Handler();
    Integer presses=0;
    TextView press;

    long startTime=0L,timeInMilliseconds=0L,timeSwapBuff=0L,updateTime=0L;



    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff+timeInMilliseconds;
            int secs=(int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            txtTimer.setText(""+mins+":"+String.format("%2d",secs)+":"
                                        +String.format("%3d",milliseconds));
            editTimer.setText(""+mins+":"+String.format("%2d",secs)+":"
                                        +String.format("%3d",milliseconds));
            customHandler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStart = (Button)findViewById(R.id.btnStart);
        btnPause = (Button)findViewById(R.id.btnPause);
        txtTimer = (TextView)findViewById(R.id.timerValue);
        editTimer = (EditText)findViewById(R.id.editText);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread,0);

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Paused", "Paused");
                timeSwapBuff+=timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
                Log.d("timeSwapBuff", "timeSwapBuff: " + timeSwapBuff);
                Log.d("startTime", "startTime: " + startTime);
                Log.d("updateTime", "updateTime: " + updateTime);
            }
        });

    }
}
