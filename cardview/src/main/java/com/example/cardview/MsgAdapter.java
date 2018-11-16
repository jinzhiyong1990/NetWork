package com.example.cardview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MsgAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Msg> mDatas;


    public MsgAdapter(Context context, List<Msg> mDatas) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Msg getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder =null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_msg, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mIvImg = convertView.findViewById(R.id.id_iv_img);
            viewHolder.mTvTitle = convertView.findViewById(R.id.id_tv_title);
            viewHolder.mTvContent = convertView.findViewById(R.id.id_tv_content);
            convertView.setTag(viewHolder);
        } else {
            convertView.getTag();
        }

        Msg msg = mDatas.get(position);
        viewHolder.mIvImg.setImageResource(msg.getImgResId());
        viewHolder.mTvTitle.setText(msg.getTitle());
        viewHolder.mTvContent.setText(msg.getContent());

        return convertView;
    }


    public static class ViewHolder {
        ImageView mIvImg;
        TextView mTvTitle, mTvContent;
    }
}
