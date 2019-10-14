package com.newland.intelligentdaysystem.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newland.intelligentdaysystem.Constant;
import com.newland.intelligentdaysystem.IntelligentDaySystemApplication;
import com.newland.intelligentdaysystem.R;
import com.newland.intelligentdaysystem.base.BaseActivity;
import com.newland.intelligentdaysystem.bean.DeviceInfo;
import com.newland.intelligentdaysystem.utils.DataCache;
import com.newland.intelligentdaysystem.utils.LogUtil;
import com.newland.intelligentdaysystem.utils.SPHelper;
import com.newland.intelligentdaysystem.view.CircleSeekBarX;
import com.newland.intelligentdaysystem.view.CircleSeekBarY;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.newland.nle_sdk.responseEntity.base.BaseResponseEntity;
import cn.com.newland.nle_sdk.util.NetWorkBusiness;
import cn.com.newland.nle_sdk.util.Tools;

public class Job2Activity extends BaseActivity {

    private static String TAG = "MainActivity";

    private TextView mAngleXText, mAngleYText, mPostResultTv;
    private CircleSeekBarX mCircleSeekBarX;
    private CircleSeekBarY mCircleSeekBarY;
    private TextView mLightValueText;
    private TextView mLampStateText;
    private ImageView mLampStateImageView, mLampControlImageView;

