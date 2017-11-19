package com.example.jock.jeim_main.FirebaseService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.jock.jeim_main.Jooungo.JooungoDetailActivity;
import com.example.jock.jeim_main.Main.MainActivity;
import com.example.jock.jeim_main.Major.Major_key_main;
import com.example.jock.jeim_main.R;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private JSONObject jsonObject;
    private String group,code,name;
    private Intent intent;
    private NotificationCompat.Builder  notificationBuilder = new NotificationCompat.Builder(this);

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getData().get("json"));
    }

    private void showNotification(String json) {
        try {
            jsonObject = new JSONObject(json);
            group = jsonObject.getString("group");
            if(group.equals("jooungo")){

                code = jsonObject.getString("code");
                name = jsonObject.getString("name");
                intent = new Intent(this, JooungoDetailActivity.class);
                intent.putExtra("게시판코드",code);
                notificationBuilder.setContentTitle("중고장터 댓글알림")
                                   .setContentText(name+"님이 댓글을 다셨습니다.");
            }else if(group.equals("key")){
                Log.i("즐즐즐","앙앙앙");
                code = jsonObject.getString("code");
                name = jsonObject.getString("name");
                intent = new Intent(this, Major_key_main.class);
                notificationBuilder.setContentTitle("열쇠 수불대장 알림")
                        .setContentText(name+"님이 "+code+"호 인수신청 하였습니다.");
            }
        }catch (Exception e){ e.printStackTrace(); }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notificationBuilder.setSmallIcon(R.mipmap.main_icon)
                           .setAutoCancel(true)
                           .setSound(defaultSoundUri)
                           .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }

}
