package com.example.artex.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.artex.R;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class AboutActivity extends AppCompatActivity {

    private LinearLayout directionLinearLayoutBtn;
    private FloatingActionButton callPhoneFloatingActionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        directionLinearLayoutBtn = findViewById(R.id.direction_linear_layout_btn);
        callPhoneFloatingActionBtn = findViewById(R.id.phone_about_foating_action_btn);

        //call phone On click Methode
        callPhoneFloatingActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent calling = new Intent(Intent.ACTION_DIAL);
                calling.setData(Uri.parse("tel:" + "73671424"));
                startActivity(calling);
            }
        });

        //direction On Click Methode
        directionLinearLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FusedLocationProviderClient fusedLocationProviderClient;

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(AboutActivity.this);

                //check location permetion
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED){

                        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if(location != null){

                                    double currentLatitude = location.getLatitude();
                                    double currentLongitude = location.getLongitude();

                                    String uri = "http://maps.google.com/maps?saddr=" + currentLatitude + "," + currentLongitude + "&daddr=" + "35.493454" + "," + "11.018049";
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                    startActivity(intent);
                                }
                            }
                        });

                    }else{
                        ActivityCompat.requestPermissions(AboutActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},100);
                    }
                }
            }
        });
    }
}