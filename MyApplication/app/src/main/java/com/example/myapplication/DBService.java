package com.example.myapplication;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService {

    private Connection conn=null; //打开数据库对象
    private PreparedStatement ps=null;//操作整合sql语句的对象
    private ResultSet rs=null;//查询结果的集合

    //DBService 对象
    public static DBService dbService=null;

    /**
     * 构造方法 私有化
     * */

    private DBService(){

    }

    /**
     * 获取MySQL数据库单例类对象
     * */

    public static DBService getDbService(){
        if(dbService==null){
            dbService=new DBService();
        }
        return dbService;
    }


    /**
     * 查
     * */

    public List<Anime> getAnimeData(){
        //结果存放集合
        List<Anime> list=new ArrayList<Anime>();
        //MySQL 语句
        String sql="select * from anime_table";
        //获取链接数据库对象
        conn= DBOpenHelper.getConn();
        try {
            if(conn!=null&&(!conn.isClosed())){
                ps= (PreparedStatement) conn.prepareStatement(sql);
                if(ps!=null){
                    rs= ps.executeQuery();
                    if(rs!=null){
                        while(rs.next()){

                            System.out.println(rs.getString("title"));
                            if(rs.getInt("image") == 0) {
                                Anime a=new Anime(rs.getString("title"),rs.getString("info"),R.drawable.image_weather);
                                list.add(a);
                            }
                            else {
                                Anime a=new Anime(rs.getString("title"),rs.getString("info"),rs.getInt("image"));
                                list.add(a);
                            }

                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DBOpenHelper.closeAll(conn,ps,rs);//关闭相关操作
        return list;
    }

    /**
     * 修改数据库中某个对象的状态  改
     * */

//    public int updateUserData(String phone){
//        int result=-1;
//        if(!StringUtils.isEmpty(phone)){
//            //获取链接数据库对象
//            conn= DBOpenHelper.getConn();
//            //MySQL 语句
//            String sql="update user set state=? where phone=?";
//            try {
//                boolean closed=conn.isClosed();
//                if(conn!=null&&(!closed)){
//                    ps= (PreparedStatement) conn.prepareStatement(sql);
//                    ps.setString(1,"1");//第一个参数state 一定要和上面SQL语句字段顺序一致
//                    ps.setString(2,phone);//第二个参数 phone 一定要和上面SQL语句字段顺序一致
//                    result=ps.executeUpdate();//返回1 执行成功
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
//        return result;
//    }

    /**
     * 批量向数据库插入数据  增
     * */

    public int insertAnimeData(List<Anime> list){
        int result=-1;
        if((list!=null)&&(list.size()>0)){
            //获取链接数据库对象
            conn= DBOpenHelper.getConn();
            //MySQL 语句
            String sql="INSERT INTO anime_table (title,info,image) VALUES (?,?,?)";
            try {
                boolean closed=conn.isClosed();
                if((conn!=null)&&(!closed)){
                    for(Anime anime:list){
                        String title=anime.getTitle();
                        String info=anime.getInfo();
                        int image=anime.getImageResource();
                        Log.w("testdbconnection",title);
                        Log.w("testdbconnection",info);
                        Log.w("testdbconnection",((Integer)image).toString());
                    }
                    for(Anime anime:list){
                        ps= (PreparedStatement) conn.prepareStatement(sql);
                        String title=anime.getTitle();
                        String info=anime.getInfo();
                        int image=anime.getImageResource();
                        ps.setString(1,title);//第一个参数 name 规则同上
                        ps.setString(2,info);//第二个参数 phone 规则同上
                        ps.setInt(3,image);//第三个参数 content 规则同上
                        Log.w("testdbconnection",ps.toString());
                        ps.addBatch();
                        ps.executeBatch();//返回1 执行成功
                        Log.w("testdbconnection",((Integer)result).toString());
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
        return result;
    }
//
//
//    /**
//     * 删除数据 删
//     * */
//
//    public int delUserData(String phone){
//        int result=-1;
//        if((!StringUtils.isEmpty(phone))&&(PhoneNumberUtils.isMobileNumber(phone))){
//            //获取链接数据库对象
//            conn= DBOpenHelper.getConn();
//            //MySQL 语句
//            String sql="delete from user where phone=?";
//            try {
//                boolean closed=conn.isClosed();
//                if((conn!=null)&&(!closed)){
//                    ps= (PreparedStatement) conn.prepareStatement(sql);
//                    ps.setString(1, phone);
//                    result=ps.executeUpdate();//返回1 执行成功
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        DBOpenHelper.closeAll(conn,ps);//关闭相关操作
//        return result;
//    }

}
