package com.example.amplify_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class VideoLinkActivity extends AppCompatActivity {
    VideoView videoView;
    TextView textView;


    int ind=0;
    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i=0;
        Intent intent=getIntent();
        int video_id=intent.getIntExtra("key",0);
        Log.i("MyAmplifyApp",Integer.toString(video_id));
        int x=0;
        setContentView(R.layout.activity_video_link);
        //int rc = FFmpeg.execute("-i file1.mp4 -c:v mpeg4 file2.mp4");


        Amplify.Storage.downloadFile(
                "video1.txt",
                new File(getApplicationContext().getFilesDir() + "/video1.txt"),
                result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                error -> Log.e("MyAmplifyApp", "Download Failure", error)

        );

            while (x <= 5) {
                Amplify.Storage.downloadFile(
                        "fileSequence" + x + ".ts",
                        new File(getApplicationContext().getFilesDir() + "/fileSequence" + x + ".ts"),
                        result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                        error -> Log.e("MyAmplifyApp", "Download Failure", error)

                );
                x++;
            }

            final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.schedule(task, 10, TimeUnit.SECONDS);



    }


    Runnable task = new Runnable() {
        public void run() {
            String cmd="-f concat -safe 0 -i "+getApplicationContext().getFilesDir()+"/video1.txt -c copy "+getApplicationContext().getFilesDir()+"/video1out.mp4";
            int rc1= FFmpeg.execute(cmd);
            Log.i("MyAmplifyApp","Running PlayVideo procedure");
            Intent intent = new Intent(getApplicationContext(), VideoDisplay.class);
            startActivity(intent);
        }
    };
}
