package com.ids.teenage.socket.client;


import com.ids.teenage.socket.codec.NetDEcode;
import com.ids.teenage.socket.msg.DataPacket;
import com.ids.teenage.socket.msg.MessageNet;
import com.ids.teenage.util.ByteUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.net.SocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Master.wang on 2017/4/6.
 */
public class Connection {
    private static Log logger = LogFactory.getLog(Connection.class);
    public Socket socket;
    public OutputStream out;
    public InputStream in ;
    private long lastActTime = 0;
    private String host;
    private int port;
    private boolean isConnected=true;


    public boolean isConnected() {
        return null!=socket && isConnected;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }


    public Connection(String host, int port) throws IOException{
            socket = SocketFactory.getDefault().createSocket(host, port);
            socket.setSoTimeout(6000);
            socket.setKeepAlive(true);
            in = socket.getInputStream();
            out =socket.getOutputStream();
            logger.info("Connetcion :链接ip " + host + " ,端口：" + port);
    }


    public void startClient(String host,int port) throws IOException {
            socket = SocketFactory.getDefault().createSocket(host, port);
            socket.setSoTimeout(60000);
            socket.setKeepAlive(true);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            logger.info("Connetcion :链接ip " + host + " ,端口：" + port);
    }




    /**
     * 关闭socket
     */
    private void closeSocket() {
        if(isConnected) {
            try {
                socket.close();
                isConnected = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 关闭输入流
     *
     * @param is
     */
    public static void closeInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭输出流
     *
     * @param os
     */
    public static void closeOutputStream(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param packet
     * @throws IOException
     */
    public synchronized  void send0(DataPacket packet) throws  IOException {
        if(isConnected) {
            try {
                lastActTime = System.currentTimeMillis();
                byte [] result= NetDEcode.encode(packet);
                out.write(result);
                out.flush();
                logger.info("发送消息....."+ ByteUtil.dumpBytesAsHEX(packet.getBodys()));
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("发送消息出错......"+e.getMessage());
                close();
            }
        }
        if(!isConnected) {
            MessageNet.startClient();
           // System.out.println("关闭.....");
        }
    }


    /**
     * 读取消息
     * @return
     * @throws IOException
     */
    public  byte[] readStream () throws IOException{
        byte[] b=null;
        if (isConnected) {
            lastActTime = System.currentTimeMillis();
            int count = 0;
            while (count == 0) {
                count = in.available();
            }
            b = new byte[count];
            in.read(b);
            return b;
        }
       if(!isConnected){
            MessageNet.startClient();
       }
       return b;
    }



    public synchronized void close() throws IOException{
        lastActTime = System.currentTimeMillis();
        ConnectionManager.removeConnection(this);
        if(socket!=null)socket.close();
        if(in!=null)in.close();
        if(out!=null)out.close();
    }

    public synchronized long getLastActTime(){
        return lastActTime;
    }



}
