package com.example.amplify_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;

import java.io.File;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    int N=3;
    final TextView[] myTextViews = new TextView[N];
    HashMap<String,Integer> videoMap=new HashMap<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        ClientFactory.init(this);

        int reload_flag=intent.getIntExtra("reload",0);

        if(reload_flag==0) {
            try {
                // Add these lines to add the AWSCognitoAuthPlugin and AWSS3StoragePlugin plugins
                Amplify.addPlugin(new AWSCognitoAuthPlugin());
                Amplify.addPlugin(new AWSS3StoragePlugin());
                Amplify.configure(getApplicationContext());

                Log.i("MyAmplifyApp", "Initialized Amplify");
            } catch (AmplifyException error) {
                Log.e("MyAmplifyApp", "Could not initialize Amplify", error);
            }
            Amplify.Storage.downloadFile(
                    "video1.txt",
                    new File(getApplicationContext().getFilesDir() + "/video1.txt"),
                    result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                    error -> Log.e("MyAmplifyApp", "Download Failure", error)

            );
            int x = 0;
            while (x <= 5) {
                Amplify.Storage.downloadFile(
                        "fileSequence" + x + ".ts",
                        new File(getApplicationContext().getFilesDir() + "/fileSequence" + x + ".ts"),
                        result -> Log.i("MyAmplifyApp", "Successfully downloaded: " + result.getFile().getName()),
                        error -> Log.e("MyAmplifyApp", "Download Failure", error)

                );
                x++;
            }
        }
        setContentView(R.layout.activity_main);
        videoMap.put("video1link",1);
        videoMap.put("video2link",2);
        videoMap.put("video3link",3);
        LinearLayout content_layout = (LinearLayout)findViewById(R.id.videoLayout);
//        GetAllItemsAsyncTask getAllDevicesTask = new GetAllItemsAsyncTask();
//        getAllDevicesTask.execute();
        for (int i = 0; i <N; i++) {
            TextView videoTextView = new TextView(this);
            ImageButton imgButton = new ImageButton(this);
            imgButton.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            imgButton.setImageResource(R.drawable.ic_stat_name);
            int finalI = i;
            imgButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(v.getContext(), VideoLinkActivity.class);
                    intent.putExtra("key", finalI);
                    intent.putExtra("download",1);
                    startActivity(intent);
                }
            });

            //add button to the layout
            content_layout.addView(imgButton);
            videoTextView.setText("Video" + (i+1));

            videoTextView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(v.getContext(), VideoLinkActivity.class);
                intent.putExtra("key", finalI);
                intent.putExtra("download",0);
                startActivity(intent);
            }
        });
            content_layout.addView(videoTextView);
            //content_layout.addView(downloadVideoView);
            myTextViews[i] = videoTextView;
        }
        }

    }

