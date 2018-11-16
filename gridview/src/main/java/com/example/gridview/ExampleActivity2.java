package com.example.gridview;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class ExampleActivity2 extends AppCompatActivity {
    private GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        gridView = findViewById(R.id.gridview);
        GridAdapter2 gridAdapter2 = new GridAdapter2(this, getAppList());
        gridView.setAdapter(gridAdapter2);
    }

    public List<AppInfo> getAppList(){
        List<AppInfo> appInfoList = new ArrayList<AppInfo>();
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);

        for (int i = 0; i < installedPackages.size(); i++) {
            PackageInfo packageInfo = installedPackages.get(i);
            AppInfo appInfo = new AppInfo();

            //设置app的信息, 名称, 图标 etc.
            appInfo.setAppName(packageInfo.applicationInfo.loadLabel(packageManager).toString());
            appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(packageManager));
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setVersionCode(packageInfo.versionCode);
            appInfo.setVersionName(packageInfo.versionName);

            //会把系统应用过滤掉, 只有用户自己的app
            if ((packageInfo.applicationInfo.flags& ApplicationInfo.FLAG_SYSTEM) ==0){
                appInfoList.add(appInfo);

            }
        }
        return appInfoList;
    }
}
