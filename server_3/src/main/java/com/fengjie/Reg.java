package com.fengjie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;


@WebServlet("/Reg")
public class Reg extends HttpServlet {
    private static final long seriaVersionUID=1L;

    public Reg(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doGEt请求");
        String username =req.getParameter("username");
        username=new String(username.getBytes(),"UTF-8");
        String password=req.getParameter("password");
        System.out.println("请求数据：—"+"username:"+username+",-paaword:"+password);
        sql sl=new sql();
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter out=resp.getWriter();
        boolean reged=sl.insertdl(username,password);
        if(reged){
            System.out.println("注册成功："+"-username:"+username+",-password:"+password);
            req.getSession().setAttribute("success","regsuccess");
            out.print("regsuccess");
        }else{
            req.getSession().setAttribute("failed","regfailed");
            System.out.println("注册失败："+"-username:"+username+",-password:"+password);
            out.print("regfailed");

        }
        out.flush();
        out.close();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("dopost请求");
        this.doGet(request, response);
    }
}
