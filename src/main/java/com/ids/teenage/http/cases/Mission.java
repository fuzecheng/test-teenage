package com.ids.teenage.http.cases;


import com.ids.teenage.http.common.Constants;
import com.ids.teenage.http.common.Context;
import com.ids.teenage.util.HTTPCLIENT;
import org.junit.Test;

import java.util.Map;

/**
 * 任务
 * Created by Master.wang on 2017/5/23.
 */
public class Mission extends Context {
     private static Map<String,String> stringMap=parms_c();
     String uid="1997968108";

    /**
     *
     * 操作类型 0：任务列表（默认），1：任务历史，2：成就
     * @param uid
     * @param type
     * @return
     * @throws Exception
     */
    public static String getMissionList(String uid,int type) throws Exception {
        String url = HTTPCLIENT.getUrl(Constants.TEST_HOST, Constants.MISSION_GETLIST);
        stringMap.put("userId", uid);
        stringMap.put("type",String.valueOf(type));
      return xxRsp(url,stringMap);
    }

    @Test
    public void testGetMissionList() throws Exception {
        System.out.println("默认列表："+getMissionList(uid, 0));
        System.out.println("任务历史："+getMissionList(uid, 1));
        System.out.println("成就："+getMissionList(uid, 2));

    }



}
