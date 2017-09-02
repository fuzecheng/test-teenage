package com.ids.teenage.jmeter.tcp;

import com.alibaba.fastjson.JSONObject;
import com.ids.teenage.http.cases.GameLogin;
import com.ids.teenage.socket.msg.MessageNet;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;


/**
 * Created by Master.wang on 2017/5/24.
 */
public class LoginGame extends AbstractJavaSamplerClient {
    private static long start = 0;
    private static long end = 0;
    private long uid=0;
    private String resultData;
    private String result= null;

    @Override
    public void setupTest(JavaSamplerContext context) {
        uid=context.getLongParameter("uid");
        try {
            result = GameLogin.gameLogin(String.valueOf(uid));
        } catch (Exception e) {
            e.printStackTrace();
        }
        start = System.currentTimeMillis();
    }



    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("uid",String.valueOf(uid));
        return params;
    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
        SampleResult sr = new SampleResult();
        sr.setSamplerData("登录测试!");
        try {
            sr.sampleStart();// jmeter 开始统计响应时间标记
            // 通过下面的操作就可以将被测方法的响应输出到Jmeter的察看结果树中的响应数据里面了。
            JSONObject jsonObject=JSONObject.parseObject(result);
            if(jsonObject.getInteger("error_code")!=0){
                System.out.println("http登录出错了!" + jsonObject.getString("msg"));
                sr.setSuccessful(false);
            }
            else {
                MessageNet.doLogin(uid, "");//socket登录，发起登录请求
                resultData= MessageNet.resLogin(); //登录响应
                if (resultData != null && resultData.length() > 0) {
                    sr.setResponseData("结果是："+resultData, null);

                    sr.setDataType(SampleResult.TEXT);
                }
                sr.setSuccessful(true);
            }

        } catch (Throwable e) {
            sr.setSuccessful(false);
            e.printStackTrace();
        } finally {
            sr.sampleEnd();// jmeter 结束统计响应时间标记
        }
        return sr;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {
        end=System.currentTimeMillis();
        // 总体耗时
        System.err.println("cost time:" + (end - start) + "毫秒");
    }


    public static void main(String  [] args){
        Arguments params = new Arguments();
        params.addArgument("uid","10000000"); //199798108
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        LoginGame gameLogin=new LoginGame();
        gameLogin.setupTest(arg0);
        gameLogin.runTest(arg0);
        gameLogin.teardownTest(arg0);

    }
}
