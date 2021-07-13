package com.example.keylesscarcontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
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

public class HomeActivity extends AppCompatActivity {
    ImageView imgbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        imgbutton = (ImageView) findViewById(R.id.imageView);

         Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        //Initialize And Assign Variables
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //Perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.keys:
                        startActivity(new Intent(getApplicationContext()
                                ,Carkeys.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:

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






        imgbutton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP){

                    String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D13?value=1";
                    callHttp(url);

                    vibrator.cancel();


                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_DOWN){
                    String url = "https://207.154.220.61:9443/I-2WVJmzCOtGoGi3uRZ0585eDTqD2w73/update/D13?value=1";
                    callHttp(url);

                    long[] pattern = { 0, 100, 0 }; //0 to start now, 200 to vibrate 200 ms, 0 to sleep for 0 ms.

                    vibrator.vibrate(VibrationEffect.createWaveform(pattern,0));
                    return  false;
                }






                return false;
            }
        });






    }

    void callHttp(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        this.getUnsafeOkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    HomeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

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

}