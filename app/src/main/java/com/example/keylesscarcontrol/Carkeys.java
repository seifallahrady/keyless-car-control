package com.example.keylesscarcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Carkeys extends AppCompatActivity {
    ImageView iacon,iacoff,ilock,iunlock,iwindon,iwindoff;
    WebView web1 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carkeys);

        iacon=(ImageView)findViewById(R.id.ivacon);
        iacoff=(ImageView)findViewById(R.id.ivacoff);
        ilock=(ImageView)findViewById(R.id.ivlock);
        iunlock=(ImageView)findViewById(R.id.ivunlock);
        iwindoff=(ImageView)findViewById(R.id.ivwindoff);
        iwindon=(ImageView)findViewById(R.id.ivwindon);

        iacon.setOnClickListener(new View.OnClickListener() {
            OkHttpClient client = new OkHttpClient();

            void run(String url) throws IOException {
                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try (Response response = client.newCall(request).execute()) {

                }
            }

            @Override
            public void onClick(View v) {
                try {
                    this.run("https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D13?value=1");
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        //Initialize And Assign Variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.keys);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.keys:

                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                ,HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.gps:
                        startActivity(new Intent(getApplicationContext()
                                ,GPSActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


    }
}