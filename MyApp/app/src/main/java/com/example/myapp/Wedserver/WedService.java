package com.example.myapp.Wedserver;


import android.os.StrictMode;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

/*
*   登录
*
* */
public class WedService {
    String Charset="UTF-8";
    private static final String PATH="http://10.40.9.15:8080/server_3_war/login";

    @Nullable
    public static String executeHttpGet(String username, String password) {
        HttpURLConnection conn=null;
        InputStream is=null;
        try {
            String path=PATH+"?username="+username+"&password="+password;
            System.out.println(path);
            conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(3000);//设置超时
            conn.setReadTimeout(3000);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset","UTF-8");
            if(conn.getResponseCode()==200){
                is=conn.getInputStream();
                return parseInfo(is);
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 意外退出时进行连接关闭保护
            if (conn != null) {
                conn.disconnect();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "服务器链接超时...";
    }

    // 将输入流转化为 String 型
    private static String parseInfo(InputStream inStream) throws Exception {
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }
    // 将输入流转化为byte型
    public static byte[] read(InputStream inStream) throws Exception {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }

}