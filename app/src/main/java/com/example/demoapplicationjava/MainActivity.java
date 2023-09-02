package com.example.demoapplicationjava;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.Constants;
import com.clevertap.android.sdk.inapp.CTLocalInApp;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import java.util.ArrayList;

public class MainActivity<jsonObject> extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CleverTapAPI.setDebugLevel(3);

        CleverTapAPI clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"YourChannelId","Your Channel Name","Your Channel Description", NotificationManager.IMPORTANCE_MAX,true);

        clevertapDefaultInstance.enableDeviceNetworkInfoReporting(true);

// Creating a Notification Channel With Sound Support
        CleverTapAPI.createNotificationChannel(getApplicationContext(),"got","Game of Thrones","Game Of Thrones",NotificationManager.IMPORTANCE_MAX,true,"gameofthrones.mp3");

// How to add a sound file to your app : https://developer.clevertap.com/docs/add-a-sound-file-to-your-android-app
        Button btnlogin=findViewById(R.id.login);
        Button btnPushProfile=findViewById(R.id.pushprofile);
        Button btnPushEvent=findViewById(R.id.pushevent);
        Button btnPushPrimer=findViewById(R.id.pushprimer);
        EditText etidentity=findViewById(R.id.identity);
        EditText etname=findViewById(R.id.name);
        EditText etemail=findViewById(R.id.email);
        EditText etmobile=findViewById(R.id.mobile);

        btnlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String identity= etidentity.getText().toString();
                String name= etname.getText().toString();
                String email= etemail.getText().toString();
                String mobile= etmobile.getText().toString();
                //Toast.makeText(MainActivity.this, "Hi" +name, Toast.LENGTH_SHORT).show();

                // each of the below mentioned fields are optional
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", name);    // String
                profileUpdate.put("Identity", identity);      // String or number
                profileUpdate.put("Email", email); // Email address of the user
                profileUpdate.put("Phone", mobile);   // Phone (with the country code, starting with +)
                profileUpdate.put("Gender", "M");             // Can be either M or F
                profileUpdate.put("DOB", new Date());         // Date of Birth. Set the Date object to the appropriate value first
// optional fields. controls whether the user will be sent email, push etc.
                profileUpdate.put("MSG-email", false);        // Disable email notifications
                profileUpdate.put("MSG-push", true);          // Enable push notifications
                profileUpdate.put("MSG-sms", false);          // Disable SMS notifications
                profileUpdate.put("MSG-whatsapp", true);      // Enable WhatsApp notifications
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("Bag");
                stuff.add("Shoes");
                profileUpdate.put("MyStuff", stuff);                        //ArrayList of Strings

                clevertapDefaultInstance.onUserLogin(profileUpdate);
            }
        });

        btnPushProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                ArrayList<String> stuff = new ArrayList<String>();
                stuff.add("Jeans");
                stuff.add("Perfume");
                //String[] otherStuff = {"Jeans","Perfume"};
                profileUpdate.put("MyStuff", stuff);
                clevertapDefaultInstance.pushProfile(profileUpdate);
                Toast.makeText(MainActivity.this, "Added" +stuff, Toast.LENGTH_SHORT).show();
            }
        });

        btnPushEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // clevertapDefaultInstance.pushEvent("Product viewed");
                HashMap<String, Object> prodViewedAction = new HashMap<String, Object>();
                prodViewedAction.put("Product Name", "Casio Chronograph Watch");
                prodViewedAction.put("Category", "Mens Accessories");
                prodViewedAction.put("Price", 59.99);
               // prodViewedAction.put("Date", new java.util.Date());

                clevertapDefaultInstance.pushEvent("Product viewed", prodViewedAction);
            }
        });

        btnPushPrimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                @SuppressLint("RestrictedApi") JSONObject jsonObject = CTLocalInApp.builder()
                        .setInAppType(CTLocalInApp.InAppType.HALF_INTERSTITIAL)
                        .setTitleText("Get Notified")
                        .setMessageText("Please enable notifications on your device to use Push Notifications.")
                        .followDeviceOrientation(true)
                        .setPositiveBtnText("Allow")
                        .setNegativeBtnText("Cancel")
                        .setBackgroundColor(Constants.WHITE)
                        .setBtnBorderColor(Constants.BLUE)
                        .setTitleTextColor(Constants.BLUE)
                        .setMessageTextColor(Constants.BLACK)
                        .setBtnTextColor(Constants.WHITE)
                     //   .setImageUrl("https://icons.iconarchive.com/icons/treetog/junior/64/camera-icon.png")
                        .setBtnBackgroundColor(Constants.BLUE)
                        .build();
                Log.d("clevertap", "onCreate: "+jsonObject.toString());
                clevertapDefaultInstance.promptPushPrimer(jsonObject);
            }
        });
        HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
        chargeDetails.put("Amount", 300);
        chargeDetails.put("Payment Mode", "Credit card");
        chargeDetails.put("Charged ID", 24052013);

        HashMap<String, Object> item1 = new HashMap<String, Object>();
        item1.put("Product category", "books");
        item1.put("Book name", "The Millionaire next door");
        item1.put("Quantity", 1);

        HashMap<String, Object> item2 = new HashMap<String, Object>();
        item2.put("Product category", "books");
        item2.put("Book name", "Achieving inner zen");
        item2.put("Quantity", 1);

        HashMap<String, Object> item3 = new HashMap<String, Object>();
        item3.put("Product category", "books");
        item3.put("Book name", "Chuck it, let's do it");
        item3.put("Quantity", 5);

        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        try {
            clevertapDefaultInstance.pushChargedEvent(chargeDetails, items);
        } catch (Exception e) {
            // You have to specify the first parameter to push()
            // as CleverTapAPI.CHARGED_EVENT
        }
    }


}