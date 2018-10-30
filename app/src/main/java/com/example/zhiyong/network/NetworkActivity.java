package com.example.zhiyong.network;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//请求的链接是:
//http://www.imooc.com/api/teacher?type=2&page=1
//需要在manifest里面声明一下, <uses-permission android:name="android.permission.INTERNET"/>

public class NetworkActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextView;
    private Button mButton, mParseDateButton;
    private String mResult;
    private static final String TAG = "NetwrokActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        findView();
        setListener();

    }

    private void findView() {

        mTextView = findViewById(R.id.textView);
        mButton = findViewById(R.id.huoqu);
        mParseDateButton = findViewById(R.id.jiexi);
    }

    private void setListener() {
        mButton.setOnClickListener(this);
        mParseDateButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.huoqu:
//                对于网络操作而言, 必须用另外一个线程才行, 不然会导致UI卡死
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        requestDateByGet();
                    }
                }).start();

                break;

            case R.id.jiexi:

                handleJonResult(mResult);


                break;
        }
    }

    private void handleJonResult(String mResult) {
//        由result直接转换成JSON Object

        try {
//            这个时候的JSON Object还是KEY-Value形式的
            JSONObject jsonObject = new JSONObject(mResult);

            //            读取的数据, 要赋值到LessonResult.java当中
            LessonResult lessonResult = new LessonResult();
            List<LessonResult.Lesson> lessonList = new ArrayList<>();


//            读取数据
            int status = jsonObject.getInt("status");
            JSONArray lessons = jsonObject.getJSONArray("data");
//            拿去data里面的数据
            if (lessons != null && lessons.length()>0){
                for (int index = 0; index < lessons.length(); index++) {
                    JSONObject lesson1 = (JSONObject) lessons.get(index);
                    int id = lesson1.getInt("id");
                    int learner = lesson1.getInt("learner");
                    String name = lesson1.getString("name");
                    String smallPic = lesson1.getString("picSmall");
                    String bigPic = lesson1.getString("picBig");
                    String description = lesson1.getString("description");

                    LessonResult.Lesson lessonItem = new LessonResult.Lesson();
                    lessonItem.setmID(id);
                    lessonItem.setmLearnerNumber(learner);
                    lessonItem.setmName(name);
                    lessonItem.setmSmallPictureURL(smallPic);
                    lessonItem.setmBigPictureURL(bigPic);
                    lessonItem.setmDescription(description);

                    lessonList.add(lessonItem);

                }
                lessonResult.setmLessons(lessonList);
            }
            mTextView.setText(lessonResult.toString());



        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    /**
     *
     * GET方法
     *
     *
     */
    private void requestDateByGet() {
        try {
            URL url = new URL("http://www.imooc.com/api/teacher?type=2&page=1");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //超时时间(毫秒)
            connection.setConnectTimeout(30 * 1000);
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
            if (responseCode == HttpURLConnection.HTTP_OK) {
//                        拿数据
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);

//                                更新主线程UI
//                                不然就不能在子线程改变UI

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(mResult);
                    }
                });
            } else {
                //TODO: error fail
                Log.e(TAG, "run: error code:" + responseCode
                        + ", message" + responseMessage);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     *
     * POST方法
     *
     *
     */

    private void requestDateByPost() {
        try {
//            ?type=2&page=1
            URL url = new URL("http://www.imooc.com/api/teacher");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(30 * 1000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Accept-Charset", "UTF-8");


//            POST新:

            connection.setDoInput(true);
            connection.setDoOutput(true);

//            因为没有缓存:
            connection.setUseCaches(false);


            connection.connect();

//            数据
            String date = "username=" + getEncodeValue("imooc") +
                    "&number=" + getEncodeValue("130088886666");


            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(date.getBytes());
            outputStream.flush();
            outputStream.close();



            int responseCode = connection.getResponseCode();
            String responseMessage = connection.getResponseMessage();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                mResult = streamToString(inputStream);


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText(mResult);
                    }
                });
            } else {
                //TODO: error fail
                Log.e(TAG, "run: error code:" + responseCode
                        + ", message" + responseMessage);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @NonNull
    private String getEncodeValue(String imooc) {
        String encode = null;
        try {
            encode = URLEncoder.encode(imooc, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encode;
    }


    /**
     *
     *
     * 将输入流转换成字符串
     *
     *
     */

    public String streamToString(InputStream is) {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }





//
////    将Unicode 转换为UTF-8
//
//    public static String decode(String unicodeStr){
//
//        if (unicodeStr == null){
//            return null;
//        }
//
//        StringBuilder retBuf = new StringBuilder();
//        int maxLoop = unicodeStr.length();
//        for (int i = 0; i < maxLoop; i++) {
//            if (unicodeStr.charAt(i) == '\\'){
//                if ((i<maxLoop-5)
//                        &&((unicodeStr.charAt(i+1) == 'u')||(unicodeStr.charAt(i+1)=='U')))
//                    try {
//                        retBuf.append((char) Integer.parseInt(unicodeStr.substring(i+2, i+3))
//                    } catch (NumberFormatException LocalNumberFormatException) {
//                        retBuf.append(unicodeStr.charAt(i));
//                    }
//            }
//        }
//    }


}
