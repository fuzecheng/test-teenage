package com.ids.teenage.common_IO;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam.fu on 2017/8/17.
 */
public class HttClient {


    public static void main(String[] args) {
//        String url="http://192.168.117.79:8080/TestCenter/resultupdate.do";
//        List<NameValuePair> pairList = new ArrayList<NameValuePair>();
//        pairList.add(pair1);
//        pairList.add(pair2);
//        HttpEntity requestHttpEntity = new UrlEncodedFormEntity(
//                pairList, HTTP.UTF_8);
//        // URL使用基本URL即可，其中不需要加参数
//        HttpPost httpPost = new HttpPost(url);
//        // 将请求体内容加入请求中
//        httpPost.setEntity(requestHttpEntity);
//        // 需要客户端对象来发送请求
//        HttpClient httpClient = new DefaultHttpClient();
//        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,3000);
//        Log.i("autoTest","Begin send HTTP request");//
//        HttpResponse response = httpClient.execute(httpPost)
//    }
}
