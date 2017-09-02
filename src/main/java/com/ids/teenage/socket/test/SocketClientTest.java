package com.ids.teenage.socket.test;

import com.alibaba.fastjson.JSONObject;
import com.ids.teenage.http.cases.GameLogin;
import com.ids.teenage.socket.msg.MessageNet;

import java.io.IOException;
import java.nio.ByteOrder;

/**
 * Created by Master.wang on 2017/5/9.
 */
public class SocketClientTest {

    public static void main(String[] args) throws IOException {
        System.out.println(ByteOrder.nativeOrder());
        long uid=199798108;
        long targetUid=9018108;
        try {

            String result=  GameLogin.gameLogin(String.valueOf(uid));
            JSONObject jsonObject=JSONObject.parseObject(result);

            if(jsonObject.getInteger("error_code")!=0){
                System.out.println("登录出错了!"+jsonObject.getString("msg"));
            }

            MessageNet.doLogin(uid, "");//socket登录，发起登录请求
            MessageNet.resLogin(); //


            MessageNet.reqMapInterRequest(1,targetUid,0); //进入公寓
            MessageNet.resMapInterResponse();

        } catch (Exception e) {
            e.printStackTrace();
        }



      /*  MessageNet.reqMapInterRequest(1,100001,0); //1761546698
        MessageNet.resMapInterResponse();*/
    }



}
