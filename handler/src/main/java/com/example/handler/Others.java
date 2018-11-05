//package com.example.handler;
//
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.URL;
//
//public class Others extends AppCompatActivity {
//
//    public static final int SUCCESS_MESSAGE = 111;
//    private Button button;
//    private ImageView imageView;
//    private Bitmap bitmap;
//
//    private Handler handler = new Handler()
//    {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            int number = msg.arg1;
//            if(number == SUCCESS_MESSAGE)
//            {
//                imageView.setImageBitmap(bitmap);
//            }
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_image);
//
//        initUI();
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        // TODO: 11/3/18 下载过程
//                        try {
//                            URL url = new URL("https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg");
//                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                            connection.setRequestMethod("GET");
//                            connection.setReadTimeout(5 * 1000);
//                            if(connection.getResponseCode() == 200)
//                            {
//                                InputStream inputStream = connection.getInputStream();
//                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                                byte[] buffer = new byte[1024];
//                                int len = 0;
//                                while ((len=inputStream.read(buffer)) != -1)
//                                {
//                                    stream.write(buffer, 0, len);
//                                }
//                                byte[] result = stream.toByteArray();
//                                bitmap = BitmapFactory.decodeByteArray(result, 0, result.length);
//                                Message message = new Message();
//                                message.arg1 = SUCCESS_MESSAGE;
//                                handler.sendMessage(message);
//                                stream.close();
//                                inputStream.close();
//                            }
//
//                        } catch (MalformedURLException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).start();
//                // TODO: 11/3/18 开始下载图片
//            }
//        });
//
//    }
//
//    private void initUI() {
//        button = findViewById(R.id.getimage);
//        imageView = findViewById(R.id.image_View);
//    }
//}