    private static final int GET_BOX_STATUS = 101;
    private static final int GET_BOX_STATUS_DELAY = 10000;
    private String mDeviceId;
    private SPHelper spHelper;
    private NetWorkBusiness mNetWorkBusiness;
    private double mAngleXMin = 0;
    private double mAngleXMax = 0;
    private double mAngleYMin = 0;
    private double mAngleYMax = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET_BOX_STATUS:
                    querySensorStatus();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spHelper = SPHelper.getInstant(getApplicationContext());
        mDeviceId = spHelper.getStringFromSP(getApplicationContext(), Constant.DEVICE_ID);
        mNetWorkBusiness = new NetWorkBusiness(DataCache.getAccessToken(getApplicationContext()), DataCache.getBaseUrl(getApplicationContext()));
        initView();
        initEvent();
        getAngleValueRegion();
        querySensorStatus();
    }

    private void initView() {
        initHeadView();
        setHeadVisable(true);
        initLeftTitleView("返回");
        initTitleView(this.getString(R.string.app_title));
        setRithtTitleViewVisable(false);

        mPostResultTv = (TextView) findViewById(R.id.postResult);
        mAngleXText = (TextView) findViewById(R.id.angleX_value);
        mAngleYText = (TextView) findViewById(R.id.angleY_value);
        mCircleSeekBarX = (CircleSeekBarX) findViewById(R.id.angleX_seekbar);
        mCircleSeekBarY = (CircleSeekBarY) findViewById(R.id.angleY_seekbar);
        mLightValueText = (TextView) findViewById(R.id.light_value_text);
        mLampStateText = (TextView) findViewById(R.id.lamp_state_text);
        mLampStateImageView = (ImageView) findViewById(R.id.lamp_state_imageView);
        mLampControlImageView = (ImageView) findViewById(R.id.lamp_control_imageView);

        mLampStateImageView.setBackgroundResource(R.mipmap.icon_lamp_off);
        mLampControlImageView.setBackgroundResource(R.drawable.btn_off);
        mLampControlImageView.setTag(false);

    }

    private void initEvent() {
        mCircleSeekBarX.setOnSeekBarChangeListener(new CircleSeekBarX.OnSeekBarChangeListener() {
            @Override
            public void onChanged(CircleSeekBarX seekbar, int curValue) {
                mAngleXText.setText(curValue + "°");
            }

            @Override
            public void onChangedFinish(CircleSeekBarX seekbar, final int curValue) {
                controlAngleX();
            }
        });

        mCircleSeekBarX.setCurProcess(0);

        mCircleSeekBarY.setOnSeekBarChangeListener(new CircleSeekBarY.OnSeekBarChangeListener() {
            @Override
            public void onChanged(CircleSeekBarY seekbar, int curValue) {
                mAngleYText.setText(curValue + "°");
            }

            @Override
            public void onChangedFinish(CircleSeekBarY seekbar, final int curValue) {
                controlAngleY();
            }
        });

        mCircleSeekBarY.setCurProcess(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void querySensorStatus() {
        LogUtil.d(TAG, "querySensorStatus");
        final Gson gson = new Gson();
        //查询单个传感器的最新状态接口
        /* *
         * param String deviceId：设备ID
         * param String apiTag：API标签
         * param Callback<BaseResponseEntity> callback回调对象
         * */
        mNetWorkBusiness.getSensor(mDeviceId, DeviceInfo.apiTagGetBrightness, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                    JSONObject resultObj = (JSONObject) jsonObject.get("ResultObj");
                    Object object = resultObj.get("Value");
                    if (null == object || object.equals("")) {
                        LogUtil.d(TAG, "Value is null, return");
                        return;
                    }
                    double value = (double) resultObj.get("Value");
                    LogUtil.d(TAG, "get Brightness value:" + value);
                    displayBrightness(Integer.parseInt(new java.text.DecimalFormat("0").format(value)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (baseResponseEntity != null) {
                    Tools.printJson(mPostResultTv, gson.toJson(baseResponseEntity));
                } else {
                    mPostResultTv.setText("请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                mPostResultTv.setText("请求出错 : \n" + t.getMessage());
            }
        });

        mNetWorkBusiness.getSensor(mDeviceId, DeviceInfo.apiTagGetSteeringAngleX, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                    JSONObject resultObj = (JSONObject) jsonObject.get("ResultObj");
                    Object object = resultObj.get("Value");
                    if (null == object || object.equals("")) {
                        LogUtil.d(TAG, "Value is null, return");
                        return;
                    }
                    double value = (double) resultObj.get("Value");
                    LogUtil.d(TAG, "get AngleX value:" + value);
                    displayAngleXText(value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (baseResponseEntity != null) {
                    Tools.printJson(mPostResultTv, gson.toJson(baseResponseEntity));
                } else {
                    mPostResultTv.setText("请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                mPostResultTv.setText("请求出错 : \n" + t.getMessage());
            }
        });

        mNetWorkBusiness.getSensor(mDeviceId, DeviceInfo.apiTagGetSteeringAngleY, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                    JSONObject resultObj = (JSONObject) jsonObject.get("ResultObj");
                    Object object = resultObj.get("Value");
                    if (null == object || object.equals("")) {
                        LogUtil.d(TAG, "Value is null, return");
                        return;
                    }
                    double value = (double) resultObj.get("Value");
                    LogUtil.d(TAG, "get AngleY value:" + value);
                    displayAngleYText(value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (baseResponseEntity != null) {
                    Tools.printJson(mPostResultTv, gson.toJson(baseResponseEntity));
                } else {
                    mPostResultTv.setText("请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                mPostResultTv.setText("请求出错 : \n" + t.getMessage());
            }
        });

        mNetWorkBusiness.getSensor(mDeviceId, DeviceInfo.apiTagGetLampState, new retrofit2.Callback<BaseResponseEntity>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull retrofit2.Response<BaseResponseEntity> response) {
                BaseResponseEntity baseResponseEntity = response.body();
                LogUtil.d(TAG, "get lamp state, response message: " + gson.toJson(baseResponseEntity));
                try {
                    JSONObject jsonObject = new JSONObject(gson.toJson(baseResponseEntity));
                    JSONObject resultObj = (JSONObject) jsonObject.get("ResultObj");
                    Object object = resultObj.get("Value");
                    if (null == object || object.equals("")) {
                        LogUtil.d(TAG, "Value is null, return");
                        return;
                    }
                    double value = (double) resultObj.get("Value");
                    LogUtil.d(TAG, "get lamp state value:" + value);
                    displayLampStatus(Integer.valueOf((int) value));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (baseResponseEntity != null) {
                    Tools.printJson(mPostResultTv, gson.toJson(baseResponseEntity));
                } else {
                    mPostResultTv.setText("请求出错 : 请求参数不合法或者服务出错");
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<BaseResponseEntity> call, @NonNull Throwable t) {
                mPostResultTv.setText("请求出错 : \n" + t.getMessage());
            }
        });

        mHandler.sendEmptyMessageDelayed(GET_BOX_STATUS, GET_BOX_STATUS_DELAY);
    }

    private void displayBrightness(int value) {
        if (null != mLightValueText) {
            mLightValueText.setText(value + "lx");
        }
    }

    private void displayLampStatus(int control) {
        if (control == Constant.BOX_STATUS_CLOSE) {
            displayLampStatusClose();
        } else if (control == Constant.BOX_STATUS_OPEN) {
            displayLampStatusOpen();
        }
    }

    private void displayLampStatusOpen() {
        mLampControlImageView.setBackgroundResource(R.drawable.btn_on);
        mLampStateImageView.setBackgroundResource(R.mipmap.icon_lamp_on);
        mLampControlImageView.setTag(true);
        mLampStateText.setText(R.string.lamp_open_state);
    }

    private void displayLampStatusClose() {
        mLampControlImageView.setBackgroundResource(R.drawable.btn_off);
        mLampStateImageView.setBackgroundResource(R.mipmap.icon_lamp_off);
        mLampControlImageView.setTag(false);
        mLampStateText.setText(R.string.lamp_close_state);
    }

    private void getAngleValueRegion() {
        mAngleXMin = Double.valueOf(spHelper.getStringFromSPDef(IntelligentDaySystemApplication.getInstance(), Constant.ANGLEX_MIN, Constant.ANGLEX_MIN_DEFAULT_VALUE));
        mAngleXMax = Double.valueOf(spHelper.getStringFromSPDef(IntelligentDaySystemApplication.getInstance(), Constant.ANGLEX_MAX, Constant.ANGLEX_MAX_DEFAULT_VALUE));
        mAngleYMin = Double.valueOf(spHelper.getStringFromSPDef(IntelligentDaySystemApplication.getInstance(), Constant.ANGLEY_MIN, Constant.ANGLEY_MIN_DEFAULT_VALUE));
        mAngleYMax = Double.valueOf(spHelper.getStringFromSPDef(IntelligentDaySystemApplication.getInstance(), Constant.ANGLEY_MAX, Constant.ANGLEY_MAX_DEFAULT_VALUE));
    }
    private void displayAngleXText(double value) {
        //控制舵机旋转的最小角度和最大角度
        if (value >= mAngleXMax) {
            value = mAngleXMax;
        } else if (value <= mAngleXMin) {
            value = mAngleXMin;
        }
        int angle = Integer.parseInt(new java.text.DecimalFormat("0").format(value));
        mCircleSeekBarX.setCurProcess(angle);
        mAngleXText.setText(angle + "°");
    }

    private void displayAngleYText(double value) {
        //控制舵机旋转的最小角度和最大角度
        if (value >= mAngleYMax) {
            value = mAngleYMax;
        } else if (value <= mAngleYMin) {
            value = mAngleYMin;
        }
        int angle = Integer.parseInt(new java.text.DecimalFormat("0").format(value));
        mCircleSeekBarY.setCurProcess(angle);
        mAngleYText.setText(angle + "°");
    }

    //Complete development based on job description
    private void controlLampSwitch(){
    }

    private void controlAngleX() {
    }

    private void controlAngleY(){
    }
}
