package com.example.jock.jeim_main.Bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;


public class BusActivity  extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_main);

        TextView bus_homeback = (TextView)findViewById(R.id.bus_homeback);
        bus_homeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backintent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(backintent);
                finish();
            }
        });
    }

}