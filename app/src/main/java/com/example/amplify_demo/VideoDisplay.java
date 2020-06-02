package com.example.amplify_demo;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoDisplay extends AppCompatActivity {
VideoView videoView;
int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);
            videoView=(VideoView)findViewById(R.id.VideoView);
            videoView.setVideoPath(getApplicationContext().getFilesDir() + "/fileSequence"+0+".ts");
            videoView.start();
            setup(videoView);
            videoView.setOnCompletionListener(completionListener);
    }
    public void setup(VideoView videoView) {
        if(i==6) {
            videoView.stopPlayback();
            return;
        }
        Log.i("MyAmplifyApp",Integer.toString(i));
        videoView.setVideoPath(getApplicationContext().getFilesDir() + "/fileSequence"+i+".ts");
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                int duration = mp.getDuration();
                int videoDuration = videoView.getDuration();
                Log.i("MyAmplifyApp-Duration", Integer.toString(videoView.getDuration()));
            }
        });
        i++;
    }
    MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {

        public void onCompletion(MediaPlayer mp) {
            setup(videoView);
            if(i==6) {
                videoView.stopPlayback();
                mp.stop();
                return;
            }
        }
    };
}
