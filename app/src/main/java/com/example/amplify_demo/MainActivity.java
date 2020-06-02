package com.example.amplify_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    int N=3;
    final TextView[] myTextViews = new TextView[N];
    HashMap<String,Integer> videoMap=new HashMap<>();
    int i=0;
    VideoView videoView;
    private MediaController mediaController;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());

            Log.i("MyAmplifyApp", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
        }
        setContentView(R.layout.activity_main);
        videoMap.put("video1link",1);
        videoMap.put("video2link",2);
        videoMap.put("video3link",3);
        LinearLayout content_layout = (LinearLayout)findViewById(R.id.videoLayout);
        for (int i = 0; i <N; i++) {
            TextView videoTextView = new TextView(this);
            String video_id="video"+(i+1);
            videoTextView.setText("Video" + (i+1));
            int finalI = i;
            videoTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), VideoLinkActivity.class);
                intent.putExtra("key", finalI);
                startActivity(intent);
            }
        });
            content_layout.addView(videoTextView);
            myTextViews[i] = videoTextView;
        }
        }

    }
