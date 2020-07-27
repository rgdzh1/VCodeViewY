package com.yey.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yey.demo.databinding.ActivityMainBinding;
import com.yey.vcodevy.IVCodeBack;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getName();
    private ActivityMainBinding dataBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = (ActivityMainBinding) DataBindingUtil.setContentView(this, R.layout.activity_main);
        dataBinding.setLifecycleOwner(this);
        initLister();
    }

    private void initLister() {
        dataBinding.vcv.setInputCallback(new IVCodeBack() {
            @Override
            public void inputComplete(String contentComplete) {
                Log.e(TAG, contentComplete);
            }

            @Override
            public void inputing(String contentInputing, int index) {
                Log.e(TAG, contentInputing);
                Log.e(TAG, "当前输入索引 " + index);
            }
        });
        dataBinding.clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBinding.vcv.clearAllContent();
            }
        });
        dataBinding.show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBinding.vcv.changeModel();
            }
        });
        dataBinding.fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataBinding.vcv.fillAllContent("12345");
            }
        });
        dataBinding.btnSoftekeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SofteKeyboardActivity.class);
                startActivity(intent);
            }
        });
    }
}
