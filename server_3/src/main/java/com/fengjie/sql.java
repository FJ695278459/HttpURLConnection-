package com.fengjie;

import java.sql.*;

public class sql {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/student?useSSL=FALSE&serverTimezone=UTC";
    private static final String ACCOUNT = "root";
    private static final String PASSWORLD = "123456";
    private Connection connection=null;
    public static int errordeal=0;
    private static String user="";
    private static String pass="";
    private PreparedStatement ps = null;

    public sql(){
        try {
            Class.forName(DRIVER);
            connection= DriverManager.getConnection(URL,ACCOUNT,PASSWORLD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("链接失败");
            e.printStackTrace();
        }
    }
    public void queryFordlTable() throws SQLException {
        String query = "select * from dl where 1 = 1";
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        System.out.println("表dl");
        ResultSetMetaData rsmd = rs.getMetaData();//ResultSetMetaData是一个有关整个数据库的信息
        int columns = rsmd.getColumnCount();
        for (int i = 1; i <= columns; i++) {//数据库的表中列的索引是从1开始的
            System.out.print(rsmd.getColumnName(i) + "\t");
        }
        System.out.println();
        while (rs.next()) {
            for (int i = 1; i <= columns; i++) {
                System.out.print(rs.getString(i) + "\t");
            }
            System.out.println();
        }
    }
    public String errorhandle() {
        String error1="";
        if(errordeal==1)
            error1="userfailed";
        else if(errordeal==2)
            error1= "passfailed";
        return error1;
    }

    public Boolean denglu(String user,String pass){
        String query="select * from user where user=?";
        PreparedStatement ps;
        try {
            ps=connection.prepareStatement(query);
            ps.setString(1,user);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                if(rs.getString("password").equals(pass)){
                    return true;
                }else errordeal=2;
            }else errordeal=1;
            sqlclose();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Boolean insertdl(String user,String pass){
        String query = String.format("select * from user where user = '%s'", user);//username为主键，唯一性的标识数据库表中的数据，器数据不可重复
        PreparedStatement ps;
        try {
            ps=connection.prepareStatement(query);
            ResultSet rs=ps.executeQuery();
            if(!rs.next()){//如果不存在直接插入
                query ="insert into user values (?,?,null,null,null)" ;
                ps=connection.prepareStatement(query);
                ps.setString(1,user);
                ps.setString(2,pass);
                ps.execute();
                sqlclose();
                return true;
            }else {
                System.out.println("账号已存在");
                sqlclose();
                return false;
            }
        } catch (SQLException throwables) {

            throwables.printStackTrace();
            return false;
        }

    }


    public void sqlclose() throws SQLException {
        if(ps!= null){
            ps.close();
            ps = null;
        }
        if(connection != null){
            connection.close();
            connection = null;
        }
    }

}
