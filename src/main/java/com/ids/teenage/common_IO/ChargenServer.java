package com.ids.teenage.common_IO;

import com.ids.teenage.util.ByteUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sam.fu on 2017/8/2.
 */
public class ChargenServer {

    public static final int DEFAULT_PORT = 56840 ;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int port ;
        port = DEFAULT_PORT ;


        String serversend="server发送了数据";

        byte[] rotation =new byte[95*2 ] ;
//        for(byte i = ' ' ; i < '~' ;i++)
//        {
//            rotation[i - ' '] = i ;
//            rotation[i + 95 - ' '] = i ;
//        }
        rotation=serversend.getBytes();
        System.out.println(ByteUtil.bytes2UTF8string(rotation));
        ServerSocketChannel serverChannel = null  ;
        Selector selector = null;


        /**
         * 先建立服务器端的通道
         *
         */

        try {
            serverChannel = ServerSocketChannel.open() ;
            ServerSocket ss = serverChannel.socket() ;
            InetSocketAddress address = new InetSocketAddress(port) ;
            ss.bind(address) ;
            serverChannel.configureBlocking(false) ;
            selector = Selector.open() ;
            serverChannel.register(selector, SelectionKey.OP_ACCEPT) ;



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        while(true)
        {

            try {
                selector.select() ;
            } catch (IOException e) {
                e.printStackTrace();
            }

            Set readyKeys = selector.selectedKeys() ;
            Iterator iter = readyKeys.iterator() ;
            while(iter.hasNext())
            {
                SelectionKey key = (SelectionKey) iter.next() ;
                iter.remove() ;

                if(key.isAcceptable())
                {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel() ;
                    try {
                        SocketChannel client = server.accept() ;
                        System.out.println("Accept connection from " + client) ;
                        client.configureBlocking(false) ;
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE) ;
                        ByteBuffer buffer = ByteBuffer.allocate(95) ;
                        buffer.put(rotation , 0 , rotation.length) ;
                        buffer.put((byte)'\r') ;
                        buffer.put((byte)'\n') ;


                        buffer.flip() ;
                        key2.attach(buffer) ;




                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }




                }

                else
                if(key.isWritable())
                {

                    /**
                     * 建立客户端通道
                     *
                     */
                    SocketChannel client = (SocketChannel)key.channel() ;
                    ByteBuffer buffer = (ByteBuffer) key.attachment() ;
                    if(!buffer.hasRemaining())
                    {
                        buffer.rewind() ;
                        int first = buffer.get() ;
                        buffer.rewind() ;
                        int position = first - ' ' + 1 ;
                        buffer.put(rotation , position , rotation.length) ;
                        buffer.put((byte) '\r') ;
                        buffer.put((byte) '\n');
                        buffer.flip() ;
                    }
                    try {
                        client.write(buffer) ;
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }








                key.cancel() ;
                try {
                    key.channel().close() ;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }


        }









    }

}