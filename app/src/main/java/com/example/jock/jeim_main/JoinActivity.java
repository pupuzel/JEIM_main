package com.example.jock.jeim_main;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class JoinActivity extends AppCompatActivity {

    private ArrayAdapter adapter;
    private Spinner spinner;
    TextView login_ok,Joinhome;
    private String idtext;
    private String pwtext;
    private String pwtext2;
    private String gender;
    private String subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        spinner = (Spinner)findViewById(R.id.majorSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.major,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText ideditext = (EditText) findViewById(R.id.idjointext);
        final EditText pweditext = (EditText) findViewById(R.id.passwdjointext);
        final EditText pweditext2 = (EditText) findViewById(R.id.passwdjointextcheck);

      RadioGroup gendergroup = (RadioGroup) findViewById(R.id.gendergroup);
        int gendergroupid = gendergroup.getCheckedRadioButtonId();
       // gender = ((RadioButton) findViewById(gendergroupid)).getText().toString();

     /*  gendergroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton genderButton = (RadioButton)findViewById(checkedId);
                gender = genderButton.getText().toString();
            }
        });*/


        Joinhome = (TextView)findViewById(R.id.Joinhome);
        login_ok = (TextView)findViewById(R.id.textlogin_ok);
        login_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(loginintent);

            }
        });
        Joinhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(home);
            }
        });


    }
}
