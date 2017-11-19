package com.example.jock.jeim_main.FAQ;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jock.jeim_main.Bottom.GongjiActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.R;
import com.example.jock.jeim_main.Another.Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class FAQActivity extends AppCompatActivity {

    TextView faq_backhome;
    private ListView faq_listView;
    private FAQAdapter adapter;
    private List<FAQNotice> faqNoticeList = new ArrayList<FAQNotice>();
    public Boolean faqOnOff = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.faq_main);

        faq_backhome = (TextView)findViewById(R.id.faq_homeback);

        faq_listView = (ListView)findViewById(R.id.faq_listview);

        adapter = new FAQAdapter(getApplicationContext(),faqNoticeList);
        faq_listView.setAdapter(adapter);
        new selectlist().execute();

        faq_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout contents= (LinearLayout) view.findViewById(R.id.faq_layout_contents);
                TextView  faq_title = (TextView) view.findViewById(R.id.faq_title);
                TextView arrow_right = (TextView)view.findViewById(R.id.arrow_right) ;
                TextView arrow_down = (TextView)view.findViewById(R.id.arrow_down);
                Animation animation2 = new AlphaAnimation(0, 1);
                animation2.setDuration(1000);
                if(faqOnOff == false){
                    contents.setVisibility(View.VISIBLE);
                    arrow_right.setVisibility(View.GONE);
                    arrow_down.setVisibility(View.VISIBLE);
                    contents.setAnimation(animation2);
                    faqOnOff = true;
                }else{
                    contents.setVisibility(View.GONE);
                    arrow_right.setVisibility(View.VISIBLE);
                    arrow_down.setVisibility(View.GONE);
                    contents.setAnimation(animation2);
                    faqOnOff = false;
                }


                // parent는 AdapterView의 속성의 모두 사용 할 수 있다.
                // String tv = (String)parent.getAdapter().getItem(position);
                //Toast.makeText(getApplicationContext(), tv, Toast.LENGTH_SHORT).show();

                // Position 은 클릭한 Row의 position 을 반환해 준다.
                //Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_SHORT).show();
                // view는 클릭한 Row의 view를 Object로 반환해 준다.
            }
        });





        faq_backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backhome = new Intent(getApplicationContext(),MainActivity.class);
                backhome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(backhome);

            }
        });
    } // finish onCreate

    /* 바텀바 컨트롤 메소드 */
    public void Bottom(View v){
        switch (v.getId()){
            case R.id.bottom_gongji :
                Intent intent = new Intent(getApplicationContext(),GongjiActivity.class);
                startActivity(intent);
                break;
            case R.id.bottom_home :
                Intent intent2 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.bottom_food :

                break;
            case R.id.bottom_schedule :

                break;
            case R.id.bottom_total :

                break;
        }
    }


    // 게시판 이용을 위한 AsyncTask 쓰레드 클래스 사용
    class selectlist extends AsyncTask<Void,Void,String> {
        String result;

        @Override
        protected String doInBackground(Void... params) {
            try{
                URL url = new URL(Url.Main+Url.FAQList);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); //해당 url에 접속할수 있도록
                InputStream inputStream = httpURLConnection.getInputStream();        //넘어오는 결과값들을 그대로 받아올수있도록
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));    // inputStream에 값을 읽을수 있도록
                String Jsontext;
                StringBuilder stringBuilder = new StringBuilder();
                while ((Jsontext = bufferedReader.readLine()) != null){

                    stringBuilder.append(Jsontext);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                result = stringBuilder.toString().trim();
            }catch (Exception e){
                e.printStackTrace();
            }


            return result;
        } // doInBackground finish

        @Override
        public void onPostExecute(String result){
            try{
                JSONArray jsonArray = new JSONArray(result);
                JSONObject json;
                int count = 0;
                String faq_title,faq_content,faq_code,faq_date;
                while( count < jsonArray.length()){

                    json = jsonArray.getJSONObject(count);
                    faq_title = json.getString("제목");
                    faq_content = json.getString("내용");
                    faq_code = json.getString("코드");
                    faq_date = json.getString("날짜");
                    faqNoticeList.add(new FAQNotice(faq_title,faq_content,faq_date,faq_code));
                    count++;
                }
                adapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }

        }   //onPostExecute finish

    }   //class selectlist finish
}
