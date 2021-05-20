package com.example.gank.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.gank.R;
import com.example.gank.base.BaseActivity;
import com.example.gank.bean.LoginBean;
import com.example.gank.mvp.RegisterPresenter;
import com.example.gank.mvp.RegisterViewInterface;
import com.example.gank.util.HttpUtil;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterViewInterface {

    private EditText counterText;
    private EditText passwordText,passwordText2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化
        inits();

        /*
        * 确定按钮的监听事件
        * */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Retrofit发送网络请求
                presenter.checkRegister(counterText.getText().toString(),
                        passwordText.getText().toString(),
                        passwordText2.getText().toString());

            }
        });
    }

    /*
    * 初始化控件
    * */
    private void inits(){
        passwordText = (EditText)findViewById(R.id.password_register);
        passwordText2 = findViewById(R.id.password_register2);
        counterText = (EditText)findViewById(R.id.counter_register);
        button = (Button)findViewById(R.id.regsiter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*
    * new一个P层对象
    * */
    @Override
    public RegisterPresenter getPresenter() {
        return new RegisterPresenter();
    }

    @Override
    public int setStatusBarColor() {
        return 0;
    }

    /*
    * V层的方法，用于展示数据
    * */

    @Override
    public void registerShow(LoginBean loginBean) {
        int code = loginBean.getErrorCode();
        String message = loginBean.getErrorMsg();
        Log.d("e",code+message);
        if (code != -1){
            Toast.makeText(RegisterActivity.this,"注册成功，请登录！", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
            (RegisterActivity.this).finish();
        }else{
            Toast.makeText(RegisterActivity.this,message, Toast.LENGTH_SHORT).show();
        }
    }

}
