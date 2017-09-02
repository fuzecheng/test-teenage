package com.ids.teenage.socket.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by sam.fu on 2017/7/3.
 */
public class Test {



    public static void main(String[] args) {
        String j="{'a':'a'}";
        JSONObject jsonObject= JSON.parseObject(j);
        jsonObject.toJSONString();
        String t="{'";
        if (j.contains(t)){
            System.out.println("Ok");
        }else {
            System.out.println("fuck");
        }


    }

}
