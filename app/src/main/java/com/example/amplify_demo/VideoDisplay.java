package com.example.amplify_demo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class VideoDisplay extends AppCompatActivity implements MediaPlayer.OnCompletionListener{
VideoView videoView;
int download_flag;
    ArrayList<Integer> videolist = new ArrayList<>();
    int currvideo = 0;
    int video_id;
    Button button;
    public Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        download_flag=intent.getIntExtra("download",0);
        video_id=intent.getIntExtra("videoId",0);
        setContentView(R.layout.activity_video_display);
        button=(Button)findViewById(R.id.back_button);
        button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("reload",1);
                startActivity(intent);
            }
        });
        if(download_flag==0) {
            videoView = (VideoView) findViewById(R.id.VideoView);
            Log.i("MyAmplifyApp2",Integer.toString(video_id));
            videoView.setVideoPath(getApplicationContext().getFilesDir() + "/video"+(video_id+1)+"out.mp4");
            videoView.start();
            MediaController vidControl = new MediaController(this);
            vidControl.requestFocus();
            vidControl.setAnchorView(findViewById(R.id.VideoView));
            videoView.setMediaController(vidControl);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            vidControl.show(0);
                        }},
                    100);

        }
        else{
            videoView = (VideoView) findViewById(R.id.VideoView);
            videoView.setVideoPath(getApplicationContext().getFilesDir() + "/video"+(video_id+1)+"out.mp4");
            videoView.start();
        }
    }
    public void onCompletion(MediaPlayer mediapalyer)
    {
        AlertDialog.Builder obj = new AlertDialog.Builder(this);
        obj.setTitle("Playback Finished!");
        obj.setIcon(R.mipmap.ic_launcher);
        MyListener m = new MyListener();
        obj.setPositiveButton("Replay", m);
        obj.setNegativeButton("Next", m);
        obj.setMessage("Want to replay or play next video?");
        obj.show();
    }

    class MyListener implements DialogInterface.OnClickListener {
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == -1) {
                videoView.seekTo(0);
                videoView.start();
            }
            else {
                ++currvideo;
                if (currvideo == videolist.size())
                    currvideo = 0;
                //setVideo(videolist.get(currvideo));
            }
        }
    }
}
