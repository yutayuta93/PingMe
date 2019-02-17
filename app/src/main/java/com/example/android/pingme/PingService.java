package com.example.android.pingme;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class PingService extends IntentService {

    @Override
    public void onCreate() {
        Log.d(SEARVICE_LOG,"Searvice:onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.d(SEARVICE_LOG,"Searvice:onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(SEARVICE_LOG,"Searvice:onDestroy()");
        super.onDestroy();
    }

    //Log出力用定数
    private static final String SEARVICE_LOG = "SEARVICE_LOG";

    private NotificationManager mNotificationManager;
    private String mMessage;
    private int mMillis;
    private final String CHANNEL_ID = "1";
    NotificationCompat.Builder builder;

    public PingService() {
        super("PingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mMessage = intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
        mMillis = intent.getIntExtra(CommonConstants.EXTRA_TIMER,CommonConstants
                .DEFAULT_TIMER_DURATION);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        String action = intent.getAction();
        switch(action){
            case CommonConstants.ACTION_PING:
                issueNotification(intent,mMessage);
                break;
            case CommonConstants.ACTION_DISMISS:
                nm.cancel(CommonConstants.NOTIFICATION_ID);
                break;
            case CommonConstants.ACTION_SNOOZE:
                nm.cancel(CommonConstants.NOTIFICATION_ID);
                Log.d(CommonConstants.DEBUG_TAG,getString(R.string.snoozing));
                mMillis = CommonConstants.SNOOZE_DURATION;
                issueNotification(intent,getString(R.string.snoozing));
                break;
        }

    }

    private void issueNotification(Intent intent,String message){
        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //通知に登録するIntentを作成
        //この場合、Intentを受け取るのもこのサービスなので、thisでいい
        Intent dismissIntent = new Intent(this,PingService.class);
        dismissIntent.setAction(CommonConstants.ACTION_DISMISS);
        PendingIntent piDismiss = PendingIntent.getService(this,0,dismissIntent,0);

        Intent snoozeIntent = new Intent(this,PingService.class);
        snoozeIntent.setAction(CommonConstants.ACTION_SNOOZE);
        PendingIntent piSnooze = PendingIntent.getService(this,0,snoozeIntent,0);


        createNotification(mNotificationManager,CHANNEL_ID);

        builder = new NotificationCompat.Builder(this,CHANNEL_ID).setContentTitle(getString(R
                .string.notification)).setContentText(getString(R.string.ping)).setSmallIcon(R
                .drawable.ic_stat_notification).setDefaults(Notification.DEFAULT_ALL).setStyle
                (new NotificationCompat.BigTextStyle().bigText(message)).addAction(R.drawable
                .ic_stat_dismiss,getString(R.string.dismiss),piDismiss).addAction(R.drawable
                .ic_stat_snooze,getString(R.string.snooze),piSnooze);


        Intent resultIntent = new Intent(this,ResultActvity.class);
        resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE,mMessage);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,resultIntent,PendingIntent
                .FLAG_UPDATE_CURRENT);

        builder.setContentIntent(pendingIntent);
        startTimer(mMillis);



    }

    private void startTimer(int mMillis) {
        Log.d(CommonConstants.DEBUG_TAG,getString(R.string.timer_start));
        try{
            Thread.sleep(mMillis);
        }catch(InterruptedException e){
            Log.d(CommonConstants.DEBUG_TAG,getString(R.string.sleep_error));
        }
        Log.d(CommonConstants.DEBUG_TAG,getString(R.string.timer_finished));
        issueNotification(builder);
    }

    private void issueNotification(NotificationCompat.Builder builder){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID,builder.build());
    }

    /*
    OLEO以降向けに、NotificationChannelを設定する
     */
    private void createNotification(NotificationManager manager,String chanel_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //NotificationChannelの設定に必要なリソースの取得
            CharSequence channel_name = getString(R.string.channel_name);
            String channel_description = getString(R.string.channel_description);

            //NotificationChannelオブジェクトの作成
            NotificationChannel channel = new NotificationChannel(chanel_ID, channel_name,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription(channel_description);

            //NotificationManagerにNotificationChannelを登録
            manager.createNotificationChannel(channel);
        }
    }
}
