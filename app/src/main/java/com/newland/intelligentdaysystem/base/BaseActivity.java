package com.newland.intelligentdaysystem.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.newland.intelligentdaysystem.R;

public abstract class BaseActivity extends FragmentActivity {
    private TextView tv_mTitle; // 标题
    private TextView tv_mLeft;//回退
    private TextView tv_mRight;//设置
    private View view;
    private Activity activity;

    protected void onCreate(Bundle savedInstanceState) {
        ////去掉标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        activity = this;
        initHeadView();
    }

    /**
     * 加载头部布局
     */
    protected void initHeadView() {
        ViewGroup viewGroup = (ViewGroup) this.findViewById(android.R.id.content);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (int) (getResources().getDimension(R.dimen.activity_head_padding)));
        view = getLayoutInflater().inflate(R.layout.include_main_head, null);
        tv_mTitle = (TextView) view.findViewById(R.id.tv_title);

        tv_mLeft = (TextView) view.findViewById(R.id.tv_back);
        tv_mLeft.setVisibility(View.VISIBLE);

        tv_mRight = (TextView) view.findViewById(R.id.tv_right);
        tv_mRight.setVisibility(View.GONE);

        tv_mRight.setOnClickListener(listener);
        tv_mLeft.setOnClickListener(listener);
        view.setLayoutParams(lp);
        viewGroup.addView(view);
        //默认显示头部
        view.setVisibility(View.GONE);
    }

    public void setHeadVisable(boolean isVisable) {
        if (isVisable) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void initLeftTitleView(String title) {
        tv_mLeft.setText(title);
    }

    public void setLeftTitleView(boolean isVisable) {
        if (isVisable) {
            tv_mLeft.setVisibility(View.VISIBLE);
        } else {
            tv_mLeft.setVisibility(View.GONE);
        }
    }


    public void initTitleView(String title) {
        tv_mTitle.setText(title);
    }

    public void setTitleViewVisable(boolean isVisable) {
        if (isVisable) {
            tv_mTitle.setVisibility(View.VISIBLE);
        } else {
            tv_mTitle.setVisibility(View.GONE);
        }
    }

    public void initRithtTitleView(String title) {
        tv_mRight.setText(title);
    }

    public void setRithtTitleViewVisable(boolean isVisable) {
        if (isVisable) {
            tv_mRight.setVisibility(View.VISIBLE);
        } else {
            tv_mRight.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_back:
                    setLeftBtn();
                    break;
                case R.id.tv_right:
                    setRightBtn();
                    break;
                default:
                    break;
            }
        }
    };


    //左上角按钮
    public void setLeftBtn() {
        activity.finish();
    }

    //右上角按钮
    protected void setRightBtn() {
    }

}
