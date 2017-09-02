package com.ids.teenage.http.cases;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ids.teenage.http.common.Constants;
import com.ids.teenage.http.common.Context;
import com.ids.teenage.util.HTTPCLIENT;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Master.wang on 2017/5/24.
 * 甜点社
 */
public class Dessert extends Context {
        private static Map<String,String> stringMap=parms_c();


        /**冰箱
         * @param uid
         * @return
         * @throws Exception
         */
        public static String getUserDessert(String uid) throws Exception {
            String url = HTTPCLIENT.getUrl(Constants.TEST_HOST, Constants.GET_USER_DESSERT);
            stringMap.put("userId", uid);
            return xxRsp(url, stringMap);
        }

        /**
         * 甜点货架信息
         * @param uid 自己id
         * @param targetUid 用户id
         * @return
         */
        public static String getDessertShelves(String uid,String targetUid) throws  Exception{
            String url = HTTPCLIENT.getUrl(Constants.TEST_HOST, Constants.GET_DESSERT_SHELVES);
            stringMap.put("userId", uid);
            if(targetUid!=null){
                stringMap.put("targetUserId",targetUid);
            }
            return xxRsp(url,stringMap);
        }

    /**
     *上架/修改商品
     * @param uid  用户id
     * @param itemId 物品id
     * @param itemNum 物品数量
     * @param sellProce 物品价格
     * @param state  是否发布广告 0:否 1:是 2:去掉广告cd时间
     * @param shelfNumber  	货架位置
     * @return
     */
    public static String saleDessertMaterial(String uid,int itemId,int itemNum,int sellProce,int state,int shelfNumber) throws  Exception{
        String url = HTTPCLIENT.getUrl(Constants.TEST_HOST, Constants.SALE_DESSERT_MATERIAL);
        stringMap.put("userId", uid);
        stringMap.put("itemId",String.valueOf(itemId));
        stringMap.put("itemNum",String.valueOf(itemNum));
        stringMap.put("sellPrice",String.valueOf(sellProce));
        stringMap.put("state",String.valueOf(state));
        stringMap.put("shelfNumber",String.valueOf(shelfNumber));
        return xxRsp(url,stringMap);
    }

    /**
     * 查询甜点订单
     * @param uid
     * @return
     */
    public static   String getDessertOrderList(String uid) throws Exception {
        String url = HTTPCLIENT.getUrl(Constants.TEST_HOST, Constants.GET_DESSERT_ORDERLIST);
        stringMap.put("userId", uid);
        return xxRsp(url,stringMap);
    }
    @Test
    public void testGetDessertOrderList(){
        try {
            List<String> listOrderId=new ArrayList<>();
            String xxs=getDessertOrderList(UID);
            JSONObject jsonObject= JSON.parseObject(xxs);
            JSONObject data=jsonObject.getJSONObject("data");
            JSONArray dessertOrder=data.getJSONArray("dessertOrder");
            for (int i=0;i<dessertOrder.size();i++){
                JSONObject jb=dessertOrder.getJSONObject(i);
                String orderId=jb.getString("orderId");
                listOrderId.add(orderId);
            }

            listOrderId.stream().forEach(s -> System.out.println(s));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void testGetUserDessert() {
        try {
            System.out.println("冰箱：" + getUserDessert(UID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testGetDessertShelves(){
        try {
            System.out.println("货架：" + getDessertShelves(UID, null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSaleDessertMaterial(){
        int itemId=2001; //白面包
        int itemNum=0; //
        int sellProce=2; //价格
        int state=2;
        int shelfNumber=10; //货架位置
        try {
            System.out.println("上架：" + saleDessertMaterial(UID, itemId, itemNum, sellProce, state, shelfNumber));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
