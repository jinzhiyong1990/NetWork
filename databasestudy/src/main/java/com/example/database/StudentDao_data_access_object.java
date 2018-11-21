package com.example.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;

public class StudentDao_data_access_object {
    private SQLiteDatabase db;

    //获取SQliteDatabase
    public StudentDao_data_access_object(Context context) {

        //添加操作

        /**
         * 参数1: 环境
         * 参数2: 数据库名称
         * 情况1: 如果只有一个数据库名称, 那么这个数据库的位置会是在私有目录中
         * 情况2: 如果有路径, 那么数据库位置就在指定的路径下
         * 参数3: 游标, 通过什么样的游标操作数据, 可以写null, 会给默认选择
         * 参数4: 版本号
         */

        String path = Environment.getExternalStorageDirectory() + "/text5.db";
        SQLiteOpenHelper helper = new SQLiteOpenHelper(context, path, null, 2) {
            @Override
            public void onCreate(SQLiteDatabase db) {

            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            }
        };
        db = helper.getReadableDatabase();
    }

//增
    public void addStudent(Student student) {
        String sql = "insert into info_tb(name, age, gender) values(?,?,?)";
        db.execSQL(sql, new String[]{student.getName(), student.getAge()+"", student.getGender()});

    }

    //查
    public Cursor getStudent(String... strings) {
        //查询所有
        String sql = "select * from info_tb";

        //含条件查询(姓名/年龄/编号)(参数形式: 第一个参数指明条件, 第二个参数指明条件值)

        if (strings.length != 0) {
            sql += " where " + strings[0] + "='" + strings[1] + "'";
        }
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
//删
    public void deleteStudent(String... strings) {
        String sql = "delete from info_tb where " + strings[0] + "='" + strings[1] + "'";
        db.execSQL(sql);


    }
//改
    public void updateStudent(Student student) {
        String sql = "update info_tab set name=?,age=?,gender=? where _id=?";
        db.execSQL(sql, new Object[]{student.getName(), student.getAge(), student.getGender(), student.getId()});
    }
}
