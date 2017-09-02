package com.ids.teenage.socket.client;

import com.ids.teenage.socket.msg.MessageNet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Master.wang on 2017/4/6.
 */
public class ConnectionManager {
    private static Log logger = LogFactory.getLog(ConnectionManager.class);
    /**
     * 心跳周期（单位：毫秒）
     */
    private volatile static long activeCycle = 5000;

    /**
     * 存放产生的长连接
     */
    private static Set<Connection> pool = Collections.synchronizedSet(new HashSet<>());

    /**
     * 用于定时发送心跳包
     */
    private static ConnectActiveMonitor monitor = new ConnectActiveMonitor();

    static{
        monitor.start();
    }

    /**
     * 创建链接
     * @param host
     * @param port
     * @return
     */
    public static  Connection createConnection(String host,int port) {
        Connection conn=null;
        try {
            conn = new Connection(host, port);
            if(null !=conn) {
                pool.add(conn);
                logger.info(pool.size() + " 建立连接成功！链接名称：" + conn.toString());
            }
        }catch (IOException ex){
            logger.error("创建链接错误：" + ex);
            ex.getStackTrace();
        }

        return conn;
    }


    public static void removeConnection(Connection conn){
        pool.remove(conn);
    }

    /**
     * 关闭心跳线程
     * @param conn
     */
    public static void closeHeartBeatTask(Connection conn){
        if(monitor!=null){
            monitor.isCancle=true;
            if(conn.out!=null){
                conn.closeOutputStream(conn.out);
                conn.out=null;
            }
            monitor=null;
        }
    }

    /**
     * 心跳线程
     */
    static class ConnectActiveMonitor extends Thread{
        private volatile boolean running = true;
        private boolean isCancle=false;
        public void run() {
                while (running) {
                    long time = System.currentTimeMillis();
                    if(pool.size()>0) {
                        for (Connection con : pool) {
                            try {
                                if (con.getLastActTime() + activeCycle < time) {
                                    MessageNet.heartBeat(); //心跳包
                                }
                            } catch (IOException e) {
                                removeConnection(con);
                            }
                        }
                        yield();
                    }

                }

        }

    }

}
