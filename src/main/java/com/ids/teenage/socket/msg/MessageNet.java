package com.ids.teenage.socket.msg;
import com.ids.teenage.proto.TFBsGo;
import com.ids.teenage.socket.client.Connection;
import com.ids.teenage.socket.codec.NetDEcode;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Master.wang on 2017/5/22.
 */
public class MessageNet {
    private static Log logger = LogFactory.getLog(MessageNet.class);
    private static Connection conn=null;
    /**
     * 心跳周期（单位：毫秒）
     */
    private volatile static long activeCycle = 5000;
   /* static {
        try {
            conn = new  Connection(Constants.TEST_HOST,Constants.HOME_PORT);
           // conn= ConnectionManager.createConnection(Constants.TEST_HOST,Constants.HOME_PORT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public  static void startClient()
    {
        try {
            if(null==conn){
                conn = new  Connection(Constants.LOAD_HOST,Constants.HOME_PORT);
                //conn= ConnectionManager.createConnection(Constants.TEST_HOST,Constants.HOME_PORT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void doLogin(long uid,String password)
    {

        if(null==conn)
        {
            startClient();
        }
        if (conn.isConnected())
        {
            try {
                requestLogin(uid,password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 心跳线程
     */
    public static class ConnectActiveMonitor extends Thread{
        private volatile boolean running = true;
        public void run() {
            while (running) {
                long time = System.currentTimeMillis();
                        try {
                            if (conn.isConnected())
                            {
                                if (conn.getLastActTime() + activeCycle < time) {
                                    MessageNet.heartBeat(); //心跳包
                                }
                            }

                        } catch (IOException e) {
                            e.getMessage();
                        }

                }

            }

    }




    /**
     * 心跳包
     * @throws java.io.IOException
     */
    public static void heartBeat() throws IOException {
        if (conn.isConnected())  {
            TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
            msg.setMsgType( TFBsGo.MsgType.MSG_HEARTBEAT_REQ);
            DataPacket dataPacket =new DataPacket();
            dataPacket.setBodys(msg.build().toByteArray());
            conn.send0(dataPacket);
        }
        else {
          startClient();
        }
    }

