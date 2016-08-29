package com.example.ms13421.eventbusdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;


public class    MainActivity extends AppCompatActivity {

    private TextView mMessageView;
    private MessageBroadcastReceiver mBroadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //注册事件
        EventBus.getDefault().register(this);
        //注册广播
        IntentFilter intentFilter = new IntentFilter("message_broadcast");
        mBroadcastReceiver = new MessageBroadcastReceiver();
        registerReceiver(mBroadcastReceiver, intentFilter);
        mMessageView = (TextView) findViewById(R.id.message);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.button2sticky).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StickyModeActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_hybrid).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startHybrid();
            }
        });

        findViewById(R.id.btn_java_interface_hybrid).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJavaInterfaceHybrid();
            }
        });

        findViewById(R.id.btn_url_router).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUrlRouter();
            }
        });
    }

    private void startHybrid() {
        Intent intent = new Intent(MainActivity.this, HybridActivity.class);
        startActivity(intent);
    }

    private void startJavaInterfaceHybrid() {
        Intent intent = new Intent(MainActivity.this, JavaInterfaceHybridActivity.class);
        startActivity(intent);
    }

    private void startUrlRouter() {
        Intent intent = new Intent(MainActivity.this, UrlRouterActivity.class);
        startActivity(intent);
    }

    //在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPostThread(MessageEvent messageEvent) {
        Log.e("PostThread", Thread.currentThread().getName());
        mMessageView.setText("Message from SecondActivity:" + messageEvent.getMessage());
    }

    //在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMainThread(MessageEvent messageEvent) {
        Log.e("MainThread", Thread.currentThread().getName());
    }

    //如果产生事件的是UI线程，则在新的线程中执行。如果产生事件的是非UI线程，则在产生事件的线程中执行
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackgroundThread(MessageEvent messageEvent) {
        Log.e("BackgroundThread", Thread.currentThread().getName());
    }

    //无论产生事件的是否是UI线程，都在新的线程中执行
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventAsync(MessageEvent messageEvent) {
        Log.e("Async", Thread.currentThread().getName());
    }


    public void onEvent(MessageEvent messageEvent) {

    }

    public void onEventMainThread(MessageEvent event)
    {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消事件注册
        EventBus.getDefault().unregister(this);
        //取消广播注册
        unregisterReceiver(mBroadcastReceiver);
    }

    public class MessageBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mMessageView.setText("Message from SecondActivity:" + intent.getStringExtra("message"));
        }
    }
}
