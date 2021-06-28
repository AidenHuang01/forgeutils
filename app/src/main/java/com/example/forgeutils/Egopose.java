package com.example.forgeutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import static android.content.Context.LOCATION_SERVICE;
public class Egopose extends AppCompatActivity {
    public  static ClipboardManager clipboard = null;
    updateThread thread;
    TextView textView;
    LocationManager locationManager;
    Button but2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_egopose);
        textView = (TextView) findViewById(R.id.textView);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        thread = new updateThread();
        but2 = findViewById(R.id.button2);
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            StringBuilder sb = new StringBuilder();
            sb.append("FINE_LOCATION: " + checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) + "\n");
            sb.append("COARSE_LOCATION: " + checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) + "\n");
            textView.setText(sb);
            System.out.println(checkSelfPermission("FINE_LOCATION: " + Manifest.permission.ACCESS_FINE_LOCATION));
            System.out.println(checkSelfPermission("FINE_LOCATION: " + Manifest.permission.ACCESS_COARSE_LOCATION));
            return;
        }
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                1,
                locationListener);

        thread.stopped = false;
        thread.start();
    }
    public void locationUpdates(Location location) {
        if(location !=null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Egopose \n");
            stringBuilder.append("Longitude: " + location.getLongitude() + "\n");
            stringBuilder.append("Latitude: " + location.getLatitude() + "\n");
            stringBuilder.append("Altitude: " + location.getAltitude() + "\n");
            stringBuilder.append("Speed: " + location.getSpeed() + "\n");
            stringBuilder.append("Bearing: " + location.getBearing() + "\n");
            textView.setText(stringBuilder.toString());
        }
        else {
            textView.setText("no GPS info");
        }
    }

    private class updateThread extends Thread {
        private boolean stopped;

        private updateThread() {
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {
                if(!stopped) {
                    if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("FINE_LOCATION: " + checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) + "\n");
                        sb.append("COARSE_LOCATION: " + checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) + "\n");
                        textView.setText(sb);
                        System.out.println(checkSelfPermission("FINE_LOCATION: " + Manifest.permission.ACCESS_FINE_LOCATION));
                        System.out.println(checkSelfPermission("FINE_LOCATION: " + Manifest.permission.ACCESS_COARSE_LOCATION));
                    }
                    locationUpdates(locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        }
    }


    public class ClipboardTools {
        public void copyTextToClipboard(final Context activity, final String str) {
            if (Looper.myLooper() == null){
                Looper.prepare();
            }
            clipboard = (ClipboardManager) activity.getSystemService(Activity.CLIPBOARD_SERVICE);

            ClipData textCd = ClipData.newPlainText("data", str);
            clipboard.setPrimaryClip(textCd);
        }
    }

    public void clickToCopy(View view) {
        ClipboardTools clipboardTools = new ClipboardTools();
        clipboardTools.copyTextToClipboard(view.getContext(),textView.getText().toString());
    }
}


