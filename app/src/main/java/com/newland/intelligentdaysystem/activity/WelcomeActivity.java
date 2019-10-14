package com.newland.intelligentdaysystem.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.newland.intelligentdaysystem.R;

public class WelcomeActivity extends Activity {
    private Button mStartBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        mStartBtn= (Button) findViewById(R.id.btn_start);
        init();
    }


    public void init() {
        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goHome();
                onDestroy();
            }
        });
    }

    public void goHome() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
