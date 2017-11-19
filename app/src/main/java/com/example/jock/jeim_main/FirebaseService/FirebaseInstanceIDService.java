package com.example.jock.jeim_main.FirebaseService;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jock.jeim_main.Another.Pref;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {
    String token;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        Log.i("MyFCM", "FCM token: " + FirebaseInstanceId.getInstance().getToken());

        token = FirebaseInstanceId.getInstance().getToken();
        Pref.Token = getSharedPreferences("Token", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = Pref.Token.edit();
        editor.putString("토큰",token);
        editor.putString("알람","1");  // 1이면 알람  2이면 알람끔
        editor.commit();
    }
}
