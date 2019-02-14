package com.example.android.pingme;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ResultActvity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //TextViewにテキストを設定
        String message = getIntent().getStringExtra(CommonConstants.EXTRA_MESSAGE);
        TextView result_msg = (TextView)findViewById(R.id.result_message);
        result_msg.setText(message);
    }

    public void onDismissClick(View view){

    }

    public void onSnoozeClick(View view){

    }
}
