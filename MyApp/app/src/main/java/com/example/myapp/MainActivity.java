package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.Wedserver.WedService;

public class MainActivity extends AppCompatActivity {
    private EditText username,password;
    private Button login,register;
    private static boolean logpd=false;
    private static String info="";

    private void init(){
        username=findViewById(R.id.username_log);
        password=findViewById(R.id.password_log);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
       // StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"登录中...",Toast.LENGTH_LONG).show();
                dologin();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"执行注册操作",Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this,activity_reg.class));
                finish();
            }
        });
    }

    private void dologin() {
        String user=username.getText().toString().trim();
        String pass=password.getText().toString().trim();
        if(user.equals("")||pass.equals("")){
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
        }else {
            Thread t2=new Thread(new MyThread());
            t2.start();
            try {
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(info.contains("success")){
                Intent intent=new Intent(MainActivity.this,Mainwelcome.class);
                intent.putExtra("username",user);
                startActivity(intent);
            }else if(info.contains("userfailed")){
                Toast.makeText(MainActivity.this, "无该用户信息", Toast.LENGTH_SHORT).show();
                info="";
            }else if(info.contains("超时")){
                Toast.makeText(MainActivity.this, "链接服务器超时", Toast.LENGTH_SHORT).show();
                info = "";
            }else {
                Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                info = "";
            }
            Log.e("error",info);
        }
    }

    public class MyThread implements Runnable{
        @Override
        public void run() {
            info=WedService.executeHttpGet(username.getText().toString().trim(),password.getText().toString().trim());
        }
    }
}
