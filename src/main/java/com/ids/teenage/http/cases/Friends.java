package com.ids.teenage.http.cases;

import com.ids.teenage.http.common.Constants;
import com.ids.teenage.http.common.Context;
import com.ids.teenage.util.HTTPCLIENT;
import org.junit.Test;

import java.util.Map;

/**
 * 好友
 * Created by Master.wang on 2017/6/16.
 */
public class Friends extends Context {
    private static Map<String,String> stringMap=parms_c();

    /**
     *
     * @param uid 用户id
     * @param friendId 好友id
     * @param type 类型 添加删除 接受邀请 删除邀请 搜索好友（目前支持userId）
     * @return
     * @throws Exception
     */
    public static String makeFriends(String uid,String friendId,int type) throws Exception {
        String url = HTTPCLIENT.getUrl(Constants.TEST_HOST,Constants.FRIEND_MAKEFRIENDS);
        stringMap.put("userId", uid);
        stringMap.put("friendId",friendId);
        stringMap.put("type",String.valueOf(type));
        return xxRsp(url,stringMap);
    }

    @Test
    public void testMarkFriends(){
        //for(int i=1100000;i<1100050;i++){ //创建50个帐号登录
            try {
               /* String login=GameLogin.gameLogin(String.valueOf(i));
                System.out.println("登录Resp: "+login);
                if(login!=null){

                }*/
                String friends=  makeFriends(String.valueOf(1100017),String.valueOf(7640481),1);
                System.out.println("好友Resp: "+friends);
            } catch (Exception e) {
                e.printStackTrace();
            }
        //}

    }
}
