package com.ids.teenage.http.common;

import com.ids.teenage.util.HTTPCLIENT;
import com.ids.teenage.util.RandomUtil;
import com.ids.teenage.util.XXTEACommon;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Master.wang on 2017/5/24.
 */
public class Context {

    //请求固定字段
    public static final String APPKEY="a5ee3a0d0566a7e645c9";
    public static final String CHANNEL="TEST0000000";
    public static final String DEVICEID="deviceid-"+ RandomUtil.getTimeRand();
    public static final String VERSION="1.0.0";
    public static final String PLATID="1"; //1 ：android ,2:ios
    public static final String UIPAGE="10";
    public static final String LEVEL="18";
    public static final String UID="1761546698";
    /**
     * 封装请求固定值
     * @return
     */
    public static Map<String,String> parms_c (){
        Map<String,String > map=new HashMap<>();
        map.put("plat_id",String.valueOf(PLATID));
        map.put("deviceid",DEVICEID);
        map.put("channel",CHANNEL);
        map.put("appkey",APPKEY);
        map.put("version",VERSION);
        map.put("ui_page",UIPAGE);
        map.put("level",LEVEL);
        map.put("openId",UID);
        return map;

    }

    /**
     * 封装发送及返回数据
     * @param url
     * @param stringMap
     * @return
     * @throws Exception
     */
    public static String xxRsp(String url,Map<String,String> stringMap) throws Exception{
        Map<String ,String> headers=new HashMap<>();
        Map<String ,String> params=new HashMap<>();
        String data= XXTEACommon.xxteaContentEncrypt(HTTPCLIENT.parseParam(stringMap));
        params.put("data",data);
        String result=HTTPCLIENT.post(url,params,headers);

        return XXTEACommon.xxteaContentDecrypt(result);
    }

    public static void main(String [] args){
        Map<String,String> stringMap=parms_c();
        String s= HTTPCLIENT.parseParam(stringMap);
        System.out.println(s);
        stringMap.forEach((k, v) -> System.out.println(k+"="+v));

    }
}
