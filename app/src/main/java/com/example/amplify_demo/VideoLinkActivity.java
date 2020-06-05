package com.example.amplify_demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.MediaController;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.amplify.generated.graphql.CreatePetMutation;
import com.amazonaws.amplify.generated.graphql.DeletePetMutation;
import com.amazonaws.amplify.generated.graphql.ListPetsQuery;
import com.amazonaws.mobileconnectors.appsync.fetcher.AppSyncResponseFetchers;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.arthenica.mobileffmpeg.FFmpeg;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import type.CreatePetInput;


public class VideoLinkActivity extends AppCompatActivity {

    private MediaController mediaController;
    int download_flag;
    int video_id;
    private List<ListPetsQuery.Item> mData = new ArrayList<>();
    private ArrayList<ListPetsQuery.Item> downloaded_videos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int i=0;
        Intent intent=getIntent();
        video_id=intent.getIntExtra("key",0);
        download_flag=intent.getIntExtra("download",0);
        Log.i("MyAmplifyApp",Integer.toString(video_id));
        int x=0;
        setContentView(R.layout.activity_video_link);
        if(download_flag==0) {
//            String cmd="-f concat -safe 0 -i "+getApplicationContext().getFilesDir()+"/video"+video_id+".txt -c copy "+getApplicationContext().getFilesDir()+"/video"+video_id+"out.mp4";
//            int rc1= FFmpeg.execute(cmd);
            Log.i("MyAmplifyApp", "Running PlayVideo procedure");
            Intent intent1 = new Intent(getApplicationContext(), VideoDisplay.class);
            intent1.putExtra("download",0);
            Log.i("MyAmplifyApp",Integer.toString(video_id));
            intent1.putExtra("videoId",video_id);
            startActivity(intent1);
        }
        else{
//            DeletePetInput delete= DeletePetInput.builder().id("249f0897-d420-4d2d-ba18-a10be0aa085d").build();
//            DeletePetMutation deletePetMutation = DeletePetMutation.builder().input(delete).build();
//            ClientFactory.appSyncClient().mutate(deletePetMutation).enqueue(mutateCallback_del);
            CreatePetInput input = CreatePetInput.builder()
                    .name(Integer.toString(video_id))
                    .description("Downloaded Video")
                    .build();
            CreatePetMutation addPetMutation = CreatePetMutation.builder()
                    .input(input)
                    .build();
            ClientFactory.appSyncClient().mutate(addPetMutation).enqueue(mutateCallback);

            String cmd="-f concat -safe 0 -i "+getApplicationContext().getFilesDir()+"/video1.txt -c copy "+getApplicationContext().getFilesDir()+"/video1out.mp4";
            String cmd2="-f concat -safe 0 -i "+getApplicationContext().getFilesDir()+"/video1.txt -c copy "+"/storage/1AEE-3619/Download"+"/video1out.mp4";

            int rc1= FFmpeg.execute(cmd);
            int rc2= FFmpeg.execute(cmd2);
        }


    }
    private GraphQLCall.Callback<DeletePetMutation.Data> mutateCallback_del = new GraphQLCall.Callback<DeletePetMutation.Data>() {
        @Override
        public void onResponse(@Nonnull final Response<DeletePetMutation.Data> response) {
            Log.i("Graphql","Deleted");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(AddPetActivity.this, "Added pet", Toast.LENGTH_SHORT).show();
//                    AddPetActivity.this.finish();
                }
            });
        }

        @Override
        public void onFailure(@Nonnull final ApolloException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("", "Failed to perform AddPetMutation", e);
//                    Toast.makeText(AddPetActivity.this, "Failed to add pet", Toast.LENGTH_SHORT).show();
//                    AddPetActivity.this.finish();
                }
            });
        }
    };
    private GraphQLCall.Callback<CreatePetMutation.Data> mutateCallback = new GraphQLCall.Callback<CreatePetMutation.Data>() {
        @Override
        public void onResponse(@Nonnull final Response<CreatePetMutation.Data> response) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(AddPetActivity.this, "Added pet", Toast.LENGTH_SHORT).show();
//                    AddPetActivity.this.finish();
                }
            });
        }

        @Override
        public void onFailure(@Nonnull final ApolloException e) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("", "Failed to perform AddPetMutation", e);
//                    Toast.makeText(AddPetActivity.this, "Failed to add pet", Toast.LENGTH_SHORT).show();
//                    AddPetActivity.this.finish();
                }
            });
        }
    };
    @Override
    public void onResume() {
        super.onResume();

        // Query list data when we return to the screen
        query();
    }

    public void query(){
        ClientFactory.appSyncClient().query(ListPetsQuery.builder().build())
                .responseFetcher(AppSyncResponseFetchers.CACHE_AND_NETWORK)
                .enqueue(queryCallback);
    }

    private GraphQLCall.Callback<ListPetsQuery.Data> queryCallback = new GraphQLCall.Callback<ListPetsQuery.Data>() {
        @Override
        public void onResponse(@Nonnull Response<ListPetsQuery.Data> response) {

        if(download_flag==1){
            downloaded_videos = new ArrayList<>(response.data().listPets().items());

            List<String> videos_list = new ArrayList<>();
//            Log.i("Graphql", "Retrieved list items: " + mPets.toString());
            Log.i("Graphql", "Retrieved list items: " + downloaded_videos.size());
            for (int i = 0; i < downloaded_videos.size(); i++) {
                //Log.i("Graphqlshjdbf", Integer.toString(downloaded_videos.get(i).name()));
                if (!videos_list.contains(downloaded_videos.get(i).name())) {
                    videos_list.add(downloaded_videos.get(i).name());
                    //videos_list_array[i] = Integer.parseInt(downloaded_videos.get(i).name());
                }
            }
            Log.i("Graphql", String.valueOf(videos_list));
            Log.i("Graphql", String.valueOf(videos_list.size()));
            int videos_list_array[] = new int[videos_list.size()];
            for (int i = 0; i < videos_list.size(); i++)
                videos_list_array[i] = Integer.parseInt(videos_list.get(i));
            Log.i("Graphql", String.valueOf(videos_list_array.length));
            Intent intent2 = new Intent(getApplicationContext(), Downloaded_VIdeos.class);
            intent2.putExtra("download",1);
            assert videos_list instanceof Parcelable;

            intent2.putExtra("videosList", videos_list_array);
            startActivity(intent2);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    mAdapter.setItems(mPets);
//                    mAdapter.notifyDataSetChanged();
                }
            });
        }
        }

        @Override
        public void onFailure(@Nonnull ApolloException e) {
            Log.e("Graphql", e.toString());
        }
    };



    Runnable task = new Runnable() {
        public void run() {
            if(download_flag==0) {
                String cmd="-f concat -safe 0 -i "+getApplicationContext().getFilesDir()+"/video1.txt -c copy "+getApplicationContext().getFilesDir()+"/video1out.mp4";
                int rc1= FFmpeg.execute(cmd);
                Log.i("MyAmplifyApp", "Running PlayVideo procedure");
                Intent intent = new Intent(getApplicationContext(), VideoDisplay.class);
                intent.putExtra("download",0);
                startActivity(intent);
            }
            else{
                String cmd="-f concat -safe 0 -i "+getApplicationContext().getFilesDir()+"/video1.txt -c copy "+"/storage/1AEE-3619/Android/data/com.example.amplify_demo/files"+"/video1out.mp4";
                int rc1= FFmpeg.execute(cmd);
                Intent intent = new Intent(getApplicationContext(), Downloaded_VIdeos.class);
                startActivity(intent);
            }
        }
    };
}
