package com.example.keylesscarcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class GPSActivity extends AppCompatActivity {
    WebView web1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g_p_s2);

        web1 = (WebView) findViewById(R.id.webview1);


// Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://gpssender-aee1e-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("car");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Object value = dataSnapshot.getValue(Object.class);
                String latitude = ((HashMap) value).get("latitude").toString();
                String longitude = ((HashMap) value).get("longitude").toString();
                web1.getSettings().setBuiltInZoomControls(true);
                web1.getSettings().setJavaScriptEnabled(true);

                web1.loadUrl("https://www.google.com/maps/search/?api=1&query=" + latitude + "," + longitude);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });

        //Initialize And Assign Variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.gps);
        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.keys:
                        startActivity(new Intent(getApplicationContext()
                                , Carkeys.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.gps:

                        return true;
                }
                return false;
            }
        });

    }
}