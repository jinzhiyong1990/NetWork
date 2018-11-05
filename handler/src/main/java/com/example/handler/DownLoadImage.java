package com.example.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownLoadImage extends AppCompatActivity {
    public static final int SUCCESS_CODE = 10001;
    private Button button;
    private ImageView imageView;
    Bitmap bitmap;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == SUCCESS_CODE) {
                imageView.setImageBitmap(bitmap);
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("GET");
                            httpURLConnection.setConnectTimeout(5 * 1000);
                            if (httpURLConnection.getResponseCode() == 200) {
                                InputStream inputStream = httpURLConnection.getInputStream();
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                byte[] bytes = new byte[1024];
                                int len = 0;
                                while ((len = inputStream.read(bytes)) != -1) {
                                    byteArrayOutputStream.write(bytes, 0, len);
                                }
                                byte[] result = byteArrayOutputStream.toByteArray();
                                bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
                                Message message = Message.obtain();
                                message.arg1 = SUCCESS_CODE;
                                handler.sendMessage(message);
                                inputStream.close();
                                byteArrayOutputStream.close();
                            }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    private void initView() {
        button = findViewById(R.id.getimage);
        imageView = findViewById(R.id.image_View);
    }
}
