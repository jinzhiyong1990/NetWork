package com.example.handler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       /*
       1. UI线程(主线程)
       */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = findViewById(R.id.textview);

//        创建hanlder

        @SuppressLint("HandlerLeak") final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                处理消息
                /*
                * 4. 主线程接到子线程发出来的消息, 处理
                * */
                Log.i(TAG, "handleMessage: " + msg.what);

                if (msg.what == 1002) {
                    textView.setText("imooc");
                    Log.d(TAG, "message: " + msg.arg1);

                }
            }
        };

        /*
        * 触发点击事件
        *
        * */

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//这里头可能做大量耗时操作
                    /*
                    2. 子线程
                    */

                new Thread(new Runnable() {
                    @Override
                    public void run() {



                        /*
                        3. 通知UI更新
                        */
                        handler.sendEmptyMessage(1001);

//                        handler.sendMessage 方法
                        Message message = Message.obtain();
                        message.what = 1002;
                        message.arg1 = 1003;
                        message.arg2 = 1004;
                        message.obj = MainActivity.this;


                        /**
                         * 定时任务
                         */

                        handler.sendMessage(message);
//                        约定一个时间寄出去, 这里头是3秒钟后, 绝对时间
                        handler.sendMessageAtTime(message, SystemClock.uptimeMillis() + 3000);
//                        多长时间之后送出去, 相对时间
                        handler.sendMessageDelayed(message, 2000);


//                        发送, 里面参数是一个可执行对象
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                int a = 1 + 2 + 3;
                            }
                        };
                        handler.post(runnable);
                        runnable.run();

//                        延迟
                        handler.postDelayed(runnable, 2000);


                    }
                }).start();
            }
        });


    }
}
