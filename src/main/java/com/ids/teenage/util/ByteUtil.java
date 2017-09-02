package com.ids.teenage.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ByteUtil {
	
	private static ByteBuffer buffer = ByteBuffer.allocate(8);

    /**
     * 把整型转换成指定长度的数组字节流
     * @param integer
     * @param len
     * @return
     */
	public static byte[] int2bytes(int integer, int len) {
		if (integer < 0) {
			throw new IllegalArgumentException("Can not cast negative to bytes : " + integer);
		}
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		for (int i = 0; i < len; i++) {
			bo.write(integer);
			integer = integer >> 8;
		}
//		byte[] res = reversEndian(bo.toByteArray(), len);
		return bo.toByteArray();
	}

    //按小端模式写入int
    private byte[]  writeInt( int v, int pos) {
        byte[] writeBuffer =new byte[pos];
        writeBuffer[pos] = (byte) (v >>> 24 & 0xFF);
        writeBuffer[pos + 1] = (byte) (v >>> 16 & 0xFF);
        writeBuffer[pos + 2] = (byte) (v >>> 8 & 0xFF);
        writeBuffer[pos + 3] = (byte) (v >>> 0 & 0xFF);
        return writeBuffer;
    }


    public static int byteArrayToInt(byte[] bytes) {
        int value= 0;
        //由高位到低位
        for (int i = 0; i < 4; i++) {
            int shift= (4 - 1 - i) * 8;
            value +=(bytes[i] & 0x000000FF) << shift;//往高位游
        }
        return value;
    }
    public static byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static byte[] intToByteArray(int i) {
        byte[] result = new byte[4];
        //由高位到低位
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF);
        result[3] = (byte)(i & 0xFF);
        return result;
    }

    /**
     * 将两个数组合并成一个数据
     * @param array1
     * @param array2
     * @return
     */
    public static byte[] combineByteArray(byte[] array1, byte[] array2) {
        byte[] combined = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, combined, 0, array1.length);
        System.arraycopy(array2, 0, combined, array1.length, array2.length);
        return combined;
    }




	// 返回指定下标长度的字节数组

	public static byte[] getbytes(byte b[], int offset, int len) {
		byte[] buf = new byte[len];
		for (int i = 0; i < len; i++) {
			buf[i] = b[offset];
			offset++;
		}
		return buf;
	}



	// 字节数据转换成整型
	public static int bytes2int(byte b[]) {
		int s = 0;
		for (int i = 0; i < b.length; i++) {
			s+= ((b[i] & 0xFF) << ((b.length - i - 1) * 8));
		}
		return s;
	}

    public static int bytes2int(byte[] b, int byteOffset, int byteCount) {
        int intValue = 0;
        for (int i = byteOffset; i < (byteOffset + byteCount); i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - (i - byteOffset)));
        }
        return intValue;
    }

    public static int bytes2Int(byte[] bytes, int start, int length) {
        int sum = 0;
        int end = start + length;
        for (int i = start; i < end; i++) {
            int n = bytes[i] & 0xff;
            n <<= (--length) * 8;
            sum += n;
        }
        return sum;
    }



    public static int bytes2Int(byte[] b, int byteOffset) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        byteBuffer.put(b, byteOffset, 4); //占4个字节
        byteBuffer.flip();
        return byteBuffer.getInt();
    }

    public static byte[] int2Bytes(int value, int length) {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[length - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return bytes;
    }



	// 将字节流中的指定字节段转换成整型
	public static int bytes2int2(byte b[], int offset, int len) {
		byte[] respcode = new byte[len];
		for (int i = 0; i < len; i++) {
			respcode[i] = b[offset];
			offset++;
		}
		return bytes2int(respcode);
	}

	// 将不同的byte[]字节数组流打包成一个字节数组
	public static byte[] pack(byte[]... agrs) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		for (byte[] b : agrs) {
			if (b != null) {
				try {
					bout.write(b);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			continue;
		}
		byte[] buff = bout.toByteArray();
		return buff;
	}


	// HEX字节流显示
	public static String dumpBytesAsHEX(byte[] bytes) {
		int idx = 0;
		String s = "";
		StringBuilder body = new StringBuilder();
		for (int i = 0; i < 1024 && i < bytes.length; i++) {
			byte b = bytes[i];
			int hex = ((int) b) & 0xff;
			String shex = Integer.toHexString(hex).toUpperCase();
			if (1 == shex.length()) {
				body.append("0");
			}
			body.append(shex);
			body.append(" ");
			idx++;
			// if (16 == idx) {
			// s = body.toString();
			// body = new StringBuilder();
			// idx = 0;
			// }
		}
		if (idx != 0) {
			s = body.toString();
		}
		return s;
	}

	public static byte[] HexString2Bytes(String hexstr)
	{
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++)
		{
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = ((byte)(parse(c0) << 4 | parse(c1)));
		}
		return b;
	}

	private static int parse(char c)
	{
		if (c >= 'a') {
			return c - 'a' + 10 & 0xF;
		}
		if (c >= 'A') {
			return c - 'A' + 10 & 0xF;
		}
		return c - '0' & 0xF;
	}

	public static String bytes2UTF8string(byte[] source){
		String dst = "";
		try{
			dst = new String(source, "UTF-8");
		}
		catch (UnsupportedEncodingException e){
			dst = "";
		}
		return dst;
	}
	public static String bytes2UTF8string2(byte[] b, int offset, int len){
		byte[] a = new byte[len];
		for (int i = 0; i < len; i++){
			a[i] = b[offset];
			offset++;
		}
		return bytes2UTF8string(a);
	}

	public static byte[] reversEndian(byte str[], int len) // 字节流翻转
	{
		byte b;
		byte res[] = new byte[len];
		for (int i = 0; i < len; i++) {
			b = str[i];
			res[len - i - 1] = b;
		}
		return res;
	}

	public static byte[] reversEndian(byte str[]) // 字节流翻转
	{
		byte b;
		int len = str.length;
		byte res[] = new byte[len];
		for (int i = 0; i < len; i++) {
			b = str[i];
			res[len - i - 1] = b;
		}
		return res;
	}


    public static byte[] str2byte(String val){
        byte[] bt=val.getBytes();
        byte[] fill={0};
        bt= ByteUtil.pack(bt, fill);
        return bt;
    }

    /**
     * @param srcObj
     *            源字节数组转换成String的字节数组
     * @return
     */
    public static String byteToString(byte[] srcObj, String charEncode) {
        String destObj = null;
        try {
            destObj = new String(srcObj, charEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destObj.replaceAll("\0", " ");
    }

    public static void main(String [] args){
		byte[] bytes=null;
		String i=dumpBytesAsHEX(bytes);
		System.out.println(i);
	}



}
