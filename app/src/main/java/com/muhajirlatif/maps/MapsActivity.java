package com.muhajirlatif.maps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    private EditText etLatitude, etLongitude, etZoom, etAddress;
    private ImageButton btnGo, btnSearch;

    private GoogleMap mMap;
    private Marker marker;
    private float zoom = 15f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        etZoom = findViewById(R.id.etZoom);
        etAddress = findViewById(R.id.etAddress);
        btnGo = findViewById(R.id.btnGo);
        btnSearch = findViewById(R.id.btnSearch);

        btnGo.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Current Marker"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoom));
    }

    public void searchAddress(String address) {
        Geocoder geocoder = new Geocoder(getBaseContext());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocationName(address, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Address location = null;
        if (addresses != null && addresses.size() > 0) {
            location = addresses.get(0);
        } else {
            Toast.makeText(this, "Address not found!", Toast.LENGTH_SHORT).show();
        }

        if (location != null) {
            Toast.makeText(this, location.getAddressLine(0), Toast.LENGTH_SHORT).show();
            goToLocation(location.getLatitude(), location.getLongitude(), zoom);
        }
    }

    public void goToLocation(double latitude, double longitude, float zoom) {
        try {
            LatLng pos = new LatLng(latitude, longitude);
            if (marker != null)
                marker.remove();
            marker = mMap.addMarker(new MarkerOptions().position(pos).title("Current Marker"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoom));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGo:
                String mLatitude = etLatitude.getText().toString();
                String mLongitude = etLongitude.getText().toString();
                String mZoom = etZoom.getText().toString();

                if (!mLatitude.isEmpty() && !mLongitude.isEmpty()) {
                    double lat = Double.parseDouble(etLatitude.getText().toString());
                    double lng = Double.parseDouble(etLongitude.getText().toString());

                    float z = zoom;
                    if (!mZoom.isEmpty())
                        z = Float.parseFloat(etZoom.getText().toString());

                    goToLocation(lat, lng, z);
                }

                break;

            case R.id.btnSearch:
                String address = etAddress.getText().toString();
                if (!address.isEmpty()) {
                    searchAddress(address);
                }
        }
    }
}
