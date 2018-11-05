package com.example.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk

public class DownLoadActivity extends Activity {

    public static final int DOWNLOAD_MESSAGE_CODE = 100001;
    private static final int DOWNLOAD_MESSAGE_FAILE_CODE = 100002;
    public static final String APP_URL = "http://download.sj.qq.com/upload/connAssitantDownload/upload/MobileAssistant_1.apk";
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final ProgressBar progressBar = findViewById(R.id.progress_bar);

        /**
         * 下载的步骤:
         *
         * 主线程 -> 点击按键 -> 发起下载 -> 开启子线程做下载 -> 下载过程中通知主线程 -> 主线程更新进度条
         *
         */

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        download(APP_URL);

                    }
                }).start();
            }
        });


        /**
         * 创建hanlder
         */
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                switch (msg.what) {

                    case DOWNLOAD_MESSAGE_CODE:
                        progressBar.setProgress((Integer) msg.obj);

                        break;
                }
            }
        };


    }

    private void download(String appurl) {

        try {
            URL url = new URL(appurl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();

            //获取文件的总长度
            int contentLength = urlConnection.getContentLength();

//            获取存储设备的地址
            String downloadFolderName = Environment.getExternalStorageDirectory() + File.separator
                    + "imooc" + File.separator;

//            创建目录
            File file = new File(downloadFolderName);
            if (!file.exists()) {
                file.mkdir();
            }
            String fileName = downloadFolderName + "_imooc.apk";
            File apkFile = new File(fileName);
            if (apkFile.exists()){
                apkFile.delete();
            }

//            开始下载了!
            int downloadSize = 0;
            byte[] bytes = new byte[1024];
            int length;

//            把outputstream下载到fileName里面
            OutputStream outputStream = new FileOutputStream(fileName);
            while ((length = inputStream.read(bytes))!=-1) {
                outputStream.write(bytes, 0, length);
//            这里头的downloadSize相当于进度
                downloadSize += length;
                /**
                 *
                 * 更新UI
                 *
                 */
                Message message = Message.obtain();
                message.obj = downloadSize * 100 / contentLength;
                message.what = DOWNLOAD_MESSAGE_CODE;
                handler.sendMessage(message);
            }
            inputStream.close();
            outputStream.close();
        } catch (MalformedURLException e) {
//            发送下载失败的消息
            notifyDownloadFaild();
            e.printStackTrace();
        } catch (IOException e) {
            notifyDownloadFaild();
            e.printStackTrace();
        }
    }


    private void notifyDownloadFaild() {
        Message message = Message.obtain();
        message.what = DOWNLOAD_MESSAGE_FAILE_CODE;
        handler.sendMessage(message);
    }
}
