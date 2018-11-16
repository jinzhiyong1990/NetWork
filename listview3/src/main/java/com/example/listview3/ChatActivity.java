package com.example.listview3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list_view_demo);


        ArrayList<ChatMessage> chatMessages = new ArrayList<>();
        chatMessages.add(new ChatMessage(1, 2, "刘晓明",
                "8:20", "你好吗", true));

        chatMessages.add(new ChatMessage(1, 2, "小军",
                "8:25", "还可", false));

        chatMessages.add(new ChatMessage(1, 2, "小军",
                "9:00", "你干甚", false));

        chatMessages.add(new ChatMessage(1, 2, "刘晓明",
                "9:20", "...", true));


        //设置适配器
        listView.setAdapter(new ChatMessageAdapter(this, chatMessages));


    }


    public class ChatMessage {
        private int mID;
        private int mFriendID;
        private String mName;
        private String mDate;
        private String mContent;
        private boolean mIsComeMessage;

        public ChatMessage(int mID, int mFriendID, String mName, String mDate, String mContent, boolean mIsComeMessage) {
            this.mID = mID;
            this.mFriendID = mFriendID;
            this.mName = mName;
            this.mDate = mDate;
            this.mContent = mContent;
            this.mIsComeMessage = mIsComeMessage;
        }


        public int getmID() {
            return mID;
        }

        public void setmID(int mID) {
            this.mID = mID;
        }

        public int getmFriendID() {
            return mFriendID;
        }

        public void setmFriendID(int mFriendID) {
            this.mFriendID = mFriendID;
        }

        public String getmName() {
            return mName;
        }

        public void setmName(String mName) {
            this.mName = mName;
        }

        public String getmDate() {
            return mDate;
        }

        public void setmDate(String mDate) {
            this.mDate = mDate;
        }

        public String getmContent() {
            return mContent;
        }

        public void setmContent(String mContent) {
            this.mContent = mContent;
        }

        public boolean ismIsComeMessage() {
            return mIsComeMessage;
        }

        public void setmIsComeMessage(boolean mIsComeMessage) {
            this.mIsComeMessage = mIsComeMessage;
        }
    }


    public static class ChatMessageAdapter extends BaseAdapter {

        private Context mContext;
        //外面的那个定义了, adapter里面需要接收
        ArrayList<ChatMessage> mchatMessages = new ArrayList<>();

        //定义消息的类型(自己发的还是朋友发的)
        interface IMessageViewType {
            int COM_MESSAGE = 0;
            int TO_MESSAGE = 1;
        }

        public ChatMessageAdapter(Context context, ArrayList<ChatMessage> mchatMessages) {
            this.mContext = context;
            this.mchatMessages = mchatMessages;
        }

        @Override
        public int getCount() {
            return mchatMessages.size();
        }

        @Override
        public Object getItem(int position) {
            return mchatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }






        //根据不同的类型来获取视图
        @Override
        public int getItemViewType(int position) {

            ChatMessage chatMessage = mchatMessages.get(position);

            return chatMessage.mIsComeMessage ?
                    IMessageViewType.COM_MESSAGE: IMessageViewType.TO_MESSAGE;
        }

        //类型的数量(这个例题里面是2种)
        @Override
        public int getViewTypeCount() {
            return 2;
        }






        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChatMessage chatMessage = mchatMessages.get(position);

            if (convertView == null) {
                //如果是进来的消息
                if (chatMessage.mIsComeMessage) {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.xxx, null);

                    //如果不是进来的消息, 而是发出的消息
                } else {
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.xxx2, null);

                }
                // TODO: 2018/11/6 写ViewHolder的内容

            }


            return null;
        }
    }
}
