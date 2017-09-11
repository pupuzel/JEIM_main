package com.example.jock.jeim_main.Food;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Byte2;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jock.jeim_main.R;


public class FoodActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayAdapter spinneradapter;
    TextView order,cancel,check;
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout bottom;
    Button btn_foodmap;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_food);

        order = (TextView) findViewById(R.id.community_food_order);
        cancel = (TextView) findViewById(R.id.community_food_order_cancel);
        check = (TextView) findViewById(R.id.community_food_order_check);
        bottom = (LinearLayout) findViewById(R.id.bottom_sheet);
        btn_foodmap = (Button) findViewById(R.id.community_btn_food_map);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        order.setOnClickListener(this);
        cancel.setOnClickListener(this);
        check.setOnClickListener(this);
        btn_foodmap.setOnClickListener(this);

        Spinner spinner=(Spinner)findViewById(R.id.spinner);
        spinneradapter = ArrayAdapter.createFromResource(this,R.array.Food,android.R.layout.simple_spinner_item);
        spinneradapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.community_food_order :  // 정렬 버튼 클릭
                if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_HIDDEN){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                }
                break;

            case R.id.community_food_order_cancel:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;

            case R.id.community_food_order_check:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                break;

            case R.id.community_btn_food_map :   // 지도로 이동
                startActivity(new Intent(getApplicationContext(),FoodMap.class));
                break;
        }

    }
}