package com.example.zhiyong.network;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//请求的链接是:
//www.imooc.com/api/teacher?type=2&page=1

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mButton, mParseDateButton;
    private String mResult;
    private static final String TAG = "NetwrokActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

    }

    private void findView(){

        mTextView = findViewById(R.id.textView);
        mButton = findViewById(R.id.huoqu);
        mParseDateButton = findViewById(R.id.jiexi);
    }

    private void setListener(){
        mButton.setOnClickListener(this);
        mParseDateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v ){
        switch (v.getId()){
            case R.id.huoqu:
                try {
                   URL url = new URL("www.imooc.com/api/teacher?type=2&page=1");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //超时时间(毫秒)
                    connection.setConnectTimeout(30*1000);
//                    设置请求类型
                    connection.setRequestMethod("GET");
//                    请求属性
//                    拿到的数据类型是: Json
                    connection.setRequestProperty("Content-Type", "application/json");
//                    希望拿到的数据集: UTF-8
                    connection.setRequestProperty("Charset", "UTF-8");
//                    希望接收到的字符集: UTF-8
                    connection.setRequestProperty("Accept-Charset", "UTF-8");
                    //发起连接!!!!!!
                    connection.connect();

//                    获取请求码(响应码)
                    int responseCode = connection.getResponseCode();
//                    返回请求的消息
                    String responseMessage = connection.getResponseMessage();

//                    如果返回码: 代表着成功
                    if (responseCode == HttpURLConnection.HTTP_OK){
//                        拿数据
                        InputStream inputStream = connection.getInputStream();
                        String result = streamToString(inputStream);

                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.jiexi:

                break;
        }
    }

    /**
     * 将输入流转换成字符串
     *
     *
     */

    public String streamToString(InputStream is)  {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len=is.read(buffer)) != -1){
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }
}
