package com.example.android.pingme;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private static String CHANNE_ID = "001";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onPingClick(View view) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,CHANNE_ID)
                .setSmallIcon(R
                .drawable.ic_stat_notification);
        builder.setContentTitle("My notification");
        builder.setContentText("Hello world!");

        Intent resultIntent = new Intent(this, ResultActvity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this,0,resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(resultPendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(CommonConstants.NOTIFICATION_ID,builder.build());

    }

    private void createNotification(NotificationManager manager,String chanel_ID){

    }
}
