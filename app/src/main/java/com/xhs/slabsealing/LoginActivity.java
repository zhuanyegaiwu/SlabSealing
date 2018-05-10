package com.xhs.slabsealing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.xhs.slabsealing.constant.Constant;
import com.xhs.slabsealing.utils.SPUtils;

/**
 * 作者: 布鲁斯.李 on 2017/12/26 15 17
 * 邮箱: lzp_lizhanpeng@163.com
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_ip;
    private Button btn_login;
    private ImageView iv_clean_ip;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String ip = (String) SPUtils.getParam(this, Constant.IP, "");
        if (!ip.equals("")){
            startActivity(new Intent(this,MainActivity.class));
            finish();
            return;
        }
        setContentView(R.layout.activity_login);
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    /**
     * 初始化view
     */
    private void initView() {
        et_ip = (EditText) findViewById(R.id.et_ip);
        et_ip.clearFocus();
        iv_clean_ip = (ImageView) findViewById(R.id.iv_clean_ip);
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_clean_ip.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_clean_ip:
                et_ip.setHint("");
                et_ip.setText("");
                break;
            case R.id.btn_login:
                String ip = et_ip.getText().toString().trim();
                if (ip.equals("")){
                    Toast.makeText(this,"IP不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                BaseApp.getInstance().ip=ip;
                startActivity(new Intent(this,MainActivity.class));
                finish();
                break;
        }
    }
}
