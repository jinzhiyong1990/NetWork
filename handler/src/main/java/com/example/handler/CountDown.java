package com.example.handler;
/*
 * 倒计时!
 *
 * */

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class CountDown extends AppCompatActivity {

    /**
     * 倒计时标记handler code
     */
    public static final int COUNTDOWN_TIME_CODE = 100001;
    /**
     * 倒计时间隔
     */
    public static final int DELAY_MILLIS = 1000;
    /**
     * 倒计时最大值
     */
    public static final int MAX_COUNT = 10;
    /**
     * 倒计时最小值
     */
    public static final int MIN_COUNT = 0;
    private TextView countdownTimeText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

//        得到控件
//        把控件变成全局变量(comman + option + F)
        countdownTimeText = findViewById(R.id.countdownTimeTextView);

//        创建一个静态handler, 静态的不会内存泄漏
        CountdownTimeHandler handler = new CountdownTimeHandler(this);

    //    新建一个message
        Message message = Message.obtain();
        message.what = COUNTDOWN_TIME_CODE;

//        10 就是最大值
        message.arg1 = MAX_COUNT;

//        第一次发送消息
        handler.sendMessageDelayed(message, DELAY_MILLIS);
    }

    public static class CountdownTimeHandler extends Handler {
        final WeakReference<CountDown> mweakReference;

//        因为上面的使用, 这里面默认的参数需要被修改
//        修改成, 这个activity, 跟老师MainActivity不同!!!

        public CountdownTimeHandler(CountDown activity) {
//            弱引用
            this.mweakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

//        从静态的handler中拿出来activity
            CountDown activity = mweakReference.get();
//            获取TextView

            switch (msg.what) {

                case COUNTDOWN_TIME_CODE:
//                    现在的值是最大值(10)
                    int value = msg.arg1;
//                    变成9了
                    activity.countdownTimeText.setText(String.valueOf(value--));

//                    循环发消息控制
                    if (value >= MIN_COUNT) {
//                    重新创建一个Message(之前的已经被占用了)(从消息池当中拿取)
                        Message message = Message.obtain();
                        message.what = COUNTDOWN_TIME_CODE;
                        message.arg1 = value;
                        sendMessageDelayed(message, DELAY_MILLIS);
                    }
                    break;
            }
        }
    }
}
