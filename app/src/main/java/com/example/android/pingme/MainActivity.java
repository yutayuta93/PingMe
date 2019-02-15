package com.example.android.pingme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static String CHANNEL_ID = "001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPingClick(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setSmallIcon(R
                .drawable.ic_stat_notification);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello world!");
        //For Lower SDK
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        Intent resultIntent = new Intent(this, ResultActvity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //For Higher SDK
        createNotification(manager,CHANNEL_ID);

        manager.notify(CommonConstants.NOTIFICATION_ID,builder.build());

    }

    private void createNotification(NotificationManager manager,String chanel_ID){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //NotificationChannelの設定に必要なリソースの取得
            CharSequence channel_name = getString(R.string.channel_name);
            String channel_description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_MAX;

            //NotificationChannelオブジェクトの作成
            NotificationChannel channel = new NotificationChannel(chanel_ID,channel_name,
                    importance);
            channel.setDescription(channel_description);

            //NotificationManagerにNotificationChannelを登録
            manager.createNotificationChannel(channel);
        }


    }
}
