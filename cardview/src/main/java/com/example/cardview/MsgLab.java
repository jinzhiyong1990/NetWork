package com.example.cardview;

import java.util.ArrayList;
import java.util.List;

public class MsgLab {

    public static List<Msg> generateMockList() {
        List<Msg> msgList = new ArrayList<>();

        Msg msg = new Msg(1, R.mipmap.img01,
                "如何才能不错过人工智能时代?",
                "下一个时代就是机器学习时代, 哈哈哈哈");
        msgList.add(msg);

        msg = new Msg(2, R.mipmap.img02,
                "如何才能不错过人工智能时代?",
                "下一个时代就是机器学习时代, 哈哈哈哈");
        msgList.add(msg);

        msg = new Msg(3, R.mipmap.img03,
                "如何才能不错过人工智能时代?",
                "下一个时代就是机器学习时代, 哈哈哈哈");
        msgList.add(msg);

        msg = new Msg(4, R.mipmap.img04,
                "如何才能不错过人工智能时代?",
                "下一个时代就是机器学习时代, 哈哈哈哈");
        msgList.add(msg);


        return msgList;
    }

}
