package com.example.asynctask;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 1. download方法, url, localPath listener
 * 2. listener: start, success fail progress
 * 3. 用asynctask封装的
 */

public class DownloadHelper {

    public static void download(String url, String localPath, OnDownloadListener listener) {
        DownloadAsyncTask task = new DownloadAsyncTask(url, localPath, listener);
        task.execute();
    }

    public static class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {
        public static final String FILE_APK = "imooc2.apk";
        String mUrl;
        String mFilePath;
        OnDownloadListener mListener;

        public DownloadAsyncTask(String mUrl, String mFilePath, OnDownloadListener mListener) {
            this.mUrl = mUrl;
            this.mFilePath = mFilePath;
            this.mListener = mListener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mListener != null) {
                mListener.onStart();
            }
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
            String apkUrl = mUrl;
            try {
                //构造URL
                URL url = new URL(apkUrl);
//                    构造链接, 并打开
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
//                    获取了下载内容的总长度
                int contentLength = urlConnection.getContentLength();

//                    对下载地址进行处理
                File apkFile = new File(mFilePath);
                if (apkFile.exists()) {
                    boolean result = apkFile.delete();
                    if (!result) {
                        if (mListener != null) {
                            mListener.onFail(-1, apkFile, "文件删除失败");
                        }
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
                while ((length = inputStream.read(bytes)) != -1) {
//                        放到文件管道里
                    outputStream.write(bytes, 0, length);
//                        累加大小
                    downloadSize += length;
//                        更新进度条
                    publishProgress(downloadSize * 100 / contentLength);
                }

                inputStream.close();
                outputStream.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                if (mListener != null) {
                    mListener.onFail(-1, new File(mFilePath), e.getMessage());
                }
                return false;
            }
            if (mListener != null) {
                mListener.onSuccess(0, new File(mFilePath));
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
//            收到进度, 处理进度
            if (values != null && values.length > 0) {
                if (mListener != null) {
                    mListener.onProgress(values[0]);
                }
            }
        }
    }

}

interface OnDownloadListener {
    void onStart();

    void onSuccess(int code, File file);

    void onFail(int code, File file, String message);

    void onProgress(int progress);


    //        让接口更加友好, 底下的两种方法, onStart, 还有onProgress这个, 不一定必须实现, 可以选择的实现
    abstract class SimpleDownloadListener implements OnDownloadListener {
        @Override
        public void onStart() {
        }

        @Override
        public void onProgress(int progress) {
        }
    }
}

