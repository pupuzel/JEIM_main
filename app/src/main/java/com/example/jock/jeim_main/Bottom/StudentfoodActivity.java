package com.example.jock.jeim_main.Bottom;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jock.jeim_main.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentfoodActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView listView;
    private List<StudentFoodNotice> NoticeList  = new ArrayList<StudentFoodNotice>();
    private StudentFoodAdapter adapter;
    private Context context;
    private final String firstdate = "2017-11-20";
    private final String lastdate = "2017-11-24";
    private TextView txt_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_studentfood_main);
        listView = (ListView) findViewById(R.id.studentfood_listview);
        txt_back = (TextView) findViewById(R.id.studentfood_homeback);
        txt_back.setOnClickListener(this);
        setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.studentfood_homeback:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /* 데이터를 가져와서 뷰에 뿌려주는 메소드*/
    public void setData(){
        try{
            context = this;
            String result = new StudentfoodTask(context).execute(firstdate,lastdate).get();
            setlistItem(result);
        }catch (Exception e){ e.printStackTrace(); }
    }

    public void setlistItem(String jsonarray){
        try{
            JSONArray jsonArray = new JSONArray(jsonarray);
            JSONObject json;
            int count = 0;
            String date,gama,inter,cham;
            while( count < jsonArray.length()){
                json = jsonArray.getJSONObject(count);
                date = json.getString("날짜");
                gama = json.getString("가마");
                inter = json.getString("인터");
                cham = json.getString("참참");
                NoticeList.add(new StudentFoodNotice(date,gama,inter,cham));
                count++;
            }
            adapter = new StudentFoodAdapter(getApplicationContext() ,NoticeList);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
