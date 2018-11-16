package com.example.gridview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GridAdapter3 extends BaseAdapter {

    private Context context;
    private List<ImageInfo> imageInfoList;

    public GridAdapter3(Context context, List<ImageInfo> imageInfoList) {
        this.context = context;
        this.imageInfoList = imageInfoList;
    }

    @Override
    public int getCount() {
        return imageInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_gridview2, null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = convertView.findViewById(R.id.img_appIcon);
            viewHolder.textView = convertView.findViewById(R.id.tv_appName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ImageInfo imageInfo = imageInfoList.get(position);
        viewHolder.textView.setText(imageInfo.getText());

        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                ;

        RequestBuilder<Drawable> load = Glide.with(context).load(imageInfo.getImagePath());
        load.into(viewHolder.imageView);
        //网络加载的时候, 可能image没有加载出来的时候
//        if (imageInfo.getBitmap() == null) {
//
//            //设置一个临时的图片
//            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher);
//        } else {
//            //如果加载完成了, 那么就显示网络上的图片
//            viewHolder.imageView.setImageBitmap(imageInfo.getBitmap());
//        }




        return convertView;
    }

    public class ViewHolder {
        ImageView imageView;
        TextView textView;
    }
}
