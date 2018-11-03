package com.example.handler;

import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Random;

public class DiglettActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int CODE = 123;
    private TextView mResultTextView;
    private Button mStartButton;
    private ImageView mDiglettImageView;

//    随机产出地鼠的坐标

    public int[][] mposition = new int[][]{
            {432,880}, {324, 900},
            {532,342}, {873, 745},
            {23,456}, {56, 247},

    };

//    所有的数量
    private int mTotoalCount;

//    成功的数量
    private int mSuccessCount;

    public static final int MAX_COUNT = 10;

    private DiglettHandler myHandler = new DiglettHandler(this);




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diglett);


        initView();

    }

    private void initView() {
        mResultTextView = findViewById(R.id.text_view);
        mDiglettImageView = findViewById(R.id.image_View);
        mStartButton = findViewById(R.id.start_button);

        mStartButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.start_button:
                start();

                break;
        }

    }

    private void start() {
//        发送消息 sendMessageDelayed
        next(0);

    }


//    让下一个地鼠出现
//    延迟的时间是一个随机的时间
    private void next(int delayTime){

        int position = new Random().nextInt(mposition.length);

        Message message = Message.obtain();

        message.what = CODE;


//        把position发过去, message就知道显示哪个了
        message.arg1 = position;

        myHandler.sendMessageDelayed(message, delayTime);

    }


//    自己做一个handler
    public static class DiglettHandler extends Handler{

    public static final int RANDOM_NUMBER = 500;
    public final WeakReference<DiglettActivity> mWeakReference;

    public DiglettHandler(DiglettActivity activity) {
        this.mWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        DiglettActivity activity = mWeakReference.get();

        switch (msg.what){


            case CODE:
                if (activity.mTotoalCount > MAX_COUNT){
                    activity.clean();
                    Toast.makeText(activity, "地鼠打完了", Toast.LENGTH_LONG).show();
                }


                int position = msg.arg1;
//                设置图片坐标
                activity.mDiglettImageView.setX(activity.mposition[position][0]);
                activity.mDiglettImageView.setY(activity.mposition[position][1]);
//                让图片显示出来(之前设置了不显示)
                activity.mDiglettImageView.setVisibility(View.VISIBLE);

//                创建随机时间
                int randomTime = new Random().nextInt(RANDOM_NUMBER) + RANDOM_NUMBER;

                activity.next(randomTime);


                break;
        }


    }
}

    private void clean() {


    }


}
