package com.fengjie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@WebServlet({"/login"})
public class sy4ser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGet请求");
        String username=req.getParameter("username");
        username=new String(username.getBytes(),"UTF-8");
        String password=req.getParameter("password");
        System.out.println("请求数据：—"+"username:"+username+",-paaword:"+password);
        sql ql=new sql();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();
        boolean loged=ql.denglu(username,password);
        if(loged){
            System.out.println("登录成功："+"-username:"+username+",-password:"+password);
            out.print("Linksuccess");
        }else{
            System.out.println("登录失败："+"-username:"+username+",-password:"+password);
            out.print(ql.errorhandle());
        }
        out.flush();
        out.close();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("sy4ser---post请求");
        this.doGet(request, response);
    }
}
