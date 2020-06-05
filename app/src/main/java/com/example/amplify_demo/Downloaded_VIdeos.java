package com.example.amplify_demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Downloaded_VIdeos extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        int[] videos_list=intent.getIntArrayExtra("videosList");
        setContentView(R.layout.activity_downloaded__v_ideos);
        LinearLayout content_layout = (LinearLayout)findViewById(R.id.downloadedVideos);
        TextView heading = new TextView(this);
        heading.setText("Downloaded Videos");
        content_layout.addView(heading);
        for (int i = 0; i <videos_list.length; i++) {
            TextView videoTextView = new TextView(this);
            int finalI = i;
            videoTextView.setText("Video" + (videos_list[i]+1));

            videoTextView.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(v.getContext(), VideoDisplay.class);
                    intent.putExtra("videoId", (videos_list[finalI]));
                    intent.putExtra("download",1);
                    startActivity(intent);
                }
            });
            content_layout.addView(videoTextView);
        }
    }
}
