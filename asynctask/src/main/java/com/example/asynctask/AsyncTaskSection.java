package com.example.asynctask;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 步骤
 * 1. 网络上请求数据: 申请网络权限 申请读写sd卡的权限
 * 2. 布局layout
 * 3. 下载数据之前: UI
 * 4. 下载数据当中: 数据
 * 5. 下载数据之后: UI
 */

public class AsyncTaskSection extends AppCompatActivity {
    public static final int INIT_PROGRESS = 0;
    public static final String APK_URL = "https://img2.mukewang.com/5adfee7f0001cbb906000338-240-135.jpg";
    ProgressBar progressBar;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        initView();
        //设置点击监听
        setListener();
        //初始化UI数据
        setData();

//        使用自己写的封装好的下载
        DownloadHelper.download("", "", new OnDownloadListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(int code, File file) {

            }

            @Override
            public void onFail(int code, File file, String message) {

            }

            @Override
            public void onProgress(int progress) {

            }
        });
    }

    /**
     * 初始化视图
     */
    private void initView() {
        progressBar = findViewById(R.id.progressBar);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textview);
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/11/4 做下载的事情
                DownloadAsyncTask downloadAsyncTask = new DownloadAsyncTask();
//                执行一个过程
                downloadAsyncTask.execute(APK_URL);
            }
        });
    }

    private void setData() {
        progressBar.setProgress(INIT_PROGRESS);
        button.setText(R.string.click_download);
        textView.setText(R.string.download_text);
    }

    /**
     * String: 入参
     * Integer: 进度
     * Boolean: 返回值
     */
    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        public static final String FILE_APK = "imooc2.apk";
        String mFilePath;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //可操作UI
            button.setText(R.string.downloading);
            textView.setText(R.string.downloading);
            progressBar.setProgress(INIT_PROGRESS);
        }

        /**
         * 另外一个县城
         *
         * @param strings 入参
         * @return
         */
        @Override
        protected Boolean doInBackground(String... strings) {

//            下载过程
            if (strings != null && strings.length > 0) {
                String apkUrl = strings[0];
                try {
                    //构造URL
                    URL url = new URL(apkUrl);
//                    构造链接, 并打开
                    URLConnection urlConnection = url.openConnection();
                    InputStream inputStream = urlConnection.getInputStream();
//获取了下载内容的总长度
                    int contentLength = urlConnection.getContentLength();

//                    下载地址准备
                    mFilePath = Environment.getExternalStorageDirectory() + File.separator + FILE_APK;

//                    对下载地址进行处理
                    File apkFile = new File(mFilePath);
                    if (apkFile.exists()) {
                        boolean result = apkFile.delete();
                        if (!result) {
                            return false;
                        }
                    }

//                    已经下载的大小
                    int downloadSize = 0;
//byte 数组
                    byte[] bytes = new byte[1024];
                    int length;

//                    输出管道
                    OutputStream outputStream = new FileOutputStream(mFilePath);

//                    不断的挖数据
                    while ((length = inputStream.read(bytes)) != -1){
//                        放到文件管道里
                        outputStream.write(bytes, 0, length);
//                        累加大小
                        downloadSize += length;
//                        更新进度条
                        publishProgress(downloadSize * 100/contentLength);
                    }

                    inputStream.close();
                    outputStream.close();


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }else {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
//            处理结果
            button.setText(aBoolean? getString(R.string.download_finish):getString(R.string.download_fail) );
            textView.setText(aBoolean?  getString(R.string.download_finish)+ mFilePath: getString(R.string.download_fail));

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            收到进度, 处理进度
            if (values !=null && values.length>0){
                progressBar.setProgress(values[0]);
            }

        }
    }

}
