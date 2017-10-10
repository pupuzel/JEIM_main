package com.example.jock.jeim_main;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class CntDate {

    private Calendar cal = Calendar.getInstance();
    private SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String currentDate;
    private String NextDate;
    private Long diffresult = null;

    public String getCntDate(){ // DB로 보내줄 방문일자 yy-mm-dd
        return getCurrentDate().substring(0,10);
    }

    public String getCurrentDate(){  // 현재 yy-mm-dd h:m:s 구하는 함수
        currentDate = formatter.format(new Date());
        return currentDate;
    }

    public String getNextDate(){
        return NextDate;
    }

    public void setNextDate(){   // 다음 초기화 날짜 구하는 함수
        cal.setTime(new Date());
        cal.add(Calendar.DATE, 1);
        String strDate = formatter.format(cal.getTime());
        NextDate = strDate.substring(0,11)+"00:00:00";
    }

    // 두날짜의 차이 구하기
    public Long doDiffOfDate(String nextDate){

        try {
            getCurrentDate();
            Date current = formatter.parse(currentDate);
            Date Next= formatter.parse(nextDate);

            diffresult = (Next.getTime() - current.getTime())/1000;

        } catch (Exception e) {
            Log.i("방문자예외",e.toString());
        }

        return diffresult;
    }

}
