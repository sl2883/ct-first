package com.sunny.cttester;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.InAppNotificationButtonListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements InAppNotificationButtonListener {

    private int currentProfile = 3;

    @Override
    public void onInAppButtonClick(HashMap<String, String> hashMap) {
        if(hashMap != null){
            //Read the values
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentProfile = 3;
        initializeClevertap();
        onFirstTimeStart();
    }

    private void switchProfileTo1() {
        currentProfile = 3;
        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Identity", "sunny.clevertap" + String.valueOf(currentProfile) + "@gmail.com");      // String or number
        CleverTapAPI.getDefaultInstance(getApplicationContext()).onUserLogin(profileUpdate);
    }

    private void switchProfileTo2() {
        currentProfile = 4;
        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Identity", "sunny.clevertap" + String.valueOf(currentProfile) + "@gmail.com");      // String or number
        CleverTapAPI.getDefaultInstance(getApplicationContext()).onUserLogin(profileUpdate);
    }

    private void initializeClevertap() {
        CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"generic","generic","Your Channel Description",NotificationManager.IMPORTANCE_MAX,true);
        CleverTapAPI.getDefaultInstance(getApplicationContext()).setInAppNotificationButtonListener(this);

// How to add a sound file to your app : https://developer.clevertap.com/docs/add-a-sound-file-to-your-android-app


    }

    private String getRandomPhoneNumber() {
        Random rand = new Random();
        int num1 = (rand.nextInt(7) + 1) * 100 + (rand.nextInt(8) * 10) + rand.nextInt(8);
        int num2 = rand.nextInt(743);
        int num3 = rand.nextInt(10000);

        DecimalFormat df3 = new DecimalFormat("000"); // 3 zeros
        DecimalFormat df4 = new DecimalFormat("0000"); // 4 zeros

        String phoneNumber = df3.format(num1) + "-" + df3.format(num2) + "-" + df4.format(num3);

        return phoneNumber;
    }

    private HashMap<String, Object> getProfileHash() {
        HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
        profileUpdate.put("Name", "Sunny Clevertap");    // String
        profileUpdate.put("Identity", "sunny.clevertap" + String.valueOf(currentProfile) + "@gmail.com");      // String or number
        profileUpdate.put("Email", "sunny.clevertap" + String.valueOf(currentProfile) + "@gmail.com");      // String or number
        profileUpdate.put("Phone", getRandomPhoneNumber());   // Phone (with the country code, starting with +)
        profileUpdate.put("Gender", "M");             // Can be either M or F
        profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
// optional fields. controls whether the user will be sent email, push etc.
        profileUpdate.put("MSG-email", false);        // Disable email notifications
        profileUpdate.put("MSG-push", true);          // Enable push notifications
        profileUpdate.put("MSG-sms", false);          // Disable SMS notifications
        profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
        profileUpdate.put("active-number", getRandomPhoneNumber());      // Enable WhatsApp notifications
        ArrayList<String> stuff = new ArrayList<String>();
        stuff.add("SL");
        stuff.add("SunnyL");
        profileUpdate.put("Nicknames", stuff);                        //ArrayList of Strings
        String[] otherStuff = {"Comedy","Sci-fi"};
        profileUpdate.put("Interests", otherStuff);

        return profileUpdate;
    }

    private void onFirstTimeStart() {
        // each of the below mentioned fields are optional
                         //String Array
        CleverTapAPI.getDefaultInstance(getApplicationContext()).onUserLogin(getProfileHash());
    }

    private void updateProfile() {
        CleverTapAPI.getDefaultInstance(getApplicationContext()).pushProfile(getProfileHash());
    }

    /** Called when the user touches the button */
    public void onProductViewed(View view) {
        // Do something in response to button click
        pushProductViewedEvent();
    }

    public void onUpdateProfile(View view) {
        // Do something in response to button click
        updateProfile();
    }

    private void pushProductViewedEvent() {
        // event with properties
        HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
        prodViewedAction.put("Product Name", "Casio Chronograph Watch");
        prodViewedAction.put("Category", "Mens Accessories");
        prodViewedAction.put("Price", 59.99);
        prodViewedAction.put("Date", new java.util.Date());
        prodViewedAction.put("Current Profile", currentProfile);
        CleverTapAPI.getDefaultInstance(getApplicationContext()).pushEvent("Product viewed", prodViewedAction);
    }
}