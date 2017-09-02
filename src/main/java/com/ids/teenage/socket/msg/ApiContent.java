package com.ids.teenage.socket.msg;

import com.ids.teenage.proto.TFBsGo;
import com.ids.teenage.util.ByteUtil;

/**
 * Created by sam.fu on 2017/7/5.
 */
public class ApiContent {


    public static String getLoginData(String uid){
        TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_LOGIN_REQ);
        TFBsGo.LoginRequest.Builder loginRequest = TFBsGo.LoginRequest.newBuilder();
        loginRequest.setUserId(Long.valueOf(uid));
        loginRequest.setPassward("");
        msg.setData(loginRequest.build().toByteString());
        byte[] body = msg.build().toByteArray();
        byte[] head = ByteUtil.int2bytes(body.length+4, 4);
        byte[] result= ByteUtil.pack(head,body);
        return ByteUtil.dumpBytesAsHEX(result).replaceAll(" ", "").toString();
    }
}
