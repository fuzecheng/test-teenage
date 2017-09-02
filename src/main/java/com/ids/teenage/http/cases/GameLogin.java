package com.ids.teenage.http.cases;

import com.ids.teenage.http.common.Constants;
import com.ids.teenage.http.common.Context;
import com.ids.teenage.util.HTTPCLIENT;
import org.junit.Test;

import java.util.Map;

/**
 * Created by Master.wang on 2017/5/22.
 * 用户登录
 */
public class GameLogin  extends Context {
    private static Map<String,String> stringMap=parms_c();

    public static String gameLogin(String uid) throws Exception {
        String url = HTTPCLIENT.getUrl(Constants.LOAD_HOST_PHP,Constants.GAME_LOGIN);
        stringMap.put("userId", uid);
        return xxRsp(url,stringMap);
    }

    @Test
    public void testGameLogin(){
        try {
            System.out.println("登录："+gameLogin("1761546698"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
