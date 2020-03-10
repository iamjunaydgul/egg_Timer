package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView eggTextView;
    SeekBar eggSeekBar;
    Button eggControllerButton;
    CountDownTimer countDownTimer;

    //for disabling the seekbar so user cant change seek bar when counter is runing

    Boolean counterIsActive=false;


    public void timerReset(){

        eggSeekBar.setEnabled(true);
        eggSeekBar.setProgress(30);
        eggControllerButton.setText("Go!");

        counterIsActive=false;

        countDownTimer.cancel();
        eggTextView.setText("0:30");


    }


    public void updateTimer(int progress){

        int minutes= progress /60;
        int seconds= progress - minutes * 60;
        String secondString= Integer.toString(seconds);


        if(seconds == 0){
            secondString = "00" ;

            eggTextView.setText(minutes+":"+secondString);

        }
        else if(seconds<=9){

            secondString="0"+ seconds;
            eggTextView.setText(minutes+":"+secondString);

        }
        else{

            eggTextView.setText(minutes+":"+seconds);

        }

    }

    public void controllTimer(View view){

        if(counterIsActive == false) {

            counterIsActive=true;
            eggSeekBar.setEnabled(false);
            eggControllerButton.setText("Stop");

            countDownTimer =new CountDownTimer(eggSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);

                }

                @Override
                public void onFinish() {
                    eggTextView.setText("0:00");
                    timerReset();
                    //playing the sound
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();


                }
            }.start();

        }
        else{

            timerReset();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eggSeekBar=findViewById(R.id.eggTimerSB);
        eggTextView=findViewById(R.id.timerTextView);
        eggControllerButton=findViewById(R.id.eggControllerButton);


        eggSeekBar.setMax(600);
        eggSeekBar.setProgress(30);

        eggSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                updateTimer(progress);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
