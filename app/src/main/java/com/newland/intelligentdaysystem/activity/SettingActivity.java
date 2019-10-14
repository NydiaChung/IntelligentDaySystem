package com.newland.intelligentdaysystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.newland.intelligentdaysystem.Constant;
import com.newland.intelligentdaysystem.R;
import com.newland.intelligentdaysystem.base.BaseActivity;
import com.newland.intelligentdaysystem.utils.DataCache;
import com.newland.intelligentdaysystem.utils.SPHelper;


public class SettingActivity extends BaseActivity {
    private static String TAG = "SettingActivity";
    private TextView tvGateWayTag;
    private TextView tvPlatformAddress;
    private TextView tvPort;
    private TextView mDeviceIdText;
    private EditText mAngleXMin, mAngleXMax, mAngleYMin, mAngleYMax;

    private SPHelper spHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        spHelper = SPHelper.getInstant(getApplicationContext());
        initView();
        initViewData();
        registerListener();
    }

    private void initView() {
        initHeadView();
        setHeadVisable(true);
        initLeftTitleView("设置");
        setTitleViewVisable(false);
        setRithtTitleViewVisable(false);

        tvGateWayTag = findViewById(R.id.Tag);
        tvPlatformAddress = findViewById(R.id.platformAddress);
        tvPort = findViewById(R.id.port);
        mDeviceIdText = findViewById(R.id.device_id_text);

        mAngleXMin = (EditText) findViewById(R.id.angleX_min_text);
        mAngleXMax = (EditText) findViewById(R.id.angleX_max_text);
        mAngleYMin = (EditText) findViewById(R.id.angleY_min_text);
        mAngleYMax = (EditText) findViewById(R.id.angleY_max_text);
    }

    protected void initViewData() {
        tvGateWayTag.setText(spHelper.getStringFromSP(getApplicationContext(), Constant.SETTING_GATEWAY_TAG));
        tvPlatformAddress.setText(spHelper.getStringFromSPDef(getApplicationContext(), Constant.SETTING_PLATFORM_ADDRESS, Constant.IP_DEFAULT_VALUE));
        tvPort.setText(spHelper.getStringFromSPDef(getApplicationContext(), Constant.SETTING_PORT, Constant.PORT_DEFAULT_VALUE));
        mDeviceIdText.setText(spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID));

        mAngleXMin.setText(spHelper.getStringFromSPDef(getApplicationContext(), Constant.ANGLEX_MIN, Constant.ANGLEX_MIN_DEFAULT_VALUE));
        mAngleXMax.setText(spHelper.getStringFromSPDef(getApplicationContext(), Constant.ANGLEX_MAX, Constant.ANGLEX_MAX_DEFAULT_VALUE));
        mAngleYMin.setText(spHelper.getStringFromSPDef(getApplicationContext(), Constant.ANGLEY_MIN, Constant.ANGLEY_MIN_DEFAULT_VALUE));
        mAngleYMax.setText(spHelper.getStringFromSPDef(getApplicationContext(), Constant.ANGLEY_MAX, Constant.ANGLEY_MAX_DEFAULT_VALUE));
    }

    protected void registerListener() {
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetting();
            }
        });

        findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void saveSetting() {
        String platformAddress = tvPlatformAddress.getText().toString().trim();
        String port = tvPort.getText().toString().trim();
        String gateWayTag = tvGateWayTag.getText().toString().trim();
        String devicdId = mDeviceIdText.getText().toString().trim();

        String angleXMin = mAngleXMin.getText().toString().trim();
        String angleXMax = mAngleXMax.getText().toString().trim();
        String angleYMin = mAngleYMin.getText().toString().trim();
        String angleYMax = mAngleYMax.getText().toString().trim();
        if ("".equals(platformAddress)) {
            Toast.makeText(getApplicationContext(), "请填写IP地址", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(port)) {
            Toast.makeText(getApplicationContext(), "请填写平台端口", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(devicdId)) {
            Toast.makeText(getApplicationContext(), "请填写设备ID", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(angleXMin)) {
            Toast.makeText(getApplicationContext(), "请填写舵机X最小值", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(angleXMax)) {
            Toast.makeText(getApplicationContext(), "请填写舵机X最大值", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(angleYMin)) {
            Toast.makeText(getApplicationContext(), "请填写舵机Y最小值", Toast.LENGTH_LONG).show();
            return;
        }
        if ("".equals(angleYMax)) {
            Toast.makeText(getApplicationContext(), "请填写舵机Y最大值", Toast.LENGTH_LONG).show();
            return;
        }
        if (Integer.valueOf(angleXMin) >= Integer.valueOf(angleXMax)) {
            Toast.makeText(getApplicationContext(), "舵机X最小值大于最大值,请重新设置", Toast.LENGTH_LONG).show();
            return;
        }
        if (Integer.valueOf(angleYMin) >= Integer.valueOf(angleYMax)) {
            Toast.makeText(getApplicationContext(), "舵机Y最小值大于最大值,请重新设置", Toast.LENGTH_LONG).show();
            return;
        }
        if (!TextUtils.isEmpty(platformAddress) && !TextUtils.isEmpty(port)) {
            DataCache.updateBaseUrl(getApplicationContext(), "http://" + platformAddress + ":" + port + "/");
        }
        DataCache.updateGateWayTag(getApplicationContext(), gateWayTag);

        spHelper.putData2SP(getApplicationContext(), Constant.SETTING_PLATFORM_ADDRESS, platformAddress);
        spHelper.putData2SP(getApplicationContext(), Constant.SETTING_PORT, port);
        spHelper.putData2SP(getApplicationContext(), Constant.DEVICE_ID, devicdId);

        spHelper.putData2SP(getApplicationContext(), Constant.ANGLEX_MIN, angleXMin);
        spHelper.putData2SP(getApplicationContext(), Constant.ANGLEX_MAX, angleXMax);
        spHelper.putData2SP(getApplicationContext(), Constant.ANGLEY_MIN, angleYMin);
        spHelper.putData2SP(getApplicationContext(), Constant.ANGLEY_MAX, angleYMax);
        Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        this.setResult(2);
        finish();
    }
}
