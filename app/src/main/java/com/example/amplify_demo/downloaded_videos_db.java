package com.example.amplify_demo;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

@DynamoDBTable(tableName = "downloaded_videos")
public class downloaded_videos_db {
    private String id;
    private int balance;

    @DynamoDBIndexHashKey(attributeName = "CustomerID")
    public String getCustomerID() {
        return id;
    }

    public void setCustomerID(String id) {
        this.id = id;
    }

    @DynamoDBAttribute(attributeName = "Balance")
    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
