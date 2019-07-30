package com.example.wynkplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlayerActivity extends AppCompatActivity {
    private TextView songName;
    private TextView time;
    private TextView endTime;
    private SeekBar seekBar;
    private Button playPause;
    private MediaPlayer mediaPlayer;
    private Handler myHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_player);
        songName=findViewById(R.id.song);
        seekBar=findViewById(R.id.seekbar);
        time=findViewById(R.id.time);
        endTime=findViewById(R.id.endTime);
        playPause=findViewById(R.id.play);
        Intent intent=getIntent();
        String name=intent.getStringExtra("songName");
        final String url=intent.getStringExtra("songPath");
        songName.setText(name);

        playPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(playPause.getText().equals("Pause")){

                    mediaPlayer.pause();
                //    mediaPlayer.reset();
                //    mediaPlayer.release();
                    //   mediaPlayer = null;
                    playPause.setText("Play");
                }
                else{

                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if(mediaPlayer==null){

                                    mediaPlayer = new MediaPlayer();
                                    mediaPlayer.setDataSource(url);
                                    mediaPlayer.prepareAsync();
                                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                        @Override
                                        public void onPrepared(MediaPlayer mediaPlayer) {
                                            mediaPlayer.start();
                                            seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                            seekBar.setMax(mediaPlayer.getDuration());
                                            Log.d("Prog", "run: " + mediaPlayer.getDuration());
                                        }
                                    });
                                    }
                                    else{
                                       mediaPlayer.start();
                                        }
                                    playPause.setText("Pause");
                                }
                                    catch (Exception e) {

                                }
                            }
                        };

                        myHandler.postDelayed(runnable,100);
                        }
            }

        });

        Thread t = new runThread();
        t.start();
    }
    public class runThread extends Thread {


        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Run", "run: " + 1);
                if (mediaPlayer != null) {
                    seekBar.post(new Runnable() {
                        @Override
                        public void run() {
                            seekBar.setProgress(mediaPlayer.getCurrentPosition());

                            int mili = mediaPlayer.getCurrentPosition();
                            int sec = mili/1000;
                            int min = sec/60;

                            time.setText(String.format("%02d:%02d", min, sec));

                            mili = mediaPlayer.getDuration();
                            sec = mili/1000;
                            min = sec/60;
                            endTime.setText(String.format("%02d:%02d", min, sec));
                        }
                    });

                    Log.d("Run", "run: " + mediaPlayer.getCurrentPosition());
                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }
}
