package com.example.listview3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RequestDataActivity extends AppCompatActivity {

    ImageView imageView;
    TextView textView;
    public static final String STATUS = "status";
    public static final String MSG = "msg";
    public static final String DATA = "data";
    public static final String NAME = "name";
    public static final String REQUEST_DATA_URL = "http://www.imooc.com/api/teacher?type=2&page=1";
    private ListView mListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);



        new RequestDataAsyncTask().execute();


    }

public class RequestDataAsyncTask extends AsyncTask<Void, Void, String>{



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Loading
    }

    @Override
    protected String doInBackground(Void... voids) {
        // 请求数据

        return request(REQUEST_DATA_URL);
    }

    private String request(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setConnectTimeout(30*1000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            int responseCode = httpURLConnection.getResponseCode();
            String responseMessage = httpURLConnection.getResponseMessage();

            if (responseCode == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();


                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                return stringBuilder.toString();
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // Loading 消失, 数据处理 刷新界面
        mListView = findViewById(R.id.main_list_view);

        LessonResult lessonResult = new LessonResult();

        try {
            JSONObject jsonObject = new JSONObject(result);

            int status = jsonObject.getInt(STATUS);
            lessonResult.setmStatus(status);

            String msg = jsonObject.getString(MSG);
            lessonResult.setmMessage(msg);


//            List里面的东西
            List<LessonInfo> lessonInfos = new ArrayList<>();
            JSONArray dataArray = jsonObject.getJSONArray(DATA);

            for (int index = 0; index < dataArray.length(); index++) {
                LessonInfo lessonInfo = new LessonInfo();

                JSONObject temJsonObject = (JSONObject) dataArray.get(index);

//                获取
                String string = temJsonObject.getString(NAME);
                lessonInfo.setmName(string);
//                遍历增加
                lessonInfos.add(lessonInfo);
            }

            lessonResult.setmLessonInfoList(lessonInfos);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //         里面有 List<data>  item.view
        mListView.setAdapter(new RequestDataAdapter(RequestDataActivity.this, lessonResult.getmLessonInfoList()));

    }
}

}
