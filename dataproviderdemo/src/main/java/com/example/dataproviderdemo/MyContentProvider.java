package com.example.dataproviderdemo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

public class MyContentProvider extends ContentProvider {
    public MyContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.


        int result = db.delete("info_tb", selection, selectionArgs);
        return result;


    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        Log.e("TEST", "调用了DataProviderDemo中的insert方法");

        //参数1: 表名
        //参数2: 非空列
        //参数3: contentvalue对象


        //提示东西用到的:
        //返回值是一个长整形
        long id =  db.insert("info_tb", null, values);
        //用于将id追加到uri后面.
        Uri uri1 = ContentUris.withAppendedId(uri, id);
        return uri1;
    }

    /**
     * 在ContentProvider创建的时候调用
     * @return
     */
    SQLiteDatabase db;
    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
         //创建数据库
        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(getContext(), "stu.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                String sql = "CREATE TABLE info_tb (_id integer primary key autoincrement," +
                        "name varchar(20)," +
                        "age integer," +
                        "gender varchar(2))";
                db.execSQL(sql);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        db = sqLiteOpenHelper.getReadableDatabase();

        //返回true, 不然不好使了
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.

        /**
         * 参数1: 表名
         * 参数2: 所要查询的列
         * 参数3: 查询条件
         * 参数4: 查询条件值
         * 参数5: 分组(数据库)
         * 参数6: 分组条件(数据库)
         * 参数7: 排序方式
         */
        Cursor c = db.query("info_tb", projection, selection, selectionArgs, null, null, sortOrder);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
