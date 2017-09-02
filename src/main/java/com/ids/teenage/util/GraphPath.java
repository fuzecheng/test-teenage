package com.ids.teenage.util;

import java.util.Stack;

/**
 * Created by Master.wang on 2017/6/12.
 */
public class GraphPath {
    private int[][] block;

    /** 高度 */
    private int maxHeight;

    /** 宽度 */
    private int maxWidth;
    /** 结束x坐标 */
    private int targetx = 9;
    /** 结束y坐标 */
    private int targety = 9;
    /** 回溯节点需要的路径 */
    private Stack stack = new Stack();
    /** 开始坐标 */
    private int startx;
    /** 结束时间 */
    private int starty;
    /** 状态保存 */
    private NodeInfo[][] table=null;

    private boolean isCompelet = true;

    private boolean result = false;


    GraphPath(int[][] grapdata) {
        this.block = grapdata;
        this.maxHeight = block.length;
        this.maxWidth = block[0].length;
        table = new NodeInfo[maxHeight][maxWidth];
        // initTable();

    }

    /**
     * 初始化路径的表格
     */
    public void initTable() {
        for (int i = 0; i < maxHeight; i++)
            for (int j = 0; j < maxWidth; j++) {
                NodeInfo node = new NodeInfo();
                node.tagx = i;
                node.tagy = j;
                table[i][j] = node;
            }
    }

    public boolean findPath(int x, int y, int endx, int endy) {
        this.targetx = endx;
        this.targety = endy;
        this.startx = x;
        this.starty = y;
        initTable();
        this.deepPathLocation(x, y);
        return result & isCompelet;
    }

    public void printPath() {
        for (int i = 0; i < maxWidth; i++) {
            for (int j = 0; j < maxHeight; j++) {

                NodeInfo info = table[i][j];
                if (info.isPaht == 1) {
                    System.out.print("1");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();

        }
    }

    /**
     * 是否到达
     *
     * @param x
     * @param y
     * @return
     */
    public boolean isOk(int x, int y) {
        if (x == targetx && y == targety)
            return true;
        return false;
    }

    /**
     * 判断是否通过
     *
     * @param x
     * @param y
     * @return
     */
    private boolean check(int x, int y) {

        // 地址越界
        if (x >= maxWidth || x < 0)
            return false;
        if (y >= maxHeight || y < 0)
            return false;
        // 是否曾经来过
        NodeInfo info = table[x][y];
        if (info.isPaht > 0)
            return false;
        // 是否可以通过
        if (block[x][y] == 1)
            return false;
        return true;
    }

    /**
     * 深度优先算法
     *
     * @param x
     * @param y
     */
    private void deepPathLocation(int x, int y) {
        NodeInfo info = table[x][y];
        // 判断是否到达，如果到达 直接返回
        if (isOk(x, y)) {
            info.isPaht = 1;
            result = true;
            return;
        }

        // 如果没有到达，先看x+1 是否可以通过 如果可以通过
        // 可以向右
        if (info.x == 0) {
            System.out.println("向下边");
            info.x = 1;
            // 可以移动
            if (check(x + 1, y)) {
                // 想右边移动
                // 记录当前为已经过来的
                info.isPaht = 1;
                stack.push(info);
                deepPathLocation(x + 1, y);
            } else {

                // 看其他的路
                deepPathLocation(x, y);
            }
            // 右边已经走了

        }
        // 右边已经走了看下边可以走吗
        else if (info.buttom == 0) {
            System.out.println("向右走");
            info.buttom = 1;
            if (check(x, y + 1)) {
                // 想右边移动
                info.isPaht = 1;
                stack.push(info);
                deepPathLocation(x, y + 1);

            } else {
                deepPathLocation(x, y);
            }

        }

        else if (info.y == 0) {
            info.y = 1;
            if (check(x, y - 1)) {
                info.isPaht = 1;
                stack.push(info);
                deepPathLocation(x, y - 1);
            } else {
                deepPathLocation(x, y);
            }
        }

        else if (info.left == 0) {
            System.out.println("向后");
            info.left = 1;
            if (check(x - 1, y)) {
                info.isPaht = 1;
                stack.push(info);
                deepPathLocation(x - 1, y);

                // info.left=1;
            } else {
                deepPathLocation(x, y);
            }
        }
        // 回退
        else {
            // 去掉当前路径
            if (!stack.isEmpty()) {
                info.isPaht = 0;
                System.out.println("后退回去");
                NodeInfo preinfo = (NodeInfo) stack.pop();
                deepPathLocation(preinfo.tagx, preinfo.tagy);
            } else {
                System.out.println("死在这里弹出" + info);
                isCompelet = false;
            }

        }
    }


    /**
     * 节点状态统计
     * @author wuwhe
     *
     */
    private  class NodeInfo {
        //當前在圖中的x坐標
        int tagx;
        //當前在圖中的y坐標
        int tagy;
        //右邊是否有同路
        int x;
        //上邊是否有同路
        int y;
        //左邊是佛有同路
        int left;
        //底部是否有同路
        int buttom;
        int isPaht;
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "tagx:"+tagx+"tagy:"+tagy+"x:"+x+" y:"+y +"left:"+left+" buttom:"+buttom+" isPath:"+isPaht;
        }

        public void resetLevel(){
            this.x=0;
            this.y=0;
            this.left=0;
            this.buttom=0;

        }
    }

    public static void main(String[] a){

        int[][] data =
                {{0,1,1,1,1,1,0},
                {0,1,1,1,1,1,0},
                {0,0,1,1,1,1,0},
                {1,0,0,1,0,0,1},
                {1,1,0,1,0,0,0},
                {1,1,0,0,0,1,0},
                {1,1,1,1,1,1,0},
        };
        int[][] data1=
                {{0,0,1,0,1,1,0},
                        {0,1,1,1,1,0,1},
                        {0,0,1,1,1,1,0},
                        {1,0,0,1,1,0,1},
                        {1,1,0,1,0,0,0},
                        {1,1,0,0,0,1,0},
                        {1,1,1,1,1,1,0},
                };

        GraphPath grap = new GraphPath(data1);
        if(grap.findPath(0, 0, 6, 6)){
            grap.printPath();
        }



    }
}
