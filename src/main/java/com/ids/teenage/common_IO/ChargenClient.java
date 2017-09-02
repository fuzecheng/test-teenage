package com.ids.teenage.common_IO;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Created by sam.fu on 2017/8/2.
 */
public class ChargenClient {

    public static int DEFAULT_PORT = 56840 ;
    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
//        if(args.length == 0)
//        {
//            System.out.println("please input the port");
//            return ;
//        }

        int port ;


        port = DEFAULT_PORT ;

        SocketAddress address = new InetSocketAddress(port) ;
        try {
            SocketChannel client = SocketChannel.open(address) ;
            ByteBuffer buffer = ByteBuffer.allocate(1024) ;
            WritableByteChannel out = Channels.newChannel(System.out) ;

            while(client.read(buffer) != -1)
            {
                buffer.flip() ;
                out.write(buffer) ;
                buffer.clear() ;

            }






        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



    }

}