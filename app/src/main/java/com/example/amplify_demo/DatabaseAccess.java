package com.example.amplify_demo;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Primitive;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import java.util.List;
public class DatabaseAccess {
    private static final String COGNITO_POOL_ID = "us-east-1:35894e38-65cd-4423-9dd2-3c15de48a08c";
    private static final Regions MY_REGION = Regions.US_EAST_1;
    private AmazonDynamoDBClient dbClient;
    private Table dbTable;
    private Context context;
    private final String DYNAMODB_TABLE = "AndroidTest";
    CognitoCachingCredentialsProvider credentialsProvider;


    private static volatile DatabaseAccess instance;
    private DatabaseAccess (Context context) {
        this.context =context;
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "us-east-1:35894e38-65cd-4423-9dd2-3c15de48a08c", // Identity pool ID
                Regions.US_EAST_1 // Region
        );
        dbClient = new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.US_EAST_1));
        Log.i("Dynamo",dbClient.toString());
        dbTable = Table.loadTable(dbClient, DYNAMODB_TABLE);
        Log.i("Dynamo",dbTable.toString());

    }

    public static synchronized DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }
    public Document getItem (String user_id){
        Document result = dbTable.getItem(new Primitive(credentialsProvider.getCachedIdentityId()), new Primitive(user_id));
        return result;
    }
    public List<Document> getAllItems() {
        return dbTable.query(new Primitive(credentialsProvider.getCachedIdentityId())).getAllResults();
    }

}
