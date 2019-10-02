package com.muhajirlatif.maps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnMap, btnRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMap = findViewById(R.id.btnMaps);
        btnRoute = findViewById(R.id.btnRoute);

        btnMap.setOnClickListener(this);
        btnRoute.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnMaps:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                break;

            case R.id.btnRoute:
                startActivity(new Intent(MainActivity.this, MapsRouteActivity.class));
                break;
        }
    }
}
