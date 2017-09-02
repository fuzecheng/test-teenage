package com.ids.teenage.util;


/**
 * Created by Master.wang on 2017/2/21.
 */
public class XXTEACommon {
    public static final String XXTEAKEY = "c93ac2a43ae3f1abb98a69c9366f57b0"; //toyblast
    public static final String TFBOS_SECRET = "a1f10342afd94cbc0e9870a0f222ac93b8f0fbfe"; //少年时代
    /**
     * 使用xxtea加密 content
     *
     * @param content
     * @return
     */
    public static String xxteaContentEncrypt(String content) {
        String xxteaContent = Base64.encode(XXTeaUtil.encrypt(content.getBytes(), TFBOS_SECRET.getBytes()));
        return xxteaContent;
    }
    /**
     * 解密xxtea
     * @param xxcontent
     * @return
     */
    public static String xxteaContentDecrypt(String xxcontent){
        String xxteaContent= ByteUtil.byteToString(XXTeaUtil.decrypt(Base64.decode(xxcontent), TFBOS_SECRET.getBytes()), "UTF-8");
        return xxteaContent;
    }


    public static void main (String [] args) {
        String xx = "6EopQqL+9QAXHQ4R2CvojYdcEwsDic+IC4gzJAb7l9LpJQhqd5KOU2u8uAJkmEpZl2y0/Ne6fef/38Ue52WHx2rKpqpMtCOR/wLNd2D1vnf/ip/141X/HBhstgnXzMxjy9pkn9kpJrn7GNNohqb2nOKZGqlRQOqAQnfQimN1yyWIQKYcASUkgAqgTpeGHc5AWpNonegGDsGYIKm63e/7OrSyWhAw1a3J1VtM+XLGy7ntZhKxMFVwDz0ZNv4mzJ80k/vZ5qlAwwQPNX3/OOSuz+ccNwPn178LO0qS9/YI0J+zEYwS1Gywr73KnvPxQxKUdljZ5IjRoqKLeGBijrccfVn7A5v/D/S3OYlPSQCND6F799I+IUp6wyN0rFC1jWaK9ejuqa7gIhtIuEPUxCurlDa9stC14v/rwjVz3HhcKlpd243JdczVS40Q6nvegvZrhCaNg6wg48OLLe0sdrfgoqvFY6gdbyl3Low4vDz46i6OsqVp4uQ9Q599fcJGex7sqeZ2Xo7RERTxM3o/D+NeuGiL6tbRh0WPuaTBMsbVK+MKwvrNCgEknyj4cC04E25udfT4cV3PT1hu5nE566UhYsKowzN66RuKiSg+70zETaqF9J/pMn8QX+3ZoBST2f4J0Yy4FT9Zxf47f0r5tfgZAUAlP1PvKVj+4Tb6WDPYO2D1vpuqXM0W6V6YNm4DBeqittpfaA6BQX5uaJGcTVGYRteLpWk4CMaM5P0DusRYYAU3B8SmtznZKEAjAa5RnyyHjs0UsgCbLqPwnMEQGY37BAZbrCd0jSdeJaTI0BBnDUZ+Kkek4bWzIg==";
        System.out.println(xxteaContentDecrypt(xx));


        String xx1 = "Z67xPz+IkpfiXfgT9YDrA7NOWlQOW5SdwE60gY2qqXpZ9E8UcFaapNCY3IVmNEAZosuWPSw/Lqfk+9cAR0ZX1ninyKiPiGQcVAZSDj+bI7xkhXgFEMNhwSYJCoWPnq5Vh3dozicwCitzGgY6oUsJ8h2aUcOmKlfTGvQdq0m9w+QlJEfzZDunSEGS0cJGLIxDvh9TQQtLkG2Ya0hmAwinck0Jm596WgRglkUwh/iViN6yGr1G/11auAwe0tfleT6Tz4mnQng7nWWNDZLJ+KquVZzgUcR3ruJHiGhkYJU2fxwP0lZFXf7dwOkUPTxzNdg4o3NJ0xS87R3zPOSSg6UdZhRxrrKOpQjQ+K9mGToIiDyBjed3tu9MTBBM4gVzgy1XB1pyQKm3QU9f6UJYXALyoyj4Om5Dqo6klH8anqYEjFGTkwQqmLutkRv3TK74yMve2a0bJB+qp4kXq1GOOrkFhWvW4hjuCnj2CM/qTg17MA7xfwmpXpnImvskjh8iuJiN/yT52YE8Ehw4qpE7Yl8Q1aXgzemufk5D/0Xb32tT52W+irkYvz25fR0/3JkqQhUZGAASMbH41XBjXLHd2Pxjbg==";
        System.out.println(xxteaContentDecrypt(xx1));


        String xx2 = "MlBasWYbDIbzXB8WFpzNmZcDHy5W759NBEnjVSSacWOmz44NU8tdAumVYig4uWjeOZNW5XWr7//KFLRpGydl2TGuitVh4GiQ7ir+TP4yICuQZYdvNTEp7iYQ9y4omJqIYpoqe6XfYvAwwRhxU4jFWtP/OZ96vGFmNdnnvwZUwOeoUHfQhc++KcdeweqtrWkextn//sUtLOc/NmyLpJARyQZn+OCJmNUxiLr/8JOPwGUs3HvYRL1ttIJka9fu0HNkwm/y+guuU8mnYbrNUdpJAqVqIrQMFWLarSXgcD6SIuk3nXySD9DbJrdsHwtNFpgduylV9gLply9wS3rkpDSd3nYyEat2EkpIrel9iQglRgeO0+6kedvp+pGfrGHKSqdiX3rZiA6FBIpgiF9WVgZL3vqfZE4p8iBH95mXbgyQ7ZASrcgAqMQJeOJEqP+ZglBRVa7jv8fnSA4XCNe+CaCWgGlmVo8=";
        System.out.println(xxteaContentDecrypt(xx2));
        //
        StringBuffer sb=new StringBuffer();

        sb.append("plat_id=1");
        sb.append("&deviceid=2");
        sb.append("&channel=3");
        sb.append("&appkey=4");
        sb.append("&ui_page=5");
        sb.append("&version=6");
        sb.append("&level=7");
        sb.append("&openId=8"); //TODO
        sb.append("&userId=9");	 //1761546698
        String sss=sb.toString();
        sss+="$missionId=11"+"$targetId=2212";

        System.out.println(sss);


        int [] r2={103,202,301,401,501,601,701,801};
        for(int i=0;i<r2.length;i++){
            System.out.println("xxx :"+r2[i]);
        }
    }

}
