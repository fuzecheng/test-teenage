package com.ids.teenage.jmeter.tcp;

import com.ids.teenage.util.ByteUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.jmeter.protocol.tcp.sampler.AbstractTCPClient;
import org.apache.jmeter.protocol.tcp.sampler.ReadException;
import org.apache.jmeter.report.processor.SampleContext;
import org.apache.jmeter.threads.JMeterContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Master.wang on 2017/6/9.
 */
public class TeenageTCPClient extends AbstractTCPClient {
    private static final Log logger = LogFactory.getLog(TeenageTCPClient.class);
    private static long start;
    private volatile static long activeCycle = 5000;
    @Override
    public void setupTest() {


    }

    @Override
    public void teardownTest() {

    }

    @Override
    public void write(OutputStream outputStream, InputStream inputStream) throws IOException {


    }

    @Override
    public void write(OutputStream outputStream, String s) throws IOException {
        //logger.info("send msg : "+s);
        outputStream.write(ByteUtil.HexString2Bytes(s));
        outputStream.flush();
    }

    @Override
    public String read(InputStream inputStream) throws ReadException {
        //logger.info("resv msg!");
        ByteArrayOutputStream w=new ByteArrayOutputStream();

        try {
            byte[] buffer=new byte[4096];
            int x=0;
            if((x=inputStream.read(buffer))>-1){
                w.write(buffer,0,x);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ByteUtil.dumpBytesAsHEX(w.toByteArray()).replaceAll(" ", "").toString();

    }

    @Override
    public byte getEolByte() {
        return 0;
    }

    @Override
    public String getCharset() {
        return null;
    }

    @Override
    public void setEolByte(int i) {


    }

    public static void main(String[] args) {


        }
}
