package com.example.jock.jeim_main.Bottom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.jock.jeim_main.R;

import java.util.ArrayList;
import java.util.List;


public class StudentfoodActivity extends AppCompatActivity {

    private ListView listView;
    private List<StudentFoodNotice> NoticeList  = new ArrayList<StudentFoodNotice>();
    private StudentFoodAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_studentfood_main);

        NoticeList.add(new StudentFoodNotice("가마","2017-09-28","떡볶이"));
        NoticeList.add(new StudentFoodNotice("인턴","2017-09-28","떡볶이"));
        NoticeList.add(new StudentFoodNotice("참참","2017-09-28","떡볶이"));
        NoticeList.add(new StudentFoodNotice("참참","2017-09-28","떡볶이"));
        NoticeList.add(new StudentFoodNotice("참참","2017-09-28","떡볶이"));
        NoticeList.add(new StudentFoodNotice("참참","2017-09-28","떡볶이"));
        NoticeList.add(new StudentFoodNotice("참참","2017-09-28","떡볶이"));

      /*  adapter = new StudentFoodAdapter(getApplicationContext() ,NoticeList);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/
    }
}
