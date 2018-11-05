package com.example.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * 步骤
 * 1. 网络上请求数据: 申请网络权限 申请读写sd卡的权限
 * 2. 布局layout
 * 3. 下载数据之前: UI
 * 4. 下载数据当中: 数据
 * 5. 下载数据之后: UI
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new 一个异步任务
        new DownloadAsyncTask().execute("imooc", "good", "Android ");


    }

    //    AsyncTask
    public class DownloadAsyncTask extends AsyncTask<String, Integer, Boolean> {

        /**
         * 在异步任务之前, 在主线程中
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //可操作UI
        }

        /**
         * 在另外一个线程中处理事件
         * @param strings 入参
         * @return 结果
         */
        @Override
//    String... strings: 表示可变参数
        protected Boolean doInBackground(String... strings) {
            for (int i = 0; i < 10000; i++) {
                Log.i(TAG, "doinBackground" + strings[0]);
//                抛出进度
                publishProgress(i);
            }
            return true;
        }

        /**
         * 执行完了之后, 会把从doInBackground中的结果作为参数
         * @param aBoolean
         */
        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            // 也是在主线程中, 处理那个结果
        }

        /**
         * 当进度在变化的时候, 就能体现出来
         * 是DownloadAsyncTask 中的那个 Integer
         * @param values
         */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //主线程中 收到进度, 之后处理
        }

        /**
         * 取消, 不会执行 onPostExecute了, 不是很好, 应该使用不带参数的onCancelled
         * @param aBoolean
         */
        @Override
        protected void onCancelled(Boolean aBoolean) {
            super.onCancelled(aBoolean);
        }


    }
}