    public static DataPacket pingPacket(){
       TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_HEARTBEAT_REQ);
        DataPacket dataPacket=new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());
        return dataPacket;
    }


    /**
     * 登录请求
     * @param uid
     * @param password
     * @throws IOException
     */
    public static void requestLogin(long uid,String password) throws IOException{
      DataPacket dataPacket= loginReq(uid,password);
        conn.send0(dataPacket);
    }

    /**
     * 登录请求参数封装
     * @param uid
     * @param password
     * @return
     */
    public static DataPacket loginReq(long uid,String password) {
        TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_LOGIN_REQ);
        TFBsGo.LoginRequest.Builder loginRequest = TFBsGo.LoginRequest.newBuilder();
        loginRequest.setUserId(uid);
        loginRequest.setPassward(password);
        msg.setData(loginRequest.build().toByteString());
        DataPacket dataPacket =new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());

        return dataPacket;
    }

    /**
     * 退出公寓
     */
    public void requestExitMap() {
        TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_MAP_EXIT_REQ);

        DataPacket dataPacket =new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());

    }


    /**
     * 登录响应
     * @throws IOException
     */
    public static String resLogin()throws  IOException {
        byte [] data= NetDEcode.decode(conn.readStream());
        String result=null;
        if (null != data) {
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            logger.info("接收登录消息");
            if (msg.getMsgType() == TFBsGo.MsgType.MSG_LOGIN_RES) {
                TFBsGo.LoginResponse loginResponse = TFBsGo.LoginResponse.parseFrom(msg.getData());
                System.out.println("loginResp ==>> "+loginResponse.toString());
                 result= String.valueOf(loginResponse.getUserId());
            }
            if (msg.getMsgType().getNumber() == TFBsGo.MsgType.MSG_ERROR_RES_VALUE){
                TFBsGo.ErrorResponse error = TFBsGo.ErrorResponse.parseFrom(msg.getData());
                logger.error("error code = " + error.getCode());
                logger.error("msg = "+error.getMsg());
                 result=error.getMsg();
            }

        }else {
            logger.error("data is null!");
        }
        return result;
    }

    /**
     * 进入地图
     * @param mapid
     * @param targetUserId
     * @param type  0,公寓， 1甜点社
     * @throws IOException
     */
    public static void reqMapInterRequest(int mapid, long targetUserId,int type) throws IOException {
        DataPacket dataPacket=reqMapInter(mapid,targetUserId,type);
        conn.send0(dataPacket);
    }

    public static DataPacket reqMapInter(int mapid, long targetUserId,int type){
        TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_MAP_ENTER_REQ);
        TFBsGo.MapInterRequest.Builder mapInter= TFBsGo.MapInterRequest.newBuilder();
        mapInter.setMapId(mapid);
        mapInter.setTargetUserId(targetUserId);
        mapInter.setType(type);
        msg.setData(mapInter.build().toByteString());
        DataPacket dataPacket =new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());
        return dataPacket;
    }



    /**
     * 进入地图成功响应
     * @throws IOException
     */
    public static void resMapInterResponse() throws IOException {
        byte [] data=NetDEcode.decode(conn.readStream());
        if(data!=null){
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            if(msg.getMsgType()== TFBsGo.MsgType.MSG_MAP_ENTER_SUCCESS_RES){
                TFBsGo.MapInterSuccessResponse rsp= TFBsGo.MapInterSuccessResponse.parseFrom(msg.getData());

                 System.out.println("MapInterSuccessResponse==>>>"+rsp.toString());
                int [] r1={809,708,607,506,405,304,203,103};
                int [] r2={103,202,301,401,501,601,701,801};
                List<int []> points=new ArrayList<>();
                points.add(r1);
                points.add(r2);

                    for (int i = 0; i <50; i++) {
                        for(int j=0;j<points.size();j++) {
                            reqMapSynchronize(points.get(j)); //移动
                        }

                    }



            }

            if(msg.getMsgType()== TFBsGo.MsgType.MSG_MAP_EXIT_RES){
                TFBsGo.MapExitResponse mapExitResponse= TFBsGo.MapExitResponse.parseFrom(msg.getData());

                System.out.println("mapExit ==>> "+mapExitResponse.toString());

                reqMapInterRequest(1,9018108,0); //再次进入
                resMapInterResponse();
            }
            if (msg.getMsgType().getNumber() == TFBsGo.MsgType.MSG_ERROR_RES_VALUE){
                TFBsGo.ErrorResponse error = TFBsGo.ErrorResponse.parseFrom(msg.getData());
                logger.error("error code = " + error.getCode());
                logger.error("msg = "+error.getMsg());
            }

        }

    }


    /**
     * 地图移动请求
     * @param point
     * @throws IOException
     */
    public static  void reqMapSynchronize(int [] point) throws  IOException{

        DataPacket dataPacket= mapSynchronizeListReq(point);
        conn.send0(dataPacket);

        handleSynchronizeBroadcast(); //地图同步
    }





    public static DataPacket mapSynchronizeListReq(int point[]){

        TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_MAP_SYNCHRONIZE_REQ);
        TFBsGo.MapSynchronizeRequest.Builder mapRequest = TFBsGo.MapSynchronizeRequest.newBuilder();

        for(int i=0;i<point.length;i++){
            mapRequest.addCoordinateList(point[i]);
        }

        msg.setData(mapRequest.build().toByteString());
        DataPacket dataPacket =new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());
        return dataPacket;
    }

    /**
     * 地图公寓广播
     * @throws IOException
     */
    public static void handleSynchronizeBroadcast() throws IOException{
        byte [] data=NetDEcode.decode(conn.readStream());
        if(data!=null){
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            if(msg.getMsgType()== TFBsGo.MsgType.MSG_SYSCHRONIZE_BROAD_RES){
                TFBsGo.SynchronizeResponse synchronizeResponse= TFBsGo.SynchronizeResponse.parseFrom(msg.getData());

                System.out.println("synchronizeResponse===>>>"+synchronizeResponse.toString());
            }

            if (msg.getMsgType().getNumber() == TFBsGo.MsgType.MSG_ERROR_RES_VALUE){
                TFBsGo.ErrorResponse error = TFBsGo.ErrorResponse.parseFrom(msg.getData());

                logger.error("error code = " + error.getCode());
                logger.error("msg = "+error.getMsg());
            }

        }

    }

    /**
     * 地图同步响应
     * @throws IOException
     */
    public static void respMapSynchronize() throws  IOException{
        byte [] data=NetDEcode.decode(conn.readStream());
        if(data!=null){
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            if(msg.getMsgType()== TFBsGo.MsgType.MSG_MAP_SYNCHRONIZE_RES){
                TFBsGo.MapSynchronizeResponse mapSynchronizeResponse = TFBsGo.MapSynchronizeResponse.parseFrom(msg.getData());
                System.out.println(mapSynchronizeResponse.toString());
            }

            if (msg.getMsgType().getNumber() == TFBsGo.MsgType.MSG_ERROR_RES_VALUE){
                TFBsGo.ErrorResponse error = TFBsGo.ErrorResponse.parseFrom(msg.getData());
                logger.error("error code = " + error.getCode());
                logger.error("msg = "+error.getMsg());
            }

        }
    }

    public static void reqGetLeavingMsg(long userId) throws  IOException{
       DataPacket dataPacket= reqGetLeaving(userId);
       conn.send0(dataPacket);
    }

    public static DataPacket reqGetLeaving(long userId) {
        TFBsGo.MessageBase.Builder msg= TFBsGo.MessageBase.newBuilder();
        msg.setMsgType(TFBsGo.MsgType.MSG_GET_LEAVING_MSG_REQ);
        TFBsGo.GetLeavingMsgRequest.Builder leavingMsg= TFBsGo.GetLeavingMsgRequest.newBuilder();
        leavingMsg.setTargetId(userId);
        leavingMsg.setStart(0);
        leavingMsg.setEnd(30);
        msg.setData(leavingMsg.build().toByteString());
        DataPacket dataPacket =new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());
        return  dataPacket;

    }

    public static void respGetLeavingMsg() throws Exception{
        byte [] data=NetDEcode.decode(conn.readStream());
        if(data!=null){
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            if(msg.getMsgType()== TFBsGo.MsgType.MSG_GET_LEAVING_MSG_RES){
                TFBsGo.GetLeavingMsgResponse getLeavingMsgResponse = TFBsGo.GetLeavingMsgResponse.parseFrom(msg.getData());

                System.out.println(getLeavingMsgResponse.toString());
            }

            if (msg.getMsgType().getNumber() == TFBsGo.MsgType.MSG_ERROR_RES_VALUE){
                TFBsGo.ErrorResponse error = TFBsGo.ErrorResponse.parseFrom(msg.getData());
                logger.error("error code = " + error.getCode());
                logger.error("msg = "+error.getMsg());
            }

        }

    }

    /**
     * 位置
     * @throws Exception
     */
    public static void respondMapAllCoordinate() throws Exception{
        byte [] data=NetDEcode.decode(conn.readStream());
        if(data!=null){
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            if(msg.getMsgType().equals(TFBsGo.MsgType.MSG_MAP_ALL_COORDINATE_RES)) {
                TFBsGo.MapAllCoordinateResponse resp = TFBsGo.MapAllCoordinateResponse.parseFrom(data);

                logger.info(resp.toString());
            }
            if(msg.getMsgType().equals(TFBsGo.MsgType.MSG_MAP_ENTER_RES)){
                TFBsGo.MapInterResponse  response= TFBsGo.MapInterResponse.parseFrom(data);

                logger.info(response);
            }

        }

    }


    /**
     * 地图家具摆放更新
     */
    public void reqMapKnapUpdateRequest(){

    }

    public void reqMergeFuniSingle(int id){

    }


    /**
     * 购买家具请求
     * @param id
     * @param num
     * @throws IOException
     */
    public void reqBuyFurniture(int id, int num) throws  IOException{
        TFBsGo.MessageBase.Builder msg = TFBsGo.MessageBase.newBuilder();
        msg.setMsgType( TFBsGo.MsgType.MSG_BUY_FURNITURE_REQ);
        TFBsGo.BuyFurnitureRequest.Builder request = TFBsGo.BuyFurnitureRequest.newBuilder();
        request.setId(id);
        request.setNum(num);
        msg.setData(request.build().toByteString());
        DataPacket dataPacket =new DataPacket();
        dataPacket.setBodys(msg.build().toByteArray());
        conn.send0(dataPacket);
    }

    /**
     * 购买家具响应
     * @throws IOException
     */
    public void resBuyFurniture()throws IOException{
        byte [] data=NetDEcode.decode(conn.readStream());
        if(data!=null){
            TFBsGo.MessageBase msg = TFBsGo.MessageBase.parseFrom(data);
            if(msg.getMsgType()== TFBsGo.MsgType.MSG_PROP_UPDATE_MORE_RES) {
                TFBsGo.PropUpdateMoreResponse resp = TFBsGo.PropUpdateMoreResponse.parseFrom(msg.getData());
            }
            if (msg.getMsgType().getNumber() == TFBsGo.MsgType.MSG_ERROR_RES_VALUE){
                TFBsGo.ErrorResponse error = TFBsGo.ErrorResponse.parseFrom(msg.getData());
                logger.error("error code = " + error.getCode());
                logger.error("msg = "+error.getMsg());
            }

        }
    }






}
