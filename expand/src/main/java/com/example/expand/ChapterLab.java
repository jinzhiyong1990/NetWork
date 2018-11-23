package com.example.expand;

import java.util.ArrayList;
import java.util.List;

/**
 * 用来模拟数据的
 */

public class ChapterLab {


    public static List<Chapter> generateMockDate(){
        List<Chapter> date = new ArrayList<>();

        Chapter root1 = new Chapter(1, "Android");
        Chapter root2 = new Chapter(1, "IOS");
        Chapter root3 = new Chapter(1, "Unity 3D");
        Chapter root4 = new Chapter(1, "Cocos2d-x");


        root1.addChild(1, "Andorid Child 1");
        root1.addChild(2, "Andorid Child 2");
        root1.addChild(3, "Andorid Child 3");
        root1.addChild(4, "Andorid Child 4");
        root1.addChild(5, "Andorid Child 5");


        root2.addChild(1, "IOS Child 1");
        root2.addChild(2, "IOS Child 2");
        root2.addChild(3, "IOS Child 3");
        root2.addChild(4, "IOS Child 4");
        root2.addChild(5, "IOS Child 5");


        root3.addChild(1, "Unity 3D Child 1");
        root3.addChild(2, "Unity 3D Child 2");
        root3.addChild(3, "Unity 3D Child 3");
        root3.addChild(4, "Unity 3D Child 4");
        root3.addChild(5, "Unity 3D Child 5");


        root4.addChild(1, "Cocos2d-x Child 1");
        root4.addChild(2, "Cocos2d-x Child 2");
        root4.addChild(3, "Cocos2d-x Child 3");
        root4.addChild(4, "Cocos2d-x Child 4");
        root4.addChild(5, "Cocos2d-x Child 5");


        date.add(root1);
        date.add(root2);
        date.add(root3);
        date.add(root4);

        return date;

    }
}
