package com.example.gank.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gank.R;
import com.example.gank.base.BaseActivity;
import com.example.gank.bean.LoginBean;
import com.example.gank.mvp.LoginPresenter;
import com.example.gank.mvp.LoginViewInterface;
import com.example.gank.util.HttpUtil;
import com.google.gson.Gson;


import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginViewInterface {

    private EditText counterLogin;
    private EditText passwordLogin;
    private TextView textView;
    private CheckBox checkBox;
    private Button login;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inits();

        /*
        * 用preference进行保存用户数据，并在保存密码后，展示出来
        * */
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = preferences.getBoolean("remember_password",false);
        if (isRemember) {
            String account = preferences.getString("account","");
            String password = preferences.getString("password","");
            counterLogin.setText(account);
            passwordLogin.setText(password);
            checkBox.setChecked(true);
        }

        /*
         * editView的回车确认监听
         * */
        passwordLogin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                                && event.getAction() == KeyEvent.ACTION_DOWN)) {
                    // 先隐藏键盘
                    ((InputMethodManager) passwordLogin.getContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(passwordLogin.getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);

                    checkLogin();
                }
                return true;
            }
        });

        /*
        * 登录按钮的监听事件
        * */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor = preferences.edit();
                if (checkBox.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("account",counterLogin.getText().toString());
                    editor.putString("password",passwordLogin.getText().toString());
                }else {
                    editor.clear();
                }
                editor.apply();

                checkLogin();

            }
        });

        /*
        * 注册账号的Activity跳转
        * */
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * 登录按钮点击进行账户密码验证
     * 用Retrofit进行网络请求
     */
    private void checkLogin() {
        presenter.checkLogin(counterLogin.getText().toString(),passwordLogin.getText().toString());
    }


    @Override
    public LoginPresenter getPresenter() {
        return new LoginPresenter();
    }

    @Override
    public int setStatusBarColor() {
        return 0;
    }

    /*
    * 对返回的数据进行判断，并做相应的处理
    * */
    private void showResult(final int code){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (code != -1){
                    Toast.makeText(LoginActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(LoginActivity.this,CenterActivity.class);
                    startActivity(intent1);
                    (LoginActivity.this).finish();
                }else {
                    Toast.makeText(LoginActivity.this,"账号或密码错误！", Toast.LENGTH_SHORT).show();
                    Log.d("d",code + "error");
                }
            }
        });
    }

    /*
    * 初始化控件
    * */
    private void inits(){
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        passwordLogin = (EditText)findViewById(R.id.password_login);
        checkBox = (CheckBox)findViewById(R.id.text_check);
        counterLogin = (EditText)findViewById(R.id.counter_login);
        login = (Button)findViewById(R.id.login);
        textView = (TextView)findViewById(R.id.intent_login);
    }

/*
* Login的V层方法，用于展示数据
* */
    @Override
    public void loginShow(LoginBean loginBean) {

        int code = loginBean.getErrorCode();
        if (code != -1){
            Toast.makeText(LoginActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(LoginActivity.this,CenterActivity.class);
            startActivity(intent1);
            (LoginActivity.this).finish();
        }else {
            Toast.makeText(LoginActivity.this,"账号或密码错误！", Toast.LENGTH_SHORT).show();
            Log.d("d",code + "error");
        }
    }

}
