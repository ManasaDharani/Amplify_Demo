package com.example.amplify_demo;

import android.os.Bundle;
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
            videoView.setVideoPath(getApplicationContext().getFilesDir() + "/video1out.mp4");
            videoView.start();
    }

}
