package com.ids.teenage.jmeter.tcp;

import com.ids.teenage.socket.msg.MessageNet;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * Created by Master.wang on 2017/6/9.
 */
public class PingRequest extends AbstractJavaSamplerClient {

    private static long start = 0;
    private static long end = 0;
    @Override
    public void setupTest(JavaSamplerContext context) {
        start = System.currentTimeMillis();
    }


    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        SampleResult sr = new SampleResult();
        sr.setSamplerData("ping!");
        try {
            sr.sampleStart();// jmeter 开始统计响应时间标记
            //心跳
            //MessageNet.ConnectActiveMonitor  messageNet=new MessageNet.ConnectActiveMonitor();
            //messageNet.start();
            MessageNet.heartBeat();//心跳包
            sr.setSuccessful(true);
        }catch(Exception ex){
            sr.setSuccessful(false);
            ex.getMessage();
        }finally {
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
        //params.addArgument("uid","199798108");
        JavaSamplerContext arg0 = new JavaSamplerContext(params);
        PingRequest ping=new PingRequest();
        ping.setupTest(arg0);
        ping.runTest(arg0);
        ping.teardownTest(arg0);
    }

}
