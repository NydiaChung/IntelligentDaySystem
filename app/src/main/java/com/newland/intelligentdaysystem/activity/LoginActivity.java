package com.newland.intelligentdaysystem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.newland.intelligentdaysystem.Constant;
import com.newland.intelligentdaysystem.R;
import com.newland.intelligentdaysystem.utils.DataCache;
import com.newland.intelligentdaysystem.utils.LogUtil;
import com.newland.intelligentdaysystem.utils.SPHelper;
import com.newland.intelligentdaysystem.utils.Utils;

import cn.com.newland.nle_sdk.requestEntity.SignIn;
import cn.com.newland.nle_sdk.responseEntity.User;
import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG = "LoginActivity";
    private EditText etUserName;
    private EditText etPwd;
    private ImageView mShowpswImageView;
    private Boolean mShowPassword = false;

    private SPHelper spHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spHelper = SPHelper.getInstant(getApplicationContext());
        initView();
        initViewData();
        registerListener();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        if (menuId == R.id.settingsMenu) {
            startActivityForResult(new Intent(getApplicationContext(), SettingActivity.class), 1);
        } else if (menuId == R.id.aboutMenu) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        etUserName = findViewById(R.id.userName);
        etPwd = findViewById(R.id.pwd);
        mShowpswImageView = (ImageView) findViewById(R.id.showpsw_imageView);
    }

    protected void initViewData() {
        etUserName.setText(DataCache.getUserName(getApplicationContext()));
        etPwd.setText(DataCache.getPwd(getApplicationContext()));
    }

    protected void registerListener() {
        findViewById(R.id.signIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isFastDoubleClick()) {
                    return;
                }
                signIn();
            }
        });
        mShowpswImageView.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
        }
    }

    private void signIn() {
        String platformAddress = spHelper.getStringFromSP(getApplicationContext(), Constant.SETTING_PLATFORM_ADDRESS);
        String port = spHelper.getStringFromSP(getApplicationContext(), Constant.SETTING_PORT);

        final String userName = etUserName.getText().toString();
        final String pwd = etPwd.getText().toString();
        if (TextUtils.isEmpty(platformAddress) || TextUtils.isEmpty(port)) {
            Toast.makeText(getApplicationContext(), "请设置云平台信息", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)) {
            Toast.makeText(getApplicationContext(), "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //构建SDK对象：负责调用云平台接口进行通信
        NetWorkBusiness netWorkBusiness = null;
        try {
            netWorkBusiness = new NetWorkBusiness( "",DataCache.getBaseUrl(getApplicationContext()));
        } catch (IllegalArgumentException e) {
            LogUtil.d(TAG, "illegal Url: " + e.getMessage());
            Toast.makeText(LoginActivity.this, "Url格式错误,请确认", Toast.LENGTH_SHORT).show();
            return;
        }

        LogUtil.d(TAG, "BaseUrl: " + DataCache.getBaseUrl(getApplicationContext()));
        //进行登录请求操作
        netWorkBusiness.signIn(new SignIn(userName, pwd), new Callback<BaseResponseEntity<User>>() {
            //登录成功返回处理
            @Override
            public void onResponse(@NonNull Call<BaseResponseEntity<User>> call, @NonNull Response<BaseResponseEntity<User>> response) {
                final Gson gson = new Gson();
                //通过返回对象-response，获取返回对象BaseResponseEntity（包含请求的返回信息内容）
                BaseResponseEntity<User> baseResponseEntity = response.body();
                LogUtil.d(TAG, "signIn, baseResponseEntity: " + gson.toJson(baseResponseEntity));
                if (baseResponseEntity != null) {
                    if (baseResponseEntity.getStatus() == 0) {
                        DataCache.updateUserName(getApplicationContext(), userName);
                        DataCache.updatePwd(getApplicationContext(), pwd);
                        //获取Token的状态码及值（Token的状态码非0时，表示异常，弹框提示错误）
                        String accessTokenErrCode = baseResponseEntity.getResultObj().getAccessTokenErrCode();
                        String accessToken = baseResponseEntity.getResultObj().getAccessToken();
                        if (Integer.valueOf(accessTokenErrCode) != 0) {
                            Toast.makeText(LoginActivity.this, accessToken, Toast.LENGTH_LONG).show();
                            return;
                        }
                        //Token值正常，并保存在本地
                        DataCache.updateAccessToken(getApplicationContext(), accessToken);
                        LogUtil.d(TAG, "signIn, accessToken: " + accessToken);
                        Intent intent = new Intent(LoginActivity.this, Job2Activity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userBaseResponseEntity", baseResponseEntity);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, baseResponseEntity.getMsg(), Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this, "请求地址出错", Toast.LENGTH_SHORT).show();
                }
            }

            //登录失败返回处理
            @Override
            public void onFailure(@NonNull Call<BaseResponseEntity<User>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showpsw_imageView:
                if (mShowPassword) {
                    mShowpswImageView.setBackgroundResource(R.mipmap.ico_eye_off);
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().toString().length());
                    mShowPassword = !mShowPassword;
                } else {
                    mShowpswImageView.setBackgroundResource(R.mipmap.ico_eye_on);
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().toString().length());
                    mShowPassword = !mShowPassword;
                }
        }
    }
}
