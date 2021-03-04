package com.example.jaspreet_bal_lab_test_one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class Mode_two extends AppCompatActivity {
    private Geocoder geocoder;
    Button coordinates,current_location;
    EditText address;
    TextView latlng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_two);
        address = (EditText)findViewById(R.id.address);
        coordinates = (Button)findViewById(R.id.coordinates);
        latlng = (TextView)findViewById(R.id.latlng);
        geocoder = new Geocoder(this);
        coordinates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAddressDetails();
            }
        });
    }
    private void loadAddressDetails() {
        if (address.getText().toString() == null){
            Toast.makeText(this, "Please Enter the address", Toast.LENGTH_SHORT).show();
        }else {
            try {
                List<Address> addresses = geocoder.getFromLocationName(address.getText().toString(), 1);
                if (addresses.size() > 0) {
                    Address address = addresses.get(0);
                    System.out.println("Address" + address.toString());
                    latlng.setText("Address Found");
                    Intent i =new Intent(Mode_two.this,Mapsfragment.class);
                    Bundle bundle =new Bundle();
                    bundle.putDouble("latitude", address.getLatitude());
                    bundle.putDouble("longitude", address.getLongitude());
                    i.putExtras(bundle);
                    startActivity(i);

                }else {
                    latlng.setText("No lat/long found for this address!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}