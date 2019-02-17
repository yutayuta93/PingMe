package com.example.android.pingme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Intent mServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intentの起動先のサービスの寿命>このアクテビティの寿命となるので、
        //getApplicationContext()で取得したアプリケーションコンテクストを使う
        mServiceIntent = new Intent(getApplicationContext(),PingService.class);
    }

    public void onPingClick(View view) {
        int seconds = 0;
        mServiceIntent.setAction(CommonConstants.ACTION_PING);

        //ユーザーが入力したメッセージを取得し、PingServiceを介してResultActivityに送る
        EditText msgText = (EditText)findViewById(R.id.edit_reminder);
        String message = msgText.getText().toString();
        mServiceIntent.putExtra(CommonConstants.EXTRA_MESSAGE,message);

        //ユーザーが指定したアラーム開始時間を取得
        EditText secondsText = (EditText)findViewById(R.id.edit_seconds);
        String secondsString = secondsText.getText().toString();
        if(secondsString == null || secondsString.trim().equals("")){
            seconds = R.string.seconds_default;
        }else{
            seconds = Integer.parseInt(secondsString);
        }
        int milliSeconds = seconds * 1000;
        mServiceIntent.putExtra(CommonConstants.EXTRA_TIMER,milliSeconds);

        startService(mServiceIntent);
    }





}
