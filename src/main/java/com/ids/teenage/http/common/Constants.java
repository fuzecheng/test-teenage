package com.ids.teenage.http.common;

/**
 * teenage 接口常量配置
 * Created by Master.wang on 2017/5/22.
 */
public  class Constants {


    public static final String DEV_HOST="192.168.141.142";
    public static final String TEST_HOST="192.168.143.21";
    public static final String LOAD_HOST_PHP="120.92.45.201";
    public static final String LOAD_HOST_C="120.92.94.80";

    //登录
    public static final String GAME_LOGIN="/game/login";

    //任务
    public static final String MISSION_GETLIST="/mission/getMissionList"; //获取任务列表

    //甜点社
    public static final String GET_USER_DESSERT="/dessert/getUserDessert"; //冰箱
    public static final String GET_DESSERT_SHELVES="/dessert/getDessertShelves"; //货架信息
    public static final String SALE_DESSERT_MATERIAL="/dessert/saleDessertMaterial"; //上架
    public static final String GET_DESSERT_ORDERLIST="/dessert/getDessertOrderList"; //甜点订单查询

    //好友
    public static final String FRIEND_MAKEFRIENDS="/friend/makeFriends"; //添加好友
}
