package com.ids.teenage.common_IO;



import com.ids.teenage.util.ByteUtil;
import org.apache.commons.io.IOUtils;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by sam.fu on 2017/8/1.
 */
public class ClientIOUtil {

    public static void main(String[] args) throws IOException {
        long a=System.currentTimeMillis();
        InputStream inputStream=new URL("http://www.apache.org").openStream();
       // System.out.println(ClientIOUtil.read(inputStream));

      // System.out.println(ClientIOUtil.reads(inputStream));
      //  System.out.println(ClientIOUtil.readavail(inputStream));
        System.out.println(ClientIOUtil.readCopyLarge(inputStream));
       System.out.println(System.currentTimeMillis()-a);
        inputStream.close();
    }

    public static String read(InputStream inputStream) throws IOException {
      //  ByteArrayOutputStream outputStream =new ByteArrayOutputStream();
        byte[] b=new byte[4096];
        IOUtils.read(inputStream,b);
        return ByteUtil.dumpBytesAsHEX(b).replaceAll(" ", "").toString();
    }
    public static String reads(InputStream inputStream) {
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
    public static String readavail(InputStream inputStream) throws IOException {
        int count = 0;
        while (count == 0) {
            count = inputStream.available();
        }
        byte[] b = new byte[count];
        inputStream.read(b);

        return ByteUtil.dumpBytesAsHEX(b).replaceAll(" ", "").toString();
    }
    public static String readCopyLarge(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        IOUtils.copyLarge(inputStream,outputStream);

        return ByteUtil.dumpBytesAsHEX(outputStream.toByteArray()).replaceAll(" ", "").toString();
    }


}
