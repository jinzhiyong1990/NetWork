package com.example.handler;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DiglettActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mResultTextView;
    private Button mStartButton;
    private ImageView mDiglettImageView;

//    随机产出地鼠的坐标

    public int[][] position = new int[][]{
            {432,880}, {324, 900},
            {532,342}, {873, 745},
            {23,456}, {56, 247},

    };

//    所有的数量
    private int mTotoalCount;

//    成功的数量
    private int mSuccessCount;

    public static final int MAX_COUNT = 10;




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

    }


//    自己做一个handler







}
