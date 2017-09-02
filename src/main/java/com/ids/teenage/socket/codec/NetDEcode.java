package com.ids.teenage.socket.codec;


import com.ids.teenage.socket.msg.DataPacket;
import com.ids.teenage.util.ByteUtil;

import java.io.IOException;

/**
 * Created by Master.wang on 2017/5/8.
 * 编解码
 */
public class NetDEcode {

    /**
     *  将数据编码 长度+内容 头部固定4个字节
      * @param dataPacket
     * @return
     * @throws java.io.IOException
     */
    public static byte [] encode  (DataPacket dataPacket) throws IOException{
        byte [] length= ByteUtil.int2bytes(dataPacket.getBodys().length + DataPacket.HEADER_LENGTH, 4);
        byte [] result=ByteUtil.pack(length,dataPacket.getBodys());
       // System.out.println("dataPacket.length: "+result.length );
       // System.out.println("package: "+ByteUtil.dumpBytesAsHEX(dataPacket.getBodys()));
       return result;

    }

    public static String getReqData(DataPacket dataPacket) throws Exception {
        String result = ByteUtil.dumpBytesAsHEX(encode(dataPacket));
        result = result.replace(" ", "");
        return result;
    }

    /**
     * 数据解码 长度+内容 头部固定4个字节
     * @param resp
     * @throws java.io.IOException
     */
    public static byte [] decode(byte [] resp) throws IOException {
        //1.首先要获取长度，整形4个字节，如果字节数不足4个字节返回null
        byte [] data=new byte[0];
        if(resp!=null) {
            if (resp.length < 4) {
                return null;

            }

            int len = ByteUtil.bytes2int(ByteUtil.reversEndian(ByteUtil.getbytes(resp,0,4)))-4;

            if (len > 0) {
                data=ByteUtil.getbytes(resp,0,len);
               // System.out.println("data==>> "+ByteUtil.dumpBytesAsHEX(data));
                System.arraycopy(resp, 4, data, 0, len);
               //System.out.println("res -> data: "+ByteUtil.dumpBytesAsHEX(data));
            }
            return data;
        }
        return data;
    }


}
