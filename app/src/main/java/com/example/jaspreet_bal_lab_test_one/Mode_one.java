package com.example.jaspreet_bal_lab_test_one;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class Mode_one extends AppCompatActivity {

    EditText lat,lng,phone;
    Button btnSearch,btnSend;
    private Geocoder geocoder;
    Double latitude,longitude;
    TextView add;
    String address;

    private static final String TAG = "Current Location";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_one);
        lat = (EditText)findViewById(R.id.latitude);
        lng = (EditText)findViewById(R.id.longitude);
        phone = (EditText)findViewById(R.id.phone);
        btnSearch = (Button)findViewById(R.id.search);
        btnSend = (Button)findViewById(R.id.send);
        add = (TextView)findViewById(R.id.addressFo);
        geocoder = new Geocoder(this);
        btnSend.setEnabled(false);
        if (checkPermission(Manifest.permission.SEND_SMS)){
            btnSend.setEnabled(true);
            btnSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  sendMessage();
                }
            });
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                latitude = Double.valueOf(lat.getText().toString());
                longitude = Double.valueOf(lng.getText().toString());
                showLocationOnMap();
                findAddress();
            }
        });

    }

    private void sendMessage() {
        String num = phone.getText().toString();
        if (num == null){
            return;
        }
        if (checkPermission((Manifest.permission.SEND_SMS))){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(num,null,address,null,null);
            Toast.makeText(Mode_one.this, "Sms send", Toast.LENGTH_SHORT).show();
        }
    }

    private void findAddress() {
        if (lat.getText().toString() == null || lng.getText().toString() == null){
            Toast.makeText(this, "Please Enter the address", Toast.LENGTH_SHORT).show();
        }else {
            if (lat.getText().toString() == null || lng.getText().toString() == null) {
                Toast.makeText(this, "Please Enter the address", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addresses.size() > 0) {
                        Log.d(TAG, "Latitude" + addresses.get(0).getLatitude());
                        Log.d(TAG, "Longitude" + addresses.get(0).getLongitude());
                        Log.d(TAG, "Country" + addresses.get(0).getCountryName());
                        Log.d(TAG, "Locality" + addresses.get(0).getLocality());
                        Log.d(TAG, "Address" + addresses.get(0).getAddressLine(0));
                        address = addresses.get(0).getCountryName() + "\n" + addresses.get(0).getLocality() + "\n" + addresses.get(0).getAddressLine(0);
                        add.setText(address);
                    }else {
                        add.setText("No Address found for this Lat/Long!");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void showLocationOnMap() {
        if (lat.getText().toString() == null || lng.getText().toString() == null) {
            Toast.makeText(this, "Please Enter the address", Toast.LENGTH_SHORT).show();
        } else{

        Intent i = new Intent(Mode_one.this, Mapsfragment.class);
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", latitude);
        bundle.putDouble("longitude", longitude);
        i.putExtras(bundle);
        startActivity(i);
    }
    }
    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this,permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }
}