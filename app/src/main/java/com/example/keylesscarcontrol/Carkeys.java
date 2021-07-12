package com.example.keylesscarcontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Carkeys extends AppCompatActivity {
    ImageView iacon, iacoff, ilock, iunlock, iwindon, iwindoff;
    WebView web1;


    void callHttp(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        this.getUnsafeOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getApplicationContext(), "Error ! ", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    Carkeys.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain,
                                                       String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0])
                    .hostnameVerifier(new HostnameVerifier() {
                        @Override
                        public boolean verify(String hostname, SSLSession session) {
                            return true;
                        }
                    }).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carkeys);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        iacon = (ImageView) findViewById(R.id.ivacon);
        iacoff = (ImageView) findViewById(R.id.ivacoff);
        ilock = (ImageView) findViewById(R.id.ivlock);
        iunlock = (ImageView) findViewById(R.id.ivunlock);
        iwindoff = (ImageView) findViewById(R.id.ivwindoff);
        iwindon = (ImageView) findViewById(R.id.ivwindon);


        iacon.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D13?value=1";
                callHttp(url);


            }
        });


        iacoff.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D13?value=0";
                callHttp(url);


            }
        });
        ilock.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D12?value=0";
                callHttp(url);


            }
        });
        iunlock.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D12?value=1";
                callHttp(url);


            }
        });
        iwindon.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D11?value=1";
                callHttp(url);


            }
        });
        iwindoff.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D11?value=0";
                callHttp(url);


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
                switch (item.getItemId()) {
                    case R.id.keys:

                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.gps:
                        startActivity(new Intent(getApplicationContext()
                                , GPSActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });


    }


}








