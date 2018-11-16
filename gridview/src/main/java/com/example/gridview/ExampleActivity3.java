package com.example.gridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ExampleActivity3 extends AppCompatActivity {
    GridView gridView;
    private List<String> imageList;
    private List<ImageInfo> imageInfoList;
    //Adapter的
    private GridAdapter3 gridAdapter;
    //AsyncTask的
    private ImageLoadTask imageLoadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        
        initUI();
        initData();
    }

    private void initData() {
        imageList = new ArrayList<String>();
        imageList.add("http://img5.duitang.com/uploads/item/201406/26/20140626164837_dzKds.jepg");
        imageList.add("http://img2.imgtn.bdimg.com/it/u=3980629563,3881837630&fm=21&gp=0.jpg");
        imageList.add("http://img5q.duitang.com/uploads/item/201505/08/20150508155052_XJaNW.jepg");
        imageList.add("http://img4.duitang.com/uploads/item/201407/d02/20140702105736_FdN5P.jepg");
//        imageList.add("http://img5.duitang.com/uploads/item/201406/26/20140626164837_dzKds.jepg");
//        imageList.add("http://img5.duitang.com/uploads/item/201406/26/20140626164837_dzKds.jepg");

        imageInfoList = new ArrayList<ImageInfo>();
        //i的值根据上面的链接数量决定的
        for (int i = 0; i < 4; i++) {
            ImageInfo imageInfo = new ImageInfo();
            imageInfo.setImagePath(imageList.get(i));
            imageInfo.setText("图片" + (i+1));
            imageInfoList.add(imageInfo);
        }
        gridAdapter = new GridAdapter3(this, imageInfoList);
        gridView.setAdapter(gridAdapter);
//        imageLoadTask = new ImageLoadTask(this, gridAdapter);
//        imageLoadTask.execute("");
    }

    private void initUI() {
        gridView = findViewById(R.id.gridview);

    }

    public Bitmap getImageFromNetwork(String path) {
        try {
            URL url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(10 * 1000);
            httpURLConnection.connect();

//            获取返回的输入流
            InputStream inputStream = httpURLConnection.getInputStream();

            //把输入流转换成bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap;



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //else的情况下就返回null了
        return null;
    }


    //AsyncTask:
    public class ImageLoadTask extends AsyncTask<String, Void, Void> {

        private Context context;
        private GridAdapter3 gridAdapter3;
        //构造方法:
        public ImageLoadTask(Context context, GridAdapter3 gridAdapter3) {
            this.context = context;
            this.gridAdapter3 = gridAdapter3;
        }
        @Override
        protected Void doInBackground(String... strings) {

//            把所有图片从网上下载下来
            for (int i = 0; i < gridAdapter3.getCount(); i++) {
                ImageInfo imageInfo = (ImageInfo) gridAdapter3.getItem(i);
                String imagePath = imageInfo.getImagePath();
                Bitmap bitmap = getImageFromNetwork(imagePath);
                imageInfo.setBitmap(bitmap);
                //刷新UI
                publishProgress();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            //通知刷新UI
            gridAdapter3.notifyDataSetChanged();
        }
    }

}
