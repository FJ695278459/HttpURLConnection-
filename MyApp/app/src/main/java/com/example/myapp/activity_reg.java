package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Wedserver.WebService2;

public class activity_reg extends AppCompatActivity {

    private EditText name,pass,passto;
    private Button golog;
    private Button reg;
    private static String info2="";
    private static boolean regpd=false;

    private void inti(){
        name=findViewById(R.id.zhanghao_1);
        pass=findViewById(R.id.mima_1);
        passto=findViewById(R.id.mima_2);
        reg=findViewById(R.id.Button_zhuche);
        golog=findViewById(R.id.Button_fanhi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        inti();
        //StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        golog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity_reg.this,"返回登录界面",Toast.LENGTH_SHORT);
                startActivity(new Intent(activity_reg.this,MainActivity.class));
                finish();
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doreg();
            }
        });

    }
    private void doreg() {
        String names=name.getText().toString().trim();
        if(names.length()<=5||names.length()>10){
            Toast.makeText(activity_reg.this,"用户名至少5位，最多10位",Toast.LENGTH_SHORT).show();
        }else{
            String mima1=pass.getText().toString().trim();
            if(mima1.length()<6||mima1.length()>12){
                Toast.makeText(activity_reg.this,"密码应该为6-12位",Toast.LENGTH_SHORT).show();
            } else if (!mima1.matches("[a-zA-Z0-9_]*$")){
                Toast.makeText(activity_reg.this,"密码只允许包含英文字母、数字",Toast.LENGTH_SHORT).show();
            } else if(!mima1.equals(passto.getText().toString().trim())){
                Toast.makeText(activity_reg.this,"两次密码的输入不一致",Toast.LENGTH_SHORT).show();
            }else {
                Thread t3=new Thread(new activity_reg.MyThread1());
                t3.start();
                try {
                    t3.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(regpd){
                    Intent intent2=new Intent(activity_reg.this,MainActivity.class);
                    intent2.putExtra("username",names);
                    startActivity(intent2);
                    finish();
                }else if(info2.contains("fail")) {
                    Toast.makeText(activity_reg.this, "用户名已被其他人使用", Toast.LENGTH_SHORT).show();
                    info2="";
                }else if(info2.contains("超时")){
                    Toast.makeText(activity_reg.this, "服务器链接超时", Toast.LENGTH_SHORT).show();
                    info2="";
                }
            }
        }
    }
    public class MyThread1 implements Runnable {
        @Override
        public void run() {
            info2= WebService2.executeHttpGet(name.getText().toString().trim(), pass.getText().toString().trim());
            if(info2.contains("success"))
                regpd=true;
            else
                regpd=false;
        }
    }
    private boolean atleastA(String n) {
        for (int i=0;i<n.length();i++){
            if(n.charAt(i)>='A'&&n.charAt(i)<='Z')
                return true;
        }
        return  false;
    }
}
