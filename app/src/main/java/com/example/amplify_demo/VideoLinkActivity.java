package com.example.amplify_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;

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
            Log.i("MyAmplifyApp","Running PlayVideo procedure");
            Intent intent = new Intent(getApplicationContext(), VideoDisplay.class);
            startActivity(intent);
        }
    };
}
